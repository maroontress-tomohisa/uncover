package com.maroontress.uncover.junit;

import com.maroontress.uncover.BlockSource;

public class TestBlockSource implements BlockSource {
    private int number;
    private long count;
    private String sourceFile;
    private int lineNumber;

    public TestBlockSource() {
	this(12, 34, "foo.c", 56);
    }

    public TestBlockSource(final int number, final long count,
			   final String sourceFile, final int lineNumber) {
	this.number = number;
	this.count = count;
	this.sourceFile = sourceFile;
	this.lineNumber = lineNumber;
    }

    public int getNumber() {
	return number;
    }
    public long getCount() {
	return count;
    }
    public String getSourceFile() {
	return sourceFile;
    }
    public int getLineNumber() {
	return lineNumber;
    }
}
