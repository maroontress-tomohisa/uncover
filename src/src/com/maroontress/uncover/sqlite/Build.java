package com.maroontress.uncover.sqlite;

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
	// リフレクションで書きたい
	id = row.getString("id");
	revision = row.getString("revision");
	timestamp = row.getString("timestamp");
	platform = row.getString("platform");
	projectID = row.getString("projectID");
    }

    /**
       ビルドIDを取得します。

       @return ID
    */
    public String getID() {
	return id;
    }
}
