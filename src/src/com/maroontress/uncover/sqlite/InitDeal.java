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
       必要なテーブルをすべて生成します。

       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public void run() throws SQLException {
	Statement s = con.createStatement();
	s.executeUpdate("CREATE TABLE " + Table.PROJECT + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ FieldArray.concatNames(ProjectRow.class, ", ")
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.BUILD + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ FieldArray.concatNames(BuildRow.class, ", ")
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.FUNCTION + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ FieldArray.concatNames(FunctionRow.class, ", ")
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ FieldArray.concatNames(GraphRow.class, ", ")
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_SUMMARY + " ("
			+ FieldArray.concatNames(GraphSummaryRow.class, ", ")
			+ ");");
    }
}
