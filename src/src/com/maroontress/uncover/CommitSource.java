package com.maroontress.uncover;

/**
   DBインタフェイスのcommitメソッドの引数となるインタフェイスです。
*/
public interface CommitSource {
    /**
       プロジェクト名を取得します。

       @return プロジェクト名
    */
    String getProjectName();

    /**
       リビジョンを取得します。

       @return リビジョン
    */
    String getRevision();

    /**
       タイムスタンプを取得します。

       @return タイムスタンプ
    */
    String getTimestamp();

    /**
       プラットフォームを取得します。

       @return プラットフォーム
    */
    String getPlatform();

    /**
       関数のイテレータのファクトリを取得します。

       @return 関数のイテレータのファクトリ
    */
    Iterable<Function> getAllFunctions();
}
