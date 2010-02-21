package com.maroontress.uncover;

import java.io.PrintStream;

/**
   ʣ���Υӥ�ɤ�¸�ߤ�������㳰�Ǥ���
*/
public class MultipleBuildsException extends Exception {
    /** ��ӥ����Ǥ��� */
    private String revision;

    /** �ӥ�ɤ�����Ǥ��� */
    private Build[] builds;

    /** ������ˡ�Ǥ��� */
    private String howToFix;

    /**
       �㳰���������ޤ���

       @param revision ��ӥ����
       @param builds �ӥ�ɤ�����
       @param howToFix ������ˡ
    */
    public MultipleBuildsException(final String revision, final Build[] builds,
				   final String howToFix) {
	super();
	this.revision = revision;
	this.builds = builds;
	this.howToFix = howToFix;
    }

    /**
       �㳰����������Ϥ��ޤ���

       @param out ���ϥ��ȥ꡼��
    */
    public final void printDescription(final PrintStream out) {
	out.printf("revision '%s' has %d results:\n\n",
		   revision, builds.length);
	for (Build b : builds) {
	    out.printf("ID: @%s\n"
		       + "Platform: %s\n"
		       + "Timestamp: %s\n"
		       + "\n",
		       b.getID(), b.getPlatform(), b.getTimestamp());
	}
	out.printf(howToFix);
    }
}
