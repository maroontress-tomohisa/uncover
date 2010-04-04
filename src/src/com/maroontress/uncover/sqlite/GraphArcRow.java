package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Arc;

/**
   グラフアークテーブルの行に関する情報を保持します。
*/
public final class GraphArcRow extends Row {
    /** グラフIDです。 */
    private long graphID;

    /** 始点となるブロックの番号です。 */
    private int startBlock;

    /** 終点となるブロックの番号です。 */
    private int endBlock;

    /** アークの実行回数です。 */
    private long count;

    /** フェイクかどうかを表します。 */
    private int fake;

    /**
       インスタンスを生成します。
    */
    public GraphArcRow() {
    }

    /**
       フィールドに値を設定します。

       @param graphID グラフID
       @param arc アーク
    */
    public void set(final long graphID, final Arc arc) {
	this.graphID = graphID;
	startBlock = arc.getStart();
	endBlock = arc.getEnd();
	count = arc.getCount();
	fake = (arc.isFake()) ? 1 : 0;
    }
}
