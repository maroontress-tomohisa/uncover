package com.maroontress.uncover;

import java.util.prefs.Preferences;

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
	Preferences prefs = Preferences.userNodeForPackage(Uncover.class);
	dbFile = prefs.get(ConfigKey.DB_DEFAULT, null);
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
