package com.maroontress.uncover.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �ơ��֥뤫��Ԥ�������뵡ǽ���󶡤��ޤ���

   @param <T> �ơ��֥�ιԤ�ɽ�����饹
*/
public final class Fetcher<T extends Row> extends Querier<T> {
    /**
       ���󥹥ȥ饯���Ǥ���

       @param ps ����ѥ���Ѥߥ��ơ��ȥ���
    */
    public Fetcher(final PreparedStatement ps) {
	super(ps);
    }

    /**
       �Ԥ�ơ��֥뤫��������ޤ���

       @return java.sql.PreparedStatement#executeQuery()�������
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public ResultSet executeQuery() throws SQLException {
	setParameters();
	return getStatement().executeQuery();
    }
}
