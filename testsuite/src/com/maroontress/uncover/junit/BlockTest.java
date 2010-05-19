package com.maroontress.uncover.junit;

import com.maroontress.uncover.Block;
import com.maroontress.uncover.BlockSource;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlockTest {
    private static final int NUMBER = 12;
    private static final int COUNT = 34;
    private static final String SOURCE_FILE = "Foo.c";
    private static final int LINE_NUMBER = 56;
    private Block block;
    private Block noSourceBlock;

    @Before public void init() {
	block = new Block(new BlockSource() {
	    public int getNumber() {
		return NUMBER;
	    }
	    public long getCount() {
		return COUNT;
	    }
	    public String getSourceFile() {
		return SOURCE_FILE;
	    }
	    public int getLineNumber() {
		return LINE_NUMBER;
	    }
	});
	noSourceBlock = new Block(new BlockSource() {
	    public int getNumber() {
		return NUMBER;
	    }
	    public long getCount() {
		return COUNT;
	    }
	    public String getSourceFile() {
		return null;
	    }
	    public int getLineNumber() {
		return 0;
	    }
	});
    }

    @Test public void getNumber() {
	assertEquals(NUMBER, block.getNumber());
    }

    @Test public void getCount() {
	assertEquals(COUNT, block.getCount());
    }

    @Test public void getSourceFile() {
	assertEquals(SOURCE_FILE, block.getSourceFile());
    }

    @Test public void getLineNumber() {
	assertEquals(LINE_NUMBER, block.getLineNumber());
    }

    @Test public void getStringNull() {
	assertNull(noSourceBlock.getSourceFile());
    }
}
