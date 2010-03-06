package com.maroontress.uncover;

/**
   基本ブロックに関する情報をカプセル化します。
*/
public final class Block implements BlockSource {
    /** 基本ブロックの番号です。 */
    private int number;

    /** 実行回数です。 */
    private int count;

    /** ソースファイルの名前です。 */
    private String sourceFile;

    /**
       ソースファイルでの行番号です。ブロックが複数行にまたがる場合は
       その先頭行になります。
    */
    private int lineNumber;

    /**
       デフォルトコンストラクタは使用できません。
    */
    private Block() {
    }

    /**
       インスタンスを生成します。

       @param s ブロックソース
    */
    public Block(final BlockSource s) {
	number = s.getNumber();
	count = s.getCount();
	sourceFile = s.getSourceFile();
	if (sourceFile != null) {
	    sourceFile = sourceFile.intern();
	}
	lineNumber = s.getLineNumber();
    }

    /** {@inheritDoc} */
    public int getNumber() {
	return number;
    }

    /** {@inheritDoc} */
    public int getCount() {
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
