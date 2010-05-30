package com.maroontress.coverture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
   �����Ϥ˴�Ϣ����ץ�ѥƥ��Ǥ���
*/
public final class IOProperties {

    /** ���¿���Υ�å���������Ϥ��뤫�ɤ����Υե饰�Ǥ��� */
    private boolean verbose;

    /** �ե��������Ϥ���ǥ��쥯�ȥ�Ǥ��� */
    private File outputDir;

    /** �������ե������ʸ������Ǥ��� */
    private Charset sourceFileCharset;

    /** gcov�ե������ʸ������Ǥ��� */
    private Charset gcovFileCharset;

    /** gcov�ե��������Ϥ��뤫�ɤ����Υե饰�Ǥ��� */
    private boolean gcovEnabled;

    /**
       �ǥե���Ȥ������ϥץ�ѥƥ����������ޤ���
    */
    public IOProperties() {
	verbose = false;
	outputDir = new File(".");
	sourceFileCharset = Charset.defaultCharset();
	gcovFileCharset = Charset.defaultCharset();
	gcovEnabled = false;
    }

    /**
       gcov�ե��������Ϥ��뤫�ɤ������ꤷ�ޤ���

       @param b gcov�ե��������Ϥ������true
    */
    public void setGcovEnabled(final boolean b) {
	gcovEnabled = b;
    }

    /**
       gcov�ե��������Ϥ��뤫�ɤ����������ޤ���

       @return gcov�ե��������Ϥ������true
    */
    public boolean isGcovEnabled() {
	return gcovEnabled;
    }

    /**
       ��å��������Ϥ��Ĺ�ˤ��뤫�ɤ������ꤷ�ޤ���

       @param b ��å��������Ϥ��Ĺ�ˤ������true�ϡ������Ǥʤ����
       false
    */
    public void setVerbose(final boolean b) {
	verbose = b;
    }

    /**
       �ե��������Ϥ���ǥ��쥯�ȥ�����ꤷ�ޤ���

       @param dir �ե��������Ϥ���ǥ��쥯�ȥ�
    */
    public void setOutputDir(final File dir) {
	outputDir = dir;
    }

    /**
       �������ե������ʸ����������ꤷ�ޤ���

       @param cs �������ե������ʸ������
    */
    public void setSourceFileCharset(final Charset cs) {
	sourceFileCharset = cs;
    }

    /**
       gcov�ե������ʸ����������ꤷ�ޤ���

       @param cs gcov�ե������ʸ������
    */
    public void setGcovFileCharset(final Charset cs) {
	gcovFileCharset = cs;
    }

    /**
       ��å��������Ϥ��Ĺ�ˤ��뤫�ɤ����������ޤ���

       @return ��å��������Ϥ��Ĺ�ˤ������true�ϡ������Ǥʤ����
       false
    */
    public boolean isVerbose() {
	return verbose;
    }

    /**
       ���ϥե�������������ޤ���

       @param path ���ϥǥ��쥯�ȥ������Ȥ������Хѥ�
       @return ���ϥե�����
    */
    public File createOutputFile(final String path) {
	return new File(outputDir, path);
    }

    /**
       ������Υǥ��쥯�ȥ���������ޤ���
    */
    public void makeOutputDir() {
	outputDir.mkdirs();
    }

    /**
       gcov�ե�����Υ饤�����������ޤ���

       @param path ���ϥǥ��쥯�ȥ������Ȥ������Хѥ�
       @return gcov�ե�����Υ饤��
       @throws FileNotFoundException �ե�����������Ǥ��ʤ�
    */
    public Writer createGcovWriter(final String path)
	throws FileNotFoundException {
	File file = createOutputFile(path);
	Writer out = new OutputStreamWriter(new FileOutputStream(file),
					    gcovFileCharset);
	return out;
    }

    /**
       �������ե�����Υ꡼�����������ޤ���

       @param file �������ե�����
       @return �������ե�����Υ꡼��
       @throws FileNotFoundException �ե����뤬¸�ߤ��ʤ�
    */
    public Reader createSourceFileReader(final File file)
	throws FileNotFoundException {
	Reader in = new InputStreamReader(new FileInputStream(file),
					  sourceFileCharset);
	return in;
    }
}
