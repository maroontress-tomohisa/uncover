package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
   ���Ǥ���
*/
public abstract class Type extends Exportable {
    /** */
    private static final String CAST_BOOL = "(bool)";

    /** ���ν����ҤΥꥹ�ȤǤ��� */
    private List<String> qualifierList;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    protected Type() {
	qualifierList = new ArrayList<String>();
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
	    return new ArrayType(Type.create(context), elementNum);
	}
	if ((m = context.matches(RE.NUMBER)) != null) {
	    Component e = Component.create(context, m.group());
	    if (context.startsWith('I')) {
		context.addSubstitution(e);
		e = new TemplatedComponent(e, context);
	    }
	    Type type = new DefaultType(e);
	    context.addSubstitution(type);
	    return type;
	}
	if ((m = context.matches(RE.TEMPLATE_PARAM)) != null) {
	    // ListArgument�ȤޤȤ��
	    String num = m.group(1);
	    int k = (num.isEmpty() ? 0 : Integer.parseInt(num));
	    context.addSubstitution(new DefaultType("template-param " + k));

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
	    return new DefaultType(s);
	}
	char c = context.getChar();
	String name;
	if ((name = BuiltinType.getName(c)) != null) {
	    return new DefaultType(name);
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
	    return new DefaultType(s);
	}
	if (c == 'M') {
	    Type classType = create(context);
	    Type memberType = create(context);
	    memberType.add(classType.toString() + "::*");
	    return memberType;
	}
	if (c == 'F') {
	    Type type = new FunctionType(context);
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
	    Type type = new DefaultType(Composite.newQualifiedName(context));
	    context.addSubstitution(type);
	    return type;
	}
	if (c == 'S') {
	    return new DefaultType(context);
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
       �����Ҥ��Ĥ��ɤ�����������ޤ���

       @return �����Ҥ��ľ���true
    */
    protected final boolean hasQualifiers() {
	return !qualifierList.isEmpty();
    }

    /**
       ʸ�����ݥ��󥿤ޤ��ϻ��ȱ黻�Ҥ�ɽ�����ɤ�����������ޤ���

       @param c ʸ��
       @return ʸ�����ݥ��󥿤ޤ��ϻ��ȱ黻�Ҥξ���true
    */
    private static boolean isModifier(final char c) {
	return (c == '*' || c == '&');
    }

    /**
       �����Ҥ���Ϥ��ޤ���

       @param b �������ݡ���
    */
    protected final void exportQualifiers(final Exporter b) {
	for (String q : qualifierList) {
	    char last = b.lastChar();
	    if (last != '('
		&& (!isModifier(q.charAt(0))
		    || !isModifier(last))) {
		b.append(" ");
	    }
	    b.append(q);
	}
    }
}
