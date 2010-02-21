package com.maroontress.uncover.html;

import com.maroontress.uncover.Function;
import com.maroontress.uncover.FunctionPair;

/**
   関数の指標変化の傾向や差分を表す文字列を生成するための機能を提供します。
*/
public abstract class Arrow {
    /**
       インスタンスを生成します。
    */
    protected Arrow() {
    }

    /**
       関数の指標変化の傾向や差分を表す文字列を取得します。

       @param v1 前の値
       @param v2 今の値
       @return 指標変化を表す文字列
    */
    protected abstract String get(int v1, int v2);

    /**
       基本ブロック数の変化を表す文字列を取得します。

       @param pair 関数ペア
       @return 指標変化を表す文字列
    */
    public final String allBlocks(final FunctionPair pair) {
	return get(pair.getLeft().getAllBlocks(),
		   pair.getRight().getAllBlocks());
    }

    /**
       複雑度の変化を表す文字列を取得します。

       @param pair 関数ペア
       @return 指標変化を表す文字列
    */
    public final String complexity(final FunctionPair pair) {
        return complexity(pair.getLeft(), pair.getRight());
    }

    /**
       複雑度の変化を表す文字列を取得します。

       @param left 前の関数
       @param right 今の関数
       @return 指標変化を表す文字列
    */
    public final String complexity(final Function left, final Function right) {
        return get(left.getComplexity(), right.getComplexity());
    }

    /**
       未実行の基本ブロック数の変化を表す文字列を取得します。

       @param pair 関数ペア
       @return 指標変化を表す文字列
    */
    public final String unexecutedBlocks(final FunctionPair pair) {
        return get(pair.getLeft().getUnexecutedBlocks(),
		   pair.getRight().getUnexecutedBlocks());
    }

    /**
       順位の変化を表す文字列を取得します。

       @param v1 前の順位
       @param v2 今の順位
       @return 指標変化を表す文字列
    */
    public final String rank(final int v1, final int v2) {
	return get(v1, v2);
    }
}
