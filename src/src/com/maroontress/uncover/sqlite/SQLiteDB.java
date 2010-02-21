package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Build;
import com.maroontress.uncover.CommitSource;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.Function;
import com.maroontress.uncover.Revision;
import com.maroontress.uncover.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
   SQLite����Ѥ����ǡ����١����μ����Ǥ���
*/
public final class SQLiteDB implements DB {
    /** JDBC��URL�Ǥ��� */
    private static final String URL = "jdbc:sqlite:";

    /** JDBC����³�Ǥ��� */
    private Connection con;

    /**
       ���󥹥ȥ饯���Ǥ���

       @param subname JDBC��URL�Υ��֥͡���
       @throws DBException �ǡ����١������˴ؤ��륨�顼��ȯ��������
       ���˥������ޤ���
    */
    public SQLiteDB(final String subname) throws DBException {
	try {
	    Class.forName("org.sqlite.JDBC");
	    con = DriverManager.getConnection(URL + subname);
	} catch (ClassNotFoundException e) {
	    throw new DBException("sqlite JDBC driver not found: "
				  + e.getMessage(), e);
	} catch (SQLException e) {
	    throw new DBException("can't get connection: "
				  + e.getMessage(), e);
	}
	try {
	    con.setAutoCommit(false);
	} catch (SQLException e) {
	    try {
		con.close();
	    } finally {
		throw new DBException("can't disable auto commit mode: "
				      + e.getMessage(), e);
	    }
	}
    }

    /** {@inheritDoc} */
    public void close() throws DBException {
	try {
	    if (con != null) {
		con.close();
		con = null;
	    }
	} catch (SQLException e) {
	    throw new DBException("failed to close connection: "
				  + e.getMessage(), e);
	}
    }

    /**
       �ǡ����١��������Хå����ޤ���
    */
    private void rollback() {
	try {
	    if (con == null || con.isClosed()) {
		return;
	    }
	    con.rollback();
	} catch (SQLException e) {
	    System.err.println("failed to rollback: " + e.getMessage());
	}
    }

    /** {@inheritDoc} */
    public void initialize() throws DBException {
	try {
	    new InitDeal(con).run();
	    con.commit();
	} catch (SQLException e) {
	    rollback();
	    throw new DBException("failed to initialize tables: "
				  + e.getMessage(), e);
	}
    }

    /** {@inheritDoc} */
    public void commit(final CommitSource source) throws DBException {
	try {
	    new CommitDeal(con, source).run();
	    con.commit();
	} catch (SQLException e) {
	    rollback();
	    throw new DBException("failed to commit: " + e.getMessage(), e);
	}
    }

    /**
       �ץ������Ȥ������������ޤ���

       @return �ץ������Ȥ�����
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String[] queryProjectNames() throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "SELECT name FROM " + Table.PROJECT + ";");
	ResultSet rs = s.executeQuery();
	ArrayList<String> list = new ArrayList<String>();
	while (rs.next()) {
	    String name = rs.getString("name");
	    list.add(name);
	}
	return list.toArray(new String[list.size()]);
    }

    /** {@inheritDoc} */
    public String[] getProjectNames() throws DBException {
	try {
	    return queryProjectNames();
	} catch (SQLException e) {
	    throw new DBException("failed to get projects: "
				  + e.getMessage(), e);
	}
    }

