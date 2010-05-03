package com.maroontress.uncover;

/**
   �������˴ؤ������򥫥ץ��벽���ޤ���
*/
public final class Arc implements ArcSource {
    /** �����Ȥʤ���ܥ֥�å����ֹ�Ǥ��� */
    private int start;

    /** �����Ȥʤ���ܥ֥�å����ֹ�Ǥ��� */
    private int end;

    /** �¹Բ���Ǥ��� */
    private long count;

    /** �ե��������ɤ�����ɽ���ޤ��� */
    private boolean isFake;

    /**
       ���󥹥��󥹤��������ޤ���

       @param s ������������
    */
    public Arc(final ArcSource s) {
	start = s.getStart();
	end = s.getEnd();
	count = s.getCount();
	isFake = s.isFake();
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
    public long getCount() {
	return count;
    }

    /** {@inheritDoc} */
    public boolean isFake() {
	return isFake;
    }
}
