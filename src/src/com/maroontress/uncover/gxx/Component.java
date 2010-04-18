package com.maroontress.uncover.gxx;

import java.util.regex.Matcher;

/**
   �͡��ॹ�ڡ����Υ���ݡ��ͥ�ȤǤ���
*/
public abstract class Component extends Exportable {
    /** �ץ�ե��å���std::�Ǥ���*/
    public static final Component STD = new SourceName("std");

    /** ƿ̾�͡��ॹ�ڡ����Υ���ݡ��ͥ�ȤǤ��� */
    public static final Component ANONYMOUS_NS = new SourceName(
	"(anonymous namespace)");

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

       @param context ����ƥ�����
       @param num ʸ����Ĺ��ɽ��ʸ����
       @return ����ݡ��ͥ��
    */
    public static Component create(final Context context, final String num) {
	int len = Integer.parseInt(num);
	CharSequence sub = context.getSequence(len);
	if (RE.ANONYMOUS_NS.matcher(sub).lookingAt()) {
	    return Component.ANONYMOUS_NS;
	}
	return new SourceName(sub);
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

	if ((m = context.matches(RE.LOCAL_SOURCE_NAME)) != null) {
	    Component c = create(context, m.group(1));
	    context.skipDiscriminator();
	    return c;
	}
	if ((m = context.matches(RE.NUMBER)) != null) {
	    return create(context, m.group());
	}
	if ((m = context.matches(RE.CTOR)) != null) {
	    return CTOR;
	}
	if ((m = context.matches(RE.DTOR)) != null) {
	    return DTOR;
	}
	if ((m = context.matches(RE.OPERATOR)) != null) {
	    return Operator.create(context, m.group());
	}
	throw new IllegalArgumentException("can't demangle: " + context);
    }

    /**
       �������ݡ�����̾����������Ϥ��ޤ���

       @param b �������ݡ���
    */
    public abstract void exportName(Exporter b);
}
