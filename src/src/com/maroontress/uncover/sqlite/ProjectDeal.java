package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �ץ������ȥơ��֥�˴ؤ�������򰷤��ޤ���
*/
public final class ProjectDeal {
    /** �ץ������ȥơ��֥뤫��Ԥ�������륤�󥹥��󥹤Ǥ��� */
    private Fetcher<ProjectRow> fetcher;

    /**
       ���󥹥��󥹤��������ޤ���

       @param con �ǡ����١����Ȥ���³
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public ProjectDeal(final Connection con) throws SQLException {
	fetcher = new QuerierFactory<ProjectRow>(
	    con, Table.PROJECT, ProjectRow.class).createFetcher("id");
	fetcher.setRow(new ProjectRow());
    }

    /**
       �ץ�������̾����ץ�������ID��������ޤ���

       @param name �ץ�������̾
       @return �ץ�������ID
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
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
