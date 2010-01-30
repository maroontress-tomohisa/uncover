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
    void commit(CommitSource source) throws DBException;

    /**
       同じリビジョンのビルドを取得します。

       @param projectName プロジェクト名
       @param revision リビジョン名
       @return ビルドの配列
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    Build[] getBuilds(String projectName, String revision) throws DBException;

    /**
       ビルドを取得します。

       @param projectName プロジェクト名
       @param id ビルドID
       @return ビルド
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    Build getBuild(String projectName, String id) throws DBException;

    /**
       リビジョンを取得します。

       @param id ビルドID
       @return リビジョン
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    Revision getRevision(String id) throws DBException;

    /**
       プロジェクト名の配列を取得します。

       @return プロジェクト名の配列
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    String[] getProjects() throws DBException;
}
