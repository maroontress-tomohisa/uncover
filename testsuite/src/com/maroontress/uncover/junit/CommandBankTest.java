package com.maroontress.uncover.junit;

import com.maroontress.uncover.Command;
import com.maroontress.uncover.CommandBank;
import com.maroontress.uncover.Properties;
import com.maroontress.uncover.Toolkit;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandBankTest {
    private static final int INDENT_WITH = 12;
    private CommandBank bank;
    private TestCommand testCommand;
    private Properties prop;

    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
	prop = new Properties();
	prop.setDBFile("test.db");
	testCommand = new TestCommand(prop, null);
	bank = new CommandBank();
    }

    @Test public void getHelpMessage0() {
	assertEquals("", bank.getHelpMessage(INDENT_WITH));
    }

    @Test public void createCommand0() {
	assertNull(bank.createCommand("none", prop, null));
    }

    @Test public void getHelpMessage() {
	bank.addCommandClass(TestCommand.class);
	assertEquals("test        This command is for unit test.\n",
		     bank.getHelpMessage(INDENT_WITH));
    }

    @Test public void createCommand() {
	bank.addCommandClass(TestCommand.class);
	Command command = bank.createCommand("test", prop, null);
	assertTrue(command instanceof TestCommand);
    }
}
