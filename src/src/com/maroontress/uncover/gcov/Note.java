package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.AbstractNote;
import com.maroontress.gcovparser.CorruptedFileException;
import com.maroontress.gcovparser.gcno.FunctionGraphRecord;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
   gcno�ե������ѡ���������̤��ݻ����ޤ���
*/
public final class Note extends AbstractNote<NoteItem> {

    /**
       ���󥹥��󥹤��������ޤ���

       @param path gcno�ե�����Υѥ�
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
       gcno�ե������ѡ������ơ��Ρ��Ȥ��������ޤ���gcno�ե��������
       ������gcda�ե����뤬���ѤǤ�����ϡ�gcda�ե������ѡ������ơ�
       �Ρ��Ȥ˥����������󥿤��ɲä��ޤ���

       gcno�ե����롢gcda�ե����붦�ˡ�����ͥ��ޥåפ���Τǡ�2G��
       ���Ȥ�Ķ����ե�����ϰ����ޤ���

       �ե���������Ƥ������ʾ��ϡ�ɸ�२�顼���Ϥ˥����å��ȥ졼��
       ����Ϥ��ơ�null���֤��ޤ���

       @param path gcno�ե�����Υѥ�
       @return �Ρ���
       @throws IOException �����ϥ��顼
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
