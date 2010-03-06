package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Arc;

/**
   ����ե������ơ��֥�ιԤ˴ؤ��������ݻ����ޤ���
*/
public final class GraphArcRow extends Row {
    /** �����ID�Ǥ��� */
    private String graphID;

    /** �����Ȥʤ�֥�å����ֹ�Ǥ��� */
    private int start;

    /** �����Ȥʤ�֥�å����ֹ�Ǥ��� */
    private int end;

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
	start = arc.getStart();
	end = arc.getEnd();
	count = arc.getCount();
	fake = (arc.isFake()) ? 1 : 0;
    }
}
