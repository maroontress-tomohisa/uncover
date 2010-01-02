package com.maroontress.uncover;

import java.util.Comparator;

/**
   関数ペアです。
*/
public final class FunctionPair {
    /** 複雑度順にソートするためのコンパレータです。 */
    public static final Comparator<FunctionPair> COMPLEXITY_DELTA_COMPARATOR;

    /**
       基本ブロック数の差分順にソートするためのコンパレータです。
    */
    public static final Comparator<FunctionPair> ALL_BLOCKS_DELTA_COMPARATOR;

    /**
       未実行の基本ブロック数の差分順にソートするためのコンパレータです。
    */
    public static final Comparator<FunctionPair>
    UNEXECUTED_BLOCKS_DELTA_COMPARATOR;

    /** 左の関数です。 */
    private Function left;

    /** 右の関数です。 */
    private Function right;

    static {
	COMPLEXITY_DELTA_COMPARATOR = new FunctionPairComparator() {
	    public int getInt(final FunctionPair p) {
		return p.getComplexityDelta();
	    }
	};
	ALL_BLOCKS_DELTA_COMPARATOR = new FunctionPairComparator() {
	    public int getInt(final FunctionPair p) {
		return p.getAllBlocksDelta();
	    }
	};
	UNEXECUTED_BLOCKS_DELTA_COMPARATOR = new FunctionPairComparator() {
	    public int getInt(final FunctionPair p) {
		return p.getUnexecutedBlocksDelta();
	    }
	};
    }

    /**
       2つの関数ペアをデフォルトの方式で比較します。

       @param p1 関数ペア
       @param p2 関数ペア
       @return p1 < p2ならば負、p1 > p2ならば正、そうでなければ0
    */
    public static int compare(final FunctionPair p1, final FunctionPair p2) {
	return Function.DEFAULT_COMPARATOR.compare(p1.right, p2.right);
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
