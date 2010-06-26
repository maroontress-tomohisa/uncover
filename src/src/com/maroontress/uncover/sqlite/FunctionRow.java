package com.maroontress.uncover.sqlite;

/**
   関数テーブルの行に関する情報を保持します。
*/
public final class FunctionRow extends Row {
    /** 関数名です。 */
    private String name;

    /** gcnoファイルのIDです。 */
    private long gcnoFileID;

    /** プロジェクトIDです。 */
    private long projectID;

    /**
       インスタンスを生成します。
    */
    public FunctionRow() {
    }

    /**
       フィールドに値を設定します。

       @param name 関数名
       @param gcnoFileID gcnoファイルのID
       @param projectID プロジェクトID
    */
    public void set(final String name, final long gcnoFileID,
		    final long projectID) {
	this.name = name;
	this.gcnoFileID = gcnoFileID;
	this.projectID = projectID;
    }
}
