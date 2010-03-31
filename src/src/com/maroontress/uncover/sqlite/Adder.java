package com.maroontress.uncover.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   テーブルに行を追加する機能を提供します。

   @param <T> テーブルの行を表すクラス
*/
public final class Adder<T extends Row> extends Querier<T> {
    /**
       コンストラクタです。

       @param ps コンパイル済みステートメント
    */
    public Adder(final PreparedStatement ps) {
	super(ps);
    }

    /**
       行をテーブルに追加します。

       @return java.sql.PreparedStatement#execute()の戻り値
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public boolean execute() throws SQLException {
	setParameters();
	return getStatement().execute();
    }

    /**
       追加した行で自動生成されたキーの値を取得します。

       @return 自動生成されたキーの結果
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public ResultSet getGeneratedKeys() throws SQLException {
	return getStatement().getGeneratedKeys();
    }

    /**
       追加した行で自動生成されたキーの値を取得します。

       @param num 列の番号
       @return 自動生成されたキー
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public long getGeneratedKey(final int num) throws SQLException {
	ResultSet rs = getGeneratedKeys();
	rs.next();
	return rs.getLong(num);
    }
}
