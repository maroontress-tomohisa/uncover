package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.FunctionSource;
import java.lang.reflect.Field;
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
	Field[] allField = this.getClass().getDeclaredFields();
	try {
	    for (Field field : allField) {
		String name = field.getName();
		Class<?> clazz = field.getType();
		if (clazz == int.class) {
		    field.setInt(this, row.getInt(name));
		} else {
		    field.set(this, row.getString(name));
		}
	    }
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("internal error", e);
	}
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
