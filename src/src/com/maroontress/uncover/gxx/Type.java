package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
   型です。
*/
public abstract class Type extends Exportable {
    /** */
    private static final String CAST_BOOL = "(bool)";

    /** 型の修飾子のリストです。 */
    private List<String> qualifierList;

    /**
       インスタンスを生成します。
    */
    protected Type() {
	qualifierList = new ArrayList<String>();
    }

    /**
       型を生成します。

       コンテキストは型が始まる位置にある必要があります。まず、修飾子
       [rKV]*があります。その後は、組み込みの型名、P、N、Sのいずれかの
       可能性があります。

       コンテキストは型の分進みます。

       @param context コンテキスト
       @return 型
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
	    // ListArgumentとまとめる
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
       修飾子のリストを追加します。

       @param qualifiers 修飾子のリスト
    */
    private void add(final List<String> qualifiers) {
	qualifierList.addAll(qualifiers);
    }

    /**
       修飾子を追加します。

       @param qualifier 修飾子
    */
    private void add(final String qualifier) {
	qualifierList.add(qualifier);
    }

    /**
       修飾子をもつかどうかを取得します。

       @return 修飾子をもつ場合はtrue
    */
    protected final boolean hasQualifiers() {
	return !qualifierList.isEmpty();
    }

    /**
       文字がポインタまたは参照演算子を表すかどうかを取得します。

       @param c 文字
       @return 文字がポインタまたは参照演算子の場合はtrue
    */
    private static boolean isModifier(final char c) {
	return (c == '*' || c == '&');
    }

    /**
       修飾子を出力します。

       @param b エクスポータ
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
