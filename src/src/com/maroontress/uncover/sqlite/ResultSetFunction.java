package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.Function;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
   特定のビルドに含まれる関数のクエリ結果から生成されるFunctionです。
*/
public final class ResultSetFunction extends Function {
    /**
       ResultSetからインスタンスを生成します。

       @param row 関数の行
       @throws SQLException エラーが発生したときにスローします。
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
