package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.Build;
import com.maroontress.uncover.CommitSource;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.Function;
import com.maroontress.uncover.Graph;
import com.maroontress.uncover.GraphSource;
import com.maroontress.uncover.Revision;
import com.maroontress.uncover.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
   SQLiteを使用したデータベースの実装です。
*/
public final class SQLiteDB implements DB {
    /** データベースのバージョンです。 */
    public static final String VERSION = "1";

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

    /**
       データベースの設定を確認します。

       今のところバージョンの一致だけを確認します。

       @throws DBException DBのバージョンが不一致のときにスローします。
    */
    private void verifyIntegrity() throws DBException {
	String version = null;
	try {
	    String sql = String.format("SELECT * FROM %s", Table.CONFIG);
	    PreparedStatement s = con.prepareStatement(sql);
	    ResultSet rs = s.executeQuery();
	    int k;

	    for (k = 0; rs.next(); ++k) {
		version = rs.getString("version");
	    }
	    if (k > 1) {
		String m = String.format("config found more than one.");
		throw new TableInconsistencyException(m);
	    }
	} catch (SQLException e) {
	    throw new DBException("can't get DB version: "
				  + e.getMessage(), e);
	}
	if (version == null || !version.equals(VERSION)) {
	    throw new DBException("DB version mismatched: " + version);
	}
    }

