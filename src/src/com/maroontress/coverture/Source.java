package com.maroontress.coverture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

/**
   ���Х�å����륽�����ե������������ޤ���
*/
public final class Source {
    /** �ѡ�����Ȥ��Ѵ����뤿��η����Ǥ��� */
    private static final double PERCENT = 100;

    /** ���Х�å��оݤΥ������ե�����Υѥ��Ǥ��� */
    private String sourceFile;

    /** ���ֹ�ȹԾ���ΥޥåפǤ��� */
    private HashMap<Integer, LineInfo> map;

    /** �������ե�����˴ޤޤ��ؿ��δؿ�����դΥ��åȤǤ��� */
    private TreeSet<FunctionGraph> functions;

    /**
       ���������������ޤ���

       @param sourceFile �������ե�����Υѥ�
    */
    public Source(final String sourceFile) {
	this.sourceFile = sourceFile;
	map = new HashMap<Integer, LineInfo>();
	functions = new TreeSet<FunctionGraph>(
	    FunctionGraph.getLineNumberComparator());
    }

    /**
       ���Υ������˴ޤޤ��ؿ�����դ��ɲä��ޤ���

       @param fg �ؿ������
    */
    public void addFunctionGraph(final FunctionGraph fg) {
	functions.add(fg);
    }

    /**
       �¹Բ�ǽ�ʹ��ֹ�����Τ������ι��ֹ�μ¹Բ����û����ޤ���

       @param lineNuber ���ֹ�
       @param delta �¹Բ��
    */
    public void addLineCount(final int lineNuber, final long delta) {
	LineInfo info = map.get(lineNuber);
	if (info == null) {
	    info = new LineInfo();
	    map.put(lineNuber, info);
	}
	info.addCount(delta);
    }

    /**
       ���ֹ�μ¹Բ����������ޤ���

       ����ι��ֹ椬�¹Բ�ǽ�Ǥʤ�����-1���֤��ޤ���

       @param lineNuber ���ֹ�
       @return �¹Բ��
    */
    public long getLineCount(final int lineNuber) {
	LineInfo info = map.get(lineNuber);
	if (info == null) {
	    return -1;
	}
	return info.getCount();
    }

    /**
       �¹Ԥ����Կ���������ޤ���

       @return �¹Ԥ����Կ�
    */
    public int getExecutedLines() {
	Collection<LineInfo> all = map.values();
	int count = 0;
	for (LineInfo i : all) {
	    if (i.getCount() > 0) {
		++count;
	    }
	}
	return count;
    }

    /**
       �¹Բ�ǽ�ʹԿ���������ޤ���

       @return �¹Բ�ǽ�ʹԿ�
    */
    public int getExecutableLines() {
	return map.size();
    }

    /**
       �������ե�����Υѥ���������ޤ���

       @return �������ե�����Υѥ�
    */
    public String getPath() {
	return sourceFile;
    }

    /**
       ��Ψ����ѡ�����Ȥ�׻����ޤ���ʬ�줬0�ΤȤ���0���֤��ޤ���

       @param n ʬ��
       @param m ʬ��
       @return n/m�Υѡ������
    */
    private int percent(final long n, final long m) {
	if (m == 0) {
	    return 0;
	}
	return (int) Math.round(PERCENT * n / m);
    }

    /**
       gcov�ߴ��Υ��Х�å���̤���Ϥ��ޤ���

       @param out ������
       @param in �������ե�����Υ꡼��
       @throws IOException �����ϥ��顼
    */
    private void outputGcovFile(final PrintWriter out,
				final LineNumberReader in) throws IOException {
	String line;
	Traverser<FunctionGraph> tr = new Traverser<FunctionGraph>(functions);
	while ((line = in.readLine()) != null) {
	    int num = in.getLineNumber();
	    while (tr.peek() != null && tr.peek().getLineNumber() == num) {
		FunctionGraph fg = tr.poll();
		long calledCount = fg.getCalledCount();
		long returnedCount = fg.getReturnedCount();
		int executedBlocks = fg.getExecutedBlockCount();
		int allBlocks = fg.getBlockCount();
		out.printf("function %s called %d returned %d%%"
			   + " blocks executed %d%%\n",
			   fg.getFunctionName(), calledCount,
			   percent(returnedCount, calledCount),
			   percent(executedBlocks, allBlocks));
	    }

	    LineInfo info = map.get(num);
	    long count;
	    String mark;
	    if (info == null) {
		mark = "-";
	    } else if ((count = info.getCount()) == 0) {
		mark = "#####";
	    } else {
		mark = String.valueOf(count);
	    }
	    out.printf("%9s:%5d:%s\n", mark, num, line);
	}
    }

    /**
       gcov�ߴ��Υ��Х�å���̤���Ϥ��ޤ���

       @param out ������
       @param origin gcno�ե�����Υ��ꥸ��
       @param prop �����ϥץ�ѥƥ�
       @throws IOException �����ϥ��顼
    */
    private void outputLines(final PrintWriter out, final Origin origin,
			     final IOProperties prop) throws IOException {
	File file = new File(sourceFile);
	if (file.lastModified() > origin.getNoteFile().lastModified()) {
	    System.err.printf("%s: source file is newer than gcno file%n",
			      sourceFile);
	    out.printf("%9s:%5d:Source is newer than gcno file\n", "-", 0);
	}
	LineNumberReader in
	    = new LineNumberReader(prop.createSourceFileReader(file));
	try {
	    outputGcovFile(out, in);
	} finally {
	    in.close();
	}
    }

    /**
       ���Х�å��ե�������������ޤ���

       @param origin gcno�ե�����Υ��ꥸ��
       @param runs �ץ����μ¹Բ��
       @param programs �ץ����ο�
       @param prop �����ϥץ�ѥƥ�
       @throws IOException �����ϥ��顼
    */
    public void outputFile(final Origin origin, final int runs,
			   final int programs, final IOProperties prop)
	throws IOException {
	String path = origin.getCoverageFilePath(sourceFile);
	PrintWriter out;
	try {
	    out = new PrintWriter(prop.createGcovWriter(path));
	} catch (FileNotFoundException e) {
	    File gcov = prop.createOutputFile(path);
	    System.err.printf("%s: can't open: %s%n",
			      gcov.getPath(), e.getMessage());
	    return;
	}
	try {
	    File gcnoFile = origin.getNoteFile();
	    File gcdaFile = origin.getDataFile();
	    out.printf("%9s:%5d:Source:%s\n", "-", 0, sourceFile);
	    out.printf("%9s:%5d:Graph:%s\n", "-", 0, gcnoFile.getPath());
	    out.printf("%9s:%5d:Data:%s\n", "-", 0, gcdaFile.getPath());
	    out.printf("%9s:%5d:Runs:%d\n", "-", 0, runs);
	    out.printf("%9s:%5d:Programs:%d\n", "-", 0, programs);
	    outputLines(out, origin, prop);
	} finally {
	    out.close();
	}
	if (prop.isVerbose()) {
	    File gcov = prop.createOutputFile(path);
	    System.err.printf("%s: created.%n", gcov.getPath());
	}
    }
}
