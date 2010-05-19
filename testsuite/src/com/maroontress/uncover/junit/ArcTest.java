package com.maroontress.uncover.junit;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.ArcSource;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArcTest {
    private static final int START = 12;
    private static final int END = 34;
    private static final int COUNT = 56;
    private Arc fakeArc;
    private Arc arc;

    @Before public void init() {
	fakeArc = new Arc(new ArcSource() {
	    public int getStart() {
		return START;
	    }
	    public int getEnd() {
		return END;
	    }
	    public long getCount() {
		return COUNT;
	    }
	    public boolean isFake() {
		return true;
	    }
	});
	arc = new Arc(new ArcSource() {
	    public int getStart() {
		return START;
	    }
	    public int getEnd() {
		return END;
	    }
	    public long getCount() {
		return COUNT;
	    }
	    public boolean isFake() {
		return false;
	    }
	});
    }

    @Test public void getStart() {
	assertEquals(START, arc.getStart());
    }

    @Test public void getEnd() {
	assertEquals(END, arc.getEnd());
    }

    @Test public void getCount() {
	assertEquals(COUNT, arc.getCount());
    }

    @Test public void nonFake() {
	assertFalse(arc.isFake());
    }

    @Test public void fake() {
	assertTrue(fakeArc.isFake());
    }
}
