package com.maroontress.uncover.junit;

import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import com.maroontress.uncover.ExitException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DBCommandTest {
    private TestDBCommand testCommand;
    private Properties prop;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
	testCommand = new TestDBCommand(prop, null);
    }

    @Test public void run() {
	testCommand.run();
	assertTrue(testCommand.isCalled());
    }

    @Test(expected=ExitException.class) public void dbFileNotSpecified() {
	prop.setDBFile(null);
	testCommand.run();
    }

    @Test(expected=ExitException.class) public void cannotOpenDB() {
	// make sure that Toolkit.createDB() throws DBException.
	Toolkit.setInstance(new TestToolkit() {
	    public DB createDB(final String subname) throws DBException {
		throw new DBException("createDB() failed.");
	    }
	});
	testCommand.run();
    }

    @Test(expected=ExitException.class) public void cannotCloseDB() {
	// make sure that DB.close() throws DBException.
	Toolkit.setInstance(new TestToolkit() {
	    public DB createDB(final String subname) throws DBException {
		return new TestDB() {
		    public void close() throws DBException {
			throw new DBException("close() failed.");
		    }
		};
	    }
	});
	testCommand.run();
    }
}
