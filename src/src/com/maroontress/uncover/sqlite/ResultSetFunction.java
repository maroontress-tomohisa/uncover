package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Function;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
   ����Υӥ�ɤ˴ޤޤ��ؿ��Υ������̤������������Function�Ǥ���
*/
public final class ResultSetFunction extends Function {
    /**
       ResultSet���饤�󥹥��󥹤��������ޤ���

       @param row �ؿ��ι�
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    public ResultSetFunction(final ResultSet row) throws SQLException {
	setSource(row.getString("name"),
		  row.getString("sourceFile").intern(),
		  row.getInt("lineNumber"),
		  row.getString("gcnoFile").intern(),
		  row.getString("checkSum"));
	setGraph(row.getInt("complexity"),
		 row.getInt("executedBlocks"),
		 row.getInt("allBlocks"),
		 row.getInt("executedArcs"),
		 row.getInt("allArcs"));
    }
}
