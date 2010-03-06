package com.maroontress.uncover;

/**
   アークに関する情報をカプセル化します。
*/
public final class Arc implements ArcSource {
    /** 始点となる基本ブロックの番号です。 */
    private int start;

    /** 終点となる基本ブロックの番号です。 */
    private int end;

    /** 実行回数です。 */
    private int count;

    /** フェイクかどうかを表します。 */
    private boolean isFake;

    /**
       デフォルトコンストラクタは使用できません。
    */
    private Arc() {
    }

    /**
       インスタンスを生成します。

       @param s アークソース
    */
    public Arc(final ArcSource s) {
	start = s.getStart();
	end = s.getEnd();
	count = s.getCount();
	isFake = s.isFake();
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
	return isFake;
    }
}
