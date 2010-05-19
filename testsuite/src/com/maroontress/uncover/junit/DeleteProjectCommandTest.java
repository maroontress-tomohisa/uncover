package com.maroontress.uncover.junit;

import com.maroontress.uncover.Command;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.DeleteProjectCommand;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DeleteProjectCommandTest {
    private Properties prop;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new DeleteProjectCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new DeleteProjectCommand(prop, av);
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
	String[] av = {"foo"};
	run(av);
    }

    @Test(expected=ExitException.class) public void runThrowsDBException() {
	DB db = new TestDB() {
	    public void deleteProject(final String s) throws DBException {
		throw new DBException("failed to delete project.");
	    }
	};
	String[] av = {"foo"};
	run(db, av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"foo", "bar"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void tooFewArgs() {
	String[] av = {};
	construct(av);
    }
}
