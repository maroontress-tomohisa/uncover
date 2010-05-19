package com.maroontress.uncover.junit;

import com.maroontress.uncover.ExitException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExitExceptionTest {
    private static final int EXIT_STATUS = 123;

    @Test public void getExitStatus() {
	ExitException e = new ExitException(EXIT_STATUS);
	assertEquals(EXIT_STATUS, e.getExitStatus());
    }
}
