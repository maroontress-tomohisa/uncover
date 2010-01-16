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
	// ��ե쥯�����ǽ�
	s.executeUpdate("CREATE TABLE " + Table.PROJECT + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "name"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.BUILD + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "revision, timestamp, platform, projectID"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.FUNCTION + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "name, gcnoFile, projectID"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH + " ("
			+ "id INTEGER PRIMARY KEY, "
			+ "functionID, buildID"
			+ ");");
	s.executeUpdate("CREATE TABLE " + Table.GRAPH_SUMMARY + " ("
			+ "graphID, checkSum, sourceFile, lineNumber, "
			+ "complexity, allBlocks, executedBlocks, "
			+ "allArcs, executedArcs"
			+ ");");
    }
}
