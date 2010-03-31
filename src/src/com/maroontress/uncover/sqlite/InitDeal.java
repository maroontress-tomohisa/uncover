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
       �ơ��֥�ιԥ��饹���顢�ơ��֥��������Ȥʤ�ʸ�����������ޤ���

       @param clazz �ԥ��饹
       @return �ơ��֥��������Ȥʤ�ʸ����
    */
    private static String getTableDefinition(
	final Class<? extends Row> clazz) {
	return FieldArray.concatNameTypes(clazz, ", ");
    }

    /**
       ɬ�פʥơ��֥�򤹤٤��������ޤ���

       @throws SQLException ������˥��顼��ȯ�������Ȥ��˥������ޤ���
    */
    public void run() throws SQLException {
	Statement s = con.createStatement();
	s.executeUpdate("CREATE TABLE " + Table.CONFIG + " ("
			+ getTableDefinition(ConfigRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.PROJECT + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(ProjectRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.BUILD + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(BuildRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.FUNCTION + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(FunctionRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ getTableDefinition(GraphRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_SUMMARY + " ("
			+ getTableDefinition(GraphSummaryRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_BLOCK + " ("
			+ getTableDefinition(GraphBlockRow.class)
			+ ")");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_ARC + " ("
			+ getTableDefinition(GraphArcRow.class)
			+ ")");

	Adder<ConfigRow> adder = new QuerierFactory<ConfigRow>(
	    con, Table.CONFIG, ConfigRow.class).createAdder();
	adder.setRow(new ConfigRow());
	adder.getRow().set(SQLiteDB.VERSION);
	adder.execute();
    }
}
