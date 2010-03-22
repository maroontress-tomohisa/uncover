package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.CommitSource;
import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Graph;
import java.sql.Connection;
import java.sql.SQLException;

/**
   ���ߥåȤν����򰷤��ޤ���
*/
public final class CommitDeal {
    /** �ǡ����١����Ȥ���³�Ǥ��� */
    private Connection con;

    /** ���ߥåȾ���Ǥ��� */
    private CommitSource source;

    /** �ؿ�ID��������륤�󥹥��󥹤Ǥ��� */
    private FunctionResolver functionResolver;

    /** �ؿ��ơ��֥�˹Ԥ��ɲä��륤�󥹥��󥹤Ǥ��� */
    private Adder<FunctionRow> functionRowAdder;

    /** ����եơ��֥�˹Ԥ��ɲä��륤�󥹥��󥹤Ǥ��� */
    private Adder<GraphRow> graphRowAdder;

    /** ����ե��ޥ�ơ��֥�˹Ԥ��ɲä��륤�󥹥��󥹤Ǥ��� */
    private Adder<GraphSummaryRow> graphSummaryRowAdder;

    /** ����ե֥�å��ơ��֥�˹Ԥ��ɲä��륤�󥹥��󥹤Ǥ��� */
    private Adder<GraphBlockRow> graphBlockRowAdder;

    /** ����ե������ơ��֥�˹Ԥ��ɲä��륤�󥹥��󥹤Ǥ��� */
    private Adder<GraphArcRow> graphArcRowAdder;

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

	functionResolver = new FunctionResolver(con);
	functionRowAdder = new QuerierFactory<FunctionRow>(
	    con, Table.FUNCTION, FunctionRow.class).createAdder();
	functionRowAdder.setRow(functionResolver.getFunctionRow());

	graphRowAdder = new QuerierFactory<GraphRow>(
	    con, Table.GRAPH, GraphRow.class).createAdder();
	graphRowAdder.setRow(new GraphRow());

	graphSummaryRowAdder = new QuerierFactory<GraphSummaryRow>(
	    con, Table.GRAPH_SUMMARY, GraphSummaryRow.class).createAdder();
	graphSummaryRowAdder.setRow(new GraphSummaryRow());

	graphBlockRowAdder = new QuerierFactory<GraphBlockRow>(
	    con, Table.GRAPH_BLOCK, GraphBlockRow.class).createAdder();
	graphBlockRowAdder.setRow(new GraphBlockRow());

	graphArcRowAdder = new QuerierFactory<GraphArcRow>(
	    con, Table.GRAPH_ARC, GraphArcRow.class).createAdder();
	graphArcRowAdder.setRow(new GraphArcRow());
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

	Iterable<FunctionGraph> allFunctionGraphs
	    = source.getAllFunctionGraphs();
	for (FunctionGraph functionGraph : allFunctionGraphs) {
	    Function function = functionGraph.getFunction();
	    String functionID = functionResolver.getFunctionID(
		function.getName(), function.getGCNOFile(), projectID);
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

	    Graph graph = functionGraph.getGraph();
	    Block[] allBlocks = graph.getAllBlocks();
	    for (Block block : allBlocks) {
		graphBlockRowAdder.getRow().set(graphID, block);
		graphBlockRowAdder.execute();
	    }
	    Arc[] allArcs = graph.getAllArcs();
	    for (Arc arc : allArcs) {
		graphArcRowAdder.getRow().set(graphID, arc);
		graphArcRowAdder.execute();
	    }
	}
    }
}
