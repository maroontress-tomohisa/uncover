package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.SQLException;

/**
   関数名、gcnoファイル名、プロジェクト名を指定して、関数IDを取得する
   機能を提供します。
*/
public final class FunctionResolver extends Resolver<FunctionRow> {
    /**
       インスタンスを生成します。

       @param con JDBC接続
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public FunctionResolver(final Connection con) throws SQLException {
	super(con);
    }

    /** {@inheritDoc} */
    protected Class<FunctionRow> getRowClass() {
	return FunctionRow.class;
    }

    /** {@inheritDoc} */
    protected FunctionRow createRow() {
	return new FunctionRow();
    }

    /** {@inheritDoc} */
    protected String getTableName() {
	return Table.FUNCTION;
    }

    /**
       関数名、gcnoファイルID、プロジェクトIDを指定して、関数IDを取得
       します。

       マッチする関数IDが存在しない場合は-1を返します。

       @param functionName 関数名
       @param gcnoFileID gcnoファイルID
       @param projectID プロジェクトID
       @return 関数ID、または-1
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public long getFunctionID(final String functionName,
			      final long gcnoFileID,
			      final long projectID) throws SQLException {
	getRow().set(functionName, gcnoFileID, projectID);
	return getID();
    }
}
