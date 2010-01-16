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
	// リフレクションで書く
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
			+ "name, gcnoFile, projectID"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "functionID, buildID"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_SUMMARY + " ("
			+ "graphID, checkSum, sourceFile, lineNumber, "
			+ "complexity, allBlocks, executedBlocks, "
			+ "allArcs, executedArcs"
			+ ");");
    }
}
