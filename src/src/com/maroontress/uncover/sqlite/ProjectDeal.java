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

       �ץ�������̾�˥ޥå�����ץ������Ȥ����Ĥ���ʤ�����-1��
       �֤��ޤ���

       @param name �ץ�������̾
       @return �ץ�������ID���ޤ���-1
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
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
