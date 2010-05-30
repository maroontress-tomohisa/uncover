package com.maroontress.coverture.gcno;

import com.maroontress.coverture.Parser;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
   LINES�쥳���ɤǤ���

   lines: header int32:block_no line* int32:0 string:NULL
*/
public final class LinesRecord {

    /** �б�����֥�å��μ��̻ҤǤ��� */
    private int blockIndex;

    /** LINE�쥳���ɤΥꥹ�ȤǤ��� */
    private ArrayList<LineRecord> list;

    /**
       �Х��ȥХåե�����LINES�쥳���ɤλĤ�����Ϥ���LINES�쥳���ɤ�
       �������ޤ����Х��ȥХåե��ΰ��֤�LINES�쥳���ɤΥ��������Ϥ���
       ľ��ΰ��֤Ǥʤ���Фʤ�ޤ��������������ϡ��Х��ȥХåե�
       �ΰ��֤�LINES�쥳���ɤμ��ΰ��֤˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
    */
    public LinesRecord(final ByteBuffer bb) throws IOException {
	int length = bb.getInt();
	int next = bb.position() + Parser.SIZE_INT32 * length;
	blockIndex = bb.getInt();
	list = new ArrayList<LineRecord>();

	LineRecord rec;
	while (!(rec = new LineRecord(bb)).isTerminator()) {
	    list.add(rec);
	}
	bb.position(next);
    }

    /**
       �֥�å��μ��̻Ҥ�������ޤ���

       @return �֥�å��μ��̻�
    */
    public int getBlockIndex() {
	return blockIndex;
    }

    /**
       LINE�쥳���ɤ�������֤��ޤ���

       @return LINE�쥳���ɤ�����
    */
    public LineRecord[] getList() {
	return list.toArray(new LineRecord[list.size()]);
    }
}
