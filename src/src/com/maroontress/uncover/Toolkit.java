package com.maroontress.uncover;

/**
   ツールキットです。
*/
public abstract class Toolkit {
    /** ツールキットのインスタンスです。 */
    private static Toolkit theInstance;

    static {
	theInstance = new DefaultToolkit();
    }

    /**
       ツールキットのインスタンスを取得します。

       @return ツールキットのインスタンス
    */
    public static Toolkit getInstance() {
	return theInstance;
    }

    /**
       ツールキットのインスタンスを設定します。

       @param kit ツールキットのインスタンス
    */
    public static void setInstance(final Toolkit kit) {
	theInstance = kit;
    }

    /**
       DBインスタンスを生成します。

       @param subname JDBCの接続URLのサブネーム
       @return DBインスタンス
       @throws DBException データベースの操作でエラーで発生したときに
       スローします。
    */
    public abstract DB createDB(final String subname) throws DBException;
}
