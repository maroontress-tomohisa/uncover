package com.maroontress.uncover.junit;

import com.maroontress.uncover.Build;
import com.maroontress.uncover.BuildSource;
import com.maroontress.uncover.MultipleBuildsException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MultipleBuildsExceptionTest {
    private static final String REVISION = "REV";
    private static final String HOW_TO_FIX = "HOW_TO_FIX";
    private static final String DESC = ""
	+ "revision 'REV' has 2 results:%n"
	+ "%n"
	+ "ID: @1%n"
	+ "Platform: FreeBSD%n"
	+ "Timestamp: 2010%n"
	+ "%n"
	+ "ID: @2%n"
	+ "Platform: Linux%n"
	+ "Timestamp: 2011%n"
	+ "%n"
	+ "HOW_TO_FIX";
    private MultipleBuildsException e;

    @Before public void init() {
	Build[] builds = new Build[2];
	builds[0] = createBuild("1", "REV", "2010", "FreeBSD");
	builds[1] = createBuild("2", "REV", "2011", "Linux");
	e = new MultipleBuildsException(REVISION, builds, HOW_TO_FIX);
    }

    private Build createBuild(final String id, final String revision,
			      final String timestamp, final String platform) {
	return new Build(new BuildSource() {
	    public String getID() {
		return id;
	    }
	    public String getRevision() {
		return revision;
	    }
	    public String getTimestamp() {
		return timestamp;
	    }
 	    public String getPlatform() {
		return platform;
	    }
	});
    }

    @Test public void printDescription() {
	String desc = String.format(DESC);
	ByteArrayOutputStream pool = new ByteArrayOutputStream();
	PrintStream out = new PrintStream(pool);
	e.printDescription(out);
	out.close();
	assertEquals(desc, pool.toString());
    }
}
