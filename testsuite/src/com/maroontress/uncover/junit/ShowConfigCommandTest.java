package com.maroontress.uncover.junit;

import com.maroontress.uncover.Command;
import com.maroontress.uncover.ConfigKey;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.ExitException;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.ShowConfigCommand;
import com.maroontress.uncover.Toolkit;
import com.maroontress.uncover.Uncover;
import java.util.prefs.Preferences;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShowConfigCommandTest {
    private Properties prop;
    private DB db;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
    }

    private void construct(final String[] av) {
	Command command = new ShowConfigCommand(prop, av);
    }

    private void run(final String[] av) {
	Command command = new ShowConfigCommand(prop, av);
	command.run();
    }

    @Test public void runNull() {
        Preferences prefs = Preferences.userNodeForPackage(Uncover.class);
	prefs.remove(ConfigKey.DB_DEFAULT);

	String[] av = {};
	run(av);
    }

    @Test public void run() {
        Preferences prefs = Preferences.userNodeForPackage(Uncover.class);
	prefs.put(ConfigKey.DB_DEFAULT, "foo.db");

	String[] av = {};
	run(av);
    }

    @Test(expected=ExitException.class) public void tooManyArgs() {
	String[] av = {"extra"};
	construct(av);
    }
}
