package com.maroontress.uncover.sqlite;

/**
   gcnoファイルテーブルの行に関する情報を保持します。
*/
public final class GcnoFileRow extends Row {
    /** gcnoファイルのパスです。 */
    private String gcnoFile;

    /**
       インスタンスを生成します。
    */
    public GcnoFileRow() {
    }

    /**
       フィールドに値を設定します。

       @param gcnoFile gcnoファイルのパス
    */
    public void set(final String gcnoFile) {
	this.gcnoFile = gcnoFile;
    }
}
