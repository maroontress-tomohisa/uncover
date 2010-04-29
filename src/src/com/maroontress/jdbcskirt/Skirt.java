package com.maroontress.jdbcskirt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
   JDBCドライバのロードと、ドライバマネージャからコネクションを取得す
   る機能を提供します。
*/
public final class Skirt {
    /**
       コンストラクタは使用できません。
    */
    private Skirt() {
    }

    /**
       JDBCのコネクションを取得します。

       @param name JDBCドライバのクラス名
       @param url JDBCデータベースURL
       @return JDBCのコネクション
       @throws SQLException データベースアクセスエラーが発生した場合ス
       ローします。
       @throws ClassNotFoundException JDBCドライバのクラスが見つからな
       い場合スローします。
    */
    public static Connection getConnection(final String name, final String url)
	throws SQLException, ClassNotFoundException {
	Class.forName(name);
	return DriverManager.getConnection(url);
    }
}
