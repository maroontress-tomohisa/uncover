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

       コンテキストの直後にはnumが表す長さの文字列が存在します。コンテ
       キストはnumが表す長さの分進みます。

       @param context コンテキスト
       @param num 文字列長を表す文字列
       @return コンポーネント
       @throws ContextException コンテキストの終端に達した場合スローし
       ます。
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

       コンテキストの直後にはローカルソースネーム、数字、コンストラク
       タ、デストラクタ、演算子のいずれかが存在します。コンテキストは
       コンポーネントの名前の分進みます。

       @param context コンテキスト
       @return コンポーネント
       @throws ContextException コンテキストがコンポーネントで始まって
       いなかった場合スローします。
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
	throw new ContextException(context);
    }

    /**
       エクスポータに名前だけを出力します。

       @param b エクスポータ
    */
    public abstract void exportName(Exporter b);
}
