package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �ؿ�̾��gcno�ե�����̾���ץ�������̾����ꤷ�ơ��ؿ�ID���������
   ��ǽ���󶡤��ޤ���
*/
public final class FunctionResolver {
    /** �ؿ��ơ��֥�ιԤ��������ե��å���Ǥ��� */
    private Fetcher<FunctionRow> fetcher;

    /**
       ���󥹥��󥹤��������ޤ���

       @param con JDBC��³
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public FunctionResolver(final Connection con) throws SQLException {
	fetcher = new QuerierFactory<FunctionRow>(
	    con, Table.FUNCTION, FunctionRow.class).createFetcher("id");
	fetcher.setRow(new FunctionRow());
    }

    /**
       �ؿ��ơ��֥�ιԤΥ��󥹥��󥹤�������ޤ���

       @return �ؿ��ơ��֥�ι�
    */
    public FunctionRow getFunctionRow() {
	return fetcher.getRow();
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
	fetcher.getRow().set(functionName, gcnoFileID, projectID);
	ResultSet rs = fetcher.executeQuery();

	long functionID = -1;
	int k;
	for (k = 0; rs.next(); ++k) {
	    functionID = rs.getLong("id");
	}
	if (k > 1) {
	    String s = String.format(
		"function %s (gcnoFile=%d; project=%d) found more than one.",
		functionName, gcnoFileID, projectID);
	    throw new TableInconsistencyException(s);
	}
	return functionID;
    }
}
