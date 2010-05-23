package com.maroontress.cui;

/**
   コマンドラインオプションのパースに関する例外です。
*/
public class OptionsParsingException extends Exception {

    /**
       コマンドラインオプションのパースに関する例外を生成します。
    */
    public OptionsParsingException() {
	super();
    }

    /**
       コマンドラインオプションのパースに関する例外を生成します。

       @param m 詳細メッセージ
    */
    public OptionsParsingException(final String m) {
	super(m);
    }

    /**
       コマンドラインオプションのパースに関する例外を生成します。

       @param m メッセージ
       @param t 原因
    */
    public OptionsParsingException(final String m, final Throwable t) {
        super(m, t);
    }
}
