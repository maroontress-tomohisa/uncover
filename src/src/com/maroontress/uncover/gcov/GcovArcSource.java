package com.maroontress.uncover.gcov;

import com.maroontress.coverture.Arc;
import com.maroontress.uncover.ArcSource;

/**
   com.maroontress.coverture.Arc�򥽡����Ȥ��륢�����������Ǥ���
*/
public final class GcovArcSource implements ArcSource {

    /** �������Ǥ��� */
    private Arc arc;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public GcovArcSource() {
    }

    /**
       �����������ꤷ�ޤ���

       @param arc ������
    */
    public void setArc(final Arc arc) {
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
