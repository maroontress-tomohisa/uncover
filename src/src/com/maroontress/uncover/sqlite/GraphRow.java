package com.maroontress.uncover.sqlite;

/**
   グラフテーブルの行に関する情報を保持します。
*/
public final class GraphRow extends Row {
    /** 関数IDです。 */
    private String functionID;

    /** */
    private String buildID;

    /**
       インスタンスを生成します。
    */
    public GraphRow() {
    }

    /**
       フィールドに値を設定します。

       @param functionID 関数ID
       @param buildID ビルドID
    */
    public void set(final String functionID, final String buildID) {
	this.functionID = functionID;
	this.buildID = buildID;
    }
}
