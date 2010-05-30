package com.maroontress.uncover.gcov;

import com.maroontress.coverture.Arc;
import com.maroontress.coverture.Block;
import com.maroontress.coverture.CorruptedFileException;
import com.maroontress.coverture.LineEntryList;
import com.maroontress.coverture.Solver;
import com.maroontress.coverture.gcda.FunctionDataRecord;
import com.maroontress.coverture.gcno.AnnounceFunctionRecord;
import com.maroontress.coverture.gcno.ArcRecord;
import com.maroontress.coverture.gcno.ArcsRecord;
import com.maroontress.coverture.gcno.FunctionGraphRecord;
import com.maroontress.coverture.gcno.LineRecord;
import com.maroontress.coverture.gcno.LinesRecord;
import java.util.ArrayList;

/**
   �ؿ�����դǤ���
*/
public final class NoteItem {

    /** �Ρ��ȤǤ��� */
    private Note note;

    /** ���̻ҤǤ��� */
    private int id;

    /** �ؿ��Υ����å�����Ǥ��� */
    private int checksum;

    /** �ؿ�̾�Ǥ��� */
    private String functionName;

    /** �����������ɤΥե�����̾�Ǥ��� */
    private String sourceFile;

    /** �ؿ����и����륽���������ɤιԿ��Ǥ��� */
    private int lineNumber;

    /** �ؿ�����������ܥ֥�å�������Ǥ��� */
    private Block[] blocks;

    /** ���٤ƤΥ������θĿ��Ǥ��� */
    private int totalArcCount;

    /**
       ���Υ������θĿ��Ǥ������Υ������ϡ��㳰��longjmp()�ˤ�äơ���
       �ߤδؿ�����ȴ�����ϩ���б����ޤ���
    */
    private int fakeArcCount;

    /** �¹Բ����Ƚ�����Ƥ��륢�����Υꥹ�ȤǤ��� */
    private ArrayList<Arc> solvedArcs;

    /** �¹Բ���������ʥ������Υꥹ�ȤǤ��� */
    private ArrayList<Arc> unsolvedArcs;

    /** �ƤӽФ��줿����Ǥ��� */
    private long calledCount;

    /** ��ä�����Ǥ��� */
    private long returnedCount;

    /** �¹Ԥ��줿�֥�å��������Ƚи�������ˤθĿ��Ǥ��� */
    private int executedBlockCount;

    /** �ե�����դ���褷�Ƥ��뤫�ɤ�����ɽ���ޤ��� */
    private boolean solved;

