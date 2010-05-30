package com.maroontress.coverture.gcno;

import com.maroontress.coverture.Parser;
import com.maroontress.coverture.Tag;
import com.maroontress.coverture.UnexpectedTagException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
   �ؿ����Υ쥳���ɤǤ���

   announce_function: header int32:ident int32:checksum string:name
                      string:source int32:lineno
*/
public final class AnnounceFunctionRecord {

    /** ���̻ҤǤ��� */
    private int id;

    /** �ؿ��Υ����å�����Ǥ��� */
    private int checksum;

    /** �ؿ�̾�Ǥ��� */
    private String functionName;

    /** �����������ɤΥե�����̾�Ǥ��� */
    private String sourceFile;

    /** �ؿ����и����륽���������ɤιԿ��Ǥ��� */
    private int lineNumber;

    /**
       �Х��ȥХåե�����ANNOUNCE_FUNCTION�쥳���ɤ����Ϥ���
       ANNOUNCE_FUNCTION�쥳���ɤ��������ޤ����Х��ȥХåե��ΰ��֤�
       ANNOUNCE_FUNCTION�쥳���ɤ���Ƭ�ΰ��֤Ǥʤ���Фʤ�ޤ�������
       �������ϡ��Х��ȥХåե��ΰ��֤�ANNOUNCE_FUNCTION�쥳���ɤμ�
       �ΰ��֤˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
       @throws UnexpectedTagException ͽ�����ʤ������򸡽�
    */
    public AnnounceFunctionRecord(final ByteBuffer bb)
	throws IOException, UnexpectedTagException {
	int tag = bb.getInt();
	int length = bb.getInt();
	int next = bb.position() + Parser.SIZE_INT32 * length;

	if (tag != Tag.FUNCTION) {
	    String m = String.format("unexpected tag: 0x%x", tag);
	    throw new UnexpectedTagException(m);
	}
	id = bb.getInt();
	checksum = bb.getInt();
	functionName = Parser.getString(bb);
	sourceFile = Parser.getInternString(bb);
	lineNumber = bb.getInt();
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
       �ؿ�̾��������ޤ���

       @return �ؿ�̾
    */
    public String getFunctionName() {
	return functionName;
    }

    /**
       �����������ɤΥե�����̾��������ޤ���

       @return �����������ɤΥե�����̾
    */
    public String getSourceFile() {
	return sourceFile;
    }

    /**
       �ؿ����и����륽���������ɤιԿ���������ޤ���

       @return �ؿ����и����륽���������ɤιԿ�
    */
    public int getLineNumber() {
	return lineNumber;
    }
}
