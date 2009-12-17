package com.maroontress.uncover;

/**
   �ġ��륭�åȤǤ���
*/
public abstract class Toolkit {
    /** �ġ��륭�åȤΥ��󥹥��󥹤Ǥ��� */
    private static Toolkit theInstance;

    static {
	theInstance = new DefaultToolkit();
    }

    /**
       �ġ��륭�åȤΥ��󥹥��󥹤�������ޤ���

       @return �ġ��륭�åȤΥ��󥹥���
    */
    public static Toolkit getInstance() {
	return theInstance;
    }

    /**
       �ġ��륭�åȤΥ��󥹥��󥹤����ꤷ�ޤ���

       @param kit �ġ��륭�åȤΥ��󥹥���
    */
    public static void setInstance(final Toolkit kit) {
	theInstance = kit;
    }

    /**
       DB���󥹥��󥹤��������ޤ���

       @param subname JDBC����³URL�Υ��֥͡���
       @return DB���󥹥���
       @throws DBException �ǡ����١��������ǥ��顼��ȯ�������Ȥ���
       �������ޤ���
    */
    public abstract DB createDB(final String subname) throws DBException;
}
