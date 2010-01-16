package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   テーブルから行を取得する機能を提供します。

   @param <T> テーブルの行を表すクラス
*/
public final class Fetcher<T extends Row> extends Querier<T> {
    /**
       コンストラクタです。

       @param ps コンパイル済みステートメント
    */
    public Fetcher(final PreparedStatement ps) {
	super(ps);
    }

    /**
       行をテーブルから取得します。

       @return java.sql.PreparedStatement#executeQuery()の戻り値
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public ResultSet executeQuery() throws SQLException {
	PreparedStatement ps = getStatement();
	T instance = getRow();
	Field[] allField = instance.getClass().getDeclaredFields();
	int offset = 1;
	try {
	    for (Field field : allField) {
		field.setAccessible(true);
		String name = field.getName();
		Class<?> clazz = field.getType();
		if (clazz == int.class) {
		    ps.setInt(offset, field.getInt(instance));
		} else {
		    ps.setString(offset, (String) field.get(instance));
		}
		++offset;
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
	return ps.executeQuery();
    }
}
