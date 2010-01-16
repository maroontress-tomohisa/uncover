package com.maroontress.uncover.sqlite;

import java.sql.PreparedStatement;

/**
   テーブルに行単位で操作する機能を提供します。

   @param <T> テーブルの行を表すクラス
*/
public abstract class Querier<T extends Row> {
    /** コンパイル済みステートメントです。 */
    private PreparedStatement ps;

    /** 行のインスタンスです。 */
    private T instance;

    /**
       コンストラクタです。

       @param ps コンパイル済みステートメント
    */
    protected Querier(final PreparedStatement ps) {
	this.ps = ps;
    }

    /**
       行を表すインスタンスを設定します。

       @param instance 行を表すインスタンス
    */
    public final void setRow(final T instance) {
	this.instance = instance;
    }

    /**
       行を表すインスタンスを取得します。

       @return 行を表すインスタンス
    */
    public final T getRow() {
	return instance;
    }

    /**
       コンパイル済みステートメントを取得します。

       @return コンパイル済みステートメント
    */
    protected final PreparedStatement getStatement() {
	return ps;
    }
}
