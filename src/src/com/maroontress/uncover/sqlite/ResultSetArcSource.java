package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.ArcSource;

/**
   クエリ結果からアークを生成するためのアークソースです。
*/
public final class ResultSetArcSource extends Row implements ArcSource {
    /** 始点となる基本ブロックの番号です。 */
    private int start;

    /** 終点となる基本ブロックの番号です。 */
    private int end;

    /** 実行回数です。 */
    private int count;

    /** フェイクかどうかを表します。 */
    private int fake;

    /**
       インスタンスを生成します。
    */
    public ResultSetArcSource() {
    }

    /** {@inheritDoc} */
    public int getStart() {
	return start;
    }

    /** {@inheritDoc} */
    public int getEnd() {
	return end;
    }

    /** {@inheritDoc} */
    public int getCount() {
	return count;
    }

    /** {@inheritDoc} */
    public boolean isFake() {
	return (fake != 0);
    }
}
