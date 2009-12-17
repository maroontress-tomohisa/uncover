package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.Function;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    /**
       ����������ɲä��ơ���ư�������줿�祭����������ޤ���

       @param s ���ơ��ȥ��ȡ�INSERT��
       @return �祭��
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String insertNew(final PreparedStatement s) throws SQLException {
	s.execute();
	ResultSet rs = s.getGeneratedKeys();
	rs.next();
	return rs.getString(1);
    }

    /**
       ����������ɲä��ơ���ư�������줿�祭����������ޤ���

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
       ɬ�פʥơ��֥�򤹤٤��������ޤ���

       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private void initializeTables() throws SQLException {
	Statement s = con.createStatement();
	s.executeUpdate("CREATE TABLE " + Table.PROJECT + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "name"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.BUILD + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "revision, timestamp, platform, projectID"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.FUNCTION + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "name, filename, projectID"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "functionID, buildID"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_SUMMARY + " ("
			+ "graphID, checkSum, lineNumber, "
			+ "complexity, allBlocks, executedBlocks, "
			+ "allArcs, executedArcs"
			+ ");");
	con.commit();
    }

    /** {@inheritDoc} */
    public void initialize() throws DBException {
	try {
	    initializeTables();
	} catch (SQLException e) {
	    rollback();
	    throw new DBException("failed to initialize tables: "
				  + e.getMessage(), e);
	}
    }

    /**
       ���ꤷ��̾���Υץ�������ID��������ޤ���

       @param name �ץ������Ȥ�̾��
       @return �ץ�������ID���ޤ���null
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String queryProjectID(final String name) throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "SELECT id FROM " + Table.PROJECT + " WHERE name = ?;");
	s.setString(1, name);
	ResultSet rs = s.executeQuery();
	int k;
	String id = null;

	for (k = 0; rs.next(); ++k) {
	    id = rs.getString("id");
	}
	if (k > 1) {
	    throw new SQLException("project " + name
				   + " found more than one.");
	}
	return id;
    }

    /**
       �ץ������Ȥ��ɲä��ޤ���

       @param name �ץ������Ȥ�̾��
       @return �ץ�������ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String appendProject(final String name) throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "INSERT INTO " + Table.PROJECT + " (name) VALUES (?);");
	s.setString(1, name);
	return insertNew(s);
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
	String projectID = queryProjectID(name);
	if (projectID == null) {
	    projectID = appendProject(name);
	    System.err.println("project " + name
			       + " not found, newly created");
	}
	return projectID;
    }

    /**
       �ӥ�ɤ��ɲä��ޤ���

       @param revision ��ӥ����
       @param timestamp �����ॹ�����
       @param platform �ץ�åȥե�����
       @param projectID �ץ�������ID
       @return �ӥ��ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String appendBuild(final String revision, final String timestamp,
			       final String platform, final String projectID)
	throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "INSERT INTO " + Table.BUILD
	    + " (revision, timestamp, platform, projectID)"
	    + " VALUES (?, ?, ?, ?);");
	setParameter(s, new Object[] {
			 revision, timestamp, platform, projectID});
	return insertNew(s);
    }

    /**
       ���ꤷ��̾���δؿ���ID��������ޤ���

       @param name �ؿ�̾
       @param filename �ե�����̾
       @param projectID �ץ�������ID
       @return �ؿ�ID���ޤ���null
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String queryFunctionID(final String name, final String filename,
				   final String projectID)
	throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "SELECT id FROM " + Table.FUNCTION
	    + " WHERE name = ? and filename = ? and projectID = ?;");
	setParameter(s, new Object[] {name, filename, projectID});
	ResultSet rs = s.executeQuery();
	int k;
	String id = null;

	for (k = 0; rs.next(); ++k) {
	    id = rs.getString("id");
	}
	if (k > 1) {
	    throw new SQLException("projectID: " + projectID
				   + "; function " + name + " (" + filename
				   + ") found more than one.");
	}
	return id;
    }

    /**
       �ؿ����ɲä��ޤ���

       @param name �ؿ�̾
       @param filename �ե�����̾
       @param projectID �ץ�������ID
       @return �ؿ�ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String appendFunction(final String name, final String filename,
				  final String projectID) throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "INSERT INTO " + Table.FUNCTION
	    + " (name, filename, projectID) VALUES (?, ?, ?);");
	setParameter(s, new Object[] {name, filename, projectID});
	return insertNew(s);
    }

    /**
       �ؿ�ID��������ޤ���

       ���ꤷ��̾���δؿ���¸�ߤ����硢���δؿ�ID���֤��ޤ���¸�ߤ�
       �ʤ����ϡ��ؿ���ǡ����١����˿����ɲä��ơ����δؿ�ID���֤�
       �ޤ���

       @param name �ؿ�̾
       @param filename �ؿ�����°����ե�����̾
       @param projectID �ץ�������ID
       @return �ؿ�ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String getFunctionID(final String name, final String filename,
				 final String projectID) throws SQLException {
	String functionID = queryFunctionID(name, filename, projectID);
	if (functionID == null) {
	    functionID = appendFunction(name, filename, projectID);
	    System.err.println("function " + name + " (" + filename
				   + ") not found, newly created");
	}
	return functionID;
    }

    /**
       ����դ��ɲä��ޤ���

       @param functionID �ؿ�ID
       @param buildID �ӥ��ID
       @return �����ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String appendGraphID(final String functionID,
				 final String buildID) throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "INSERT INTO " + Table.GRAPH
	    + " (functionID, buildID) VALUES (?, ?);");
	setParameter(s, new Object[] {functionID, buildID});
	return insertNew(s);
    }

    /**
       ����ե��ޥ���ɲä��ޤ���

       @param graphID �����ID
       @param function �ؿ�
       @return ����ե��ޥ�ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    private String appendGraphSummary(final String graphID,
				      final Function function)
	throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "INSERT INTO " + Table.GRAPH_SUMMARY
	    + " (graphID, checkSum, lineNumber, complexity,"
	    + " allBlocks, executedBlocks, allArcs, executedArcs)"
	    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
	setParameter(s, new Object[] {
			 graphID,
			 function.getCheckSum(),
			 function.getLineNumber(),
			 function.getComplexity(),
			 function.getAllBlocks(),
			 function.getExecutedBlocks(),
			 function.getAllArcs(),
			 function.getExecutedArcs()});
	return insertNew(s);
    }

    /** {@inheritDoc} */
    public void commit(final String projectName, final String revision,
		       final String timestamp, final String platform,
		       final Iterable<Function> allFunctions)
	throws DBException {
	try {
	    String projectID = getProjectID(projectName);
	    String buildID = appendBuild(revision, timestamp, platform,
					 projectID);
	    for (Function function : allFunctions) {
		String functionID = getFunctionID(function.getName(),
						  function.getFilename(),
						  projectID);
		String graphID = appendGraphID(functionID, buildID);
		appendGraphSummary(graphID, function);
	    }
	    con.commit();
	} catch (SQLException e) {
	    rollback();
	    throw new DBException("failed to commit: " + e.getMessage(), e);
	}
    }
}
