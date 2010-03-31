package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   関数名、gcnoファイル名、プロジェクト名を指定して、関数IDを取得する
   機能を提供します。
*/
public final class FunctionResolver {
    /** 関数テーブルの行を取得するフェッチャです。 */
    private Fetcher<FunctionRow> fetcher;

    /**
       インスタンスを生成します。

       @param con JDBC接続
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public FunctionResolver(final Connection con) throws SQLException {
	fetcher = new QuerierFactory<FunctionRow>(
	    con, Table.FUNCTION, FunctionRow.class).createFetcher("id");
	fetcher.setRow(new FunctionRow());
    }

    /**
       関数テーブルの行のインスタンスを取得します。

       @return 関数テーブルの行
    */
    public FunctionRow getFunctionRow() {
	return fetcher.getRow();
    }

    /**
       関数名、gcnoファイル名、プロジェクトIDを指定して、関数IDを取得
       します。

       マッチする関数IDが存在しない場合は-1を返します。

       @param functionName 関数名
       @param gcnoFile gcnoファイル名
       @param projectID プロジェクトID
       @return 関数ID、または-1
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public long getFunctionID(final String functionName,
			      final String gcnoFile,
			      final long projectID) throws SQLException {
	fetcher.getRow().set(functionName, gcnoFile, projectID);
	ResultSet rs = fetcher.executeQuery();

	long functionID = -1;
	int k;
	for (k = 0; rs.next(); ++k) {
	    functionID = rs.getLong("id");
	}
	if (k > 1) {
	    String s = String.format(
		"projectID: %s; function %s (%s) found more than one.",
		projectID, functionName, gcnoFile);
	    throw new TableInconsistencyException(s);
	}
	return functionID;
    }
}
