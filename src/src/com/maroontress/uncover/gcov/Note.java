package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.AbstractNote;
import com.maroontress.gcovparser.CorruptedFileException;
import com.maroontress.gcovparser.gcno.FunctionGraphRecord;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
   gcnoファイルをパースした結果を保持します。
*/
public final class Note extends AbstractNote<NoteItem> {

    /**
       インスタンスを生成します。

       @param path gcnoファイルのパス
    */
    private Note(final String path) {
	super(path);
    }

    /** {@inheritDoc} */
    @Override protected NoteItem createFunctionGraph(
	final FunctionGraphRecord e) throws CorruptedFileException {
        return new NoteItem(this, e);
    }

    /**
       gcnoファイルをパースして、ノートを生成します。gcnoファイルに対
       応するgcdaファイルが利用できる場合は、gcdaファイルをパースして、
       ノートにアークカウンタを追加します。

       gcnoファイル、gcdaファイル共に、チャネルをマップするので、2Gバ
       イトを超えるファイルは扱えません。

       ファイルの内容が不正な場合は、標準エラー出力にスタックトレース
       を出力して、nullを返します。

       @param path gcnoファイルのパス
       @return ノート
       @throws IOException 入出力エラー
    */
    public static Note parse(final String path) throws IOException {
	if (!path.endsWith(".gcno")) {
	    System.err.printf("%s: suffix is not '.gcno'.%n", path);
	    return null;
	}
        Note note = new Note(path);
        try {
            note.parseNote();
        } catch (CorruptedFileException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            System.err.printf("%s: not found.%n", path);
            return null;
        }
        try {
            note.parseData();
        } catch (CorruptedFileException e) {
            e.printStackTrace();
            return note;
        } catch (FileNotFoundException e) {
            File dataFile = note.getOrigin().getDataFile();
            System.err.printf("%s: not found.%n", dataFile.getPath());
            return note;
        }
	return note;
    }
}
