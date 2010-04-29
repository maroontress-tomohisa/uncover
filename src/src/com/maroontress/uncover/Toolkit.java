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
       ���󥹥��󥹤��������ޤ���
    */
    protected Toolkit() {
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
      �ץ�����λ���ޤ���

      @param status ��λ���ơ�����������
    */
    public abstract void exit(int status);

    /**
       �ؿ�����դΥ��ƥ졼���ե����ȥ���������ޤ���

       @param file ���ϤȤʤ�ե�����
       @return �ؿ�����դΥ��ƥ졼���ե����ȥ�
       @throws ParsingException �ե�����Υѡ����ǥ��顼��ȯ�������Ȥ�
       �˥������ޤ���
    */
    public abstract Parser createParser(final String file)
	throws ParsingException;

    /**
       DB���󥹥��󥹤��������ޤ���

       @param subname JDBC����³URL�Υ��֥͡���
       @return DB���󥹥���
       @throws DBException �ǡ����١��������ǥ��顼��ȯ�������Ȥ���
       �������ޤ���
    */
    public abstract DB createDB(final String subname) throws DBException;

    /**
       �ؿ����󥹥��󥹤��������ޤ���

       �ؿ������������ͤ�������ƴؿ����󥹥��󥹤��������ޤ�������
       ���������κݤ˥����������ͤ򤹤٤ƥ��ԡ����ޤ����������äơ�
       �������source���ѹ����Ƥ⡢���������ؿ����󥹥��󥹤˱ƶ��Ϥ�
       ��ޤ���

       @param source �ؿ�������
       @return �ؿ�
    */
    public abstract Function createFunction(final FunctionSource source);

    /**
       ����ե��󥹥��󥹤��������ޤ���

       ����ե����������ͤ�������ƥ���ե��󥹥��󥹤��������ޤ�����
       �󥹥��������κݤ˥����������ͤ򤹤٤ƥ��ԡ����ޤ�����������
       �ơ��������source���ѹ����Ƥ⡢������������ե��󥹥��󥹤˱�
       ���Ϥ���ޤ���

       @param source ����ե�����
       @return �����
    */
    public abstract Graph createGraph(final GraphSource source);

    /**
       �֥�å����󥹥��󥹤��������ޤ���

       �֥�å������������ͤ�������ƥ֥�å����󥹥��󥹤��������ޤ���
       ���󥹥��������κݤ˥����������ͤ򤹤٤ƥ��ԡ����ޤ�����������
       �ơ��������source���ѹ����Ƥ⡢���������֥�å����󥹥��󥹤�
       �ƶ��Ϥ���ޤ���

       @param source �֥�å�������
       @return �֥�å�
    */
    public abstract Block createBlock(final BlockSource source);

    /**
       ���������󥹥��󥹤��������ޤ���

       �����������������ͤ�������ƥ��������󥹥��󥹤��������ޤ�����
       �󥹥��������κݤ˥����������ͤ򤹤٤ƥ��ԡ����ޤ�����������
       �ơ��������source���ѹ����Ƥ⡢�����������������󥹥��󥹤˱�
       ���Ϥ���ޤ���

       @param source ������������
       @return ������
    */
    public abstract Arc createArc(final ArcSource source);

    /**
       �ӥ�ɥ��󥹥��󥹤��������ޤ���

       �ӥ�ɥ����������ͤ�������ƥӥ�ɥ��󥹥��󥹤��������ޤ�����
       �󥹥��������κݤ˥����������ͤ򤹤٤ƥ��ԡ����ޤ�����������
       �ơ��������source���ѹ����Ƥ⡢���������ӥ�ɥ��󥹥��󥹤˱�
       ���Ϥ���ޤ���

       @param source �ӥ�ɥ�����
       @return �ӥ��
    */
    public abstract Build createBuild(final BuildSource source);
}
