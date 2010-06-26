package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.SQLException;

/**
   �ؿ�̾��gcno�ե�����̾���ץ�������̾����ꤷ�ơ��ؿ�ID���������
   ��ǽ���󶡤��ޤ���
*/
public final class FunctionResolver extends Resolver<FunctionRow> {
    /**
       ���󥹥��󥹤��������ޤ���

       @param con JDBC��³
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
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
       �ؿ�̾��gcno�ե�����ID���ץ�������ID����ꤷ�ơ��ؿ�ID�����
       ���ޤ���

       �ޥå�����ؿ�ID��¸�ߤ��ʤ�����-1���֤��ޤ���

       @param functionName �ؿ�̾
       @param gcnoFileID gcno�ե�����ID
       @param projectID �ץ�������ID
       @return �ؿ�ID���ޤ���-1
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public long getFunctionID(final String functionName,
			      final long gcnoFileID,
			      final long projectID) throws SQLException {
	getRow().set(functionName, gcnoFileID, projectID);
	return getID();
    }
}
