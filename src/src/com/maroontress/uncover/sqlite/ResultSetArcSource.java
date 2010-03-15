package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.ArcSource;

/**
   �������̤��饢�������������뤿��Υ������������Ǥ���
*/
public final class ResultSetArcSource extends Row implements ArcSource {
    /** �����Ȥʤ���ܥ֥�å����ֹ�Ǥ��� */
    private int start;

    /** �����Ȥʤ���ܥ֥�å����ֹ�Ǥ��� */
    private int end;

    /** �¹Բ���Ǥ��� */
    private int count;

    /** �ե��������ɤ�����ɽ���ޤ��� */
    private int fake;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ResultSetArcSource() {
    }

    /** {@inheritDoc} */
    public int getStart() {
	return start;
    }

    /** {@inheritDoc} */
    public int getEnd() {
	return end;
    }

    /** {@inheritDoc} */
    public int getCount() {
	return count;
    }

    /** {@inheritDoc} */
    public boolean isFake() {
	return (fake != 0);
    }
}
