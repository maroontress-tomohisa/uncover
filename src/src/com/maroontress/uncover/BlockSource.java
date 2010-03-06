package com.maroontress.uncover;

/**
   基本ブロックを生成するのに必要な情報を提供するインタフェイスです。
*/
public interface BlockSource {
    /**
       番号を取得します。

       @return 番号
    */
    int getNumber();

    /**
       実行回数を取得します。

       @return 実行回数
    */
    int getCount();

    /**
       ソースファイルの名前を取得します。

       ブロックにソースファイルの情報が無い場合はnullを返します。

       @return ソースファイルの名前、またはnull
    */
    String getSourceFile();

    /**
       ソースファイルの行番号を取得します。

       ブロックにソースファイルの情報が無い場合は0を返します。

       ブロックが複数行にまたがる場合はその先頭行の行番号になります。

       @return ソースファイルの行番号、または0
    */
    int getLineNumber();
}
