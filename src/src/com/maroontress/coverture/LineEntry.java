package com.maroontress.coverture;

import java.util.ArrayList;

/**
   �ԥ���ȥ�Ϲ�ñ�̤�ɽ���줿���ҤȤĤΥ������ե��������ʬ����Ǥ���
*/
public final class LineEntry {

    /** �������ե������̾�� */
    private String fileName;

    /** ���ֹ�Υꥹ�� */
    private ArrayList<Integer> lines;

    /**
       �ԥ���ȥ���������ޤ��������������󥹥��󥹤ˤϹ��ֹ椬�ޤޤ�
       �ޤ���

       @param name �������ե������̾��
    */
    public LineEntry(final String name) {
	fileName = name;
	lines = new ArrayList<Integer>();
    }

    /**
       �ԥ���ȥ�˹��ֹ���ɲä��ޤ���

       @param num ���ֹ�
    */
    public void add(final int num) {
	lines.add(num);
    }

    /**
       �ԥ���ȥ꤫�饽�����ե������̾����������ޤ���

       @return �������ե������̾��
    */
    public String getFileName() {
	return fileName;
    }

    /**
       �ԥ���ȥ꤫����ֹ�������������ޤ���

       @return ���ֹ������
    */
    public int[] getLines() {
	int[] nums = new int[lines.size()];
	for (int k = 0; k < nums.length; ++k) {
	    nums[k] = lines.get(k);
	}
	return nums;
    }
}
