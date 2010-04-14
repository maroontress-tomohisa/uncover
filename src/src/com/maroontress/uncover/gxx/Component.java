package com.maroontress.uncover.gxx;

import java.util.regex.Matcher;

/**
   �͡��ॹ�ڡ����Υ���ݡ��ͥ�ȤǤ���
*/
public abstract class Component extends Exportable {
    /** �ץ�ե��å���std::�Ǥ���*/
    public static final Component STD = new ConstantComponent("std");

    /** ƿ̾�͡��ॹ�ڡ����Υ���ݡ��ͥ�ȤǤ��� */
    public static final Component ANONYMOUS_NS
        = new ConstantComponent("(anonymous namespace)");

    /** ���󥹥ȥ饯������ݡ��ͥ�ȤǤ��� */
    public static final Component CTOR = new Constructor();

    /** �ǥ��ȥ饯������ݡ��ͥ�ȤǤ��� */
    public static final Component DTOR = new Destructor();

    /**
       ���󥹥��󥹤��������ޤ���
    */
    protected Component() {
    }

    /**
       ����ݡ��ͥ�Ȥ��������ޤ���

       ����ƥ����Ȥ�ľ��ˤϿ��������󥹥ȥ饯�����ǥ��ȥ饯�����黻
       �ҤΤ����줫��¸�ߤ��ޤ�������ƥ����Ȥϥ���ݡ��ͥ�Ȥ�̾����
       ʬ�ʤߤޤ���

       @param context ����ƥ�����
       @return ����ݡ��ͥ��
    */
    public static Component create(final Context context) {
	Matcher m;

	if ((m = context.matches(RE.NUMBER)) != null) {
	    int len = Integer.parseInt(m.group());
	    CharSequence sub = context.getSequence(len);
	    if (RE.ANONYMOUS_NS.matcher(sub).lookingAt()) {
		return Component.ANONYMOUS_NS;
	    }
	    return TemplatedComponent.create(sub);
	}
	if ((m = context.matches(RE.CTOR)) != null) {
	    return CTOR;
	}
	if ((m = context.matches(RE.DTOR)) != null) {
	    return DTOR;
	}
	if ((m = context.matches(RE.OPERATOR)) != null) {
	    return Operator.create(m.group());
	}
	throw new IllegalArgumentException("can't demangle: " + context);
    }
}
