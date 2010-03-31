package com.maroontress.uncover.sqlite;

/**
   グラフテーブルの行に関する情報を保持します。
*/
public final class GraphRow extends Row {
    /** 関数IDです。 */
    private long functionID;

    /** ビルドID */
    private long buildID;

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
    public void set(final long functionID, final long buildID) {
	this.functionID = functionID;
	this.buildID = buildID;
    }
}
