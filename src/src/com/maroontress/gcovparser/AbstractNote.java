package com.maroontress.gcovparser;

import com.maroontress.gcovparser.gcda.DataRecord;
import com.maroontress.gcovparser.gcda.FunctionDataRecord;
import com.maroontress.gcovparser.gcno.FunctionGraphRecord;
import com.maroontress.gcovparser.gcno.NoteRecord;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.TreeMap;

/**
   gcno�ե������ѡ���������̤��ݻ����ޤ���

   @param <T> �ؿ������
*/
public abstract class AbstractNote<T extends AbstractFunctionGraph> {

    /** gcno�ե�����Υ��ꥸ��Ǥ��� */
    private Origin origin;

    /** gcno�ե�����ΥС�������ֹ�Ǥ��� */
    private int version;

    /**
       gcno�ե�����Υ����ॹ����פǤ���gcda�ե������Ʊ�����Ȥ�Ƥ�
       �뤳�Ȥ��ǧ���뤿��˻��Ѥ���ޤ���
    */
    private int stamp;

    /** �ؿ�����դȤ��μ��̻ҤΥޥåפǤ��� */
    private TreeMap<Integer, T> map;

    /** �ץ����μ¹Բ���Ǥ��� */
    private int runs;

    /** �ץ����ο��Ǥ��� */
    private int programs;

    /**
       ���󥹥��󥹤��������ޤ���

       @param path gcno�ե�����Υѥ�
    */
    public AbstractNote(final String path) {
	origin = new Origin(path);
    }

    /**
       �ؿ�����ե쥳���ɤ���ؿ�����դ��������ޤ���

       @param e �ؿ�����ե쥳����
       @return �ؿ������
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    protected abstract T createFunctionGraph(
	FunctionGraphRecord e) throws CorruptedFileException;

    /**
       ���ꥸ���������ޤ���

       @return ���ꥸ��
    */
    public final Origin getOrigin() {
	return origin;
    }

    /**
       gcno�ե�����ΥС�������������ޤ���

       @return gcno�ե�����ΥС������
    */
    protected final int getVersion() {
	return version;
    }

    /**
       gcno�ե�����Υ����ॹ����פ�������ޤ���

       @return gcno�ե�����Υ����ॹ�����
    */
    protected final int getStamp() {
	return stamp;
    }

    /**
       �ץ����μ¹Բ����������ޤ���

       @return �ץ����μ¹Բ��
    */
    protected final int getRuns() {
	return runs;
    }

    /**
       �ץ����ο���������ޤ���

       @return �ץ����ο�
    */
    protected final int getPrograms() {
	return programs;
    }

    /**
       �ؿ�����դΥ��쥯������������ޤ���

       @return �ؿ�����դΥ��쥯�����
    */
    public final Collection<T> getFunctionGraphCollection() {
	return map.values();
    }

    /**
       �Ρ��ȥ쥳���ɤ����ꤷ�ޤ���

       @param rec �Ρ��ȥ쥳����
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    private void setNoteRecord(final NoteRecord rec)
	throws CorruptedFileException {
	version = rec.getVersion();
	stamp = rec.getStamp();
	map = new TreeMap<Integer, T>();

	FunctionGraphRecord[] list = rec.getList();
	for (FunctionGraphRecord e : list) {
	    T fg = createFunctionGraph(e);
	    map.put(fg.getId(), fg);
	}
    }

    /**
       gcno�ե������ѡ������ơ��Ρ��Ȥ��������ޤ�������ͥ��ޥå�
       ����Τǡ�2G�Х��Ȥ�Ķ����ե�����ϰ����ޤ���

       @throws IOException �����ϥ��顼
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    protected final void parseNote() throws IOException,
	CorruptedFileException {
	File noteFile = origin.getNoteFile();
	RandomAccessFile file = new RandomAccessFile(noteFile, "r");
	FileChannel ch = file.getChannel();
	try {
	    ByteBuffer bb = ch.map(FileChannel.MapMode.READ_ONLY,
				   0, ch.size());
	    NoteRecord noteRecord = new NoteRecord(bb);
	    setNoteRecord(noteRecord);
	} finally {
	    file.close();
	}
    }

    /**
       �ǡ����쥳���ɤ����ꤷ�ޤ���

       @param rec �ǡ����쥳����
       @param file gcda�ե�����
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    private void setDataRecord(final DataRecord rec, final File file)
	throws CorruptedFileException {
	String path = origin.getNoteFile().getPath();
	if (version != rec.getVersion()) {
	    throw new CorruptedFileException(path + ": version mismatch.");
	}
	if (stamp != rec.getStamp()) {
	    throw new CorruptedFileException(path + ": timestamp mismatch.");
	}
	if (origin.getNoteFile().lastModified() > file.lastModified()) {
	    System.err.printf(
		"%s: warning: gcno file is newer than gcda file.%n", path);
	}
	FunctionDataRecord[] list = rec.getList();
	for (FunctionDataRecord e : list) {
	    int id = e.getId();
	    T g = map.get(id);
	    if (g == null) {
		System.err.printf(
		    "%s: warning: unknown function id '%d'.%n", path, id);
		continue;
	    }
	    g.setFunctionDataRecord(e);
	}
	runs = rec.getObjectSummary().getRuns();
	programs = rec.getProgramSummaries().length;
    }

    /**
       gcda�ե������ѡ������ơ��Ρ��Ȥ˥����������󥿤��ɲä��ޤ���
       ����ͥ��ޥåפ���Τǡ�2G�Х��Ȥ�Ķ����ե�����ϰ����ޤ���

       @throws IOException �����ϥ��顼
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    protected final void parseData() throws IOException,
	CorruptedFileException {
	File dataFile = origin.getDataFile();
	RandomAccessFile file = new RandomAccessFile(dataFile, "r");
	FileChannel ch = file.getChannel();
	try {
	    ByteBuffer bb = ch.map(FileChannel.MapMode.READ_ONLY,
				   0, ch.size());
	    DataRecord dataRecord = new DataRecord(bb);
	    setDataRecord(dataRecord, dataFile);
	} finally {
	    file.close();
	}
    }
}
