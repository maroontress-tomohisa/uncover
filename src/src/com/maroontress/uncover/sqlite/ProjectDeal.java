package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   プロジェクトテーブルに関する処理を扱います。
*/
public final class ProjectDeal {
    /** プロジェクトテーブルから行を取得するインスタンスです。 */
    private Fetcher<ProjectRow> fetcher;

    /**
       インスタンスを生成します。

       @param con データベースとの接続
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public ProjectDeal(final Connection con) throws SQLException {
	fetcher = new QuerierFactory<ProjectRow>(
	    con, Table.PROJECT, ProjectRow.class).createFetcher("id");
	fetcher.setRow(new ProjectRow());
    }

    /**
       プロジェクト名からプロジェクトIDを取得します。

       プロジェクト名にマッチするプロジェクトが見つからない場合は-1を
       返します。

       @param name プロジェクト名
       @return プロジェクトID、または-1
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public long queryID(final String name) throws SQLException {
	fetcher.getRow().set(name);
	ResultSet rs = fetcher.executeQuery();
	int k;
	long id = -1;

	for (k = 0; rs.next(); ++k) {
	    id = rs.getLong("id");
	}
	if (k > 1) {
	    String m = String.format("project '%s' found more than one.",
				     name);
	    throw new TableInconsistencyException(m);
	}
	return id;
    }
}
