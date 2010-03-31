package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   テーブルとクエリ結果の行に関する情報を保持します。
*/
public abstract class Row {
    /**
       インスタンスを生成します。
    */
    protected Row() {
    }

    /**
       クエリ結果を設定します。

       @param row 関数の行
       @throws SQLException エラーが発生したときにスローします。
    */
    public final void setResultSet(final ResultSet row) throws SQLException {
	Field[] allFields = this.getClass().getDeclaredFields();
	try {
	    for (Field field : allFields) {
		field.setAccessible(true);

		String name = field.getName();
		Class<?> clazz = field.getType();
		if (clazz == int.class) {
		    field.setInt(this, row.getInt(name));
		} else if (clazz == long.class) {
		    field.setLong(this, row.getLong(name));
		} else if (clazz == String.class) {
		    field.set(this, row.getString(name));
		} else {
		    throw new RuntimeException(
			"internal error: unexpected type.");
		}
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
    }
}
