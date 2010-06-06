package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.DefaultArc;
import com.maroontress.uncover.ArcSource;

/**
   com.maroontress.gcovparser.DefaultArc�򥽡����Ȥ��륢������������
   ����
*/
public final class DefaultArcSource implements ArcSource {

    /** �������Ǥ��� */
    private DefaultArc arc;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public DefaultArcSource() {
    }

    /**
       �����������ꤷ�ޤ���

       @param arc ������
    */
    public void setArc(final DefaultArc arc) {
	this.arc = arc;
    }

    /** {@inheritDoc} */
    public int getStart() {
	return arc.getStart().getId();
    }

    /** {@inheritDoc} */
    public int getEnd() {
	return arc.getEnd().getId();
    }

    /** {@inheritDoc} */
    public long getCount() {
	return arc.getCount();
    }

    /** {@inheritDoc} */
    public boolean isFake() {
	return arc.isFake();
    }
}
