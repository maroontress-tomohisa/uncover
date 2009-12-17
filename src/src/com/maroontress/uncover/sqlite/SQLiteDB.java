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
   SQLiteを使用したデータベースの実装です。
*/
public final class SQLiteDB implements DB {
    /** JDBCのURLです。 */
    private static final String URL = "jdbc:sqlite:";

    /** JDBCの接続です。 */
    private Connection con;

    /**
       コンストラクタです。

       @param subname JDBCのURLのサブネーム
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
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
       データベースをロールバックします。
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
       新しい列を追加して、自動生成された主キーを取得します。

       @param s ステートメント（INSERT）
       @return 主キー
       @throws SQLException エラーが発生したときにスローします。
    */
    private String insertNew(final PreparedStatement s) throws SQLException {
	s.execute();
	ResultSet rs = s.getGeneratedKeys();
	rs.next();
	return rs.getString(1);
    }

    /**
       新しい列を追加して、自動生成された主キーを取得します。

       @param s ステートメント
       @param values パラメータの値の配列
       @throws SQLException エラーが発生したときにスローします。
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
       必要なテーブルをすべて生成します。

       @throws SQLException クエリにエラーが発生したときにスローします。
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
       指定した名前のプロジェクトIDを取得します。

       @param name プロジェクトの名前
       @return プロジェクトID、またはnull
       @throws SQLException クエリにエラーが発生したときにスローします。
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
       プロジェクトを追加します。

       @param name プロジェクトの名前
       @return プロジェクトID
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    private String appendProject(final String name) throws SQLException {
	PreparedStatement s = con.prepareStatement(
	    "INSERT INTO " + Table.PROJECT + " (name) VALUES (?);");
	s.setString(1, name);
	return insertNew(s);
    }

    /**
       プロジェクトIDを取得します。

       指定した名前のプロジェクトが存在する場合、そのプロジェクトIDを
       返します。存在しない場合は、プロジェクトをデータベースに新規追
       加して、そのプロジェクトIDを返します。

       @param name プロジェクトの名前
       @return プロジェクトID
       @throws SQLException クエリにエラーが発生したときにスローします。
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
       ビルドを追加します。

       @param revision リビジョン
       @param timestamp タイムスタンプ
       @param platform プラットフォーム
       @param projectID プロジェクトID
       @return ビルドID
       @throws SQLException クエリにエラーが発生したときにスローします。
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
       指定した名前の関数のIDを取得します。

       @param name 関数名
       @param filename ファイル名
       @param projectID プロジェクトID
       @return 関数ID、またはnull
       @throws SQLException クエリにエラーが発生したときにスローします。
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
       関数を追加します。

       @param name 関数名
       @param filename ファイル名
       @param projectID プロジェクトID
       @return 関数ID
       @throws SQLException クエリにエラーが発生したときにスローします。
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
       関数IDを取得します。

       指定した名前の関数が存在する場合、その関数IDを返します。存在し
       ない場合は、関数をデータベースに新規追加して、その関数IDを返し
       ます。

       @param name 関数名
       @param filename 関数が所属するファイル名
       @param projectID プロジェクトID
       @return 関数ID
       @throws SQLException クエリにエラーが発生したときにスローします。
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
       グラフを追加します。

       @param functionID 関数ID
       @param buildID ビルドID
       @return グラフID
       @throws SQLException クエリにエラーが発生したときにスローします。
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
       グラフサマリを追加します。

       @param graphID グラフID
       @param function 関数
       @return グラフサマリID
       @throws SQLException クエリにエラーが発生したときにスローします。
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
