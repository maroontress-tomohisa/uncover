package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.FunctionSource;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
   �������̤���ؿ����������뤿��δؿ��������Ǥ���
*/
public final class ResultSetFunctionSource implements FunctionSource {
    /** "name" ����ͤǤ��� */
    private String name;

    /** "sourceFile" ����ͤǤ��� */
    private String sourceFile;

    /** "gcnoFile" ����ͤǤ��� */
    private String gcnoFile;

    /** "checkSum" ����ͤǤ��� */
    private String checkSum;

    /** "lineNumber" ����ͤǤ��� */
    private int lineNumber;

    /** "complexity" ����ͤǤ��� */
    private int complexity;

    /** "executedBlocks" ����ͤǤ��� */
    private int executedBlocks;

    /** "allBlocks" ����ͤǤ��� */
    private int allBlocks;

    /** "executedArcs" ����ͤǤ��� */
    private int executedArcs;

    /** "allArcs" ����ͤǤ��� */
    private int allArcs;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    public ResultSetFunctionSource() {
    }

    /**
       �������̤����ꤷ�ޤ���

       @param row �ؿ��ι�
       @throws SQLException ���顼��ȯ�������Ȥ��˥������ޤ���
    */
    public void setResultSet(final ResultSet row) throws SQLException {
	name = row.getString("name");
	sourceFile = row.getString("sourceFile");
	lineNumber = row.getInt("lineNumber");
	gcnoFile = row.getString("gcnoFile");
	checkSum = row.getString("checkSum");
	complexity = row.getInt("complexity");
	executedBlocks = row.getInt("executedBlocks");
	allBlocks = row.getInt("allBlocks");
	executedArcs = row.getInt("executedArcs");
	allArcs = row.getInt("allArcs");
    }

    /** {@inheritDoc} */
    public String getName() {
	return name;
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
	return sourceFile;
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	return lineNumber;
    }

    /** {@inheritDoc} */
    public String getGCNOFile() {
	return gcnoFile;
    }

    /** {@inheritDoc} */
    public String getCheckSum() {
	return checkSum;
    }

    /** {@inheritDoc} */
    public int getComplexity() {
	return complexity;
    }

    /** {@inheritDoc} */
    public int getExecutedBlocks() {
	return executedBlocks;
    }

    /** {@inheritDoc} */
    public int getAllBlocks() {
	return allBlocks;
    }

    /** {@inheritDoc} */
    public int getExecutedArcs() {
	return executedArcs;
    }

    /** {@inheritDoc} */
    public int getAllArcs() {
	return allArcs;
    }
}
