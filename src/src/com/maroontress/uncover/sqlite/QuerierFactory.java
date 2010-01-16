package com.maroontress.uncover.sqlite;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

/**
   �������ȯ�Ԥ��륤�󥹥��󥹤��������ޤ���

   @param <T> �ơ��֥�ιԤ�ɽ�����饹
*/
public final class QuerierFactory<T extends Row> {
    /** JDBC����³�Ǥ��� */
    private Connection con;

    /** �ơ��֥�̾�Ǥ��� */
    private String tableName;

    /** �ե�����ɤ�����Ǥ��� */
    private Field[] allFields;

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
	allFields = clazz.getDeclaredFields();
    }

    /**
       �Ԥ��ɲä��륤�󥹥��󥹤��������ޤ���

       @return �Ԥ��ɲä��륤�󥹥���
       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public Adder<T> createAdder() throws SQLException {
	String s = "INSERT INTO " + tableName + " (";
	s += FieldArray.concatNames(allFields, ", ");
	s += ") VALUES (";
	int n = allFields.length;
	String prefix = "";
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
	for (Field field : allFields) {
	    s += prefix + field.getName() + " = ?";
	    prefix = "and ";
	}
	s += ";";
	return new Fetcher<T>(con.prepareStatement(s));
    }
}
