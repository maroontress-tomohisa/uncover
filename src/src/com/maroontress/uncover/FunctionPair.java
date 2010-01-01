package com.maroontress.uncover;

import java.util.Comparator;

/**
   関数ペアです。
*/
public final class FunctionPair {
    /** 複雑度順にソートするためのコンパレータです。 */
    public static final
    Comparator<FunctionPair> COMPLEXITY_DELTA_COMPARATOR;

    /** 基本ブロック数順にソートするためのコンパレータです。 */
    public static final
    Comparator<FunctionPair> ALL_BLOCKS_DELTA_COMPARATOR;

    /** 未実行の基本ブロック数順にソートするためのコンパレータです。 */
    public static final
    Comparator<FunctionPair> UNEXECUTED_BLOCKS_DELTA_COMPARATOR;

    /** 左の関数です。 */
    private Function left;

    /** 右の関数です。 */
    private Function right;

    static {
	COMPLEXITY_DELTA_COMPARATOR = new Comparator<FunctionPair>() {
	    public int compare(final FunctionPair p1, final FunctionPair p2) {
		int d;

		int d1 = p1.getComplexityDelta();
		int d2 = p2.getComplexityDelta();
		if ((d = -(d1 - d2)) != 0) {
		    return d;
		}
		return p1.right.compareTo(p2.right);
	    }
	};

	ALL_BLOCKS_DELTA_COMPARATOR = new Comparator<FunctionPair>() {
	    public int compare(final FunctionPair p1, final FunctionPair p2) {
		int d;

		int d1 = p1.getAllBlocksDelta();
		int d2 = p2.getAllBlocksDelta();
		if ((d = -(d1 - d2)) != 0) {
		    return d;
		}
		return p1.right.compareTo(p2.right);
	    }
	};

	UNEXECUTED_BLOCKS_DELTA_COMPARATOR = new Comparator<FunctionPair>() {
	    public int compare(final FunctionPair p1, final FunctionPair p2) {
		int d;

		int d1 = p1.getUnexecutedBlocksDelta();
		int d2 = p2.getUnexecutedBlocksDelta();
		if ((d = -(d1 - d2)) != 0) {
		    return d;
		}
		return p1.right.compareTo(p2.right);
	    }
	};
    }

    /**
       インスタンスを生成します。

       @param left 左の関数
       @param right 右の関数
    */
    public FunctionPair(final Function left, final Function right) {
	this.left = left;
	this.right = right;
    }

    /**
       左の関数を取得します。

       @return 左の関数
    */
    public Function getLeft() {
	return left;
    }

    /**
       右の関数を取得します。

       @return 右の関数
    */
    public Function getRight() {
	return right;
    }

    /**
       複雑度の差分を取得します。

       @return 複雑度の差分
    */
    public int getComplexityDelta() {
	return right.getComplexity() - left.getComplexity();
    }

    /**
       基本ブロック総数の差分を取得します。

       @return 基本ブロック総数の差分
    */
    public int getAllBlocksDelta() {
	return right.getAllBlocks() - left.getAllBlocks();
    }

    /**
       未実行の基本ブロック総数の差分を取得します。

       @return 未実行の基本ブロック総数の差分
    */
    public int getUnexecutedBlocksDelta() {
	return right.getUnexecutedBlocks() - left.getUnexecutedBlocks();
    }
}
