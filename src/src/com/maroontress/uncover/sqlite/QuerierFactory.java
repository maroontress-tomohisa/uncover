package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
   クエリを発行するインスタンスを生成します。

   @param <T> テーブルの行を表すクラス
*/
public final class QuerierFactory<T extends Row> {
    /** JDBCの接続です。 */
    private Connection con;

    /** テーブル名です。 */
    private String tableName;

    /** フィールド名のリストです。 */
    private List<String> list;

    /**
       コンストラクタです。

       @param con データベースとの接続
       @param tableName テーブル名
       @param clazz テーブルに対応する行のクラス
    */
    public QuerierFactory(final Connection con, final String tableName,
			  final Class<T> clazz) {
	this.con = con;
	this.tableName = tableName;
	list = new ArrayList<String>();

	Field[] allField = clazz.getDeclaredFields();
	for (Field field : allField) {
	    list.add(field.getName());
	}
    }

    /**
       行を追加するインスタンスを生成します。

       @return 行を追加するインスタンス
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public Adder<T> createAdder() throws SQLException {
	String s = "INSERT INTO " + tableName + " (";
	String prefix = "";
	for (String name : list) {
	    s += prefix + name;
	    prefix = ", ";
	}
	s += ") VALUES (";
	int n = list.size();
	prefix = "";
	for (int k = 0; k < n; ++k) {
	    s += prefix + "?";
	    prefix = ", ";
	}
	s += ");";
	return new Adder<T>(con.prepareStatement(s));
    }

    /**
       行を取得するインスタンスを生成します。

       @param colNames 取得する行の列名
       @return 行を取得するインスタンス
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public Fetcher<T> createFetcher(final String colNames)
	throws SQLException {
	String s = "SELECT " + colNames + " FROM " + tableName + " WHERE ";
	String prefix = "";
	for (String name : list) {
	    s += prefix + name + " = ?";
	    prefix = "and ";
	}
	s += ";";
	return new Fetcher<T>(con.prepareStatement(s));
    }
}
