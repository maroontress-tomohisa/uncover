package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.BlockSource;

/**
   クエリ結果からブロックを生成するためのブロックソースです。
*/
public final class ResultSetBlockSource extends Row implements BlockSource {
    /** 基本ブロックの番号です。 */
    private int number;

    /** 実行回数です。 */
    private long count;

    /** ソースファイルの名前です。 */
    private String sourceFile;

    /**
       ソースファイルでの行番号です。ブロックが複数行にまたがる場合は
       その先頭行になります。
    */
    private int lineNumber;

    /**
       インスタンスを生成します。
    */
    public ResultSetBlockSource() {
    }

    /** {@inheritDoc} */
    public int getNumber() {
        return number;
    }

    /** {@inheritDoc} */
    public long getCount() {
        return count;
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
        return sourceFile;
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
        return lineNumber;
    }
}
