package com.maroontress.coverture;

/**
   �Ծ���Ǥ��������������ɤι��ֹ���μ¹Բ����������ޤ���
*/
public final class LineInfo {

    /** �¹Բ���Υ����󥿤Ǥ��� */
    private long count;

    /**
       �Ծ�����������ޤ���
    */
    public LineInfo() {
	count = 0;
    }

    /**
       �¹Բ����û����ޤ���

       @param delta �û�����¹Բ��
    */
    public void addCount(final long delta) {
	count += delta;
    }

    /**
       �¹Բ����������ޤ���

       @return �¹Բ��
    */
    public long getCount() {
	return count;
    }
}
