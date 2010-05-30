package com.maroontress.coverture.gcda;

import com.maroontress.coverture.Parser;
import com.maroontress.coverture.Tag;
import com.maroontress.coverture.UnexpectedTagException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
   �ؿ��ǡ����쥳���ɤǤ���

   function-data: announce_function arc_counts
   announce_function: header int32:ident int32:checksum
   arc_counts: header int64:count*
*/
public final class FunctionDataRecord {

    /** ���̻ҤǤ��� */
    private int id;

    /** �ؿ��Υ����å�����Ǥ��� */
    private int checksum;

    /** ARCS�쥳���ɤΥꥹ�ȤǤ��� */
    private long[] arcCounts;

    /**
       �Х��ȥХåե�����FUNCTION_DATA�쥳���ɤ����Ϥ��ƥ��󥹥��󥹤�
       �������ޤ����Х��ȥХåե��ΰ��֤�FUNCTION_DATA�쥳���ɤΥ�����
       ���Ϥ���ľ��ΰ��֤Ǥʤ���Фʤ�ޤ��������������ϡ��Х���
       �Хåե��ΰ��֤�FUNCTION_DATA�쥳���ɤμ��ΰ��֤˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
       @throws UnexpectedTagException ͽ�����ʤ������򸡽�
    */
    public FunctionDataRecord(final ByteBuffer bb)
	throws IOException, UnexpectedTagException {
	int length = bb.getInt();
	int next = bb.position() + Parser.SIZE_INT32 * length;

	id = bb.getInt();
	checksum = bb.getInt();
	bb.position(next);

	int tag = bb.getInt();
	if (tag != Tag.ARC_COUNTS) {
	    String m = String.format("unexpected tag: 0x%x", tag);
	    throw new UnexpectedTagException(m);
	}
	length = bb.getInt();
	next = bb.position() + Parser.SIZE_INT32 * length;

	arcCounts = new long[length / 2];
	for (int k = 0; k < arcCounts.length; ++k) {
	    arcCounts[k] = Parser.getInt64(bb);
	}
	bb.position(next);
    }

    /**
       ���̻Ҥ�������ޤ���

       @return ���̻�
    */
    public int getId() {
	return id;
    }

    /**
       �ؿ��Υ����å������������ޤ���

       @return �ؿ��Υ����å�����
    */
    public int getChecksum() {
	return checksum;
    }

    /**
       ������������Ȥ�������ޤ���

       @return �������������
    */
    public long[] getArcCounts() {
	return arcCounts;
    }
}
