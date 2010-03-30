package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   簡単なクエリを実行する機能を提供します。

   クエリ文字列とそのパラメータの組み合せは、コンパイル時にそれが正し
   いかどうかを判定することはできません。
*/
public final class SimpleQuery {
    /** JDBCの接続です。 */
    private Connection con;

    /**
       デフォルトコンストラクタは利用できません。
    */
    private SimpleQuery() {
    }

    /**
       インスタンスを生成します。

       @param con JDBCの接続
    */
    public SimpleQuery(final Connection con) {
	this.con = con;
    }

    /**
       SQLステートメントとそのパラメータを実行してリザルトセットを生成
       します。

       @param template クエリ文字列
       @param params クエリのパラメータ
       @return 生成したリザルトセット
       @throws SQLException エラーが発生したときにスローします。
    */
    public ResultSet executeQuery(final String template,
				  final Object[] params) throws SQLException {
	PreparedStatement s = prepareStatement(template, params);
	return s.executeQuery();
    }

    /**
       SQLステートメントとそのパラメータを実行します。

       @param template クエリ文字列
       @param params クエリのパラメータ
       @throws SQLException エラーが発生したときにスローします。
    */
    public void execute(final String template,
			final Object[] params) throws SQLException {
	PreparedStatement s = prepareStatement(template, params);
	s.execute();
    }

    /**
       コンパイル済みのステートメントを生成し、それにパラメータを設定
       したものを取得します。

       @param template クエリ文字列
       @param values パラメータの値の配列
       @return コンパイル済みのステートメント
       @throws SQLException エラーが発生したときにスローします。
    */
    private PreparedStatement prepareStatement(
	final String template, final Object[] values) throws SQLException {
	PreparedStatement s = con.prepareStatement(template);
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
	return s;
    }
}
