package com.maroontress.uncover;

import java.util.Arrays;

/**
   ����դ˴ؤ������򥫥ץ��벽���ޤ���
*/
public final class Graph implements GraphSource {
    /** �ؿ�̾�Ǥ��� */
    private String name;

    /** ͳ�褹��gcno�ե�����Ǥ��� */
    private String gcnoFile;

    /** ���٤Ƥδ��ܥ֥�å��Ǥ��� */
    private Block[] allBlocks;

    /** ���٤ƤΥ������Ǥ��� */
    private Arc[] allArcs;

    /**
       ���󥹥��󥹤��������ޤ���

       @param s ����ե�����
    */
    public Graph(final GraphSource s) {
	name = s.getName();
	gcnoFile = s.getGCNOFile().intern();
	allBlocks = s.getAllBlocks();
	allArcs = s.getAllArcs();
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
    public Block[] getAllBlocks() {
	return Arrays.copyOf(allBlocks, allBlocks.length);
    }

    /** {@inheritDoc} */
    public Arc[] getAllArcs() {
	return Arrays.copyOf(allArcs, allArcs.length);
    }
}
