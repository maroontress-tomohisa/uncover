package com.maroontress.uncover.junit;

import com.maroontress.uncover.ArcSource;

public class TestArcSource implements ArcSource {
    private int start;
    private int end;
    private long count;
    private boolean fake;

    public TestArcSource() {
	this(1, 2, 3, true);
    }

    public TestArcSource(final int start, final int end, final long count,
			 final boolean fake) {
 	this.start = start;
 	this.end = end;
 	this.count = count;
 	this.fake = fake;
    }

    public int getStart() {
	return start;
    }
    public int getEnd() {
	return end;
    }
    public long getCount() {
	return count;
    }
    public boolean isFake() {
	return fake;
    }
}
