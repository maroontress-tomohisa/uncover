package com.maroontress.uncover.sqlite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
   �ǡ����١����ν���������򰷤��ޤ���
*/
public final class InitDeal {
    /** �ǡ����١����Ȥ���³�Ǥ��� */
    private Connection con;

    /**
       ���󥹥��󥹤��������ޤ���

       @param con �ǡ����١����Ȥ���³
    */
    public InitDeal(final Connection con) {
	this.con = con;
    }

    /**
       ɬ�פʥơ��֥�򤹤٤��������ޤ���

       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public void run() throws SQLException {
	Statement s = con.createStatement();
	s.executeUpdate("CREATE TABLE " + Table.PROJECT + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ FieldArray.concatNames(ProjectRow.class, ", ")
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.BUILD + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ FieldArray.concatNames(BuildRow.class, ", ")
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.FUNCTION + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ FieldArray.concatNames(FunctionRow.class, ", ")
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ FieldArray.concatNames(GraphRow.class, ", ")
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_SUMMARY + " ("
			+ FieldArray.concatNames(GraphSummaryRow.class, ", ")
			+ ");");
    }
}
