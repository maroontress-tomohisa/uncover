package com.maroontress.uncover;

/**
   コマンドの実行に関する例外です。
*/
public class CommandException extends Exception {
    /**
       例外を生成します。

       @param s メッセージ
       @param t 原因
    */
    public CommandException(final String s, final Throwable t) {
	super(s, t);
    }
}
