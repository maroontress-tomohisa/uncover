package com.maroontress.uncover.junit;

import com.maroontress.uncover.Arc;
import com.maroontress.uncover.Block;
import com.maroontress.uncover.Build;
import com.maroontress.uncover.Command;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.Graph;
import com.maroontress.uncover.GraphCommand;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphCommandTest {
    private static final String PROJECT_NAME = "test";
    private static final String REVISION = "1.0";
    private static final String PLATFORM = "FreeBSD 8.0";
    private static final String TIMESTAMP = "2010-04-30 12:34:56";
    private static final String BUILD_ID = "123";
    private Properties prop;
    private DB db;
    private Graph graph;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new GraphCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new GraphCommand(prop, av);
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

    private void run(final String[] av, final Graph graph) {
	db = new TestDB() {
	    public Build[] getBuilds(final String projectName,
				     final String rev) throws DBException {
		Build b = new Build(new TestBuildSource(BUILD_ID, REVISION,
							TIMESTAMP, PLATFORM));
		return new Build[] {b};
	    }
	    public Graph getGraph(final String projectName,
				  final String id,
				  final String function,
				  final String gcnoFile) throws DBException {
		return graph;
	    }
	};
	run(db, av);
    }

    @Test public void run() {
	Block[] blocks = new Block[3];
	blocks[0] = new Block(new TestBlockSource(1, 2, "foo.c", 34));
	blocks[1] = new Block(new TestBlockSource(2, 0, "foo.c", 0));
	blocks[2] = new Block(new TestBlockSource(3, 3, null, 0));
	Arc[] arcs = new Arc[2];
	arcs[0] = new Arc(new TestArcSource(1, 2, 0, true));
	arcs[1] = new Arc(new TestArcSource(1, 3, 2, false));
	graph = new Graph(new TestGraphSource(blocks, arcs));

	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "function@file.gcno"};
	run(av, graph);
    }

    @Test(expected=ExitException.class) public void multipleBuilds() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "function@file.gcno"};
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

    @Test(expected=ExitException.class) public void getGraphFailed() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "function@file.gcno"};
	DB db = new TestDB() {
	    public Build[] getBuilds(final String projectName,
				     final String rev) throws DBException {
		Build b = new Build(new TestBuildSource(BUILD_ID, REVISION,
							TIMESTAMP, PLATFORM));
		return new Build[] {b};
	    }
	    public Graph getGraph(final String projectName,
				  final String id,
				  final String function,
				  final String gcnoFile) throws DBException {
		throw new DBException("failed to get graph.");
	    }
	};
	run(db, av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "function@file.gcno",
		       "function@file.gcno"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void tooFewArgs() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectNotSpecified() {
	String[] av = {"--revision=" + REVISION,
		       "function@file.gcno"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectEmpty() {
	String[] av = {"--project=",
		       "--revision=" + REVISION,
		       "function@file.gcno"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void revisionNotSpecified() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "function@file.gcno"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void revisionEmpty() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=",
		       "function@file.gcno"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void invalidArgument() {
	String[] av = {"--project=" + PROJECT_NAME,
		       "--revision=" + REVISION,
		       "funcition"};
	construct(av);
    }
}
