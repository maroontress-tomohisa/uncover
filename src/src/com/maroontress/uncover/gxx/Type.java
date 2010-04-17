package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
   ���Ǥ���
*/
public final class Type extends Exportable {
    /** */
    private static final String CAST_BOOL = "(bool)";

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
       �������ݡ����֥�Ƿ�̾���������ޤ���

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
	Matcher m;

	if ((m = context.matches(RE.ARRAY_TYPE)) != null) {
	    int elementNum = Integer.parseInt(m.group(1));
	    Type type = Type.create(context);
	    type.add("[" + elementNum + "]");
	    return type;
	}
	if ((m = context.matches(RE.NUMBER)) != null) {
	    Component e = Component.create(context, m.group());
	    if (context.startsWith('I')) {
		context.addSubstitution(e);
		e = new TemplatedComponent(e, context);
	    }
	    Type type = new Type(e);
	    context.addSubstitution(type);
	    return type;
	}
	if ((m = context.matches(RE.TEMPLATE_PARAM)) != null) {
	    String num = m.group(1);
	    int k = (num.isEmpty() ? 0 : Integer.parseInt(num));
	    context.addSubstitution(new Type("template-param " + k));

	    List<Type> list = new ArrayList<Type>();
	    do {
		list.add(Type.create(context));
	    } while (!context.startsWith('E'));
	    String s = list.get(k).toString();
	    char c = s.charAt(s.length() - 1);
	    if (!Character.isLetterOrDigit(c) && c != '_') {
		s += " ";
	    }
	    s += "<";
	    String prefix = "";
	    for (Type t : list) {
		s += prefix + t.toString();
		prefix = ", ";
	    }
	    s += ">";
	    return new Type(s);
	}
	char c = context.getChar();
	String name;
	if ((name = BuiltinType.getName(c)) != null) {
	    return new Type(name);
	}
	if (c == 'L') {
	    Type type = Type.create(context);
	    String s = "(" + type.toString() + ")";
	    if (s.equals("(int)")) {
		s = "";
	    }
	    do {
		char n = context.getChar();
		if (n == 'n') {
		    n = '-';
		}
		s += n;
	    } while (!context.startsWith('E'));
	    if (s.startsWith(CAST_BOOL)) {
		s = s.substring(CAST_BOOL.length());
		s = Boolean.toString(Integer.parseInt(s) != 0);
	    }
	    return new Type(s);
	}
	if (c == 'M') {
	    Type classType = create(context);
	    Type memberType = create(context);
	    memberType.add(classType.toString() + "::*");
	    return memberType;
	}
	if (c == 'F') {
	    Type type = create(context);
	    String s = "()(";
	    String prefix = "";
	    while (!context.startsWith('E')) {
		s += prefix + Type.create(context).toString();
		prefix = ", ";
	    }
	    s += ")";
	    type.add(s);
	    context.addSubstitution(type);
	    return type;
	}
	if (c == 'P') {
	    Type type = create(context);
	    type.add("*");
	    context.addSubstitution(type);
	    return type;
	}
	if (c == 'R') {
	    Type type = create(context);
	    type.add("&");
	    context.addSubstitution(type);
	    return type;
	}
	if (c == 'N') {
	    Type type = new Type(Composite.newQualifiedName(context));
	    context.addSubstitution(type);
	    return type;
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

    /**
       ʸ�����ݥ��󥿤ޤ��ϻ��ȱ黻�Ҥ�ɽ�����ɤ�����������ޤ���

       @param c ʸ��
       @return ʸ�����ݥ��󥿤ޤ��ϻ��ȱ黻�Ҥξ���true
    */
    private static boolean isModifier(final char c) {
	return (c == '*' || c == '&');
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	name.exportName(b);
	for (String q : qualifierList) {
	    if (!isModifier(q.charAt(0))
		|| !isModifier(b.lastChar())) {
		b.append(" ");
	    }
	    b.append(q);
	}
    }
}
