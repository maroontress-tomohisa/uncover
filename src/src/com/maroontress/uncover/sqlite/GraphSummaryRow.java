package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Function;

/**
   グラフサマリテーブルの行に関する情報を保持します。
*/
public final class GraphSummaryRow extends Row {
    /** グラフIDです。 */
    private long graphID;

    /** チェックサムです。 */
    private String checkSum;

    /** ソースファイルです。 */
    private String sourceFile;

    /** 行番号です。 */
    private int lineNumber;

    /** 複雑度です。 */
    private int complexity;

    /** 基本ブロックの総数です。 */
    private int allBlocks;

    /** 実行済みの基本ブロックの個数です。 */
    private int executedBlocks;

    /** アークの総数です。 */
    private int allArcs;

    /** 実行済みのアークの個数です。 */
    private int executedArcs;

    /**
       インスタンスを生成します。
    */
    public GraphSummaryRow() {
    }

    /**
       フィールドに値を設定します。

       @param graphID グラフID
       @param function 関数
    */
    public void set(final long graphID, final Function function) {
	this.graphID = graphID;
	checkSum = function.getCheckSum();
	sourceFile = function.getSourceFile();
	lineNumber = function.getLineNumber();
	complexity = function.getComplexity();
	allBlocks = function.getAllBlocks();
	executedBlocks = function.getExecutedBlocks();
	allArcs = function.getAllArcs();
	executedArcs = function.getExecutedArcs();
    }
}
