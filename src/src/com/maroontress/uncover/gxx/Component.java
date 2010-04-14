package com.maroontress.uncover.gxx;

import java.util.regex.Matcher;

/**
   ネームスペースのコンポーネントです。
*/
public abstract class Component extends Exportable {
    /** プレフィックスstd::です。*/
    public static final Component STD = new ConstantComponent("std");

    /** 匿名ネームスペースのコンポーネントです。 */
    public static final Component ANONYMOUS_NS
        = new ConstantComponent("(anonymous namespace)");

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

       コンテキストの直後には数字、コンストラクタ、デストラクタ、演算
       子のいずれかが存在します。コンテキストはコンポーネントの名前の
       分進みます。

       @param context コンテキスト
       @return コンポーネント
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
