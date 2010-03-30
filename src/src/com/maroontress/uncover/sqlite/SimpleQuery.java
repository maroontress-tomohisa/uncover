package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
   ��ñ�ʥ������¹Ԥ��뵡ǽ���󶡤��ޤ���

   ������ʸ����Ȥ��Υѥ�᡼�����Ȥ߹礻�ϡ�����ѥ�����ˤ��줬����
   �����ɤ�����Ƚ�ꤹ�뤳�ȤϤǤ��ޤ���
*/
public final class SimpleQuery {
    /** JDBC����³�Ǥ��� */
    private Connection con;

    /**
       �ǥե���ȥ��󥹥ȥ饯�������ѤǤ��ޤ���
    */
    private SimpleQuery() {
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param con JDBC����³
    */
    public SimpleQuery(final Connection con) {
	this.con = con;
    }

    /**
       SQL���ơ��ȥ��ȤȤ��Υѥ�᡼����¹Ԥ��ƥꥶ��ȥ��åȤ�����
       ���ޤ���

       @param template ������ʸ����
       @param params ������Υѥ�᡼��
       @return ���������ꥶ��ȥ��å�
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    public ResultSet executeQuery(final String template,
				  final Object[] params) throws SQLException {
	PreparedStatement s = prepareStatement(template, params);
	return s.executeQuery();
    }

    /**
       SQL���ơ��ȥ��ȤȤ��Υѥ�᡼����¹Ԥ��ޤ���

       @param template ������ʸ����
       @param params ������Υѥ�᡼��
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    public void execute(final String template,
			final Object[] params) throws SQLException {
	PreparedStatement s = prepareStatement(template, params);
	s.execute();
    }

    /**
       ����ѥ���ѤߤΥ��ơ��ȥ��Ȥ�������������˥ѥ�᡼��������
       ������Τ�������ޤ���

       @param template ������ʸ����
       @param values �ѥ�᡼�����ͤ�����
       @return ����ѥ���ѤߤΥ��ơ��ȥ���
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private PreparedStatement prepareStatement(
	final String template, final Object[] values) throws SQLException {
	PreparedStatement s = con.prepareStatement(template);
	int k = 0;
	for (Object v : values) {
	    ++k;
	    if (v instanceof String) {
		s.setString(k, (String) v);
	    } else if (v instanceof Integer) {
		s.setInt(k, (Integer) v);
	    } else {
		throw new IllegalArgumentException("unexpected type: "
						   + v.getClass().toString());
	    }
	}
	return s;
    }
}