    /**
       ����ѥ���ѤߤΥ��ơ��ȥ��Ȥ˥ѥ�᡼�������ꤷ�ޤ���

       @param s ���ơ��ȥ���
       @param values �ѥ�᡼�����ͤ�����
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private void setParameter(final PreparedStatement s, final Object[] values)
	throws SQLException {
	int k = 0;
	for (Object v : values) {
	    ++k;
	    if (v instanceof String) {
		s.setString(k, (String) v);
	    } else if (v instanceof Integer) {
		s.setInt(k, (Integer) v);
	    } else {
		throw new IllegalArgumentException("unexpected type: "
						   + v.getClass().toString());
	    }
	}
    }

    /**
       �ӥ�ɤ������������ޤ���

       ���ꤷ����ӥ����ȥץ�������ID�˥ޥå�����ӥ�ɤ��������
       ���ޤ���

       @param revision ��ӥ����
       @param projectID �ץ�������ID
       @return �ӥ�ɤ�����
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private Build[] queryBuilds(final String revision, final String projectID)
	throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "SELECT * FROM " + Table.BUILD
	    + " WHERE revision = ? and projectID = ?;");
	setParameter(s, new Object[] {revision, projectID});
	ResultSet rs = s.executeQuery();
	ArrayList<Build> list = new ArrayList<Build>();
	Toolkit tk = Toolkit.getInstance();
	ResultSetBuildSource source = new ResultSetBuildSource();
	while (rs.next()) {
	    source.setResultSet(rs);
	    list.add(tk.createBuild(source));
	}
	return list.toArray(new Build[list.size()]);
    }

    /** {@inheritDoc} */
    public Build[] getBuilds(final String projectName,
			     final String revision) throws DBException {
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    String projectID = projectDeal.queryID(projectName);
	    if (projectID == null) {
		throw new DBException("project not found: " + projectName);
	    }
	    Build[] builds = queryBuilds(revision, projectID);
	    if (builds.length == 0) {
		throw new DBException("revision not found: " + revision);
	    }
	    return builds;
	} catch (SQLException e) {
	    throw new DBException("failed to get builds: "
				  + e.getMessage(), e);
	}
    }

    /**
       �ӥ�ɤ�������ޤ���

       @param id �ӥ��ID
       @param projectID �ץ�������ID
       @return �ӥ�ɤ�����
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private Build[] queryBuild(final String id,
			     final String projectID) throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "SELECT * FROM " + Table.BUILD
	    + " WHERE id = ? and projectID = ?;");
	setParameter(s, new Object[] {id, projectID});
	ResultSet rs = s.executeQuery();
	ArrayList<Build> list = new ArrayList<Build>();
	Toolkit tk = Toolkit.getInstance();
	ResultSetBuildSource source = new ResultSetBuildSource();
	while (rs.next()) {
	    source.setResultSet(rs);
	    list.add(tk.createBuild(source));
	}
	return list.toArray(new Build[list.size()]);
    }

    /** {@inheritDoc} */
    public Build getBuild(final String projectName,
			  final String id) throws DBException {
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    String projectID = projectDeal.queryID(projectName);
	    if (projectID == null) {
		throw new DBException("project not found: " + projectName);
	    }
	    Build[] builds = queryBuild(id, projectID);
	    if (builds.length == 0) {
		throw new DBException("not found: @" + id);
	    }
	    if (builds.length > 1) {
		throw new DBException("internal error");
	    }
	    return builds[0];
	} catch (SQLException e) {
	    throw new DBException("failed to get build: " + e.getMessage(), e);
	}
    }

    /**
       ���ꤷ���ӥ�ɤδؿ��Υꥹ�Ȥ�������ޤ���

       @param buildID �ӥ��ID
       @return �ؿ��Υꥹ��
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private List<Function> getFunctions(final String buildID)
	throws SQLException {
	String format = String.format(
	    // FieldArray.concatNames(ResultSetFunctionSource.class, ",")
	    // ��Ȥ�
	    "SELECT name, sourceFile, lineNumber,  gcnoFile, checkSum,"
	    + " complexity, executedBlocks, allBlocks, executedArcs, allArcs"
	    + " FROM %s g"
	    + " INNER JOIN %s f ON f.id = g.functionID"
	    + " INNER JOIN %s gs ON g.id = gs.graphID"
	    + " WHERE g.buildID = ?;",
	    Table.GRAPH, Table.FUNCTION, Table.GRAPH_SUMMARY);
	PreparedStatement s = con.prepareStatement(format);
	setParameter(s, new Object[] {buildID});
	ResultSet rs = s.executeQuery();
	List<Function> list = new ArrayList<Function>();
	Toolkit toolkit = Toolkit.getInstance();
	ResultSetFunctionSource source = new ResultSetFunctionSource();
	while (rs.next()) {
	    source.setResultSet(rs);
	    Function function = toolkit.createFunction(source);
	    list.add(function);
	}
	return list;
    }

    /** {@inheritDoc} */
    public Revision getRevision(final String id) throws DBException {
	try {
	    return new Revision(getFunctions(id));
	} catch (SQLException e) {
	    throw new DBException("failed to get revision: "
				  + e.getMessage(), e);
	}
    }

    /**
       ��ӥ����̾�������������ޤ���

       ���ꤷ���ץ�������ID�˥ޥå������ӥ����̾��������֤��ޤ���

       @param projectID �ץ�������ID
       @return �ӥ�ɤ�����
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String[] queryRevisionNames(final String projectID)
	throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "SELECT * FROM " + Table.BUILD
	    + " WHERE projectID = ?;");
	setParameter(s, new Object[] {projectID});
	ResultSet rs = s.executeQuery();
	ArrayList<String> list = new ArrayList<String>();
	Toolkit tk = Toolkit.getInstance();
	ResultSetBuildSource source = new ResultSetBuildSource();
	while (rs.next()) {
	    source.setResultSet(rs);
	    list.add(tk.createBuild(source).getRevision());
	}
	return list.toArray(new String[list.size()]);
    }

    /** {@inheritDoc} */
    public String[] getRevisionNames(final String projectName)
	throws DBException {
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    String projectID = projectDeal.queryID(projectName);
	    if (projectID == null) {
		throw new DBException("project not found: " + projectName);
	    }
	    return queryRevisionNames(projectID);
	} catch (SQLException e) {
	    throw new DBException("failed to get revision names: "
				  + e.getMessage(), e);
	}
    }

    /**
       �ӥ�ɥơ��֥�ιԤ��������塢�ƥơ��֥�ǻ��Ȥ���ʤ��ʤ��
       �������ޤ���

       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private void removeUnreferencedRows() throws SQLException {
	Statement s = con.createStatement();
	String sql;

	sql = String.format(
	    "DELETE FROM %s WHERE NOT EXISTS ("
	    + "SELECT * FROM %s b WHERE b.id = %s.buildID);",
	    Table.GRAPH, Table.BUILD, Table.GRAPH);
	s.executeUpdate(sql);

	sql = String.format(
	    "DELETE FROM %s WHERE NOT EXISTS ("
	    + "SELECT * FROM %s g WHERE g.id = %s.graphID);",
	    Table.GRAPH_SUMMARY, Table.GRAPH, Table.GRAPH_SUMMARY);
	s.executeUpdate(sql);

	sql = String.format(
	    "DELETE FROM %s WHERE NOT EXISTS"
	    + " (SELECT * FROM %s g WHERE g.functionID = %s.id);",
	    Table.FUNCTION, Table.GRAPH, Table.FUNCTION);
	s.executeUpdate(sql);
    }

    /**
       �ӥ��ID����ꤷ�ƥӥ�ɤ������ޤ���

       @param id �ӥ��ID
       @param projectID �ץ�������ID
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private void removeBuild(final String id,
			     final String projectID) throws SQLException {
	String format = String.format(
	    "DELETE FROM %s WHERE id = ? and projectID = ?;", Table.BUILD);
	PreparedStatement s = con.prepareStatement(format);
	setParameter(s, new Object[] {id, projectID});
	s.execute();
	removeUnreferencedRows();
    }

    /** {@inheritDoc} */
    public void deleteBuild(final String projectName,
			    final String id) throws DBException {
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    String projectID = projectDeal.queryID(projectName);
	    if (projectID == null) {
		throw new DBException("project not found: " + projectName);
	    }
	    removeBuild(id, projectID);
	    con.commit();
	} catch (SQLException e) {
	    rollback();
	    throw new DBException("failed to delete build: "
				  + e.getMessage(), e);
	}
    }

    /**
       ��ӥ�������ꤷ�ƥӥ�ɤ������ޤ���

       @param revision ��ӥ����
       @param projectID �ץ�������ID
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private void removeBuilds(final String revision,
			      final String projectID) throws SQLException {
	String format = String.format(
	    "DELETE FROM %s WHERE revision = ? and projectID = ?;",
	    Table.BUILD);
	PreparedStatement s = con.prepareStatement(format);
	setParameter(s, new Object[] {revision, projectID});
	s.execute();
	removeUnreferencedRows();
    }

    /** {@inheritDoc} */
    public void deleteBuilds(final String projectName,
			     final String revision) throws DBException {
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    String projectID = projectDeal.queryID(projectName);
	    if (projectID == null) {
		throw new DBException("project not found: " + projectName);
	    }
	    removeBuilds(revision, projectID);
	    con.commit();
	} catch (SQLException e) {
	    rollback();
	    throw new DBException("failed to delete builds: "
				  + e.getMessage(), e);
	}
    }

    /**
       �ץ������Ȥ������ޤ���

       @param projectID �ץ�������ID
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private void removeProject(final String projectID) throws SQLException {
	PreparedStatement s;
	String format;

	format = String.format("DELETE FROM %s WHERE id = ?;", Table.PROJECT);
	s = con.prepareStatement(format);
	setParameter(s, new Object[] {projectID});
	s.execute();

	format = String.format("DELETE FROM %s WHERE projectID = ?;",
			       Table.BUILD);
	s = con.prepareStatement(format);
	setParameter(s, new Object[] {projectID});
	s.execute();

	removeUnreferencedRows();
    }

    /** {@inheritDoc} */
    public void deleteProject(final String projectName) throws DBException {
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    String projectID = projectDeal.queryID(projectName);
	    if (projectID == null) {
		throw new DBException("project not found: " + projectName);
	    }
	    removeProject(projectID);
	    con.commit();
	} catch (SQLException e) {
	    rollback();
	    throw new DBException("failed to delete project: "
				  + e.getMessage(), e);
	}
    }
}
