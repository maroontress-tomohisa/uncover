package com.maroontress.uncover.junit;

import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import com.maroontress.uncover.ExitException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandTest {
    private TestCommand testCommand;
    private Properties prop;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
	testCommand = new TestCommand(prop, null);
    }

    @Test public void run() {
	testCommand.run();
	assertTrue(testCommand.isCalled());
    }

    @Test(expected=ExitException.class) public void help() {
	String[] av = {"--help"};
	testCommand.invokeParseArguments(av);
    }

    @Test(expected=ExitException.class) public void unknownOption() {
	String[] av = {"--unknown-option"};
	testCommand.invokeParseArguments(av);
    }

    @Test public void parseArguments() {
	String[] av = {"--test", "arg1", "arg2"};
	String[] parsed = testCommand.invokeParseArguments(av);
	assertEquals(2, parsed.length);
	assertEquals(av[1], parsed[0]);
	assertEquals(av[2], parsed[1]);
    }
}
