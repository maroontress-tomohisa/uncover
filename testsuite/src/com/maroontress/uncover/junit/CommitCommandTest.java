package com.maroontress.uncover.junit;

import com.maroontress.uncover.Command;
import com.maroontress.uncover.CommitCommand;
import com.maroontress.uncover.CommitSource;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommitCommandTest {
    private static final String PROJECT_NAME = "test";
    private static final String REVISION = "1.0";
    private static final String PLATFORM = "FreeBSD 8.0";
    private static final String TIMESTAMP = "2010-04-30 12:34:56";
    private Properties prop;
    private CommitSource source;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new CommitCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new CommitCommand(prop, av);
	command.run();
    }

    private void run(final DB db, final String[] av) {
	Toolkit.setInstance(new TestToolkit() {
	    public DB createDB(final String subname) {
		return db;
	    }
	});
	run(av);
    }

    private DB createDB() {
	return new TestDB() {
	    public void commit(final CommitSource s)
		throws DBException {
		source = s;
	    }
	};
    }

    private DB createFailDB() {
	return new TestDB() {
	    public void commit(final CommitSource s)
		throws DBException {
		throw new DBException("failed.");
	    }
	};
    }

    @Test public void runShort() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "file.xml"};
	run(createDB(), av);
	assertEquals(PROJECT_NAME, source.getProjectName());
	assertEquals(REVISION, source.getRevision());
	assertTrue(source.getAllFunctionGraphs() instanceof TestParser);
    }

    @Test public void runFull() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "--platform=" + PLATFORM,
		       "--timestamp=" + TIMESTAMP,
		       "file.xml"};
	run(createDB(), av);
	assertEquals(PROJECT_NAME, source.getProjectName());
	assertEquals(REVISION, source.getRevision());
	assertEquals(PLATFORM, source.getPlatform());
	assertEquals(TIMESTAMP, source.getTimestamp());
	assertTrue(source.getAllFunctionGraphs() instanceof TestParser);
    }

    @Test(expected=ExitException.class) public void runNotFound() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "notfound"};
	run(createDB(), av);
    }

    @Test(expected=ExitException.class) public void runCommitFailed() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "test.xml"};
	run(createFailDB(), av);
    }

    @Test(expected=ExitException.class) public void fileNotSpecified() {
	String[] av = {"--project=test", "--revision=1.0"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"--project=test", "--revision=1.0", "1.xml", "2.xml"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectNotSpecified() {
	String[] av = {"--revision=1.0", "test.xml"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectEmpty() {
	String[] av = {"--project=", "--revision=1.0", "test.xml"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void revisionNotSpecified() {
	String[] av = {"--project=test", "test.xml"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void revisionEmpty() {
	String[] av = {"--project=test", "--revision=", "test.xml"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void revisionStartsWithAt() {
	String[] av = {"--project=test", "--revision=@1.0", "test.xml"};
	construct(av);
    }
}
