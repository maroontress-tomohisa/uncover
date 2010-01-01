package com.maroontress.uncover;

import java.util.Comparator;

/**
   �ؿ��˴ؤ������򥫥ץ��벽���ޤ���
*/
public abstract class Function implements Comparable<Function> {
    /** ʣ���ٽ�˥����Ȥ��륳��ѥ졼���Ǥ��� */
    public static final Comparator<Function> COMPLEXITY_COMPARATOR;

    /** �ؿ�̾�Ǥ��� */
    private String name;

    /** �ؿ����и����륽�����ե�����Ǥ��� */
    private String sourceFile;

    /** ͳ�褹��gcno�ե�����Ǥ��� */
    private String gcnoFile;

    /** �ؿ��Υ����å�����Ǥ��� */
    private String checkSum;

    /** �ؿ����и����륽�����ե�����Ǥι��ֹ�Ǥ��� */
    private int lineNumber;

    /**
       ʣ���٤Ǥ���FAKE�Υ������������������McCabe��cyclomatic
       complexity���������ޤ���
    */
    private int complexity;

    /** �¹ԺѤߤδ��ܥ֥�å��ο��Ǥ��� */
    private int executedBlocks;

    /** ���ܥ֥�å�������Ǥ��� */
    private int allBlocks;

    /** �¹ԺѤߤΥ������ο��Ǥ��� */
    private int executedArcs;

    /** ������������Ǥ��� */
    private int allArcs;

    /**
       Ʊ��ץ������Ȥǥ�ˡ����ʼ��̻ҤǤ���"�ؿ�̾" + "@" + "gcno
       �ե�����Υե�����̾" �η����ˤʤ�ޤ���
    */
    private String key;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    protected Function() {
    }

    /**
       �����������ɤΥѥ�᡼�������ꤷ�ޤ���

       @param name �ؿ�̾
       @param sourceFile �ؿ����и����륽�����ե�����
       @param lineNumber �ؿ����и����륽�����ե�����Ǥι��ֹ�
       @param gcnoFile ͳ�褹��gcno�ե�����
       @param checkSum �ؿ��Υ����å�����
    */
    protected final void setSource(final String name,
				   final String sourceFile,
				   final int lineNumber,
				   final String gcnoFile,
				   final String checkSum) {
	this.name = name;
	this.sourceFile = sourceFile;
	this.lineNumber = lineNumber;
	this.gcnoFile = gcnoFile;
	this.checkSum = checkSum;
	key = name + "@" + gcnoFile;
    }

    /**
       �ե�����դΥѥ�᡼�������ꤷ�ޤ���

       @param complexity ʣ����
       @param executedBlocks �¹ԺѤߤδ��ܥ֥�å��ο�
       @param allBlocks ���ܥ֥�å������
       @param executedArcs �¹ԺѤߤΥ������ο�
       @param allArcs �����������
    */
    protected final void setGraph(final int complexity,
				  final int executedBlocks,
				  final int allBlocks,
				  final int executedArcs,
				  final int allArcs) {
	this.complexity = complexity;
	this.executedBlocks = executedBlocks;
	this.allBlocks = allBlocks;
	this.executedArcs = executedArcs;
	this.allArcs = allArcs;
    }

    static {
	COMPLEXITY_COMPARATOR = new Comparator<Function>() {
	    public int compare(final Function f1, final Function f2) {
		int d;

		if ((d = -(f1.complexity - f2.complexity)) != 0) {
		    return d;
		}
		return f1.compareTo(f2);
	    }
	};
    }

    /** {@inheritDoc} */
    public final int compareTo(final Function function) {
	int d;

	if ((d = name.compareTo(function.name)) != 0) {
	    return d;
	}
	return gcnoFile.compareTo(function.gcnoFile);
    }

    /**
       �����å������������ޤ���

       @return �����å�����
    */
    public final String getCheckSum() {
	return checkSum;
    }

    /**
       ʣ���٤�������ޤ���

       @return ʣ����
    */
    public final int getComplexity() {
	return complexity;
    }

    /**
       �ץ������Ȥǥ�ˡ����ʼ��̻Ҥ�������ޤ���

       @return �ץ������Ȥǥ�ˡ����ʼ��̻�
    */
    public final String getKey() {
	return key;
    }

    /**
       �ؿ�̾��������ޤ���

       @return �ؿ�̾
    */
    public final String getName() {
	return name;
    }

    /**
       �и����֤�������ޤ���

       �и����֤� "�������ե�����̾" + ":" + "���ֹ�" �Ȥ��������ˤʤ�
       �ޤ���

       @return �и�����
    */
    public final String getLocation() {
	return sourceFile + ":" + lineNumber;
    }

    /**
       gcno�ե�����̾��������ޤ���

       @return gcno�ե�����̾
    */
    public final String getGCNOFile() {
	return gcnoFile;
    }

    /**
       �������ե�����̾��������ޤ���

       @return �������ե�����̾
    */
    public final String getSourceFile() {
	return sourceFile;
    }

    /**
       �������ե�����ǽи�������ֹ��������ޤ���

       @return �������ե�����ǽи�������ֹ�
    */
    public final int getLineNumber() {
	return lineNumber;
    }

    /**
       ������Ф���¹ԺѤߴ��ܥ֥�å��γ���������ޤ���

       0����1�δ֤ο��ͤˤʤ�ޤ���

       @return ������Ф���¹ԺѤߴ��ܥ֥�å��ο��γ��
    */
    public final float getBlockRate() {
	if (allBlocks == 0) {
	    return 0;
	}
	return (float) executedBlocks / allBlocks;
    }

    /**
       �¹ԺѤߤδ��ܥ֥�å�����������ޤ���

       @return �¹ԺѤߤδ��ܥ֥�å���
    */
    public final int getExecutedBlocks() {
	return executedBlocks;
    }

    /**
       ���ܥ֥�å������������ޤ���

       @return ���ܥ֥�å����
    */
    public final int getAllBlocks() {
	return allBlocks;
    }

    /**
       ̤�¹Ԥδ��ܥ֥�å�����������ޤ���

       @return ̤�¹Ԥδ��ܥ֥�å���
    */
    public final int getUnexecutedBlocks() {
	return allBlocks - executedBlocks;
    }

    /**
       �¹ԺѤߤΥ���������������ޤ���

       @return �¹ԺѤߤΥ�������
    */
    public final int getExecutedArcs() {
	return executedArcs;
    }

    /**
       �����������������ޤ���

       @return ���������
    */
    public final int getAllArcs() {
	return allArcs;
    }
}
