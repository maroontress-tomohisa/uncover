package com.maroontress.gcovparser.gcno;

import com.maroontress.gcovparser.Parser;
import com.maroontress.gcovparser.Tag;
import com.maroontress.gcovparser.UnexpectedTagException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
   ���ܥ֥�å��쥳���ɤǤ���

   basic_block: header int32:flags*
*/
public final class BasicBlockRecord {

    /** �ե饰������Ǥ��� */
    private int[] flags;

    /**
       �Х��ȥХåե�����BASIC_BLOCK�쥳���ɤ����Ϥ���BASIC_BLOCK�쥳��
       �ɤ��������ޤ����Х��ȥХåե��ΰ��֤�BASIC_BLOCK�쥳���ɤ���Ƭ
       �ΰ��֤Ǥʤ���Фʤ�ޤ��������������ϡ��Х��ȥХåե��ΰ�
       �֤�BASIC_BLOCK�쥳���ɤμ��ΰ��֤˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
       @throws UnexpectedTagException ͽ�����ʤ������򸡽�
    */
    public BasicBlockRecord(final ByteBuffer bb)
	throws IOException, UnexpectedTagException {
	int tag = bb.getInt();
	int length = bb.getInt();
	int next = bb.position() + Parser.SIZE_INT32 * length;

	if (tag != Tag.BLOCK) {
	    String m = String.format("unexpected tag: 0x%x", tag);
	    throw new UnexpectedTagException(m);
	}
	flags = new int[length];
	for (int k = 0; k < length; ++k) {
	    flags[k] = bb.getInt();
	}
	bb.position(next);
    }

    /**
       �ե饰�������������ޤ���

       @return �ե饰������
    */
    public int[] getFlags() {
	return flags;
    }
}
