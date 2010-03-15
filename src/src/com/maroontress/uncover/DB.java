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
    String[] getProjectNames() throws DBException;

    /**
       リビジョン名の配列を取得します。

       @param projectName プロジェクト名
       @return リビジョンの配列
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    String[] getRevisionNames(String projectName) throws DBException;

    /**
       同じリビジョンのビルドを削除します。

       @param projectName プロジェクト名
       @param revision リビジョン名
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    void deleteBuilds(String projectName, String revision) throws DBException;

    /**
       ビルドを削除します。

       @param projectName プロジェクト名
       @param id ビルドID
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    void deleteBuild(String projectName, String id) throws DBException;

    /**
       プロジェクトを削除します。

       @param projectName プロジェクト名
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    void deleteProject(String projectName) throws DBException;

    /**
       グラフを取得します。

       @param projectName プロジェクト名
       @param buildID ビルドID
       @param function 関数名
       @param gcnoFile GCNOファイル名
       @return グラフ
       @throws DBException データベース操作に関するエラーが発生したと
       きにスローします。
    */
    Graph getGraph(String projectName, String buildID, String function,
		   String gcnoFile) throws DBException;
}
