package com.maroontress.coverture;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
   �ե���������������ݥ��饹�Ǥ���
*/
public abstract class Processor {

    /** �����ϥץ�ѥƥ��Ǥ��� */
    private IOProperties props;

    /**
       ���󥹥��󥹤��������ޤ���

       @param props �����ϥץ�ѥƥ�
    */
    protected Processor(final IOProperties props) {
	this.props = props;
    }

    /**
       verbose�⡼�ɤΤȤ��˥ҡ��ץ�������ɸ�२�顼���Ϥ�ɽ�����ޤ���
    */
    private void verifyHeapSize() {
	if (props.isVerbose()) {
	    Runtime t = Runtime.getRuntime();
	    t.gc();
	    System.err.printf("heap: %d/%d%n", t.freeMemory(), t.maxMemory());
	}
    }

    /**
       gcov�ե�����������������gcov�ե��������Ϥ���ǥ��쥯�ȥ�
       ���������ޤ���
    */
    protected final void makeOutputDir() {
	if (props.isGcovEnabled()) {
	    props.makeOutputDir();
	}
    }

    /**
       gcov�ե�������������ޤ���

       @param note �Ρ���
    */
    protected final void createSourceList(final Note note) {
	if (props.isGcovEnabled()) {
	    note.createSourceList(props);
	}
    }

    /**
       �ե����뤫��gcno�ե�����̾�Υꥹ�Ȥ����Ϥ�������gcno�ե������
       �������ޤ���

       �ե�����̾���ϥ��ե�ξ��ϡ�ɸ�����Ϥ���ե�����̾�Υꥹ�Ȥ�
       ���Ϥ��ޤ���

       @param inputFile ���Ϥ���ꥹ�ȤΥե�����̾���ޤ��ϥϥ��ե�
       @throws IOException �����ϥ��顼
    */
    private void processFileList(final String inputFile)
	throws IOException {
	try {
	    InputStreamReader in;
	    if (inputFile.equals("-")) {
		in = new InputStreamReader(System.in);
	    } else {
		in = new FileReader(inputFile);
	    }
	    BufferedReader rd = new BufferedReader(in);
	    String name;
	    while ((name = rd.readLine()) != null) {
		processFile(name);
	    }
	} catch (FileNotFoundException e) {
	    System.err.printf("%s: not found: %s%n",
			      inputFile, e.getMessage());
	    System.exit(1);
	}
    }

    /**
       gcno�ե������ҤȤĽ������ޤ���

       @param name ���Ϥ���gcno�ե�����Υե�����̾
       @throws IOException �����ϥ��顼
    */
    protected abstract void processFile(final String name) throws IOException;

    /**
       gcno�ե����������������˸ƤӽФ��ޤ���

       @throws IOException �����ϥ��顼
    */
    protected abstract void pre() throws IOException;

    /**
       gcno�ե���������������˸ƤӽФ��ޤ���

       @throws IOException �����ϥ��顼
    */
    protected abstract void post() throws IOException;

    /**
       gcno�ե������������ޤ���

       @param files �ե�����̾������
       @param inputFile ���Ϥ���ꥹ�ȤΥե�����̾���ϥ��ե󡢤ޤ���null
       @throws IOException �����ϥ��顼
    */
    public final void run(final String[] files,
			  final String inputFile) throws IOException {
	verifyHeapSize();
	pre();
	for (String arg : files) {
	    processFile(arg);
	}
	if (inputFile != null) {
	    processFileList(inputFile);
	}
	post();
	verifyHeapSize();
    }
}
