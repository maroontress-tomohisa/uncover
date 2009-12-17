package com.maroontress.uncover;

import com.maroontress.cui.Options;

/**
   ���ޥ�ɤǤ���
*/
public abstract class Command {
    /** �إ�ץ�å������Υ���ǥ�����Ǥ��� */
    private static final int INDENT_WIDTH = 20;

    /** �ץ�ѥƥ��Ǥ��� */
    private Properties props;

    /**
       ���ޥ�ɤ��������ޤ���

       @param props �ץ�ѥƥ�
    */
    protected Command(final Properties props) {
	this.props = props;
    }

    /**
       �ץ�ѥƥ���������ޤ���

       @return �ץ�ѥƥ�
    */
    protected final Properties getProperties() {
	return props;
    }

    /**
       ���ޥ�ɤ�¹Ԥ��ޤ���
    */
    public abstract void run();

    /**
       ������ˡ��ɽ�����ƽ�λ���ޤ���

       @param opt ���ޥ�ɥ饤�󥪥ץ��������
    */
    protected final void usage(final Options opt) {
	System.err.printf(getUsage(opt));
        System.exit(1);
    }

    /**
       ������ˡ��������ޤ���

       @param opt ���ޥ�ɥ饤�󥪥ץ��������
       @return ������ˡ
    */
    private String getUsage(final Options opt) {
	String name;
	String args;
	String desc;
	try {
	    name = (String) getClass().getField("NAME").get(null);
	    args = (String) getClass().getField("ARGS").get(null);
	    desc = (String) getClass().getField("DESC").get(null);
	} catch (NoSuchFieldException e) {
	    return null;
	} catch (IllegalAccessException e) {
	    return null;
	}

        String m = String.format("Usage: uncover %s [Options] %s\n"
				 + "Options are:\n", name, args);
	String[] help = opt.getHelpMessage(INDENT_WIDTH).split("\n");
	for (String s : help) {
	    m += String.format("  %s\n", s);
	}
	m += "Description:\n  " + desc + "\n";
        return m;
    }
}
