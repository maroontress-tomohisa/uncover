package com.maroontress.uncover.gcov;

import com.maroontress.uncover.FunctionGraph;
import com.maroontress.uncover.Parser;
import com.maroontress.uncover.ParsingException;
import java.util.Iterator;
import java.util.List;

/**
   ノートパーサです。
*/
public final class GcovNoteParser implements Parser {

    /** gcnoファイルのパスの配列です。 */
    private String[] files;

    /**
       パーサを生成します。

       @param fileList gcnoファイルのパスのリスト
       @throws ParsingException パースに失敗したときにスローします。
    */
    public GcovNoteParser(final List<String> fileList)
	throws ParsingException {
	files = fileList.toArray(new String[fileList.size()]);
    }

    /** {@inheritDoc} */
    public Iterator<FunctionGraph> iterator() {
	return new FunctionGraphIterator(files);
    }
}
