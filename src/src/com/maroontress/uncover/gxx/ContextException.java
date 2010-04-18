package com.maroontress.uncover.gxx;

/**
   コンテキスト操作の例外です。
*/
public class ContextException extends RuntimeException {
    /**
       例外を生成します。

       @param s メッセージ
    */
    public ContextException(final String s) {
        super(s);
    }

    /**
       例外を生成します。

       @param s メッセージ
       @param t 原因
    */
    public ContextException(final String s, final Throwable t) {
	super(s, t);
    }

    /**
       例外を生成します。

       @param c コンテキスト
    */
    public ContextException(final Context c) {
	super("can't demangle: " + c.toString());
    }

    /**
       例外を生成します。

       @param c コンテキスト
       @param s メッセージ
    */
    public ContextException(final Context c, final String s) {
	super("can't demangle: " + c.toString() + ":" + s);
    }
}
