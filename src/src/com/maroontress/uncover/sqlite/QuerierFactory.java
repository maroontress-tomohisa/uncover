package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
   �������ȯ�Ԥ��륤�󥹥��󥹤��������ޤ���

   @param <T> �ơ��֥�ιԤ�ɽ�����饹
*/
public final class QuerierFactory<T extends Row> {
    /** JDBC����³�Ǥ��� */
    private Connection con;

    /** �ơ��֥�̾�Ǥ��� */
    private String tableName;

    /** �ե������̾�Υꥹ�ȤǤ��� */
    private List<String> list;

    /**
       ���󥹥ȥ饯���Ǥ���

       @param con �ǡ����١����Ȥ���³
       @param tableName �ơ��֥�̾
       @param clazz �ơ��֥���б�����ԤΥ��饹
    */
    public QuerierFactory(final Connection con, final String tableName,
			  final Class<T> clazz) {
	this.con = con;
	this.tableName = tableName;
	list = new ArrayList<String>();

	Field[] allField = clazz.getDeclaredFields();
	for (Field field : allField) {
	    list.add(field.getName());
	}
    }

    /**
       �Ԥ��ɲä��륤�󥹥��󥹤��������ޤ���

       @return �Ԥ��ɲä��륤�󥹥���
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public Adder<T> createAdder() throws SQLException {
	String s = "INSERT INTO " + tableName + " (";
	String prefix = "";
	for (String name : list) {
	    s += prefix + name;
	    prefix = ", ";
	}
	s += ") VALUES (";
	int n = list.size();
	prefix = "";
	for (int k = 0; k < n; ++k) {
	    s += prefix + "?";
	    prefix = ", ";
	}
	s += ");";
	return new Adder<T>(con.prepareStatement(s));
    }

    /**
       �Ԥ�������륤�󥹥��󥹤��������ޤ���

       @param colNames ��������Ԥ���̾
       @return �Ԥ�������륤�󥹥���
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public Fetcher<T> createFetcher(final String colNames)
	throws SQLException {
	String s = "SELECT " + colNames + " FROM " + tableName + " WHERE ";
	String prefix = "";
	for (String name : list) {
	    s += prefix + name + " = ?";
	    prefix = "and ";
	}
	s += ";";
	return new Fetcher<T>(con.prepareStatement(s));
    }
}
