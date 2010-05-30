package com.maroontress.coverture;

import java.util.ArrayList;

/**
   �ԥ���ȥ�Υꥹ�ȤǤ����ҤȤĤδ��ܥ֥�å����б����륽����������
   �ν���Ǥ���
*/
public final class LineEntryList {

    /** �ԥ���ȥ�Υꥹ�ȤǤ��� */
    private ArrayList<LineEntry> list;

    /**
       �ԥ���ȥ�Υꥹ�Ȥ��������ޤ��������������󥹥��󥹤ϥǥե���
       �Ȥιԥ���ȥ��1�ĥꥹ�Ȥ˴ޤߤޤ���

       @param name �ǥե���ȤΥե�����̾
    */
    public LineEntryList(final String name) {
	list = new ArrayList<LineEntry>();
	list.add(new LineEntry(name));
    }

    /**
       �ꥹ�ȤκǸ�����ǤǤ���ԥ���ȥ�˹��ֹ���ɲä��ޤ���

       @param num ���ֹ�
    */
    public void addLineNumber(final int num) {
	list.get(list.size() - 1).add(num);
    }

    /**
       �������ԥ���ȥ��ꥹ�ȤκǸ���ɲä��ޤ���

       @param name �ե�����̾
    */
    public void changeFileName(final String name) {
	list.add(new LineEntry(name));
    }

    /**
       �ԥ���ȥ�Υꥹ�Ȥ�����Ȥ��Ƽ������ޤ���

       @return �ԥ���ȥ������
    */
    public LineEntry[] getLineEntries() {
	return list.toArray(new LineEntry[list.size()]);
    }
}