    /**
       �ؿ�����ե쥳���ɤ��饤�󥹥��󥹤��������ޤ���

       @param note �Ρ���
       @param rec �ؿ�����ե쥳����
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    public NoteItem(final Note note, final FunctionGraphRecord rec)
	throws CorruptedFileException {
	this.note = note;
	AnnounceFunctionRecord announce = rec.getAnnounce();
	id = announce.getId();
	checksum = announce.getChecksum();
	functionName = announce.getFunctionName();
	sourceFile = announce.getSourceFile();
	lineNumber = announce.getLineNumber();
	solvedArcs = new ArrayList<Arc>();
	unsolvedArcs = new ArrayList<Arc>();

	int[] blockFlags = rec.getBlocks().getFlags();
	blocks = new Block[blockFlags.length];
	for (int k = 0; k < blockFlags.length; ++k) {
	    blocks[k] = new Block(k, blockFlags[k]);
	}

	ArcsRecord[] arcs = rec.getArcs();
	for (ArcsRecord e : arcs) {
	    addArcsRecord(e);
	}

	LinesRecord[] lines = rec.getLines();
	for (LinesRecord e : lines) {
	    addLinesRecord(e);
	}

	if (blocks.length < 2) {
	    throw new CorruptedFileException("lacks entry and/or exit blocks");
	}
	Block entryBlock = blocks[0];
	Block exitBlock = blocks[blocks.length - 1];
	if (entryBlock.getInArcs().size() != 0) {
	    throw new CorruptedFileException("has arcs to entry block");
	}
	if (exitBlock.getOutArcs().size() != 0) {
	    throw new CorruptedFileException("has arcs from exit block");
	}
	for (Block e : blocks) {
	    e.presolve();
	}
    }

    /**
       ARCS�쥳���ɤ��饢�������������ơ��ؿ�����դ��ɲä��ޤ���

       @param arcsRecord ARCS�쥳����
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    private void addArcsRecord(final ArcsRecord arcsRecord)
	throws CorruptedFileException {
	int startIndex = arcsRecord.getStartIndex();
	ArcRecord[] list = arcsRecord.getList();
	if (startIndex >= blocks.length) {
	    throw new CorruptedFileException();
	}
	for (ArcRecord arcRecord : list) {
	    int endIndex = arcRecord.getEndIndex();
	    int flags = arcRecord.getFlags();
	    if (endIndex >= blocks.length) {
		throw new CorruptedFileException();
	    }
	    Block start = blocks[startIndex];
	    Block end = blocks[endIndex];
	    Arc arc = new Arc(start, end, flags);
	    if (!arc.isOnTree()) {
		/*
		  ���ѥ˥󥰥ĥ꡼�ǤϤʤ���������gcda�ե�����ˤϤ���
		  ���������б�����¹Բ������Ͽ����롣�������μ¹Բ�
		  �����ºݤ˲�褹��Τ�setFunctionDataRecord()�᥽��
		  �ɤ��ƤФ줿�Ȥ���
		*/
		solvedArcs.add(arc);
	    } else {
		/*
		  ���ѥ˥󥰥ĥ꡼�Υ��������������μ¹Բ����gcda�ե�
		  ���뤫������Ǥ��ʤ��Τǡ��ե�����դ�򤯤��Ȥǥ���
		  ���μ¹Բ������ʤ���Фʤ�ʤ���
		*/
		unsolvedArcs.add(arc);
	    }
	    if (arc.isFake()) {
		++fakeArcCount;
	    }
	}
	totalArcCount += list.length;
    }

    /**
       LINES�쥳���ɤ���ԥ���ȥ�Υꥹ�Ȥ��������ơ��ؿ�����դ��ɲ�
       ���ޤ���

       @param linesRecord LINES�쥳����
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    private void addLinesRecord(final LinesRecord linesRecord)
	throws CorruptedFileException {
	int blockIndex = linesRecord.getBlockIndex();
	LineRecord[] list = linesRecord.getList();
	if (blockIndex >= blocks.length) {
	    throw new CorruptedFileException();
	}
	LineEntryList entryList = new LineEntryList(sourceFile);
	for (LineRecord rec : list) {
	    int number = rec.getNumber();
	    if (number == 0) {
		entryList.changeFileName(rec.getFileName());
	    } else {
		entryList.addLineNumber(number);
	    }
	}
	blocks[blockIndex].setLines(entryList.getLineEntries());
    }

    /**
       �ե�����դ�򤭤ޤ���

       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    private void solveFlowGraph() throws CorruptedFileException {
	Solver s = new Solver();
	for (Block e : blocks) {
	    e.sortOutArcs();
	    s.addInvalid(e);
	}
	s.solve();
    }

    /**
       �ؿ��θƤӽФ��������������¹Ԥ��줿�֥�å����������Ƚи�
       ������ˤ�׻����ޤ���

       calledCount, returnedCount, executedBlockCount��ͭ���ˤʤ�ޤ���
    */
    private void countCallSummary() {
	calledCount = blocks[0].getCount();

	ArrayList<Arc> list = blocks[blocks.length - 1].getInArcs();
	long count = 0;
	for (Arc arc : list) {
	    if (arc.isFake()) {
		continue;
	    }
	    count += arc.getCount();
	}
	returnedCount = count;

	int start = 1;
	int end = blocks.length - 1;
	for (int k = start; k < end; ++k) {
	    if (blocks[k].getCount() > 0) {
		++executedBlockCount;
	    }
	}
    }

    /**
       �ؿ��ǡ����쥳���ɤ�ؿ�����դ��ɲä��ޤ���

       @param rec �ؿ��ǡ����쥳����
       @throws CorruptedFileException �ե�����ι�¤������Ƥ��뤳�Ȥ򸡽�
    */
    public void setFunctionDataRecord(final FunctionDataRecord rec)
	throws CorruptedFileException {
	if (checksum != rec.getChecksum()) {
	    String m = String.format("gcda file: checksum mismatch for '%s'",
				     functionName);
	    throw new CorruptedFileException(m);
	}
	long[] arcCounts = rec.getArcCounts();
	if (solvedArcs.size() != arcCounts.length) {
	    String m = String.format("gcda file: profile mismatch for '%s'",
				     functionName);
	    throw new CorruptedFileException(m);
	}
	for (int k = 0; k < arcCounts.length; ++k) {
	    solvedArcs.get(k).addCount(arcCounts[k]);
	}
	solveFlowGraph();
	countCallSummary();
	solved = true;
    }

    /**
       ���̻Ҥ�������ޤ���

       @return ���̻�
    */
    public int getId() {
	return id;
    }

    /**
       �ؿ����Ϥޤ���ֹ��������ޤ���

       @return �ؿ����Ϥޤ���ֹ�
    */
    public int getLineNumber() {
	return lineNumber;
    }

    /**
       �ؿ�̾��������ޤ���

       @return �ؿ�̾
    */
    public String getFunctionName() {
	return functionName;
    }

    /**
       �ؿ����ƤФ줿�����������ޤ���

       ������setFunctionDataRecord()�Ǵؿ��ǡ����쥳���ɤ����ꤵ��Ƥ�
       ��ɬ�פ�����ޤ���

       @return �ؿ����ƤФ줿���
    */
    public long getCalledCount() {
	return calledCount;
    }

    /**
       �ؿ�������ä������������ޤ���

       ������setFunctionDataRecord()�Ǵؿ��ǡ����쥳���ɤ����ꤵ��Ƥ�
       ��ɬ�פ�����ޤ���

       @return �ؿ�������ä����
    */
    public long getReturnedCount() {
	return returnedCount;
    }

    /**
       �������и�������¹Ԥ��줿�֥�å�����������ޤ���

       ������setFunctionDataRecord()�Ǵؿ��ǡ����쥳���ɤ����ꤵ��Ƥ�
       ��ɬ�פ�����ޤ���

       @return �¹Ԥ��줿�֥�å������������и��������
    */
    public int getExecutedBlockCount() {
	return executedBlockCount;
    }

    /**
       �������и�������֥�å�����������ޤ���

       @return �֥�å������������и��������
    */
    public int getBlockCount() {
	return blocks.length - 2;
    }

    /**
       �֥�å��������������ޤ���

       @return �֥�å�������
    */
    public Block[] getBlocks() {
	return blocks;
    }

    /**
       �������������������ޤ���

       @return ������������
    */
    public Arc[] getArcs() {
	ArrayList<Arc> arcs = new ArrayList<Arc>();
	arcs.addAll(solvedArcs);
	arcs.addAll(unsolvedArcs);
	return arcs.toArray(new Arc[arcs.size()]);
    }

    /**
       �����å������������ޤ���

       @return �����å�����
    */
    public int getChecksum() {
	return checksum;
    }

    /**
       �����å������������ޤ���

       @return �����å�����
    */
    public int getComplexity() {
	int complexityWithFake = totalArcCount - blocks.length + 2;
	return complexityWithFake - fakeArcCount;
    }

    /**
       �Ρ��Ȥ�������ޤ���

       @return �Ρ���
    */
    public Note getNote() {
	return note;
    }

    /**
       �������ե�����̾��������ޤ���

       @return �������ե�����̾
    */
    public String getSourceFile() {
	return sourceFile;
    }
}
