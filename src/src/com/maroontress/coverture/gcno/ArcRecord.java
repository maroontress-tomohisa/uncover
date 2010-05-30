package com.maroontress.coverture.gcno;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
   ARC�쥳���ɤǤ���

   arc:  int32:dest_block int32:flags
*/
public final class ArcRecord {

    /** �������Ȥʤ�֥�å��μ��̻ҤǤ��� */
    private int endIndex;

    /** �ե饰�Ǥ��� */
    private int flags;

    /**
       �Х��ȥХåե�����ARC�쥳���ɤ����Ϥ���ARC�쥳���ɤ��������ޤ���
       �Х��ȥХåե��ΰ��֤�ARC�쥳���ɤ���Ƭ�ΰ��֤Ǥʤ���Фʤ�ޤ�
       �������������ϡ��Х��ȥХåե��ΰ��֤�ARC�쥳���ɤμ��ΰ���
       �˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
    */
    public ArcRecord(final ByteBuffer bb) throws IOException {
	endIndex = bb.getInt();
	flags = bb.getInt();
    }

    /**
       �������Ȥʤ�֥�å��μ��̻Ҥ�������ޤ���

       @return �������Ȥʤ�֥�å��μ��̻�
    */
    public int getEndIndex() {
	return endIndex;
    }

    /**
       �ե饰��������ޤ���

       @return �ե饰
    */
    public int getFlags() {
	return flags;
    }
}
