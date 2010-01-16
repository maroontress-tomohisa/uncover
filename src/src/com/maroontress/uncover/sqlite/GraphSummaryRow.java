package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Function;

/**
   ����ե��ޥ�ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class GraphSummaryRow extends Row {
    /** �����ID�Ǥ��� */
    private String graphID;

    /** */
    private String checkSum;

    /** */
    private String sourceFile;

    /** */
    private int lineNumber;

    /** */
    private int complexity;

    /** */
    private int allBlocks;

    /** */
    private int executedBlocks;

    /** */
    private int allArcs;

    /** */
    private int executedArcs;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public GraphSummaryRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param graphID �����ID
       @param function �ؿ�
    */
    public void set(final String graphID, final Function function) {
	this.graphID = graphID;
	checkSum = function.getCheckSum();
	sourceFile = function.getSourceFile();
	lineNumber = function.getLineNumber();
	complexity = function.getComplexity();
	allBlocks = function.getAllBlocks();
	executedBlocks = function.getExecutedBlocks();
	allArcs = function.getAllArcs();
	executedArcs = function.getExecutedArcs();
    }
}
