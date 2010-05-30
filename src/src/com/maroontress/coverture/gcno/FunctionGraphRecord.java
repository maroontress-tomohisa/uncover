package com.maroontress.coverture.gcno;

import com.maroontress.coverture.CorruptedFileException;
import com.maroontress.coverture.Tag;
import com.maroontress.coverture.UnexpectedTagException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
   �ؿ�����ե쥳���ɤǤ���

   function_graph: announce_function basic_blocks {arcs | lines}*
*/
public final class FunctionGraphRecord {

    /** �ؿ����Υ쥳���ɤǤ��� */
    private AnnounceFunctionRecord announce;

    /** ���ܥ֥�å��쥳���ɤǤ��� */
    private BasicBlockRecord blocks;

    /** ARCS�쥳���ɤΥꥹ�ȤǤ��� */
    private ArrayList<ArcsRecord> arcs;

    /** LINES�쥳���ɤΥꥹ�ȤǤ��� */
    private ArrayList<LinesRecord> lines;

    /**
       �Х��ȥХåե�����FUNCTION_GRAPH�쥳���ɤ����Ϥ���
       FUNCTION_GRAPH�쥳���ɤ��������ޤ����Х��ȥХåե��ΰ��֤�
       FUNCTION_GRAPH�쥳���ɤ���Ƭ�ΰ��֤Ǥʤ���Фʤ�ޤ���������
       �����ϡ��Х��ȥХåե��ΰ��֤�FUNCTION_GRAPH�쥳���ɤμ��ΰ�
       �֤˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    public FunctionGraphRecord(final ByteBuffer bb)
	throws IOException, CorruptedFileException {
	announce = new AnnounceFunctionRecord(bb);
	blocks = new BasicBlockRecord(bb);
	arcs = new ArrayList<ArcsRecord>();
	lines = new ArrayList<LinesRecord>();
	while (parseArcsOrLines(bb) == 0) {
	    continue;
	}
    }

    /**
       �Х��ȥХåե�����ARCS�쥳���ɤޤ���LINES�쥳���ɤ����Ϥ����б�
       ���륤�󥹥��󥹤��������ơ��ꥹ�Ȥ��ɲä��ޤ����Х��ȥХåե�
       �ΰ��֤�ARCS/LINES�쥳���ɤ���Ƭ�ΰ��֤Ǥʤ���Фʤ�ޤ��󡣥�
       ���ȥХåե��ΰ��֤�ARCS/LINES�쥳���ɤμ��˿ʤߤޤ���

       �Х��ȥХåե��ν�ü�����Ϥ��뤫��FUNCTION���������Ϥ����-1��
       �֤��ޤ���FUNCTION���������Ϥ������ϥХ��ȥХåե��ΰ��֤���
       ������ޤ���

       @param bb �Х��ȥХåե�
       @return ARCS/LINES�쥳���ɤ����Ϥ�������0�������Ǥʤ����-1
       @throws IOException �����ϥ��顼
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    private int parseArcsOrLines(final ByteBuffer bb)
	throws IOException, CorruptedFileException {
	if (!bb.hasRemaining()) {
	    return -1;
	}
	int saved = bb.position();
	int tag = bb.getInt();
	switch (tag) {
	default:
	    String m = String.format("unexpected tag: 0x%x", tag);
	    throw new UnexpectedTagException(m);
	case Tag.FUNCTION:
	    bb.position(saved);
	    return -1;
	case Tag.ARCS:
	    arcs.add(new ArcsRecord(bb));
	    break;
	case Tag.LINES:
	    lines.add(new LinesRecord(bb));
	    break;
	}
	return 0;
    }

    /**
       �ؿ����Υ쥳���ɤ�������ޤ���

       @return �ؿ����Υ쥳����
    */
    public AnnounceFunctionRecord getAnnounce() {
	return announce;
    }

    /**
       ���ܥ֥�å��쥳���ɤ�������ޤ���

       @return ���ܥ֥�å��쥳����
    */
    public BasicBlockRecord getBlocks() {
	return blocks;
    }

    /**
       ARCS�쥳���ɤ������������ޤ���

       @return ARCS�쥳���ɤ�����
    */
    public ArcsRecord[] getArcs() {
	return arcs.toArray(new ArcsRecord[arcs.size()]);
    }

    /**
       LINES�쥳���ɤ������������ޤ���

       @return LINES�쥳���ɤ�����
    */
    public LinesRecord[] getLines() {
	return lines.toArray(new LinesRecord[lines.size()]);
    }
}
