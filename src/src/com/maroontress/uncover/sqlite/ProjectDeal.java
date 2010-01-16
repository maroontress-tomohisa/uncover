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

       @param name プロジェクト名
       @return プロジェクトID
       @throws SQLException クエリにエラーが発生したときにスローします。
    */
    public String queryID(final String name) throws SQLException {
	fetcher.getRow().set(name);
	ResultSet rs = fetcher.executeQuery();
	int k;
	String id = null;

	for (k = 0; rs.next(); ++k) {
	    id = rs.getString("id");
	}
	if (k > 1) {
	    String m = String.format("project '%s' found more than one.",
				     name);
	    throw new TableInconsistencyException(m);
	}
	return id;
    }
}
