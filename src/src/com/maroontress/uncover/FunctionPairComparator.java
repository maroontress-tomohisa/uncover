package com.maroontress.uncover;

import java.util.Comparator;

/**
   関数ペアから特定の値を取得して比較するコンパレータです。
*/
public abstract class FunctionPairComparator
    implements Comparator<FunctionPair> {
    /** {@inheritDoc} */
    public final int compare(final FunctionPair p1, final FunctionPair p2) {
	int d = -(getInt(p1) - getInt(p2));
	if (d != 0) {
	    return d;
	}
	return FunctionPair.compare(p1, p2);
    }

    /**
       関数ペアから特定の値を取得します。

       @param p 関数ペア
       @return 整数値
    */
    public abstract int getInt(FunctionPair p);
}
