package com.maroontress.uncover.junit;

import com.maroontress.cui.Options;
import com.maroontress.uncover.DBCommand;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.Properties;

public class TestDBCommand extends DBCommand {
    public static final String NAME = "test";
    public static final String ARGS = "";
    public static final String DESC = "This command is for unit test.";
    private boolean called;

    public TestDBCommand(final Properties prop, final String[] av) {
	super(prop);

        Options opt = getOptions();
        opt.add("test", "option for unit test.");

	called = false;
    }

    public void run(final DB db) {
	called = true;
    }

    public boolean isCalled() {
	return called;
    }
}
