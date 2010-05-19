package com.maroontress.uncover.junit;

import com.maroontress.uncover.FunctionSource;

public class TestFunctionSource implements FunctionSource {
    public static final String NAME = "functionName";
    public static final String GCNO_FILE = "gcnoFile.gcno";
    public static final String SOURCE_FILE = "sourceFile.c";
    public static final int LINE_NUMBER = 765;
    public static final String CHECK_SUM = "0x12345678";
    public static final int COMPLEXITY = 123;
    public static final int EXECUTED_BLOCKS = 3;
    public static final int ALL_BLOCKS = 10;
    public static final int EXECUTED_ARCS = 4;
    public static final int ALL_ARCS = 16;

    private String name;
    private String gcnoFile;
    private String sourceFile;
    private int lineNumber;
    private String checkSum;
    private int complexity;
    private int executedBlocks;
    private int allBlocks;
    private int executedArcs;
    private int allArcs;

    public TestFunctionSource() {
	this(NAME, GCNO_FILE);
    }

    public TestFunctionSource(final String name,
			      final String gcnoFile) {
	this.name = name;
	this.gcnoFile = gcnoFile;

	sourceFile = SOURCE_FILE;
	lineNumber = LINE_NUMBER;

	checkSum = CHECK_SUM;
	complexity = COMPLEXITY;
	executedBlocks = EXECUTED_BLOCKS;
	allBlocks = ALL_BLOCKS;
	executedArcs = EXECUTED_ARCS;
	allArcs = ALL_ARCS;
    }

    public void setCheckSum(final String checkSum) {
	this.checkSum = checkSum;
    }

    public void setSourceFile(final String sourceFile, final int lineNumber) {
	this.sourceFile = sourceFile;
	this.lineNumber = lineNumber;
    }

    public void setBlocks(final int executedBlocks, final int allBlocks) {
	this.executedBlocks = executedBlocks;
	this.allBlocks = allBlocks;
    }

    public void setArcs(final int executedArcs, final int allArcs) {
	this.executedArcs = executedArcs;
	this.allArcs = allArcs;
    }

    public void setComplexity(final int complexity) {
	this.complexity = complexity;
    }

    public String getCheckSum() {
	return checkSum;
    }
    public int getComplexity() {
	return complexity;
    }
    public String getName() {
	return name;
    }
    public String getGCNOFile() {
	return gcnoFile;
    }
    public String getSourceFile() {
	return sourceFile;
    }
    public int getLineNumber() {
	return lineNumber;
    }
    public int getExecutedBlocks() {
	return executedBlocks;
    }
    public int getAllBlocks() {
	return allBlocks;
    }
    public int getExecutedArcs() {
	return executedArcs;
    }
    public int getAllArcs() {
	return allArcs;
    }
}
