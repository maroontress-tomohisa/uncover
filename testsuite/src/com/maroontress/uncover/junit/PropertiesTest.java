package com.maroontress.uncover.junit;

import com.maroontress.uncover.Properties;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PropertiesTest {
    private static final String DB_FILE = "test.db";
    private Properties myProperties;

    @Before public void init() {
	myProperties = new Properties();
    }

    @Test public void setDBFile() {
	myProperties.setDBFile(DB_FILE);
	assertEquals(DB_FILE, myProperties.getDBFile());
    }
}
