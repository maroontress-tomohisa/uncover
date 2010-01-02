package com.maroontress.uncover;

import java.util.Comparator;

/**
   �ؿ��˴ؤ������򥫥ץ��벽���ޤ���
*/
public final class Function implements FunctionSource {
    /** �ǥե���ȤΥ���ѥ졼���Ǥ��� */
    public static final Comparator<Function> DEFAULT_COMPARATOR;

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

    static {
	DEFAULT_COMPARATOR = new Comparator<Function>() {
	    public int compare(final Function f1, final Function f2) {
		int d;

		if ((d = f1.name.compareTo(f2.name)) != 0) {
		    return d;
		}
		return f1.gcnoFile.compareTo(f2.gcnoFile);
	    }
	};
	COMPLEXITY_COMPARATOR = new Comparator<Function>() {
	    public int compare(final Function f1, final Function f2) {
		int d;

		if ((d = -(f1.complexity - f2.complexity)) != 0) {
		    return d;
		}
		return DEFAULT_COMPARATOR.compare(f1, f2);
	    }
	};
    }

    /**
       �ǥե���ȥ��󥹥ȥ饯���ϻ��ѤǤ��ޤ���
    */
    private Function() {
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param s �ؿ�������
    */
    public Function(final FunctionSource s) {
	name = s.getName();
	sourceFile = s.getSourceFile().intern();
	gcnoFile = s.getGCNOFile().intern();
	checkSum = s.getCheckSum();
	lineNumber = s.getLineNumber();
	complexity = s.getComplexity();
	executedBlocks = s.getExecutedBlocks();
	allBlocks = s.getAllBlocks();
	executedArcs = s.getExecutedArcs();
	allArcs = s.getAllArcs();
	key = name + "@" + gcnoFile;
    }

    /** {@inheritDoc} */
    public String getCheckSum() {
	return checkSum;
    }

    /** {@inheritDoc} */
    public int getComplexity() {
	return complexity;
    }

    /** {@inheritDoc} */
    public String getName() {
	return name;
    }

    /** {@inheritDoc} */
    public String getGCNOFile() {
	return gcnoFile;
    }

    /** {@inheritDoc} */
    public String getSourceFile() {
	return sourceFile;
    }

    /** {@inheritDoc} */
    public int getLineNumber() {
	return lineNumber;
    }

    /** {@inheritDoc} */
    public int getExecutedBlocks() {
	return executedBlocks;
    }

    /** {@inheritDoc} */
    public int getAllBlocks() {
	return allBlocks;
    }

    /** {@inheritDoc} */
    public int getExecutedArcs() {
	return executedArcs;
    }

    /** {@inheritDoc} */
    public int getAllArcs() {
	return allArcs;
    }

    /**
       �ץ������Ȥǥ�ˡ����ʼ��̻Ҥ�������ޤ���

       ���̻Ҥ� "�ؿ�̾" + "@" + "gcno�ե�����Υե�����̾" �η����ˤ�
       ��ޤ���

       @return �ץ������Ȥǥ�ˡ����ʼ��̻�
    */
    public String getKey() {
	return key;
    }

    /**
       ������Ф���¹ԺѤߴ��ܥ֥�å��γ���������ޤ���

       0����1�δ֤ο��ͤˤʤ�ޤ���

       @return ������Ф���¹ԺѤߴ��ܥ֥�å��ο��γ��
    */
    public float getBlockRate() {
	if (allBlocks == 0) {
	    return 0;
	}
	return (float) executedBlocks / allBlocks;
    }

    /**
       ̤�¹Ԥδ��ܥ֥�å�����������ޤ���

       @return ̤�¹Ԥδ��ܥ֥�å���
    */
    public int getUnexecutedBlocks() {
	return allBlocks - executedBlocks;
    }

    /**
       �и����֤�������ޤ���

       �и����֤� "�������ե�����̾" + ":" + "���ֹ�" �Ȥ��������ˤʤ�
       �ޤ���

       @return �и�����
    */
    public String getLocation() {
	return sourceFile + ":" + lineNumber;
    }
}
