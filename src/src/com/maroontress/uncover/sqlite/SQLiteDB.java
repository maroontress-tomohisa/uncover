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
       プロジェクトの配列を取得します。

       @return プロジェクトの配列
       @throws SQLException エラーが発生したときにスローします。
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
       コンパイル済みのステートメントにパラメータを設定します。

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
       ビルドの配列を取得します。

       指定したリビジョンとプロジェクトIDにマッチするビルドの配列を返
       します。

       @param revision リビジョン
       @param projectID プロジェクトID
       @return ビルドの配列
       @throws SQLException エラーが発生したときにスローします。
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
       ビルドを取得します。

       @param id ビルドID
       @param projectID プロジェクトID
       @return ビルドの配列
       @throws SQLException エラーが発生したときにスローします。
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
       指定したビルドの関数のリストを取得します。

       @param buildID ビルドID
       @return 関数のリスト
       @throws SQLException エラーが発生したときにスローします。
    */
    private List<Function> getFunctions(final String buildID)
	throws SQLException {
	String format = String.format(
	    // FieldArray.concatNames(ResultSetFunctionSource.class, ",")
	    // を使う
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
       リビジョン名の配列を取得します。

       指定したプロジェクトIDにマッチするリビジョン名の配列を返します。

       @param projectID プロジェクトID
       @return ビルドの配列
       @throws SQLException エラーが発生したときにスローします。
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
       ビルドテーブルの行を削除した後、各テーブルで参照されなくなる行
       を削除します。

       @throws SQLException エラーが発生したときにスローします。
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
       ビルドIDを指定してビルドを削除します。

       @param id ビルドID
       @param projectID プロジェクトID
       @throws SQLException エラーが発生したときにスローします。
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
       リビジョンを指定してビルドを削除します。

       @param revision リビジョン
       @param projectID プロジェクトID
       @throws SQLException エラーが発生したときにスローします。
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
       プロジェクトを削除します。

       @param projectID プロジェクトID
       @throws SQLException エラーが発生したときにスローします。
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
