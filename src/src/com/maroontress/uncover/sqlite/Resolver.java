package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   テーブルの行のIDを取得するための抽象クラスです。

   @param <T> テーブルの行となるクラス
*/
public abstract class Resolver<T extends Row> {
    /** 関数テーブルの行を取得するフェッチャです。 */
    private Fetcher<T> fetcher;

    /**
       インスタンスを生成します。

       @param con JDBC接続
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public Resolver(final Connection con) throws SQLException {
	T row = createRow();
	fetcher = new QuerierFactory<T>(con, getTableName(),
					getRowClass()).createFetcher("id");
	fetcher.setRow(row);
    }

    /**
       テーブルの行のクラスを取得します。

       @return テーブルの行のクラス。
    */
    protected abstract Class<T> getRowClass();

    /**
       テーブルの行のインスタンスを生成します。

       @return テーブルの行
    */
    protected abstract T createRow();

    /**
       テーブルの名前を取得します。

       @return テーブル名
    */
    protected abstract String getTableName();

    /**
       テーブルの行のインスタンスを取得します。

       @return テーブルの行
    */
    public final T getRow() {
	return fetcher.getRow();
    }

    /**
       行のIDを取得します。

       @return 行のID
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    protected final long getID() throws SQLException {
	ResultSet rs = fetcher.executeQuery();
	long id = -1;
	int k;
	for (k = 0; rs.next(); ++k) {
	    id = rs.getLong("id");
	}
	if (k > 1) {
	    String s = "too many rows found: " + dumpRow();
	    throw new TableInconsistencyException(s);
	}
	return id;
    }

    /**
       行の内容を表す文字列を取得します。

       @return 行の各フィールドの名前と値
    */
    private String dumpRow() {
	StringBuilder b = new StringBuilder();
	String prefix = "";
	Object instance = getRow();
	Field[] allFields = instance.getClass().getDeclaredFields();
	try {
	    b.append("(");
	    for (Field field : allFields) {
		field.setAccessible(true);
		String name = field.getName();
		Object value = field.get(instance);
		b.append(prefix);
		b.append(name);
		b.append("=");
		b.append(value.toString());
		prefix = ", ";
	    }
	    b.append(")");
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error.", e);
	}
	return b.toString();
    }
}
