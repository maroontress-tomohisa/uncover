package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
   SQL���ơ��ȥ��Ȥ������Ǥ��������ƥꥹ�Ȥ��������뵡ǽ���󶡤��ޤ���

   @param <X> ��������ꥹ�Ȥ����ǤȤʤ륯�饹
*/
public abstract class ListCreator<X> {
    /** �������¹Ԥ��륤�󥹥��󥹤Ǥ��� */
    private SimpleQuery query;

    /**
       �ǥե���ȥ��󥹥ȥ饯�������ѤǤ��ޤ���
    */
    private ListCreator() {
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param con JDBC����³
    */
    public ListCreator(final Connection con) {
	this.query = new SimpleQuery(con);
    }

    /**
       �����������Ǥ�������ޤ���

       @param toolkit �ġ��륭�åȤΥ��󥹥���
       @return ������������
    */
    public abstract X create(Toolkit toolkit);

    /**
       �ꥶ��ȥ��åȤ����ꤹ��Ԥ�������ޤ���

       @return �ꥶ��ȥ��åȤ����ꤹ���
    */
    public abstract Row getRow();

    /**
       SQL���ơ��ȥ��Ȥȥѥ�᡼���������Ǥ��������ơ��ꥹ�ȤȤ��Ƽ�
       �����ޤ���

       @param template ������ʸ����
       @param params ������Υѥ�᡼��
       @return �����������ǤΥꥹ��
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    public final List<X> getList(final String template,
				 final Object[] params) throws SQLException {
	ResultSet rs = query.executeQuery(template, params);

	Toolkit toolkit = Toolkit.getInstance();
	Row source = getRow();
	List<X> list = new ArrayList<X>();
	while (rs.next()) {
	    source.setResultSet(rs);
	    list.add(create(toolkit));
	}
	return list;
    }
}
