package com.maroontress.uncover.junit;

import com.maroontress.uncover.Build;
import com.maroontress.uncover.Command;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.DeleteRevisionCommand;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeleteRevisionCommandTest {
    private static final String PROJECT_NAME = "test";
    private static final String REVISION = "1.0";
    private static final String BUILD_ID = "123";
    private static final String REVISION_ID = "@" + BUILD_ID;
    private static final String PLATFORM = "FreeBSD 8.0";
    private static final String TIMESTAMP = "2010-04-30 12:34:56";
    private Properties prop;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new DeleteRevisionCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new DeleteRevisionCommand(prop, av);
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

    @Test public void runRevision() {
	String[] av = {"--project=" + PROJECT_NAME, REVISION};
	DB db = new TestDB() {
	    public Build[] getBuilds(final String projectName,
				     final String rev) throws DBException {
		Build b = new Build(new TestBuildSource(BUILD_ID, REVISION,
							TIMESTAMP, PLATFORM));
		return new Build[] {b};
	    }
	};
	run(db, av);
    }

    @Test public void runRevisionID() {
	String[] av = {"--project=" + PROJECT_NAME, REVISION_ID};
	DB db = new TestDB() {
	    public Build getBuild(final String projectName, final String id)
		throws DBException {
		return new Build(new TestBuildSource(BUILD_ID, REVISION,
						     TIMESTAMP, PLATFORM));
	    }
	};
	run(db, av);
    }

    @Test public void runAll() {
	String[] av = {"--project=" + PROJECT_NAME, "--all", REVISION};
	run(av);
    }

    @Test(expected=ExitException.class) public void deleteFailed() {
	String[] av = {"--project=" + PROJECT_NAME, REVISION_ID};
	DB db = new TestDB() {
	    public Build getBuild(final String projectName, final String id)
		throws DBException {
		return new Build(new TestBuildSource(BUILD_ID, REVISION,
						     TIMESTAMP, PLATFORM));
	    }
	    public void deleteBuild(final String projectName, final String id)
		throws DBException {
		throw new DBException("failed to delete build.");
	    }
	};
	run(db, av);
    }

    @Test(expected=ExitException.class) public void multipleBuilds() {
	String[] av = {"--project=" + PROJECT_NAME, REVISION};
	DB db = new TestDB() {
	    public Build[] getBuilds(final String projectName,
				     final String rev) throws DBException {
		Build b1 = new Build(new TestBuildSource(BUILD_ID, REVISION,
							 TIMESTAMP, PLATFORM));
		Build b2 = new Build(new TestBuildSource(BUILD_ID, REVISION,
							 TIMESTAMP, PLATFORM));
		return new Build[] {b1, b2};
	    }
	};
	run(db, av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"--project=" + PROJECT_NAME, REVISION, REVISION};
	construct(av);
    }

    @Test(expected=ExitException.class) public void tooFewArgs() {
	String[] av = {"--project=" + PROJECT_NAME};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectNotSpecified() {
	String[] av = {REVISION};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectEmpty() {
	String[] av = {"--project=", REVISION};
	construct(av);
    }
}
