package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Block;

/**
   グラフブロックテーブルの行に関する情報を保持します。
*/
public final class GraphBlockRow extends Row {
    /** グラフIDです。 */
    private long graphID;

    /** ブロックの番号です。 */
    private int number;

    /** ブロックの実行回数です。 */
    private int count;

    /** ソースファイルです。 */
    private String sourceFile;

    /** 行番号です。 */
    private int lineNumber;

    /**
       インスタンスを生成します。
    */
    public GraphBlockRow() {
    }

    /**
       フィールドに値を設定します。

       @param graphID グラフID
       @param block 基本ブロック
    */
    public void set(final long graphID, final Block block) {
	this.graphID = graphID;
	number = block.getNumber();
	count = block.getCount();
	sourceFile = block.getSourceFile();
	lineNumber = block.getLineNumber();
    }
}
