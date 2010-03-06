package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Block;

/**
   ����ե֥�å��ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class GraphBlockRow extends Row {
    /** �����ID�Ǥ��� */
    private String graphID;

    /** �֥�å����ֹ�Ǥ��� */
    private int number;

    /** �֥�å��μ¹Բ���Ǥ��� */
    private int count;

    /** �������ե�����Ǥ��� */
    private String sourceFile;

    /** ���ֹ�Ǥ��� */
    private int lineNumber;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public GraphBlockRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param graphID �����ID
       @param block ���ܥ֥�å�
    */
    public void set(final String graphID, final Block block) {
	this.graphID = graphID;
	number = block.getNumber();
	count = block.getCount();
	sourceFile = block.getSourceFile();
	lineNumber = block.getLineNumber();
    }
}
