package com.maroontress.uncover.gxx;

import java.util.regex.Matcher;

/**
   ネームスペースのコンポーネントです。
*/
public abstract class Component extends Exportable {
    /** プレフィックスstd::です。*/
    public static final Component STD = new SourceName("std");

    /** 匿名ネームスペースのコンポーネントです。 */
    public static final Component ANONYMOUS_NS = new SourceName(
	"(anonymous namespace)");

    /** コンストラクタコンポーネントです。 */
    public static final Component CTOR = new Constructor();

    /** デストラクタコンポーネントです。 */
    public static final Component DTOR = new Destructor();

    /**
       インスタンスを生成します。
    */
    protected Component() {
    }

    /**
       コンポーネントを生成します。

       @param context コンテキスト
       @param num 文字列長を表す文字列
       @return コンポーネント
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
       コンポーネントを生成します。

       コンテキストの直後には数字、コンストラクタ、デストラクタ、演算
       子のいずれかが存在します。コンテキストはコンポーネントの名前の
       分進みます。

       @param context コンテキスト
       @return コンポーネント
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
       エクスポータに名前だけを出力します。

       @param b エクスポータ
    */
    public abstract void exportName(Exporter b);
}
