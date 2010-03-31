package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Arc;

/**
   ����ե������ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class GraphArcRow extends Row {
    /** �����ID�Ǥ��� */
    private String graphID;

    /** �����Ȥʤ�֥�å����ֹ�Ǥ��� */
    private int startBlock;

    /** �����Ȥʤ�֥�å����ֹ�Ǥ��� */
    private int endBlock;

    /** �������μ¹Բ���Ǥ��� */
    private int count;

    /** �ե��������ɤ�����ɽ���ޤ��� */
    private int fake;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public GraphArcRow() {
    }

    /**
       �ե�����ɤ��ͤ����ꤷ�ޤ���

       @param graphID �����ID
       @param arc ������
    */
    public void set(final String graphID, final Arc arc) {
	this.graphID = graphID;
	startBlock = arc.getStart();
	endBlock = arc.getEnd();
	count = arc.getCount();
	fake = (arc.isFake()) ? 1 : 0;
    }
}
