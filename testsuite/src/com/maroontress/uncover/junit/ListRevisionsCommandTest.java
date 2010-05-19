package com.maroontress.uncover.junit;

import com.maroontress.uncover.Command;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.ListRevisionsCommand;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ListRevisionsCommandTest {
    private static final String PROJECT_NAME = "test";
    private Properties prop;
    private DB db;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new ListRevisionsCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new ListRevisionsCommand(prop, av);
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
	String[] av = {"--project=" + PROJECT_NAME};
	DB db = new TestDB() {
	    @Override public String[] getRevisionNames(final String name)
		throws DBException {
		String[] names = {"1.0", "1.1"};
		return names;
	    }
	};
	run(db, av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"--project=" + PROJECT_NAME, "extra"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectNotSpecified() {
	String[] av = {};
	construct(av);
    }

    @Test(expected=ExitException.class) public void projectEmpty() {
	String[] av = {"--project="};
	construct(av);
    }

    @Test(expected=ExitException.class) public void getProjectNamesFailed() {
	String[] av = {"--project=" + PROJECT_NAME};
	DB db = new TestDB() {
	    @Override public String[] getRevisionNames(final String name)
		throws DBException {
		throw new DBException("failed to get revision names.");
	    }
	};
	run(db, av);
    }
}
