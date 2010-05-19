package com.maroontress.uncover.junit;

import com.maroontress.cui.Options;
import com.maroontress.uncover.Command;
import com.maroontress.uncover.Properties;

public class TestCommand extends Command {
    public static final String NAME = "test";
    public static final String ARGS = "";
    public static final String DESC = "This command is for unit test.";
    private boolean called;

    public TestCommand(final Properties prop, final String[] av) {
	super(prop);

        Options opt = getOptions();
        opt.add("test", "option for unit test.");

	called = false;
    }

    public void run() {
	called = true;
    }

    public boolean isCalled() {
	return called;
    }

    public String[] invokeParseArguments(final String[] av) {
	return parseArguments(av);
    }
}
