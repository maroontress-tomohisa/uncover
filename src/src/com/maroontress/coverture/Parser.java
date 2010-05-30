package com.maroontress.coverture;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
   gcno/gcda�ե������ѡ������뤿��Υ桼�ƥ���ƥ����饹�Ǥ���
*/
public final class Parser {

    /** INT32�ΥХ��ȥ������Ǥ��� */
    public static final int SIZE_INT32 = 4;

    /** ʸ�����åȤǤ��� */
    private static final Charset CHARSET = Charset.defaultCharset();

    /**
       ���󥹥ȥ饯���Ǥ���
    */
    private Parser() {
    }

    /**
       �Х��ȥХåե�����64�ӥå��ͤ����Ϥ��������ͤ��֤��ޤ����Х���
       �Хåե��ΰ��֤�8�Х��ȿʤߤޤ���

       gcov�λ��ͤˤ��64�ӥå��ͤϡ�����32�ӥåȡ����32�ӥåȤν��
       �¤�Ǥ��ޤ���

       @param bb �Х��ȥХåե�
       @return 64�ӥå���
       @throws IOException �����ϥ��顼
    */
    public static long getInt64(final ByteBuffer bb) throws IOException {
	long low = bb.getInt();
	long high = bb.getInt();
	if (low < 0) {
	    low += (1L << Integer.SIZE);
	}
	high <<= Integer.SIZE;
	return high | low;
    }

    /**
       �Х��ȥХåե�����ʸ��������Ϥ������Υ��󥹥��󥹤��֤��ޤ���
       ʸ�����Ĺ����0�ΤȤ���null���֤��ޤ����̥륿���ߥ͡��ȤΤ����
       0������ӥѥǥ��󥰤�0�ϥ����åפ��졢�Х��ȥХåե��ΰ��֤�ʸ
       ����μ��ΰ��֤˿ʤߤޤ���

       string: int32:0 | int32:length char* char:0 padding
       padding: | char:0 | char:0 char:0 | char:0 char:0 char:0

       @param bb �Х��ȥХåե�
       @return ʸ����
       @throws IOException �����ϥ��顼
    */
    public static String getString(final ByteBuffer bb) throws IOException {
	int length = bb.getInt();
	if (length == 0) {
	    return null;
	}
	byte[] bytes = new byte[length * SIZE_INT32];
	bb.get(bytes);

	int k;
	for (k = 0; k < bytes.length && bytes[k] != 0; ++k) {
	    continue;
	}
	return new String(bytes, 0, k, CHARSET);
    }

    /**
       �Х��ȥХåե�����ʸ��������Ϥ������Υ��󥹥��󥹤��֤��ޤ���

       ���󥹥��󥹤�Stirng.intern()���֤�ʸ����Ǥ�������ʳ������ˤ�
       ���Ƥ�Parser.getString()��Ʊ�ͤˤʤ�ޤ���

       @param bb �Х��ȥХåե�
       @return ʸ����
       @throws IOException �����ϥ��顼
    */
    public static String getInternString(final ByteBuffer bb)
	throws IOException {
	String s = getString(bb);
	return (s == null) ? null : s.intern();
    }
}
