package com.maroontress.gcovparser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/**
   �ؿ�����դΥΡ��ɤȤʤ���ܥ֥�å��Ǥ���
*/
public final class DefaultBlock extends AbstractBlock {

    /** �֥�å��μ��̻ҤǤ��� */
    private int id;

    /** �֥�å��Υե饰�Ǥ��� */
    private int flags;

    /** �����륢�����פΥꥹ�ȤǤ��� */
    private ArrayList<DefaultArc> inArcs;

    /** �¹Բ����Ƚ�����������륢�����פν���Ǥ��� */
    private ArrayDeque<DefaultArc> solvedInArcs;

    /** �¹Բ���������ʡ����륢�����פν���Ǥ��� */
    private ArrayDeque<DefaultArc> unsolvedInArcs;

    /** �ֽФ륢�����פΥꥹ�ȤǤ��� */
    private ArrayList<DefaultArc> outArcs;

    /** �¹Բ����Ƚ�������ֽФ륢�����פΥꥹ�ȤǤ��� */
    private ArrayDeque<DefaultArc> solvedOutArcs;

    /** �¹Բ���������ʡֽФ륢�����פΥꥹ�ȤǤ��� */
    private ArrayDeque<DefaultArc> unsolvedOutArcs;

    /** �֥�å��μ¹Բ���Ǥ��� */
    private long count;

    /**
       Block is a call instrumenting site; does the call: �ؿ��ƤӽФ�
       ��¬�֥�å��ʥ�����ˤǤ��뤳�Ȥ򼨤��ޤ���
    */
    private boolean callSite;

    /**
       Block is a call instrumenting site; is the return: �ؿ��ƤӽФ�
       ��¬�֥�å��ʥ꥿����ˤ��ɤ�����������ޤ���
    */
    private boolean callReturn;

    /**
       Block is a landing pad for longjmp or throw: longjmp()�ޤ���
       throw���������Ǥ����setjmp()�ޤ���catch�Ǥ���ˤ��Ȥ򼨤��ޤ���
    */
    private boolean nonLocalReturn;

    /** �֥�å����б����륽���������ɤιԥ���ȥ������Ǥ��� */
    private LineEntry[] lines;

    /** {@inheritDoc} */
    @Override public long getCount() {
	return (count < 0) ? 0 : count;
    }

    /**
       ������Ȥ�ͭ������������ޤ���

       @return ������Ȥ�ͭ���ʤ�true�������Ǥʤ����false
    */
    public boolean getCountValid() {
	return (count >= 0);
    }

    /**
       �֥�å����������ޤ���

       @param id �֥�å��μ��̻�
       @param flags �֥�å��Υե饰
    */
    public DefaultBlock(final int id, final int flags) {
	this.id = id;
	this.flags = flags;
	this.count = -1;
	inArcs = new ArrayList<DefaultArc>();
	solvedInArcs = new ArrayDeque<DefaultArc>();
	unsolvedInArcs = new ArrayDeque<DefaultArc>();
	outArcs = new ArrayList<DefaultArc>();
	solvedOutArcs = new ArrayDeque<DefaultArc>();
	unsolvedOutArcs = new ArrayDeque<DefaultArc>();
    }

    /**
       �ؿ��ƤӽФ���¬�֥�å��ʥ�����ˤ��ɤ��������ꤷ�ޤ���

       @param b �ؿ��ƤӽФ���¬�֥�å��ʥ�����ˤξ���true��������
       �ʤ����false
    */
    public void setCallSite(final boolean b) {
	callSite = b;
    }

    /**
       catch�ޤ���setjmp()��ޤ�֥�å����ɤ��������ꤷ�ޤ���

       @param b catch�ޤ���setjmp()��ޤ�֥�å��ξ���true��������
       �ʤ����false
    */
    public void setNonLocalReturn(final boolean b) {
	nonLocalReturn = b;
    }

    /**
       �ԥ���ȥ�������������ޤ���

       @return �ԥ���ȥ������
    */
    public LineEntry[] getLines() {
	return lines;
    }

    /**
       ���Υ֥�å��˥��������ɲä��ޤ���

       @param arcs �������Υꥹ��
       @param unsolvedArcs �¹Բ���������ʥ������ν���
       @param solvedArcs �¹Բ����Ƚ�����륢�����ν���
       @param arc ������
    */
    private void addArc(final ArrayList<DefaultArc> arcs,
			final ArrayDeque<DefaultArc> unsolvedArcs,
			final ArrayDeque<DefaultArc> solvedArcs,
			final DefaultArc arc) {
	arcs.add(arc);
	if (arc.isOnTree()) {
	    unsolvedArcs.add(arc);
	} else {
	    solvedArcs.add(arc);
	}
    }

