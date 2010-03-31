package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
   テーブルに行単位で操作する機能を提供します。

   @param <T> テーブルの行を表すクラス
*/
public abstract class Querier<T extends Row> {
    /** コンパイル済みステートメントです。 */
    private PreparedStatement ps;

    /** 行のインスタンスです。 */
    private T instance;

    /** 行のインスタンスのフィールドです。 */
    private Field[] allFields;

    /**
       コンストラクタです。

       @param ps コンパイル済みステートメント
    */
    protected Querier(final PreparedStatement ps) {
	this.ps = ps;
    }

    /**
       行を表すインスタンスを設定します。

       @param instance 行を表すインスタンス
    */
    public final void setRow(final T instance) {
	this.instance = instance;
	allFields = instance.getClass().getDeclaredFields();
	for (Field field : allFields) {
	    field.setAccessible(true);
	}
    }

    /**
       行を表すインスタンスを取得します。

       @return 行を表すインスタンス
    */
    public final T getRow() {
	return instance;
    }

    /**
       コンパイル済みステートメントを取得します。

       @return コンパイル済みステートメント
    */
    protected final PreparedStatement getStatement() {
	return ps;
    }

    /**
       コンパイル済みステートメントに、行に含まれる値をパラメータとし
       て設定します。

       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    protected final void setParameters() throws SQLException {
	int offset = 1;
	try {
	    for (Field field : allFields) {
		Class<?> clazz = field.getType();
		if (clazz == int.class) {
		    ps.setInt(offset, field.getInt(instance));
		} else if (clazz == long.class) {
		    ps.setLong(offset, field.getLong(instance));
		} else if (clazz == String.class) {
		    ps.setString(offset, (String) field.get(instance));
		} else {
		    throw new RuntimeException(
			"internal error: unexpected type.");
		}
		++offset;
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
    }
}
