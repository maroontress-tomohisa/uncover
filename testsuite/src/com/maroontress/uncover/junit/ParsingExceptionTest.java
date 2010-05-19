package com.maroontress.uncover.junit;

import com.maroontress.uncover.ParsingException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParsingExceptionTest {
    @Test public void create() {
	Exception e = new ParsingException("message");
    }

    @Test public void createWithCause() {
	Throwable cause = new Throwable();
	Exception e = new ParsingException("message", cause);
    }
}
