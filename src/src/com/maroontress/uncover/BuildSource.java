package com.maroontress.uncover;

/**
   ビルドを生成するのに必要な情報を提供するインタフェイスです。
*/
public interface BuildSource {
    /**
       IDを取得します。

       @return ID
    */
    String getID();

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
}
