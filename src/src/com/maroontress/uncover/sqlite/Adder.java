package com.maroontress.uncover.sqlite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   �ơ��֥�˹Ԥ��ɲä��뵡ǽ���󶡤��ޤ���

   @param <T> �ơ��֥�ιԤ�ɽ�����饹
*/
public final class Adder<T extends Row> extends Querier<T> {
    /**
       ���󥹥ȥ饯���Ǥ���

       @param ps ����ѥ���Ѥߥ��ơ��ȥ���
    */
    public Adder(final PreparedStatement ps) {
	super(ps);
    }

    /**
       �Ԥ�ơ��֥���ɲä��ޤ���

       @return java.sql.PreparedStatement#execute()�������
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public boolean execute() throws SQLException {
	setParameters();
	return getStatement().execute();
    }

    /**
       �ɲä����ԤǼ�ư�������줿�������ͤ�������ޤ���

       @return ��ư�������줿�����η��
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public ResultSet getGeneratedKeys() throws SQLException {
	return getStatement().getGeneratedKeys();
    }

    /**
       �ɲä����ԤǼ�ư�������줿�������ͤ�������ޤ���

       @param num ����ֹ�
       @return ��ư�������줿����
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public long getGeneratedKey(final int num) throws SQLException {
	ResultSet rs = getGeneratedKeys();
	rs.next();
	return rs.getLong(num);
    }
}
