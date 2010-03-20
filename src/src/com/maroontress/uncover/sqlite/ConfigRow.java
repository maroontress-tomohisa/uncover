package com.maroontress.uncover.sqlite;

/**
   設定テーブルの行に関する情報を保持します。
*/
public final class ConfigRow extends Row {
    /** データベースのバージョンです。 */
    private String version;

    /**
       インスタンスを生成します。
    */
    public ConfigRow() {
    }

    /**
       フィールドに値を設定します。

       @param version データベースのバージョン
    */
    public void set(final String version) {
	this.version = version;
    }
}