    /**
       ���Υ֥�å������륢�������ɲä��ޤ���

       @param arc ������
    */
    public void addInArc(final DefaultArc arc) {
	addArc(inArcs, unsolvedInArcs, solvedInArcs, arc);
    }

    /**
       ���Υ֥�å�����Ф륢�������ɲä��ޤ���

       @param arc ������
    */
    public void addOutArc(final DefaultArc arc) {
	addArc(outArcs, unsolvedOutArcs, solvedOutArcs, arc);
    }

    /** {@inheritDoc} */
    @Override public void setLines(final LineEntry[] lines) {
	this.lines = lines;
    }

    /** {@inheritDoc} */
    @Override public int getId() {
	return id;
    }

    /** {@inheritDoc} */
    @Override public ArrayList<? extends AbstractArc> getInArcs() {
	return inArcs;
    }

    /** {@inheritDoc} */
    @Override public ArrayList<? extends AbstractArc> getOutArcs() {
	return outArcs;
    }

    /**
       �ե饰��������ޤ���

       @return �ե饰
    */
    public int getFlags() {
	return flags;
    }

    /**
       �ؿ��ƤӽФ���¬�֥�å��ʥ�����ˤ��ɤ�����������ޤ���

       @return �ؿ��ƤӽФ���¬�֥�å��ʥ�����ˤξ���true��������
       �ʤ����false
    */
    public boolean isCallSite() {
	return callSite;
    }

    /**
       �ؿ��ƤӽФ���¬�֥�å��ʥ꥿����ˤ��ɤ�����������ޤ���

       @return �ؿ��ƤӽФ���¬�֥�å��ʥ꥿����ˤξ���true������
       �Ǥʤ����false
    */
    public boolean isCallReturn() {
	return callReturn;
    }

    /**
       ���Υ֥�å���longjmp()�ޤ���throw���������Ǥ����setjmp()�ޤ�
       ��catch�Ǥ���ˤ��ɤ�����������ޤ���

       @return ���Υ֥�å���longjmp()�ޤ���throw���������Ǥ������
       true�������Ǥʤ����false
    */
    public boolean isNonLocalReturn() {
	return nonLocalReturn;
    }

    /** {@inheritDoc} */
    @Override public void presolve() {
	int nonFakeArcs = 0;
	DefaultArc lastNonFakeArc = null;

	for (DefaultArc a : outArcs) {
	    if (!a.isFake()) {
		lastNonFakeArc = a;
		++nonFakeArcs;
	    }
	}
	if (nonFakeArcs != 1) {
	    return;
	}
	DefaultArc arc = lastNonFakeArc;
	DefaultBlock destBlock = arc.getEnd();
	/*
	  If there is only one non-fake exit, it is an unconditional
	  branch.
	*/
	arc.setUnconditional(true);
	/*
	  If this block is instrumenting a call, it might be an
	  artificial block. It is not artificial if it has a
	  non-fallthrough exit, or the destination of this arc has
	  more than one entry.  Mark the destination block as a return
	  site, if none of those conditions hold.
	*/
	if (callSite
	    && arc.isFallThrough()
	    && destBlock.inArcs.get(0) == arc
	    && destBlock.inArcs.size() == 1) {
	    destBlock.callReturn = true;
	}
    }

    /** {@inheritDoc} */
    @Override public void sortOutArcs() {
	TreeMap<Integer, DefaultArc> map = new TreeMap<Integer, DefaultArc>();
	for (DefaultArc a : outArcs) {
	    map.put(a.getEnd().getId(), a);
	}
	outArcs.clear();
	outArcs.addAll(map.values());
    }

    /**
       �������ν��礫��¹Բ������ޤ���

       @param arcs �������ν���
       @return ��¹Բ��
    */
    private long sumCount(final Collection<DefaultArc> arcs) {
	long total = 0;
	for (DefaultArc a : arcs) {
	    total += a.getCount();
	}
	return total;
    }

    /**
       �¹Բ������ޤ���

       @param arcs �������Υꥹ��
       @param unsolvedArcs �¹Բ���������ʥ������Υꥹ��
       @return �¹Բ������ޤä�����true�������Ǥʤ����false
    */
    private boolean validateCount(final ArrayList<DefaultArc> arcs,
				  final ArrayDeque<DefaultArc> unsolvedArcs) {
	if (arcs.size() > 0 && unsolvedArcs.size() == 0) {
	    count = sumCount(arcs);
	    return true;
	}
	return false;
    }

    /** {@inheritDoc} */
    @Override public void validate(final Solver s) {
	if (validateCount(inArcs, unsolvedInArcs)
	    || validateCount(outArcs, unsolvedOutArcs)) {
	    s.addValid(this);
	}
    }

