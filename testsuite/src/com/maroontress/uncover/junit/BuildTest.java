package com.maroontress.uncover.junit;

import com.maroontress.uncover.Build;
import com.maroontress.uncover.BuildSource;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BuildTest {
    private static final String ID = "1234";
    private static final String REVISION = "1.0.0";
    private static final String TIMESTAMP = "2010-01-01 00:00:00";
    private static final String PLATFORM = "FreeBSD 8.0-RELEASE";
    private Build build;

    @Before public void init() {
	build = new Build(new BuildSource() {
	    public String getID() {
		return ID;
	    }
	    public String getRevision() {
		return REVISION;
	    }
	    public String getTimestamp() {
		return TIMESTAMP;
	    }
 	    public String getPlatform() {
		return PLATFORM;
	    }
	});
    }

    @Test public void getID() {
	assertEquals(ID, build.getID());
    }

    @Test public void getRevision() {
	assertEquals(REVISION, build.getRevision());
    }

    @Test public void getTimestamp() {
	assertEquals(TIMESTAMP, build.getTimestamp());
    }

    @Test public void getPlatform() {
	assertEquals(PLATFORM, build.getPlatform());
    }
}
