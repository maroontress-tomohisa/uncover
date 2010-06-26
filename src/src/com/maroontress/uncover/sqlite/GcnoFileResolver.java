package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

// FunctionResolver�ȤޤȤ��...
/**
   gcno�ե�����̾����ꤷ�ơ�gcno�ե�����ID��������뵡ǽ���󶡤��ޤ���
*/
public final class GcnoFileResolver {
    /** gcno�ե�����ơ��֥�ιԤ��������ե��å���Ǥ��� */
    private Fetcher<GcnoFileRow> fetcher;

    /**
       ���󥹥��󥹤��������ޤ���

       @param con JDBC��³
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public GcnoFileResolver(final Connection con) throws SQLException {
	fetcher = new QuerierFactory<GcnoFileRow>(
	    con, Table.GCNO_FILE, GcnoFileRow.class).createFetcher("id");
	fetcher.setRow(new GcnoFileRow());
    }

    /**
       gcno�ե�����ơ��֥�ιԤΥ��󥹥��󥹤�������ޤ���

       @return gcno�ե�����ơ��֥�ι�
    */
    public GcnoFileRow getGcnoFileRow() {
	return fetcher.getRow();
    }

    /**
       gcno�ե�����̾����ꤷ�ơ�gcno�ե�����ID��������ޤ���

       �ޥå�����ID��¸�ߤ��ʤ�����-1���֤��ޤ���

       @param path gcno�ե�����Υѥ�
       @return gcno�ե�����ID���ޤ���-1
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
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