    /**
       �ֽФ륢�����פμ¹Բ����Ƚ�������Ȥ��˸ƤӽФ���ޤ���

       �֥�å��μ¹Բ��������Ƚ�����Ƥ������ϡ��֥�å�����ֽФ�
       �������פΤ������¹Բ����Ƚ�����Ƥ��ʤ��������μ¹Բ�����ǽ
       �ʤ���ޤ���

       �֥�å��μ¹Բ���������ʾ��ϡ��֥�å��μ¹Բ�����ǽ�ʤ�
       ���ޤ������η�̤ȶ��˥���Ф˥֥�å����ɲä��ޤ���

       @param s �ե�����ե����
       @param arc �¹Բ����Ƚ������������
    */
    private void validateInSideBlock(final Solver s, final DefaultArc arc) {
	unsolvedOutArcs.remove(arc);
	solvedOutArcs.add(arc);
	if (inArcs.size() > 0 && unsolvedInArcs.size() == 0) {
	    /* ���˼¹Բ����Ƚ�����Ƥ��� */
	    if (unsolvedOutArcs.size() == 1) {
		validateOutSide(s);
	    }
	} else {
	    /* �ޤ��¹Բ�������� */
	    s.add(this, validateCount(outArcs, unsolvedOutArcs));
	}
    }

    /**
       �¹Բ���������ʡ����륢�����פ�1�Ĥ����Ǥ��ꡢ���ĥ֥�å��μ�
       �Բ����Ƚ�������Ȥ��˸ƤӽФ��졢���٤ƤΡ����륢�����פμ¹�
       �����Ƚ�����ޤ���

       �¹Բ����Ƚ�������������γ��ϥ֥�å��ˤĤ��ơ��Ƶ�Ū�˼¹Բ�
       ������ޤ���

       @param s �ե�����ե����
    */
    private void validateInSide(final Solver s) {
	DefaultArc arc = unsolvedInArcs.remove();
	arc.setCount(count - sumCount(solvedInArcs));
	solvedInArcs.add(arc);
	/* arc���Ф�֥�å��ˤĤ��Ƥν��� */
	arc.getStart().validateInSideBlock(s, arc);
    }

    /**
       �����륢�����פμ¹Բ����Ƚ�������Ȥ��˸ƤӽФ���ޤ���

       �֥�å��μ¹Բ��������Ƚ�����Ƥ������ϡ��֥�å��ˡ����륢��
       ���פΤ������¹Բ����Ƚ�����Ƥ��ʤ��������μ¹Բ�����ǽ�ʤ�
       ���ޤ���

       �֥�å��μ¹Բ���������ʾ��ϡ��֥�å��μ¹Բ�����ǽ�ʤ�
       ���ޤ������η�̤ȶ��˥���Ф˥֥�å����ɲä��ޤ���

       @param s �ե�����ե����
       @param arc �¹Բ����Ƚ������������
    */
    private void validateOutSideBlock(final Solver s, final DefaultArc arc) {
	unsolvedInArcs.remove(arc);
	solvedInArcs.add(arc);
	if (outArcs.size() > 0 && unsolvedOutArcs.size() == 0) {
	    /* ���˼¹Բ����Ƚ�����Ƥ��� */
	    if (unsolvedInArcs.size() == 1) {
		validateInSide(s);
	    }
	} else {
	    /* �ޤ��¹Բ�������� */
	    s.add(this, validateCount(inArcs, unsolvedInArcs));
	}
    }

    /**
       �¹Բ���������ʡֽФ륢�����פ�1�Ĥ����Ǥ��ꡢ���ĥ֥�å��μ�
       �Բ����Ƚ�������Ȥ��˸ƤӽФ��졢���٤ƤΡֽФ륢�����פμ¹�
       �����Ƚ�����ޤ���

       �¹Բ����Ƚ�������������ν�λ�֥�å��ˤĤ��ơ��Ƶ�Ū�˼¹Բ�
       ������ޤ���

       @param s �ե�����ե����
    */
    private void validateOutSide(final Solver s) {
	DefaultArc arc = unsolvedOutArcs.remove();
	arc.setCount(count - sumCount(solvedOutArcs));
	solvedOutArcs.add(arc);
	/* arc������֥�å��ˤĤ��Ƥν��� */
	arc.getEnd().validateOutSideBlock(s, arc);
    }

    /** {@inheritDoc} */
    @Override public void validateSides(final Solver s) {
	if (unsolvedInArcs.size() == 1) {
	    validateInSide(s);
	}
	if (unsolvedOutArcs.size() == 1) {
	    validateOutSide(s);
	}
    }
}
