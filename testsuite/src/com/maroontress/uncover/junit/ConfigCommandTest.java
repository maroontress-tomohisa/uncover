package com.maroontress.uncover.junit;

import com.maroontress.uncover.Command;
import com.maroontress.uncover.ConfigCommand;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import java.util.prefs.Preferences;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigCommandTest {
    private Properties prop;
    private DB db;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new ConfigCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new ConfigCommand(prop, av);
	command.run();
    }

    @Test public void run() {
	String[] av = {"db.default", "test.db"};
	run(av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"key", "value", "extra"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void tooFewArgs() {
	String[] av = {""};
	construct(av);
    }

    @Test(expected=ExitException.class) public void unknownKey() {
	String[] av = {"unknown.key", "value"};
	construct(av);
    }

    @Test(expected=ExitException.class) public void longValue() {
	StringBuilder b = new StringBuilder();
	for (int k = 0; k < Preferences.MAX_VALUE_LENGTH + 1; ++k) {
	    b.append('x');
	}
	String[] av = {"db.default", b.toString()};
	construct(av);
    }
}
