package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

// FunctionResolverとまとめる...
/**
   gcnoファイル名を指定して、gcnoファイルIDを取得する機能を提供します。
*/
public final class GcnoFileResolver {
    /** gcnoファイルテーブルの行を取得するフェッチャです。 */
    private Fetcher<GcnoFileRow> fetcher;

    /**
       インスタンスを生成します。

       @param con JDBC接続
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public GcnoFileResolver(final Connection con) throws SQLException {
	fetcher = new QuerierFactory<GcnoFileRow>(
	    con, Table.GCNO_FILE, GcnoFileRow.class).createFetcher("id");
	fetcher.setRow(new GcnoFileRow());
    }

    /**
       gcnoファイルテーブルの行のインスタンスを取得します。

       @return gcnoファイルテーブルの行
    */
    public GcnoFileRow getGcnoFileRow() {
	return fetcher.getRow();
    }

    /**
       gcnoファイル名を指定して、gcnoファイルIDを取得します。

       マッチするIDが存在しない場合は-1を返します。

       @param path gcnoファイルのパス
       @return gcnoファイルID、または-1
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public long getGcnoFileID(final String path) throws SQLException {
	fetcher.getRow().set(path);
	ResultSet rs = fetcher.executeQuery();

	long id = -1;
	int k;
	for (k = 0; rs.next(); ++k) {
	    id = rs.getLong("id");
	}
	if (k > 1) {
	    String s = String.format("gcnoFile %s found more than one.", path);
	    throw new TableInconsistencyException(s);
	}
	return id;
    }
}
