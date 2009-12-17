package com.maroontress.uncover;

/**
   データベースの操作に関する例外です。
*/
public class DBException extends Exception {
    /**
       例外を生成します。

       @param s メッセージ
    */
    public DBException(final String s) {
        super(s);
    }

    /**
       例外を生成します。

       @param s メッセージ
       @param t 原因
    */
    public DBException(final String s, final Throwable t) {
	super(s, t);
    }
}
