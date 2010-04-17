package com.maroontress.uncover.gxx;

import java.util.regex.Pattern;

/**
   �ѡ����˻Ȥ�����ɽ�����󶡤��ޤ���
*/
public final class RE {
    /** �ǥޥ󥰥볫�ϤΥѥ�����Ǥ��� */
    public static final Pattern ENCODING;

    /** �����Х륳�󥹥ȥ饯��/�ǥ��ȥ饯�����ϤΥѥ�����Ǥ��� */
    public static final Pattern GLOBAL_CTOR_DTOR;

    /** ���������˥ޥå�����ѥ�����Ǥ��� */
    public static final Pattern NUMBER;

    /** ���󥹥ȥ饯���Υѥ�����Ǥ��� */
    public static final Pattern CTOR;

    /** �ǥ��ȥ饯���Υѥ�����Ǥ��� */
    public static final Pattern DTOR;

    /** �黻�ҤΥѥ�����Ǥ��� */
    public static final Pattern OPERATOR;

    /** ƿ̾̾�����֤Υѥ�����Ǥ��� */
    public static final Pattern ANONYMOUS_NS;

    /** �ִ�ʸ����Υ�������ID�Υѥ�����Ǥ��� */
    public static final Pattern SEQ_ID;

    /** ���󷿤Υѥ�����Ǥ��� */
    public static final Pattern ARRAY_TYPE;

    /** �ƥ�ץ졼�ȥѥ�᡼���Υѥ�����Ǥ��� */
    public static final Pattern TEMPLATE_PARAM;

    /** �ǥ�������ߥ͡����Υѥ�����Ǥ��� */
    public static final Pattern DISCRIMINATOR;

    static {
	NUMBER = Pattern.compile("[1-9][0-9]*");
	CTOR = Pattern.compile("C[123]");
	DTOR = Pattern.compile("D[012]");
	OPERATOR = Pattern.compile("[a-z][A-Za-z]");
	ANONYMOUS_NS = Pattern.compile("_GLOBAL_[._$]N");
	ENCODING = Pattern.compile("_Z");
	GLOBAL_CTOR_DTOR = Pattern.compile("_GLOBAL_[._$]([DI])_");
	SEQ_ID = Pattern.compile("([0-9A-Z]*)_");
	ARRAY_TYPE = Pattern.compile("A([1-9][0-9]*)_");
	TEMPLATE_PARAM = Pattern.compile("T([0-9]*)_I");
	DISCRIMINATOR = Pattern.compile("_[0-9]+");
    }

    /**
       ���󥹥ȥ饯���ϻ��ѤǤ��ޤ���
    */
    private RE() {
    }
}
