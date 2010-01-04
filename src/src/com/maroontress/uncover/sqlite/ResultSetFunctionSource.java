package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.FunctionSource;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
   クエリ結果から関数を生成するための関数ソースです。
*/
public final class ResultSetFunctionSource implements FunctionSource {
    /** "name" 列の値です。 */
    private String name;

    /** "sourceFile" 列の値です。 */
    private String sourceFile;

    /** "gcnoFile" 列の値です。 */
    private String gcnoFile;

    /** "checkSum" 列の値です。 */
    private String checkSum;

    /** "lineNumber" 列の値です。 */
    private int lineNumber;

    /** "complexity" 列の値です。 */
    private int complexity;

    /** "executedBlocks" 列の値です。 */
    private int executedBlocks;

    /** "allBlocks" 列の値です。 */
    private int allBlocks;

    /** "executedArcs" 列の値です。 */
    private int executedArcs;

    /** "allArcs" 列の値です。 */
    private int allArcs;

    /**
       インスタンスを生成します。
    */
    public ResultSetFunctionSource() {
    }

    /**
       クエリ結果を設定します。

       @param row 関数の行
       @throws SQLException エラーが発生したときにスローします。
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
