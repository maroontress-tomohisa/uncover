package com.maroontress.uncover.sqlite;

/**
   プロジェクトテーブルの行に関する情報を保持します。
*/
public final class ProjectRow extends Row {
    /** プロジェクト名です。 */
    private String name;

    /**
       インスタンスを生成します。
    */
    public ProjectRow() {
    }

    /**
       フィールドに値を設定します。

       @param name プロジェクト名
    */
    public void set(final String name) {
	this.name = name;
    }
}
