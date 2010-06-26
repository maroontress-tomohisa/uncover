package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
   データベースの初期化処理を扱います。
*/
public final class InitDeal {
    /** データベースとの接続です。 */
    private Connection con;

    /**
       インスタンスを生成します。

       @param con データベースとの接続
    */
    public InitDeal(final Connection con) {
	this.con = con;
    }

    /**
       テーブルの行クラスから、テーブルの列定義となる文字列を取得します。

       @param clazz 行クラス
       @return テーブルの列定義となる文字列
    */
    private static String getTableDefinition(
	final Class<? extends Row> clazz) {
	return FieldArray.concatNameTypes(clazz, ", ");
    }

    /**
       必要なテーブルをすべて生成します。

       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public void run() throws SQLException {
	Statement s = con.createStatement();
	s.executeUpdate("CREATE TABLE " + Table.CONFIG + " ("
			+ getTableDefinition(ConfigRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.PROJECT + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(ProjectRow.class)
			+ ", UNIQUE(name))");
	s.executeUpdate("CREATE TABLE " + Table.GCNO_FILE + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(GcnoFileRow.class)
			+ ", UNIQUE (gcnoFile))");
	s.executeUpdate("CREATE TABLE " + Table.BUILD + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(BuildRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.FUNCTION + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(FunctionRow.class)
			+ ", UNIQUE (name, gcnoFileID, projectID))");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(GraphRow.class)
			+ ", UNIQUE(functionID, buildID))");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_SUMMARY + " ("
			+ getTableDefinition(GraphSummaryRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_BLOCK + " ("
			+ getTableDefinition(GraphBlockRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_ARC + " ("
			+ getTableDefinition(GraphArcRow.class)
			+ ")");

	Adder<ConfigRow> adder = new QuerierFactory<ConfigRow>(
	    con, Table.CONFIG, ConfigRow.class).createAdder();
	adder.setRow(new ConfigRow());
	adder.getRow().set(SQLiteDB.VERSION);
	adder.execute();
    }
}
