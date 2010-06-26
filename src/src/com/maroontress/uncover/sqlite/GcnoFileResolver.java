package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.SQLException;

/**
   gcno�ե�����̾����ꤷ�ơ�gcno�ե�����ID��������뵡ǽ���󶡤��ޤ���
*/
public final class GcnoFileResolver extends Resolver<GcnoFileRow> {
    /**
       ���󥹥��󥹤��������ޤ���

       @param con JDBC��³
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
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
       gcno�ե�����̾����ꤷ�ơ�gcno�ե�����ID��������ޤ���

       �ޥå�����ID��¸�ߤ��ʤ�����-1���֤��ޤ���

       @param path gcno�ե�����Υѥ�
       @return gcno�ե�����ID���ޤ���-1
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public long getGcnoFileID(final String path) throws SQLException {
	getRow().set(path);
	return getID();
    }
}
