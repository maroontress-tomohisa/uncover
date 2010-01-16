package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Function;

/**
   グラフサマリテーブルの行に関する情報を保持します。
*/
public final class GraphSummaryRow extends Row {
    /** グラフIDです。 */
    private String graphID;

    /** */
    private String checkSum;

    /** */
    private String sourceFile;

    /** */
    private int lineNumber;

    /** */
    private int complexity;

    /** */
    private int allBlocks;

    /** */
    private int executedBlocks;

    /** */
    private int allArcs;

    /** */
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
    public void set(final String graphID, final Function function) {
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
