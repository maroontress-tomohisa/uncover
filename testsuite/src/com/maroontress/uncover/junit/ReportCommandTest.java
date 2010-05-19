package com.maroontress.uncover.junit;

import com.maroontress.uncover.Build;
import com.maroontress.uncover.Command;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.Function;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.ReportCommand;
import com.maroontress.uncover.Revision;
import com.maroontress.uncover.Toolkit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReportCommandTest {
    private static final String PROJECT_NAME = "test";
    private static final String BUILD_ID = "123";
    private static final String TIMESTAMP = "2010-04-30 12:34:56";
    private static final String PLATFORM = "FreeBSD 8.0";
    private static final String ANOTHER_PLATFORM = "Ubuntu 9.10";
    private Properties prop;
    private DB db;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new ReportCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new ReportCommand(prop, av);
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

    @Test public void run() {
	db = new TestDB() {
	    @Override
	    public Build[] getBuilds(final String projectName,
				     final String rev) throws DBException {
		Build b = new Build(new TestBuildSource(BUILD_ID, rev,
							TIMESTAMP, PLATFORM));
		return new Build[] {b};
	    }
	    @Override
	    public Revision getRevision(final String id) throws DBException {
		List<Function> list = new ArrayList<Function>();
		list.add(new Function(new TestFunctionSource()));
		return new Revision(list);
	    }
	};
	String[] av = {"--project=" + PROJECT_NAME, "1.0", "1.1"};
	run(db, av);
    }

    @Test public void runDifferentPlatforms() {
	db = new TestDB() {
	    @Override
	    public Build[] getBuilds(final String projectName,
				     final String rev) throws DBException {
		String platform;
		if (rev.equals("1.0")) {
		    platform = PLATFORM;
		} else {
		    platform = ANOTHER_PLATFORM;
		}
		Build b = new Build(
		    new TestBuildSource(BUILD_ID, rev, TIMESTAMP, platform));
		return new Build[] {b};
	    }
	    @Override
	    public Revision getRevision(final String id) throws DBException {
		List<Function> list = new ArrayList<Function>();
		list.add(new Function(new TestFunctionSource()));
		return new Revision(list);
	    }
	};
	String[] av = {"--project=" + PROJECT_NAME, "1.0", "1.1"};
	run(db, av);
    }

    @Test(expected=ExitException.class) public void multipleBuilds() {
	DB db = new TestDB() {
	    public Build[] getBuilds(final String projectName,
				     final String rev) throws DBException {
		TestBuildSource src
		    = new TestBuildSource(BUILD_ID, rev, TIMESTAMP, PLATFORM);
		Build b1 = new Build(src);
		Build b2 = new Build(src);
		return new Build[] {b1, b2};
	    }
	};
	String[] av = {"--project=" + PROJECT_NAME, "1.0", "1.1"};
	run(db, av);
    }

    @Test(expected=ExitException.class) public void getRevisionFailed() {
	DB db = new TestDB() {
	    @Override
	    public Build[] getBuilds(final String projectName,
				     final String rev) throws DBException {
		Build b = new Build(new TestBuildSource(BUILD_ID, rev,
							TIMESTAMP, PLATFORM));
		return new Build[] {b};
	    }
	    @Override
	    public Revision getRevision(final String id) throws DBException {
		throw new DBException("failed to get revision.");
	    }
	};
	String[] av = {"--project=" + PROJECT_NAME, "1.0", "1.1"};
	run(db, av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"--project=" + PROJECT_NAME, "1.0", "1.1", "1.2"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void tooFewArgs() {
	String[] av = {"--project=" + PROJECT_NAME, "1.0"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectNotSpecified() {
	String[] av = {"1.0", "1.1"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectEmpty() {
	String[] av = {"--project=", "1.0", "1.1"};
	construct(av);
    }
}
