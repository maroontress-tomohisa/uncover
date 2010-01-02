package com.maroontress.uncover;

/**
   関数を生成するのに必要な情報を提供するインタフェイスです。
*/
public interface FunctionSource {
    /**
       チェックサムを取得します。

       @return チェックサム
    */
    String getCheckSum();

    /**
       複雑度を取得します。

       @return 複雑度
    */
    int getComplexity();

    /**
       関数名を取得します。

       @return 関数名
    */
    String getName();

    /**
       gcnoファイル名を取得します。

       @return gcnoファイル名
    */
    String getGCNOFile();

    /**
       ソースファイル名を取得します。

       @return ソースファイル名
    */
    String getSourceFile();

    /**
       ソースファイルで出現する行番号を取得します。

       @return ソースファイルで出現する行番号
    */
    int getLineNumber();

    /**
       実行済みの基本ブロック数を取得します。

       @return 実行済みの基本ブロック数
    */
    int getExecutedBlocks();

    /**
       基本ブロック総数を取得します。

       @return 基本ブロック総数
    */
    int getAllBlocks();

    /**
       実行済みのアーク数を取得します。

       @return 実行済みのアーク数
    */
    int getExecutedArcs();

    /**
       アーク総数を取得します。

       @return アーク総数
    */
    int getAllArcs();
}
