package com.maroontress.coverture.gcno;

import com.maroontress.coverture.CorruptedFileException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

/**
   �Ρ��ȥ쥳���ɤǤ���

   note: int32:magic int32:version int32:stamp function-graph*
*/
public final class NoteRecord {

    /** �ޥ��å��ʥ�С��ΥХ���Ĺ�Ǥ��� */
    private static final int MAGIC_LENGTH = 4;

    /** �ӥå�����ǥ�����Υޥ��å��ʥ�С��Ǥ��� */
    private static final byte[] MAGIC_BE = {'g', 'c', 'n', 'o'};

    /** ��ȥ륨��ǥ�����Υޥ��å��ʥ�С��Ǥ��� */
    private static final byte[] MAGIC_LE = {'o', 'n', 'c', 'g'};

    /**
       gcno�ե�����ΥС�������ֹ�Ǥ���

       Although the ident and version are formally 32 bit numbers,
       they are derived from 4 character ASCII strings.  The version
       number consists of the single character major version number, a
       two character minor version number (leading zero for versions
       less than 10), and a single character indicating the status of
       the release.  That will be 'e' experimental, 'p' prerelease and
       'r' for release.  Because, by good fortune, these are in
       alphabetical order, string collating can be used to compare
       version strings.  Be aware that the 'e' designation will
       (naturally) be unstable and might be incompatible with itself.
       For gcc 3.4 experimental, it would be '304e' (0x33303465).
       When the major version reaches 10, the letters A-Z will be
       used.  Assuming minor increments releases every 6 months, we
       have to make a major increment every 50 years.  Assuming major
       increments releases every 5 years, we're ok for the next 155
       years -- good enough for me.
    */
    private int version;

    /**
       gcno�ե�����Υ����ॹ����פǤ���gcda�ե������Ʊ�����Ȥ�Ƥ�
       �뤳�Ȥ��ǧ���뤿��˻��Ѥ���ޤ���

       The stamp value is used to synchronize note and data files and
       to synchronize merging within a data file. It need not be an
       absolute time stamp, merely a ticker that increments fast
       enough and cycles slow enough to distinguish different
       compile/run/compile cycles.
    */
    private int stamp;

    /** �ؿ�����ե쥳���ɤΥꥹ�ȤǤ��� */
    private ArrayList<FunctionGraphRecord> list;

    /**
       �Х��ȥХåե�����gcno�ե������ѡ������ơ��Ρ��ȥ쥳���ɤ���
       �����ޤ����Х��ȥХåե��ΰ��֤ϥХåե�����Ƭ�Ǥʤ���Фʤ��
       ���������������ϥХ��ȥХåե��ΰ��֤Ͻ�ü�˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    public NoteRecord(final ByteBuffer bb)
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
	list = new ArrayList<FunctionGraphRecord>();
	while (bb.hasRemaining()) {
	    list.add(new FunctionGraphRecord(bb));
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
       �ؿ�����ե쥳���ɤ������������ޤ���

       @return �ؿ�����ե쥳���ɤ�����
    */
    public FunctionGraphRecord[] getList() {
	return list.toArray(new FunctionGraphRecord[list.size()]);
    }
}
