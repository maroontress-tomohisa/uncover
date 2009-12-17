package com.maroontress.uncover;

/**
   プロパティです。
*/
public final class Properties {
    /** データベースファイルのパスです。 */
    private String dbFile;

    /**
       デフォルトのプロパティを生成します。
    */
    public Properties() {
	dbFile = null;
    }

    /**
       データベースファイルのパスを設定します。

       @param dbFile データベースファイルのパス
    */
    public void setDBFile(final String dbFile) {
	this.dbFile = dbFile;
    }

    /**
       データベースファイルのパスを取得します。

       @return データベースファイルのパス
    */
    public String getDBFile() {
	return dbFile;
    }
}
