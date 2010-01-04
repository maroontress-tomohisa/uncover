package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   ビルドに関する情報を保持します。
*/
public final class Build {
    /** ビルドIDです。 */
    private String id;

    /** リビジョンです。 */
    private String revision;

    /** タイムスタンプです。 */
    private String timestamp;

    /** プラットフォームです。 */
    private String platform;

    /** プロジェクトIDです。 */
    private String projectID;

    /**
       インスタンスを生成します。

       @param row buildテーブルの行
       @throws SQLException エラーが発生したときにスローします。
    */
    public Build(final ResultSet row) throws SQLException {
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

    /**
       ビルドIDを取得します。

       @return ID
    */
    public String getID() {
	return id;
    }
}
