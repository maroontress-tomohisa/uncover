package com.maroontress.uncover;

import java.util.Comparator;

/**
   関数に関する情報をカプセル化します。
*/
public final class Function implements FunctionSource {
    /** デフォルトのコンパレータです。 */
    public static final Comparator<Function> DEFAULT_COMPARATOR;

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

    static {
	DEFAULT_COMPARATOR = new Comparator<Function>() {
	    public int compare(final Function f1, final Function f2) {
		int d;

		if ((d = f1.name.compareTo(f2.name)) != 0) {
		    return d;
		}
		return f1.gcnoFile.compareTo(f2.gcnoFile);
	    }
	};
	COMPLEXITY_COMPARATOR = new Comparator<Function>() {
	    public int compare(final Function f1, final Function f2) {
		int d;

		if ((d = -(f1.complexity - f2.complexity)) != 0) {
		    return d;
		}
		return DEFAULT_COMPARATOR.compare(f1, f2);
	    }
	};
    }

    /**
       デフォルトコンストラクタは使用できません。
    */
    private Function() {
    }

    /**
       インスタンスを生成します。

       @param s 関数ソース
    */
    public Function(final FunctionSource s) {
	name = s.getName();
	sourceFile = s.getSourceFile().intern();
	gcnoFile = s.getGCNOFile().intern();
	checkSum = s.getCheckSum();
	lineNumber = s.getLineNumber();
	complexity = s.getComplexity();
	executedBlocks = s.getExecutedBlocks();
	allBlocks = s.getAllBlocks();
	executedArcs = s.getExecutedArcs();
	allArcs = s.getAllArcs();
	key = name + "@" + gcnoFile;
    }

    /** {@inheritDoc} */
    public String getCheckSum() {
	return checkSum;
    }

    /** {@inheritDoc} */
    public int getComplexity() {
	return complexity;
    }

    /** {@inheritDoc} */
    public String getName() {
	return name;
    }

    /** {@inheritDoc} */
    public String getGCNOFile() {
	return gcnoFile;
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
	return sourceFile;
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	return lineNumber;
    }

    /** {@inheritDoc} */
    public int getExecutedBlocks() {
	return executedBlocks;
    }

    /** {@inheritDoc} */
    public int getAllBlocks() {
	return allBlocks;
    }

    /** {@inheritDoc} */
    public int getExecutedArcs() {
	return executedArcs;
    }

    /** {@inheritDoc} */
    public int getAllArcs() {
	return allArcs;
    }

    /**
       プロジェクトでユニークな識別子を取得します。

       識別子は "関数名" + "@" + "gcnoファイルのファイル名" の形式にな
       ります。

       @return プロジェクトでユニークな識別子
    */
    public String getKey() {
	return key;
    }

    /**
       総数に対する実行済み基本ブロックの割合を取得します。

       0から1の間の数値になります。

       @return 総数に対する実行済み基本ブロックの数の割合
    */
    public float getBlockRate() {
	if (allBlocks == 0) {
	    return 0;
	}
	return (float) executedBlocks / allBlocks;
    }

    /**
       未実行の基本ブロック数を取得します。

       @return 未実行の基本ブロック数
    */
    public int getUnexecutedBlocks() {
	return allBlocks - executedBlocks;
    }

    /**
       出現位置を取得します。

       出現位置は "ソースファイル名" + ":" + "行番号" という形式になり
       ます。

       @return 出現位置
    */
    public String getLocation() {
	return sourceFile + ":" + lineNumber;
    }
}
