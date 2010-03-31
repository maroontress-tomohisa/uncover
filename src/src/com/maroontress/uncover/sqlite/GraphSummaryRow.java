package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Function;

/**
   ����ե��ޥ�ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class GraphSummaryRow extends Row {
    /** �����ID�Ǥ��� */
    private long graphID;

    /** �����å�����Ǥ��� */
    private String checkSum;

    /** �������ե�����Ǥ��� */
    private String sourceFile;

    /** ���ֹ�Ǥ��� */
    private int lineNumber;

    /** ʣ���٤Ǥ��� */
    private int complexity;

    /** ���ܥ֥�å�������Ǥ��� */
    private int allBlocks;

    /** �¹ԺѤߤδ��ܥ֥�å��θĿ��Ǥ��� */
    private int executedBlocks;

    /** ������������Ǥ��� */
    private int allArcs;

    /** �¹ԺѤߤΥ������θĿ��Ǥ��� */
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
    public void set(final long graphID, final Function function) {
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
