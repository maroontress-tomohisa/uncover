package com.maroontress.uncover;

import java.util.Comparator;

/**
   関数に関する情報をカプセル化します。
*/
public abstract class Function implements Comparable<Function> {
    /** 複雑度順にソートするコンパレータです。 */
    public static final Comparator<Function> COMPLEXITY_COMPARATOR;

    /** 関数名です。 */
    private String name;

    /** 関数が出現するソースファイルです。 */
    private String sourceFile;

    /** 由来するgcnoファイルです。 */
    private String gcnoFile;

    /** 関数のチェックサムです。 */
    private String checkSum;

    /** 関数が出現するソースファイルでの行番号です。 */
    private int lineNumber;

    /**
       複雑度です。FAKEのアークを除外した場合のMcCabeのcyclomatic
       complexityに相当します。
    */
    private int complexity;

    /** 実行済みの基本ブロックの数です。 */
    private int executedBlocks;

    /** 基本ブロックの総数です。 */
    private int allBlocks;

    /** 実行済みのアークの数です。 */
    private int executedArcs;

    /** アークの総数です。 */
    private int allArcs;

    /**
       同一プロジェクトでユニークな識別子です。"関数名" + "@" + "gcno
       ファイルのファイル名" の形式になります。
    */
    private String key;

    /**
       インスタンスを生成します。
    */
    protected Function() {
    }

    /**
       ソースコードのパラメータを設定します。

       @param name 関数名
       @param sourceFile 関数が出現するソースファイル
       @param lineNumber 関数が出現するソースファイルでの行番号
       @param gcnoFile 由来するgcnoファイル
       @param checkSum 関数のチェックサム
    */
    protected final void setSource(final String name,
				   final String sourceFile,
				   final int lineNumber,
				   final String gcnoFile,
				   final String checkSum) {
	this.name = name;
	this.sourceFile = sourceFile;
	this.lineNumber = lineNumber;
	this.gcnoFile = gcnoFile;
	this.checkSum = checkSum;
	key = name + "@" + gcnoFile;
    }

    /**
       フローグラフのパラメータを設定します。

       @param complexity 複雑度
       @param executedBlocks 実行済みの基本ブロックの数
       @param allBlocks 基本ブロックの総数
       @param executedArcs 実行済みのアークの数
       @param allArcs アークの総数
    */
    protected final void setGraph(final int complexity,
				  final int executedBlocks,
				  final int allBlocks,
				  final int executedArcs,
				  final int allArcs) {
	this.complexity = complexity;
	this.executedBlocks = executedBlocks;
	this.allBlocks = allBlocks;
	this.executedArcs = executedArcs;
	this.allArcs = allArcs;
    }

    static {
	COMPLEXITY_COMPARATOR = new Comparator<Function>() {
	    public int compare(final Function f1, final Function f2) {
		int d;

		if ((d = -(f1.complexity - f2.complexity)) != 0) {
		    return d;
		}
		return f1.compareTo(f2);
	    }
	};
    }

    /** {@inheritDoc} */
    public final int compareTo(final Function function) {
	int d;

	if ((d = name.compareTo(function.name)) != 0) {
	    return d;
	}
	return gcnoFile.compareTo(function.gcnoFile);
    }

    /**
       チェックサムを取得します。

       @return チェックサム
    */
    public final String getCheckSum() {
	return checkSum;
    }

    /**
       複雑度を取得します。

       @return 複雑度
    */
    public final int getComplexity() {
	return complexity;
    }

    /**
       プロジェクトでユニークな識別子を取得します。

       @return プロジェクトでユニークな識別子
    */
    public final String getKey() {
	return key;
    }

    /**
       関数名を取得します。

       @return 関数名
    */
    public final String getName() {
	return name;
    }

    /**
       出現位置を取得します。

       出現位置は "ソースファイル名" + ":" + "行番号" という形式になり
       ます。

       @return 出現位置
    */
    public final String getLocation() {
	return sourceFile + ":" + lineNumber;
    }

    /**
       gcnoファイル名を取得します。

       @return gcnoファイル名
    */
    public final String getGCNOFile() {
	return gcnoFile;
    }

    /**
       ソースファイル名を取得します。

       @return ソースファイル名
    */
    public final String getSourceFile() {
	return sourceFile;
    }

    /**
       ソースファイルで出現する行番号を取得します。

       @return ソースファイルで出現する行番号
    */
    public final int getLineNumber() {
	return lineNumber;
    }

    /**
       総数に対する実行済み基本ブロックの割合を取得します。

       0から1の間の数値になります。

       @return 総数に対する実行済み基本ブロックの数の割合
    */
    public final float getBlockRate() {
	if (allBlocks == 0) {
	    return 0;
	}
	return (float) executedBlocks / allBlocks;
    }

    /**
       実行済みの基本ブロック数を取得します。

       @return 実行済みの基本ブロック数
    */
    public final int getExecutedBlocks() {
	return executedBlocks;
    }

    /**
       基本ブロック総数を取得します。

       @return 基本ブロック総数
    */
    public final int getAllBlocks() {
	return allBlocks;
    }

    /**
       未実行の基本ブロック数を取得します。

       @return 未実行の基本ブロック数
    */
    public final int getUnexecutedBlocks() {
	return allBlocks - executedBlocks;
    }

    /**
       実行済みのアーク数を取得します。

       @return 実行済みのアーク数
    */
    public final int getExecutedArcs() {
	return executedArcs;
    }

    /**
       アーク総数を取得します。

       @return アーク総数
    */
    public final int getAllArcs() {
	return allArcs;
    }
}
