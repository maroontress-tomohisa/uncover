package com.maroontress.gcovparser.gcno;

import com.maroontress.gcovparser.Parser;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
   ARCS�쥳���ɤǤ���

   arcs: header int32:block_no arc*
*/
public final class ArcsRecord {

    /** ��ȯ���Ȥʤ�֥�å��μ��̻ҤǤ��� */
    private int startIndex;

    /** ARC�쥳���ɤΥꥹ�ȤǤ��� */
    private ArcRecord[] list;

    /**
       �Х��ȥХåե�����ARCS�쥳���ɤλĤ�����Ϥ���ARCS�쥳���ɤ���
       �����ޤ����Х��ȥХåե��ΰ��֤�ARCS�쥳���ɤΥ��������Ϥ���ľ
       ��ΰ��֤Ǥʤ���Фʤ�ޤ��������������ϡ��Х��ȥХåե���
       ���֤�ARCS�쥳���ɤμ��ΰ��֤˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
    */
    public ArcsRecord(final ByteBuffer bb) throws IOException {
	int length = bb.getInt();
	int next = bb.position() + Parser.SIZE_INT32 * length;
	startIndex = bb.getInt();
	int num = (length - 1) / 2;
	list = new ArcRecord[num];
	for (int k = 0; k < num; ++k) {
	    list[k] = new ArcRecord(bb);
	}
	bb.position(next);
    }

    /**
       ��ȯ���Ȥʤ�֥�å��μ��̻Ҥ�������ޤ���

       @return ��ȯ���Ȥʤ�֥�å��μ��̻�
    */
    public int getStartIndex() {
	return startIndex;
    }

    /**
       ARC�쥳���ɤ�������֤��ޤ���

       @return ARC�쥳���ɤ�����
    */
    public ArcRecord[] getList() {
	return list;
    }
}
