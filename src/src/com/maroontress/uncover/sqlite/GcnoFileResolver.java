package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.SQLException;

/**
   gcnoファイル名を指定して、gcnoファイルIDを取得する機能を提供します。
*/
public final class GcnoFileResolver extends Resolver<GcnoFileRow> {
    /**
       インスタンスを生成します。

       @param con JDBC接続
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public GcnoFileResolver(final Connection con) throws SQLException {
	super(con);
    }

    /** {@inheritDoc} */
    protected Class<GcnoFileRow> getRowClass() {
	return GcnoFileRow.class;
    }

    /** {@inheritDoc} */
    protected GcnoFileRow createRow() {
	return new GcnoFileRow();
    }

    /** {@inheritDoc} */
    protected String getTableName() {
	return Table.GCNO_FILE;
    }

    /**
       gcnoファイル名を指定して、gcnoファイルIDを取得します。

       マッチするIDが存在しない場合は-1を返します。

       @param path gcnoファイルのパス
       @return gcnoファイルID、または-1
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public long getGcnoFileID(final String path) throws SQLException {
	getRow().set(path);
	return getID();
    }
}
