package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
   型です。
*/
public final class Type extends Exportable {
    /** 型の名前を表すインスタンスです。 */
    private TypeName name;

    /** 型の修飾子のリストです。 */
    private List<String> qualifierList;

    /**
       インスタンスを生成します。
    */
    private Type() {
	qualifierList = new ArrayList<String>();
    }

    /**
       文字列で型名を生成します。

       @param name 型名
    */
    private Type(final String name) {
	this();
	this.name = new StringTypeName(name);
    }

    /**
       置換文字列で型名を生成します。

       @param name 型名
    */
    private Type(final Exportable name) {
	this();
	this.name = new ExportableTypeName(name);
    }

    /**
       コンテキストで型名を生成します。

       コンテキストは型名が始まる位置にある必要があります。

       コンテキストは型名分進みます。

       @param context コンテキスト
    */
    private Type(final Context context) {
	this();
	this.name = new ExportableTypeName(context);
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

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	name.exportName(b);
	for (String q : qualifierList) {
	    b.append(" ");
	    b.append(q);
	}
    }
}
