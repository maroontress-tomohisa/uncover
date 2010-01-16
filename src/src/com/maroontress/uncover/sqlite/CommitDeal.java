package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.CommitSource;
import com.maroontress.uncover.Function;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   ���ߥåȤν����򰷤��ޤ���
*/
public final class CommitDeal {
    /** �ǡ����١����Ȥ���³�Ǥ��� */
    private Connection con;

    /** ���ߥåȾ���Ǥ��� */
    private CommitSource source;

    /** �ؿ��ơ��֥�ιԤ�������륤�󥹥��󥹤Ǥ��� */
    private Fetcher<FunctionRow> functionRowFetcher;

    /** �ؿ��ơ��֥�˹Ԥ��ɲä��륤�󥹥��󥹤Ǥ��� */
    private Adder<FunctionRow> functionRowAdder;

    /** ����եơ��֥�˹Ԥ��ɲä��륤�󥹥��󥹤Ǥ��� */
    private Adder<GraphRow> graphRowAdder;

    /** ����ե��ޥ�ơ��֥�˹Ԥ��ɲä��륤�󥹥��󥹤Ǥ��� */
    private Adder<GraphSummaryRow> graphSummaryRowAdder;

    /**
       ���󥹥��󥹤��������ޤ���

       @param con �ǡ����١����Ȥ���³
       @param source ���ߥåȾ���
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public CommitDeal(final Connection con, final CommitSource source)
	throws SQLException {
	this.con = con;
	this.source = source;

	FunctionRow functionRow = new FunctionRow();

	functionRowFetcher = new QuerierFactory<FunctionRow>(
	    con, Table.FUNCTION, FunctionRow.class).createFetcher("id");
	functionRowFetcher.setRow(functionRow);

	functionRowAdder = new QuerierFactory<FunctionRow>(
	    con, Table.FUNCTION, FunctionRow.class).createAdder();
	functionRowAdder.setRow(functionRow);

	graphRowAdder = new QuerierFactory<GraphRow>(
	    con, Table.GRAPH, GraphRow.class).createAdder();
	graphRowAdder.setRow(new GraphRow());

	graphSummaryRowAdder = new QuerierFactory<GraphSummaryRow>(
	    con, Table.GRAPH_SUMMARY, GraphSummaryRow.class).createAdder();
	graphSummaryRowAdder.setRow(new GraphSummaryRow());
    }

    /**
       �ץ�������ID��������ޤ���

       ���ꤷ��̾���Υץ������Ȥ�¸�ߤ����硢���Υץ�������ID��
       �֤��ޤ���¸�ߤ��ʤ����ϡ��ץ������Ȥ�ǡ����١����˿�����
       �ä��ơ����Υץ�������ID���֤��ޤ���

       @param name �ץ������Ȥ�̾��
       @return �ץ�������ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String getProjectID(final String name) throws SQLException {
	ProjectDeal projectDeal = new ProjectDeal(con);
	String projectID = projectDeal.queryID(name);
	if (projectID == null) {
	    Adder<ProjectRow> adder = new QuerierFactory<ProjectRow>(
		con, Table.PROJECT, ProjectRow.class).createAdder();
	    adder.setRow(new ProjectRow());
	    adder.getRow().set(name);
	    adder.execute();
	    projectID = adder.getGeneratedKey(1);

	    System.err.println("project " + name
			       + " not found, newly created");
	}
	return projectID;
    }

    /**
       ���ߥåȾ����ơ��֥����Ͽ���ޤ���

       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public void run() throws SQLException {
	String projectID = getProjectID(source.getProjectName());

	Adder<BuildRow> buildRowAdder = new QuerierFactory<BuildRow>(
	    con, Table.BUILD, BuildRow.class).createAdder();
	BuildRow buildRow = new BuildRow();
	buildRow.set(source.getRevision(), source.getTimestamp(),
		     source.getPlatform(), projectID);
	buildRowAdder.setRow(buildRow);
	buildRowAdder.execute();
	String buildID = buildRowAdder.getGeneratedKey(1);

	Iterable<Function> allFunctions = source.getAllFunctions();
	for (Function function : allFunctions) {
	    functionRowFetcher.getRow().set(function.getName(),
					    function.getGCNOFile(),
					    projectID);
	    ResultSet rs = functionRowFetcher.executeQuery();
	    String functionID = null;
	    int k;
	    for (k = 0; rs.next(); ++k) {
		functionID = rs.getString("id");
	    }
	    if (k > 1) {
		String s = String.format(
		    "projectID: %s; function %s (%s) found more than one.",
		    projectID, function.getName(), function.getGCNOFile());
		throw new TableInconsistencyException(s);
	    }
	    if (functionID == null) {
		functionRowAdder.execute();
		functionID = functionRowAdder.getGeneratedKey(1);
		System.err.println("function " + function.getName() + " ("
				   + function.getGCNOFile()
				   + ") not found, newly created");
	    }

	    graphRowAdder.getRow().set(functionID, buildID);
	    graphRowAdder.execute();
	    String graphID = graphRowAdder.getGeneratedKey(1);

	    graphSummaryRowAdder.getRow().set(graphID, function);
	    graphSummaryRowAdder.execute();
	}
    }
}
