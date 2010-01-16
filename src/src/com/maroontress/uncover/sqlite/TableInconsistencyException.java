package com.maroontress.uncover.sqlite;

import java.sql.SQLException;

/**
   テーブルが想定外の状態であることを表す例外です。
*/
public class TableInconsistencyException extends SQLException {
    /**
       例外を生成します。

       @param s メッセージ
    */
    public TableInconsistencyException(final String s) {
        super(s);
    }

    /**
       例外を生成します。

       @param s メッセージ
       @param t 原因
    */
    public TableInconsistencyException(final String s, final Throwable t) {
	super(s, t);
    }
}
