package com.maroontress.gcovparser;

/**
   �쥳���ɤΥ�����������ޤ���
*/
public final class Tag {

    /**
       ���󥹥ȥ饯���Ǥ���
    */
    private Tag() {
    }

    /** FUNCTION�쥳���ɤ򼨤������Ǥ��� */
    public static final int FUNCTION = 0x1000000;

    /** BLOCK�쥳���ɤ򼨤������Ǥ��� */
    public static final int BLOCK = 0x1410000;

    /** ARCS�쥳���ɤ򼨤������Ǥ��� */
    public static final int ARCS = 0x1430000;

    /** LINES�쥳���ɤ򼨤������Ǥ��� */
    public static final int LINES = 0x1450000;

    /** OBJECT_SUMMARY�쥳���ɤ򼨤������Ǥ��� */
    public static final int OBJECT_SUMMARY = 0xa1000000;

    /** PROGRAM_SUMMARY�쥳���ɤ򼨤������Ǥ��� */
    public static final int PROGRAM_SUMMARY = 0xa3000000;

    /** ARC_COUNTS�쥳���ɤ򼨤������Ǥ��� */
    public static final int ARC_COUNTS = 0x01a10000;
}
