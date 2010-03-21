package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
   SQL���ơ��ȥ��Ȥ������Ǥ��������ƥꥹ�Ȥ��������뵡ǽ���󶡤��ޤ���

   @param <X> ��������ꥹ�Ȥ����ǤȤʤ륯�饹
*/
public abstract class ListCreator<X> {
    /** JDBC����³�Ǥ��� */
    private Connection con;

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
	this.con = con;
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
	PreparedStatement s = con.prepareStatement(template);
	setParameter(s, params);
	ResultSet rs = s.executeQuery();

	Toolkit toolkit = Toolkit.getInstance();
	Row source = getRow();
	List<X> list = new ArrayList<X>();
	while (rs.next()) {
	    source.setResultSet(rs);
	    list.add(create(toolkit));
	}
	return list;
    }

    /**
       ����ѥ���ѤߤΥ��ơ��ȥ��Ȥ˥ѥ�᡼�������ꤷ�ޤ���

       @param s ���ơ��ȥ���
       @param values �ѥ�᡼�����ͤ�����
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    private static void setParameter(final PreparedStatement s,
				     final Object[] values)
	throws SQLException {
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
    }
}