    /** {@inheritDoc} */
    public void commit(final CommitSource source) throws DBException {
	verifyIntegrity();
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
	    "SELECT name FROM " + Table.PROJECT);
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
	verifyIntegrity();
	try {
	    return queryProjectNames();
	} catch (SQLException e) {
	    throw new DBException("failed to get projects: "
				  + e.getMessage(), e);
	}
    }

    /**
       ビルドのリストを取得します。

       @param template クエリ文字列
       @param params クエリのパラメータ
       @return ビルドのリスト
       @throws SQLException エラーが発生したときにスローします。
    */
    private List<Build> getBuildList(final String template,
				     final Object[] params)
	throws SQLException {
	ListCreator<Build> creator = new ListCreator<Build>(con) {
	    private ResultSetBuildSource source = new ResultSetBuildSource();
	    public Row getRow() {
		return source;
	    }
	    public Build create(final Toolkit toolkit) {
		return toolkit.createBuild(source);
	    }
	};
	return creator.getList(template, params);
    }

    /**
       ビルドのリストを取得します。

       指定したリビジョンとプロジェクトIDにマッチするビルドの配列を返
       します。

       @param revision リビジョン
       @param projectID プロジェクトID
       @return ビルドのリスト
       @throws SQLException エラーが発生したときにスローします。
    */
    private List<Build> queryBuilds(final String revision,
				    final long projectID)
	throws SQLException {
	String template = String.format(
	    "SELECT * FROM %s WHERE revision = ? and projectID = ?",
	    Table.BUILD);
	Object[] params = new Object[] {revision, projectID};
	return getBuildList(template, params);
    }

    /** {@inheritDoc} */
    public Build[] getBuilds(final String projectName,
			     final String revision) throws DBException {
	verifyIntegrity();
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    long projectID = projectDeal.queryID(projectName);
	    if (projectID == -1) {
		throw new DBException("project not found: " + projectName);
	    }
	    List<Build> list = queryBuilds(revision, projectID);
	    if (list.size() == 0) {
		throw new DBException("revision not found: " + revision);
	    }
	    return list.toArray(new Build[list.size()]);
	} catch (SQLException e) {
	    throw new DBException("failed to get builds: "
				  + e.getMessage(), e);
	}
    }

    /**
       ビルドを取得します。

       @param buildID ビルドID
       @param projectID プロジェクトID
       @return ビルドのリスト
       @throws SQLException エラーが発生したときにスローします。
    */
    private List<Build> queryBuild(final long buildID,
				   final long projectID) throws SQLException {
	String template = String.format(
	    "SELECT * FROM %s WHERE id = ? and projectID = ?",
	    Table.BUILD);
	Object[] params = new Object[] {buildID, projectID};
	return getBuildList(template, params);
    }

    /** {@inheritDoc} */
    public Build getBuild(final String projectName,
			  final String id) throws DBException {
	verifyIntegrity();
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    long buildID = Long.parseLong(id);
	    long projectID = projectDeal.queryID(projectName);
	    if (projectID == -1) {
		throw new DBException("project not found: " + projectName);
	    }
	    List<Build> list = queryBuild(buildID, projectID);
	    if (list.size() == 0) {
		throw new DBException("not found: @" + id);
	    }
	    if (list.size() > 1) {
		throw new DBException("internal error");
	    }
	    return list.get(0);
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
	String template = String.format(
	    "SELECT %s FROM %s g"
	    + " INNER JOIN %s f ON f.id = g.functionID"
	    + " INNER JOIN %s gs ON g.id = gs.graphID"
	    + " WHERE g.buildID = ?",
	    FieldArray.concatNames(ResultSetFunctionSource.class, ","),
	    Table.GRAPH, Table.FUNCTION, Table.GRAPH_SUMMARY);
	Object[] params = new Object[] {buildID};

	ListCreator<Function> creator = new ListCreator<Function>(con) {
	    private ResultSetFunctionSource source
	        = new ResultSetFunctionSource();
	    public Row getRow() {
		return source;
	    }
	    public Function create(final Toolkit toolkit) {
		return toolkit.createFunction(source);
	    }
	};
	return creator.getList(template, params);
    }

    /** {@inheritDoc} */
    public Revision getRevision(final String id) throws DBException {
	verifyIntegrity();
	try {
	    return new Revision(getFunctions(id));
	} catch (SQLException e) {
	    throw new DBException("failed to get revision: "
				  + e.getMessage(), e);
	}
    }

    /**
       リビジョン名のリストを取得します。

       指定したプロジェクトIDにマッチするリビジョン名のリストを返します。

       @param projectID プロジェクトID
       @return リビジョン名のリスト
       @throws SQLException エラーが発生したときにスローします。
    */
    private List<String> queryRevisionNames(final long projectID)
	throws SQLException {
	String template = String.format(
	    "SELECT * FROM %s WHERE projectID = ?",
	    Table.BUILD);
	Object[] params = new Object[] {projectID};

	ListCreator<String> creator = new ListCreator<String>(con) {
	    private ResultSetBuildSource source = new ResultSetBuildSource();
	    public Row getRow() {
		return source;
	    }
	    public String create(final Toolkit toolkit) {
		return toolkit.createBuild(source).getRevision();
	    }
	};
	return creator.getList(template, params);
    }

    /** {@inheritDoc} */
    public String[] getRevisionNames(final String projectName)
	throws DBException {
	verifyIntegrity();
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    long projectID = projectDeal.queryID(projectName);
	    if (projectID == -1) {
		throw new DBException("project not found: " + projectName);
	    }
	    List<String> list = queryRevisionNames(projectID);
	    return list.toArray(new String[list.size()]);
	} catch (SQLException e) {
	    throw new DBException("failed to get revision names: "
				  + e.getMessage(), e);
	}
    }

    /**
       テーブルの列で別のテーブルの行を参照するものの中で、参照先が存
       在しない列をもつ行をすべて削除します。

       table.columnがrefTable.refColumnを参照していて、その参照先とな
       るrefTable.refColumnが存在しない場合、参照元のtable.columnを削
       除する、という操作をtableの各行に対して実行します。

       @param table 参照するテーブルの名前
       @param column tableの列の名前
       @param refTable 参照されるテーブルの名前
       @param refColumn refTableの列の名前
       @throws SQLException エラーが発生したときにスローします。
    */
    private void removeUnreferencedRows(final String table,
					final String column,
					final String refTable,
					final String refColumn)
	throws SQLException {
	String sql = String.format("DELETE FROM %s WHERE NOT EXISTS ("
				   + "SELECT * FROM %s g WHERE g.%s = %s.%s)",
				   table, refTable, refColumn, table, column);
	con.createStatement().executeUpdate(sql);
    }

    /**
       ビルドテーブルの行を削除した後、各テーブルで参照されなくなる行
       を削除します。

       @throws SQLException エラーが発生したときにスローします。
    */
    private void removeUnreferencedRows() throws SQLException {
	removeUnreferencedRows(Table.GRAPH, "buildID",
			       Table.BUILD, "id");
	removeUnreferencedRows(Table.GRAPH_SUMMARY, "graphID",
			       Table.GRAPH, "id");
	removeUnreferencedRows(Table.GRAPH_BLOCK, "graphID",
			       Table.GRAPH, "id");
	removeUnreferencedRows(Table.GRAPH_ARC, "graphID",
			       Table.GRAPH, "id");
	removeUnreferencedRows(Table.FUNCTION, "id",
			       Table.GRAPH, "functionID");
    }

    /**
       ビルドIDを指定してビルドを削除します。

       @param id ビルドID
       @param projectID プロジェクトID
       @throws SQLException エラーが発生したときにスローします。
    */
    private void removeBuild(final long id,
			     final long projectID) throws SQLException {
	String template = String.format(
	    "DELETE FROM %s WHERE id = ? and projectID = ?", Table.BUILD);
	SimpleQuery query = new SimpleQuery(con);
	query.execute(template, new Object[] {id, projectID});
	removeUnreferencedRows();
    }

    /** {@inheritDoc} */
    public void deleteBuild(final String projectName,
			    final String id) throws DBException {
	verifyIntegrity();
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    long buildID = Long.parseLong(id);
	    long projectID = projectDeal.queryID(projectName);
	    if (projectID == -1) {
		throw new DBException("project not found: " + projectName);
	    }
	    removeBuild(buildID, projectID);
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
			      final long projectID) throws SQLException {
	String template = String.format(
	    "DELETE FROM %s WHERE revision = ? and projectID = ?",
	    Table.BUILD);
	SimpleQuery query = new SimpleQuery(con);
	query.execute(template, new Object[] {revision, projectID});
	removeUnreferencedRows();
    }

    /** {@inheritDoc} */
    public void deleteBuilds(final String projectName,
			     final String revision) throws DBException {
	verifyIntegrity();
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    long projectID = projectDeal.queryID(projectName);
	    if (projectID == -1) {
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
    private void removeProject(final long projectID) throws SQLException {
	SimpleQuery query = new SimpleQuery(con);
	String template;

	template = String.format("DELETE FROM %s WHERE id = ?",
				 Table.PROJECT);
	query.execute(template, new Object[] {projectID});

	template = String.format("DELETE FROM %s WHERE projectID = ?",
				 Table.BUILD);
	query.execute(template, new Object[] {projectID});

	removeUnreferencedRows();
    }

    /** {@inheritDoc} */
    public void deleteProject(final String projectName) throws DBException {
	verifyIntegrity();
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    long projectID = projectDeal.queryID(projectName);
	    if (projectID == -1) {
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

    /**
       指定したビルドと関数の、ブロックのリストを取得します。

       @param functionID 関数ID
       @param buildID ビルドID
       @return ブロックのリスト
       @throws SQLException エラーが発生したときにスローします。
    */
    private List<Block> getBlocks(final long functionID,
				  final long buildID) throws SQLException {
	String template = String.format(
	    "SELECT %s FROM %s gb"
	    + " INNER JOIN %s g ON g.id = gb.graphID"
	    + " WHERE g.functionID = ? and g.buildID = ?",
	    FieldArray.concatNames(ResultSetBlockSource.class, ","),
	    Table.GRAPH_BLOCK, Table.GRAPH);
	Object[] params = new Object[] {functionID, buildID};

	ListCreator<Block> creator = new ListCreator<Block>(con) {
	    private ResultSetBlockSource source = new ResultSetBlockSource();
	    public Row getRow() {
		return source;
	    }
	    public Block create(final Toolkit toolkit) {
		return toolkit.createBlock(source);
	    }
	};
	return creator.getList(template, params);
    }

    /**
       指定したビルドと関数の、アークのリストを取得します。

       @param functionID 関数ID
       @param buildID ビルドID
       @return アークのリスト
       @throws SQLException エラーが発生したときにスローします。
    */
    private List<Arc> getArcs(final long functionID,
			      final long buildID) throws SQLException {
	String template = String.format(
	    "SELECT %s FROM %s ga"
	    + " INNER JOIN %s g ON g.id = ga.graphID"
	    + " WHERE g.functionID = ? and g.buildID = ?",
	    FieldArray.concatNames(ResultSetArcSource.class, ","),
	    Table.GRAPH_ARC, Table.GRAPH);
	Object[] params = new Object[] {functionID, buildID};

	ListCreator<Arc> creator = new ListCreator<Arc>(con) {
	    private ResultSetArcSource source = new ResultSetArcSource();
	    public Row getRow() {
		return source;
	    }
	    public Arc create(final Toolkit toolkit) {
		return toolkit.createArc(source);
	    }
	};
	return creator.getList(template, params);
    }

    /** {@inheritDoc} */
    public Graph getGraph(final String projectName, final String id,
			  final String function, final String gcnoFile)
	throws DBException {
	verifyIntegrity();
	try {
	    ProjectDeal projectDeal = new ProjectDeal(con);
	    long projectID = projectDeal.queryID(projectName);
	    if (projectID == -1) {
		throw new DBException("project not found: " + projectName);
	    }

	    FunctionResolver functionResolver = new FunctionResolver(con);
            long functionID = functionResolver.getFunctionID(
		function, gcnoFile, projectID);
	    if (functionID == -1) {
		String s = String.format(
		    "project: %s; function %s (%s) not found.",
		    projectName, function, gcnoFile);
		throw new DBException(s);
	    }

	    long buildID = Long.parseLong(id);
	    final List<Block> blocks = getBlocks(functionID, buildID);
	    final List<Arc> arcs = getArcs(functionID, buildID);
	    Toolkit toolkit = Toolkit.getInstance();
	    return toolkit.createGraph(new GraphSource() {
		public String getName() {
		    return function;
		}
		public String getGCNOFile() {
		    return gcnoFile;
		}
		public Block[] getAllBlocks() {
		    return blocks.toArray(new Block[blocks.size()]);
		}
		public Arc[] getAllArcs() {
		    return arcs.toArray(new Arc[arcs.size()]);
		}
	    });
	} catch (SQLException e) {
	    throw new DBException("failed to get graph: " + e.getMessage(), e);
	}
    }
}
