package com.maroontress.uncover;

/**
   データベース操作のインタフェイスです。
*/
public interface DB {
    /**
       データベースを初期化します。

       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    void initialize() throws DBException;

    /**
       データベースの接続をクローズします。

       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    void close() throws DBException;

    /**
       データベースに関数情報を登録します。

       @param projectName プロジェクト名
       @param revision リビジョン
       @param timestamp タイムスタンプ
       @param platform プラットフォーム
       @param allFunctions 関数のイテレータのファクトリ
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    void commit(final String projectName, final String revision,
		final String timestamp, final String platform,
		final Iterable<Function> allFunctions) throws DBException;

    /**
       データベースからリビジョンを取得します。

       @param projectName プロジェクト名
       @param revision リビジョン名
       @return リビジョン
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    Revision getRevision(final String projectName, final String revision)
	throws DBException;
}
