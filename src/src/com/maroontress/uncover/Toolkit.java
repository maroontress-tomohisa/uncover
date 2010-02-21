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
       インスタンスを生成します。
    */
    protected Toolkit() {
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
       @throws DBException データベースの操作でエラーが発生したときに
       スローします。
    */
    public abstract DB createDB(final String subname) throws DBException;

    /**
       関数インスタンスを生成します。

       関数ソースから値を取得して関数インスタンスを生成します。インス
       タンス生成の際にソースから値をすべてコピーします。したがって、
       生成後にsourceを変更しても、生成した関数インスタンスに影響はあ
       りません。

       @param source 関数ソース
       @return 関数
    */
    public abstract Function createFunction(final FunctionSource source);

    /**
       ビルドインスタンスを生成します。

       ビルドソースから値を取得してビルドインスタンスを生成します。イ
       ンスタンス生成の際にソースから値をすべてコピーします。したがっ
       て、生成後にsourceを変更しても、生成したビルドインスタンスに影
       響はありません。

       @param source ビルドソース
       @return ビルド
    */
    public abstract Build createBuild(final BuildSource source);
}
