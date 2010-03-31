package com.maroontress.uncover.sqlite;

/**
   ビルドテーブルの行に関する情報を保持します。
*/
public final class BuildRow extends Row {
    /** リビジョンです。 */
    private String revision;

    /** タイムスタンプです。 */
    private String timestamp;

    /** プラットフォームです。 */
    private String platform;

    /** プロジェクトIDです。 */
    private long projectID;

    /**
       インスタンスを生成します。
    */
    public BuildRow() {
    }

    /**
       フィールドに値を設定します。

       @param revision リビジョン
       @param timestamp タイムスタンプ
       @param platform プラットフォーム
       @param projectID プロジェクトID
    */
    public void set(final String revision, final String timestamp,
		    final String platform, final long projectID) {
	this.revision = revision;
	this.timestamp = timestamp;
	this.platform = platform;
	this.projectID = projectID;
    }
}
