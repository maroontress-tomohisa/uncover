package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
   ���Ǥ���
*/
public final class Type extends Exportable {
    /** ����̾����ɽ�����󥹥��󥹤Ǥ��� */
    private TypeName name;

    /** ���ν����ҤΥꥹ�ȤǤ��� */
    private List<String> qualifierList;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    private Type() {
	qualifierList = new ArrayList<String>();
    }

    /**
       ʸ����Ƿ�̾���������ޤ���

       @param name ��̾
    */
    private Type(final String name) {
	this();
	this.name = new StringTypeName(name);
    }

    /**
       �ִ�ʸ����Ƿ�̾���������ޤ���

       @param name ��̾
    */
    private Type(final Exportable name) {
	this();
	this.name = new ExportableTypeName(name);
    }

    /**
       ����ƥ����ȤǷ�̾���������ޤ���

       ����ƥ����ȤϷ�̾���Ϥޤ���֤ˤ���ɬ�פ�����ޤ���

       ����ƥ����ȤϷ�̾ʬ�ʤߤޤ���

       @param context ����ƥ�����
    */
    private Type(final Context context) {
	this();
	this.name = new ExportableTypeName(context);
    }

    /**
       �����������ޤ���

       ����ƥ����ȤϷ����Ϥޤ���֤ˤ���ɬ�פ�����ޤ����ޤ���������
       [rKV]*������ޤ������θ�ϡ��Ȥ߹��ߤη�̾��P��N��S�Τ����줫��
       ��ǽ��������ޤ���

       ����ƥ����ȤϷ���ʬ�ʤߤޤ���

       @param context ����ƥ�����
       @return ��
    */
    public static Type create(final Context context) {
	List<String> qualifiers = new ArrayList<String>();
	context.parseQualifier(qualifiers);
	if (!qualifiers.isEmpty()) {
	    Type type = create(context);
	    type.add(qualifiers);
	    context.addSubstitution(type);
	    return type;
	}
	char c = context.getChar();
	String name;
	if ((name = BuiltinType.getName(c)) != null) {
	    return new Type(name);
	}
	if (c == 'P') {
	    Type type = create(context);
	    type.add("*");
	    context.addSubstitution(type);
	    return type;
	}
	if (c == 'N') {
	    return new Type(Composite.newQualifiedName(context));
	}
	if (c == 'S') {
	    return new Type(context);
	}
	throw new IllegalArgumentException("can't demangle: " + context);
    }

    /**
       �����ҤΥꥹ�Ȥ��ɲä��ޤ���

       @param qualifiers �����ҤΥꥹ��
    */
    private void add(final List<String> qualifiers) {
	qualifierList.addAll(qualifiers);
    }

    /**
       �����Ҥ��ɲä��ޤ���

       @param qualifier ������
    */
    private void add(final String qualifier) {
	qualifierList.add(qualifier);
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	name.exportName(b);
	for (String q : qualifierList) {
	    b.append(" ");
	    b.append(q);
	}
    }
}
