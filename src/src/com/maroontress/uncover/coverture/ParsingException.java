package com.maroontress.uncover.coverture;

/**
   Covertureの生成したXMLのパースに関する例外です。
*/
public class ParsingException extends Exception {
    /**
       例外を生成します。

       @param s メッセージ
    */
    public ParsingException(final String s) {
        super(s);
    }

    /**
       例外を生成します。

       @param s メッセージ
       @param t 原因
    */
    public ParsingException(final String s, final Throwable t) {
	super(s, t);
    }
}
