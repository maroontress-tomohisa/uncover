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

       @param source 登録する情報のソース
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    void commit(final CommitSource source) throws DBException;

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
