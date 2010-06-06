package com.maroontress.uncover.gcov;

import com.maroontress.gcovparser.CorruptedFileException;
import com.maroontress.gcovparser.Origin;
import com.maroontress.gcovparser.gcda.DataRecord;
import com.maroontress.gcovparser.gcda.FunctionDataRecord;
import com.maroontress.gcovparser.gcno.FunctionGraphRecord;
import com.maroontress.gcovparser.gcno.NoteRecord;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.TreeMap;

/**
   gcno�ե������ѡ���������̤��ݻ����ޤ���
*/
public final class Note {

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
    private TreeMap<Integer, NoteItem> map;

    /** �ץ����μ¹Բ���Ǥ��� */
    private int runs;

    /** �ץ����ο��Ǥ��� */
    private int programs;

    /**
       �Ρ��ȥ쥳���ɤ��饤�󥹥��󥹤��������ޤ���

       @param rec �Ρ��ȥ쥳����
       @param origin gcno�ե�����Υ��ꥸ��
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    private Note(final NoteRecord rec, final Origin origin)
	throws CorruptedFileException {
	this.origin = origin;
	version = rec.getVersion();
	stamp = rec.getStamp();
	map = new TreeMap<Integer, NoteItem>();

	FunctionGraphRecord[] list = rec.getList();
	for (FunctionGraphRecord e : list) {
	    NoteItem item = new NoteItem(this, e);
	    map.put(item.getId(), item);
	}
    }

    /**
       �Ρ��ȥ����ƥ�Υ��쥯������������ޤ���

       @return �Ρ��ȥ����ƥ�Υ��쥯�����
    */
    public Collection<NoteItem> getNoteItems() {
	return map.values();
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
	    NoteItem g = map.get(id);
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

       �ե���������Ƥ������ʾ��ϡ�ɸ�२�顼���Ϥ˥����å��ȥ졼��
       ����Ϥ��ޤ���

       @param origin gcno�ե�����Υ��ꥸ��
    */
    private void parseData(final Origin origin) {
	File dataFile = origin.getDataFile();
	RandomAccessFile file;
	try {
	    file = new RandomAccessFile(dataFile, "r");
	} catch (FileNotFoundException e) {
	    System.err.printf("%s: not found.%n", dataFile.getPath());
	    return;
	}
	FileChannel ch = file.getChannel();
	try {
	    try {
		ByteBuffer bb = ch.map(FileChannel.MapMode.READ_ONLY,
				       0, ch.size());
		DataRecord dataRecord = new DataRecord(bb);
		setDataRecord(dataRecord, dataFile);
	    } catch (CorruptedFileException e) {
		e.printStackTrace();
	    } finally {
		file.close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
       gcno�ե������ѡ������ơ��Ρ��Ȥ��������ޤ�������ͥ��ޥå�
       ����Τǡ�2G�Х��Ȥ�Ķ����ե�����ϰ����ޤ���

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
	Origin origin = new Origin(path);
	RandomAccessFile file;
	try {
	    file = new RandomAccessFile(path, "r");
	} catch (FileNotFoundException e) {
	    System.err.printf("%s: not found.%n", path);
	    return null;
	}
	FileChannel ch = file.getChannel();
	Note note = null;
	try {
	    try {
		ByteBuffer bb = ch.map(FileChannel.MapMode.READ_ONLY,
				       0, ch.size());
		NoteRecord noteRecord = new NoteRecord(bb);
		note = new Note(noteRecord, origin);
	    } catch (CorruptedFileException e) {
		e.printStackTrace();
		return null;
	    } finally {
		file.close();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
	note.parseData(origin);
	return note;
    }

    /**
       ���ꥸ���������ޤ���

       @return ���ꥸ��
    */
    public Origin getOrigin() {
	return origin;
    }
}
