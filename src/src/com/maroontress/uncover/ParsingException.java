package com.maroontress.uncover;

/**
   関数グラフの入力となるファイルのパース処理に関する例外です。
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
