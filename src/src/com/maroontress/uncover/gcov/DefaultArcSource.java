package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.DefaultArc;
import com.maroontress.uncover.ArcSource;

/**
   com.maroontress.gcovparser.DefaultArcをソースとするアークソースで
   す。
*/
public final class DefaultArcSource implements ArcSource {

    /** アークです。 */
    private DefaultArc arc;

    /**
       インスタンスを生成します。
    */
    public DefaultArcSource() {
    }

    /**
       アークを設定します。

       @param arc アーク
    */
    public void setArc(final DefaultArc arc) {
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
