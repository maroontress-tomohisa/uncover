package com.maroontress.uncover.gcov;

import com.maroontress.coverture.Arc;
import com.maroontress.uncover.ArcSource;

/**
   com.maroontress.coverture.Arcをソースとするアークソースです。
*/
public final class GcovArcSource implements ArcSource {

    /** アークです。 */
    private Arc arc;

    /**
       インスタンスを生成します。
    */
    public GcovArcSource() {
    }

    /**
       アークを設定します。

       @param arc アーク
    */
    public void setArc(final Arc arc) {
	this.arc = arc;
    }

    /** {@inheritDoc} */
    public int getStart() {
	return arc.getStart().getId();
    }

    /** {@inheritDoc} */
    public int getEnd() {
	return arc.getEnd().getId();
    }

    /** {@inheritDoc} */
    public long getCount() {
	return arc.getCount();
    }

    /** {@inheritDoc} */
    public boolean isFake() {
	return arc.isFake();
    }
}
