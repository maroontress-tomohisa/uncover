package com.maroontress.uncover.junit;

import com.maroontress.uncover.DBException;
import org.junit.Test;
import static org.junit.Assert.*;

public class DBExceptionTest {
    @Test public void withMessage() {
	Exception e = new DBException("message");
    }

    @Test public void withCause() {
	Exception e = new DBException("message", new Throwable());
    }
}
