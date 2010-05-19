package com.maroontress.uncover.junit;

import com.maroontress.uncover.Toolkit;
import com.maroontress.uncover.Uncover;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UncoverTest {
    @Before public void init() {
	Toolkit.setInstance(new TestToolkit());
    }

    @Test public void help() {
	String[] av = {"--help"};
	Uncover.main(av);
    }

    @Test public void version() {
	String[] av = {"--version"};
	Uncover.main(av);
    }

    @Test public void unknownCommand() {
	String[] av = {"null"};
	Uncover.main(av);
    }

    @Test public void db() {
	String[] av = {"--db=foo.db"};
	Uncover.main(av);
    }

    @Test public void commandNotSpecified() {
	String[] av = {};
	Uncover.main(av);
    }

    @Test public void unknownOption() {
	String[] av = {"--unknown"};
	Uncover.main(av);
    }

    @Test public void run() {
	String[] av = {"config", "db.default", "foo.db"};
	Uncover.main(av);
    }

    @Test public void runThrowsException() {
	String[] av = {"--db=foo.db", "init"};
	Uncover.main(av);
    }
}
