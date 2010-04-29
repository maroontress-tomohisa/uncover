package com.maroontress.jdbcskirt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
   JDBC�ɥ饤�ФΥ��ɤȡ��ɥ饤�Хޥ͡����㤫�饳�ͥ������������
   �뵡ǽ���󶡤��ޤ���
*/
public final class Skirt {
    /**
       ���󥹥ȥ饯���ϻ��ѤǤ��ޤ���
    */
    private Skirt() {
    }

    /**
       JDBC�Υ��ͥ�������������ޤ���

       @param name JDBC�ɥ饤�ФΥ��饹̾
       @param url JDBC�ǡ����١���URL
       @return JDBC�Υ��ͥ������
       @throws SQLException �ǡ����١��������������顼��ȯ��������祹
       �����ޤ���
       @throws ClassNotFoundException JDBC�ɥ饤�ФΥ��饹�����Ĥ����
       ����祹�����ޤ���
    */
    public static Connection getConnection(final String name, final String url)
	throws SQLException, ClassNotFoundException {
	Class.forName(name);
	return DriverManager.getConnection(url);
    }
}
