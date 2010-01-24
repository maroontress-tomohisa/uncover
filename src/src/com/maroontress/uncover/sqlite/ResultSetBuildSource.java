package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.BuildSource;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   クエリ結果からビルドを生成するためのビルドソースです。
*/
public final class ResultSetBuildSource implements BuildSource {
    /** ビルドIDです。 */
    private String id;

    /** リビジョンです。 */
    private String revision;

    /** タイムスタンプです。 */
    private String timestamp;

    /** プラットフォームです。 */
    private String platform;

    /**
       インスタンスを生成します。
    */
    public ResultSetBuildSource() {
    }

    /**
       クエリ結果を設定します。

       @param row ビルドの行
       @throws SQLException エラーが発生したときにスローします。
    */
    public void setResultSet(final ResultSet row) throws SQLException {
	Field[] allField = this.getClass().getDeclaredFields();
	try {
	    for (Field field : allField) {
		String name = field.getName();
		field.set(this, row.getString(name));
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
    }

    /** {@inheritDoc} */
    public String getID() {
	return id;
    }

    /** {@inheritDoc} */
    public String getRevision() {
	return revision;
    }

    /** {@inheritDoc} */
    public String getTimestamp() {
	return timestamp;
    }

    /** {@inheritDoc} */
    public String getPlatform() {
	return platform;
    }
}
