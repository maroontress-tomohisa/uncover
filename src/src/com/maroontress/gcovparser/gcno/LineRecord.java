package com.maroontress.gcovparser.gcno;

import com.maroontress.gcovparser.Parser;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
   LINE�쥳���ɤǤ���

   line: int32:line_no | int32:0 string:filename
*/
public final class LineRecord {

    /** ���ֹ�Ǥ��� */
    private int number;

    /** �ե�����̾�Ǥ��� */
    private String fileName;

    /**
       �Х��ȥХåե�����LINE�쥳���ɤ����Ϥ���LINE�쥳���ɤ���������
       �����Х��ȥХåե��ΰ��֤�LINE�쥳���ɤ���Ƭ�ΰ��֤Ǥʤ���Ф�
       ��ޤ��������������ϡ��Х��ȥХåե��ΰ��֤�LINE�쥳���ɤ�
       ���ΰ��֤˰�ư���ޤ���

       @param bb �Х��ȥХåե�
       @throws IOException �����ϥ��顼
    */
    public LineRecord(final ByteBuffer bb) throws IOException {
	number = bb.getInt();
	fileName = (number != 0) ? null : Parser.getInternString(bb);
    }

    /**
       ���ֹ��������ޤ���

       @return ���ֹ�
    */
    public int getNumber() {
	return number;
    }

    /**
       �ե�����̾��������ޤ���

       @return �ե�����̾
    */
    public String getFileName() {
	return fileName;
    }

    /**
       LINE�쥳���ɤν�ü���ɤ����������ޤ���

       @return LINES�쥳���ɤν�ü�ʤ�true�������Ǥʤ����false
    */
    public boolean isTerminator() {
	return number == 0 && fileName == null;
    }
}
