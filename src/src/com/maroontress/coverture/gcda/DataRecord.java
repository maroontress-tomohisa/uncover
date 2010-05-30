package com.maroontress.coverture.gcda;

import com.maroontress.coverture.CorruptedFileException;
import com.maroontress.coverture.Tag;
import com.maroontress.coverture.UnexpectedTagException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

/**
   �ǡ����쥳���ɤǤ���

   data: int32:magic int32:version int32:stamp
         {function-data* object-summary program-summary*}*
*/
public final class DataRecord {

    /** �ޥ��å��ʥ�С��ΥХ���Ĺ�Ǥ��� */
    private static final int MAGIC_LENGTH = 4;

    /** �ӥå�����ǥ�����Υޥ��å��ʥ�С��Ǥ��� */
    private static final byte[] MAGIC_BE = {'g', 'c', 'd', 'a'};

    /** ��ȥ륨��ǥ�����Υޥ��å��ʥ�С��Ǥ��� */
    private static final byte[] MAGIC_LE = {'a', 'd', 'c', 'g'};

    /**
       gcda�ե�����ΥС�������ֹ�Ǥ���
    */
    private int version;

    /**
       gcda�ե�����Υ����ॹ����פǤ���gcno�ե������Ʊ�����Ȥ�Ƥ�
       �뤳�Ȥ��ǧ���뤿��˻��Ѥ���ޤ���
    */
    private int stamp;

    /** �ؿ��ǡ����쥳���ɤΥꥹ�ȤǤ��� */
    private ArrayList<FunctionDataRecord> list;

    /** ���֥������ȤΥ��ޥ�쥳���ɤǤ��� */
    private SummaryRecord objectSummary;

    /** �ץ����Υ��ޥ�쥳���ɤΥꥹ�ȤǤ��� */
    private ArrayList<SummaryRecord> programSummaries;

    /**
       �Х��ȥХåե�����FUNCTION_DATA�쥳���ɤ����Ϥ����б����륤��
       ���󥹤��������ơ��ꥹ�Ȥ��ɲä��ޤ����Х��ȥХåե��ΰ��֤�
       FUNCTION_DATA�쥳���ɤ���Ƭ�ΰ��֤Ǥʤ���Фʤ�ޤ��󡣥Х��ȥХ�
       �ե��ΰ��֤�FUNCTION_DATA�쥳���ɤμ��˿ʤߤޤ���

       OBJECT_SUMMARY���������Ϥ����-1���֤��ޤ���OBJECT_SUMMARY����
       �����Ϥ������ϥХ��ȥХåե��ΰ��֤�OBJECT_SUMMARY������ľ��
       �˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @return FUNCTION_DATA�쥳���ɤ����Ϥ�������0�������Ǥʤ����-1
       @throws IOException �����ϥ��顼
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    private int parseFunctionData(final ByteBuffer bb)
	throws IOException, CorruptedFileException {
	int saved = bb.position();
	int tag = bb.getInt();
	switch (tag) {
	default:
	    String m = String.format("unexpected tag: 0x%x", tag);
	    throw new UnexpectedTagException(m);
	case Tag.OBJECT_SUMMARY:
	    return -1;
	case Tag.FUNCTION:
	    list.add(new FunctionDataRecord(bb));
	    break;
	}
	return 0;
    }

    /**
       �Х��ȥХåե�����gcda�ե������ѡ������ơ��ǡ����쥳���ɤ���
       �����ޤ����Х��ȥХåե��ΰ��֤ϥХåե�����Ƭ�Ǥʤ���Фʤ��
       ���������������ϥХ��ȥХåե��ΰ��֤Ͻ�ü�˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    public DataRecord(final ByteBuffer bb)
	throws IOException, CorruptedFileException {
	byte[] magic = new byte[MAGIC_LENGTH];
	bb.get(magic);
	if (Arrays.equals(magic, MAGIC_BE)) {
	    bb.order(ByteOrder.BIG_ENDIAN);
	} else if (Arrays.equals(magic, MAGIC_LE)) {
	    bb.order(ByteOrder.LITTLE_ENDIAN);
	} else {
	    throw new CorruptedFileException();
	}
	version = bb.getInt();
	stamp = bb.getInt();
	list = new ArrayList<FunctionDataRecord>();
	programSummaries = new ArrayList<SummaryRecord>();

	while (parseFunctionData(bb) == 0) {
	    continue;
	}
	objectSummary = new SummaryRecord(bb);
	while (bb.hasRemaining()) {
	    int saved = bb.position();
	    int tag = bb.getInt();
	    switch (tag) {
	    default:
		if (tag == 0 && !bb.hasRemaining()) {
		    return;
		}
		String m = String.format("unexpected tag: 0x%x", tag);
		throw new UnexpectedTagException(m);
	    case Tag.FUNCTION:
		bb.position(saved);
		return;
	    case Tag.PROGRAM_SUMMARY:
		programSummaries.add(new SummaryRecord(bb));
		break;
	    }
	}
    }

    /**
       �С�������������ޤ���

       @return �С������
    */
    public int getVersion() {
	return version;
    }

    /**
       �����ॹ����פ�������ޤ���

       @return �����ॹ�����
    */
    public int getStamp() {
	return stamp;
    }

    /**
       �ؿ��ǡ����쥳���ɤ������������ޤ���

       @return �ؿ��ǡ����쥳���ɤ�����
    */
    public FunctionDataRecord[] getList() {
	return list.toArray(new FunctionDataRecord[list.size()]);
    }

    /**
       ���֥������ȤΥ��ޥ�쥳���ɤ�������ޤ���

       @return ���֥������ȤΥ��ޥ�쥳����
    */
    public SummaryRecord getObjectSummary() {
	return objectSummary;
    }

    /**
       �ץ����Υ��ޥ�쥳���ɤ������������ޤ���

       @return �ץ����Υ��ޥ�쥳���ɤ�����
    */
    public SummaryRecord[] getProgramSummaries() {
	return programSummaries.toArray(
	    new SummaryRecord[programSummaries.size()]);
    }
}
