package com.maroontress.gcovparser;

import java.io.File;
import java.util.ArrayList;

/**
   gcno�ե�����˴ط�����ѥ���������ޤ���
*/
public final class Origin implements Comparable<Origin> {

    /** gcno�ե�����Ǥ��� */
    private File gcnoFile;

    /** �б�����gcda�ե�����Ǥ��� */
    private File gcdaFile;

    /**
       basePath�򥨥������פ���ʸ����Ǥ���
    */
    private String pathPrefix;

    /**
       ���ꥸ����������ޤ���

       path�γ�ĥ�Ҥ�.gcno�Ǥʤ���Фʤ�ޤ���

       @param path gcno�ե�����Υѥ�
    */
    public Origin(final String path) {
	if (!path.endsWith(".gcno")) {
	    String m = String.format("%s: suffix is not '.gcno'.", path);
	    throw new IllegalArgumentException(m);
	}
	gcnoFile = new File(path);
	String basePath = path.substring(0, path.lastIndexOf('.'));
	gcdaFile = new File(basePath + ".gcda");
	pathPrefix = escapeGcov(basePath);
    }

    /**
       �ѥ���gcov�Υ�������ǥ��������פ��ޤ���

       @param path �ѥ�
       @return ���������פ����ѥ�
    */
    private String escapeGcov(final String path) {
	String[] allComp = path.replace(File.separatorChar, '#').split("#+");
	ArrayList<String> list = new ArrayList<String>();
	for (String comp : allComp) {
	    if (comp.equals(".")) {
		continue;
	    }
	    if (comp.equals("..")) {
		comp = "^";
	    }
	    list.add(comp);
	}
	int n = list.size();
	if (n == 0) {
	    return "";
	}
	StringBuilder b = new StringBuilder(list.get(0));
	for (int k = 1; k < n; ++k) {
	    b.append("#");
	    b.append(list.get(k));
	}
	return b.toString();
    }

    /**
       gcno�ե������������ޤ���

       @return gcno�ե�����
    */
    public File getNoteFile() {
	return gcnoFile;
    }

    /**
       gcda�ե������������ޤ���

       @return gcda�ե�����
    */
    public File getDataFile() {
	return gcdaFile;
    }

    /**
       ���Х�å��ե�����Υѥ���������ޤ���

       �ѥ��ϡ�gcno�ե�����Υѥ������ĥ�Ҥ����������Τˡ����Х��
       ���оݤΥ������ե�����Υѥ��� "##" ��Ϣ�뤷���塢���Υ롼���
       �Ѵ�����ʸ����ˤʤ�ޤ���

       - �ѥ�����ݡ��ͥ�Ȥζ��ڤ�ʸ�����#�פ��Ѵ�
       - �ѥ�����ݡ��ͥ�Ȥ���.�פξ��Ϻ��
       - �ѥ�����ݡ��ͥ�Ȥ���..�פξ��ϡ�^�פ��Ѵ�

       @param sourceFile ���Х�å��оݤΥ������ե�����Υѥ�
       @return ���Х�å��ե�����Υѥ�
    */
    public String getCoverageFilePath(final String sourceFile) {
	return pathPrefix + "##" + escapeGcov(sourceFile) + ".gcov";
    }

    /**
       ��������Τ˻��Ѥ���gcno�ե�����Υѥ��ǡ�2�ĤΥ��ꥸ������Ū
       ����Ӥ��ޤ���

       @param origin ���Υ��ꥸ�����Ӥ���륪�ꥸ��
       @return ���������Υ��ꥸ�������������0�����Υ��ꥸ�󤬰�����
       �����Ū�˾�������������͡����Υ��ꥸ�󤬰���������Ū����
       ��������������
       @see java.io.File#compareTo(File)
    */
    public int compareTo(final Origin origin) {
	return gcnoFile.compareTo(origin.gcnoFile);
    }
}
