package com.maroontress.uncover.junit;

import com.maroontress.uncover.Command;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.InitCommand;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InitCommandTest {
    private Properties prop;
    private DB db;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new InitCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new InitCommand(prop, av);
	command.run();
    }

    @Test public void run() {
	String[] av = {};
	run(av);
    }

    private void run(final DB db, final String[] av) {
	Toolkit.setInstance(new TestToolkit() {
	    public DB createDB(final String subname) {
		return db;
	    }
	});
	run(av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"extra"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void initializeFailed() {
	String[] av = {};
	DB db = new TestDB() {
	    public void initialize() throws DBException {
		throw new DBException("failed to intialize.");
	    }
	};
	run(db, av);
    }
}
