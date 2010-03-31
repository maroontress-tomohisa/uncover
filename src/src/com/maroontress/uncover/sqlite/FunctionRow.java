package com.maroontress.uncover.sqlite;

/**
   関数テーブルの行に関する情報を保持します。
*/
public final class FunctionRow extends Row {
    /** 関数名です。 */
    private String name;

    /** gcnoファイルのファイル名です。 */
    private String gcnoFile;

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
       @param gcnoFile gcnoファイルのファイル名
       @param projectID プロジェクトID
    */
    public void set(final String name, final String gcnoFile,
		    final long projectID) {
	this.name = name;
	this.gcnoFile = gcnoFile;
	this.projectID = projectID;
    }
}
