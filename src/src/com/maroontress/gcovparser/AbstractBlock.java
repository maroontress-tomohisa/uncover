package com.maroontress.gcovparser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/**
   �ؿ�����դΥΡ��ɤȤʤ���ܥ֥�å�����ݥ��饹�Ǥ���

   @param <T> �֥�å��ζ�ݥ��饹
   @param <U> �������ζ�ݥ��饹
*/
public abstract class AbstractBlock<T extends AbstractBlock<T, U>,
				    U extends AbstractArc<T, U>> {

    /** �֥�å��μ��̻ҤǤ��� */
    private int id;

    /** �֥�å��Υե饰�Ǥ��� */
    private int flags;

    /** �����륢�����פΥꥹ�ȤǤ��� */
    private ArrayList<U> inArcs;

    /** �¹Բ����Ƚ�����������륢�����פν���Ǥ��� */
    private ArrayDeque<U> solvedInArcs;

    /** �¹Բ���������ʡ����륢�����פν���Ǥ��� */
    private ArrayDeque<U> unsolvedInArcs;

    /** �ֽФ륢�����פΥꥹ�ȤǤ��� */
    private ArrayList<U> outArcs;

    /** �¹Բ����Ƚ�������ֽФ륢�����פΥꥹ�ȤǤ��� */
    private ArrayDeque<U> solvedOutArcs;

    /** �¹Բ���������ʡֽФ륢�����פΥꥹ�ȤǤ��� */
    private ArrayDeque<U> unsolvedOutArcs;

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

    /**
       �֥�å����������ޤ���

       @param id �֥�å��μ��̻�
       @param flags �֥�å��Υե饰
    */
    protected AbstractBlock(final int id, final int flags) {
	this.id = id;
	this.flags = flags;
	this.count = -1;
	inArcs = new ArrayList<U>();
	solvedInArcs = new ArrayDeque<U>();
	unsolvedInArcs = new ArrayDeque<U>();
	outArcs = new ArrayList<U>();
	solvedOutArcs = new ArrayDeque<U>();
	unsolvedOutArcs = new ArrayDeque<U>();
    }

    /**
       ������Ȥ�������ޤ���������Ȥ�ͭ���Ǥʤ��Ȥ���0���֤��ޤ���

       @return �������
    */
    public final long getCount() {
	return (count < 0) ? 0 : count;
    }

    /**
       ������Ȥ�ͭ������������ޤ���

       @return ������Ȥ�ͭ���ʤ�true�������Ǥʤ����false
    */
    public final boolean getCountValid() {
	return (count >= 0);
    }

    /**
       �ؿ��ƤӽФ���¬�֥�å��ʥ�����ˤ��ɤ��������ꤷ�ޤ���

       @param b �ؿ��ƤӽФ���¬�֥�å��ʥ�����ˤξ���true��������
       �ʤ����false
    */
    public final void setCallSite(final boolean b) {
	callSite = b;
    }

    /**
       catch�ޤ���setjmp()��ޤ�֥�å����ɤ��������ꤷ�ޤ���

       @param b catch�ޤ���setjmp()��ޤ�֥�å��ξ���true��������
       �ʤ����false
    */
    public final void setNonLocalReturn(final boolean b) {
	nonLocalReturn = b;
    }

    /**
       �ԥ���ȥ�������������ޤ���

       @return �ԥ���ȥ������
    */
    public final LineEntry[] getLines() {
	return lines;
    }

    /**
       ���Υ֥�å��˥��������ɲä��ޤ���

       @param arcs �������Υꥹ��
       @param unsolvedArcs �¹Բ���������ʥ������ν���
       @param solvedArcs �¹Բ����Ƚ�����륢�����ν���
       @param arc ������
    */
    private void addArc(final ArrayList<U> arcs,
			final ArrayDeque<U> unsolvedArcs,
			final ArrayDeque<U> solvedArcs,
			final U arc) {
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
    public final void addInArc(final U arc) {
	addArc(inArcs, unsolvedInArcs, solvedInArcs, arc);
    }

    /**
       ���Υ֥�å�����Ф륢�������ɲä��ޤ���

       @param arc ������
    */
    public final void addOutArc(final U arc) {
	addArc(outArcs, unsolvedOutArcs, solvedOutArcs, arc);
    }

    /**
       ���Υ֥�å��ιԥ���ȥ����������ꤷ�ޤ���

       @param lines �ԥ���ȥ������
    */
    public final void setLines(final LineEntry[] lines) {
	this.lines = lines;
    }

    /**
       ���̻Ҥ�������ޤ���

       @return ���̻�
    */
    public final int getId() {
	return id;
    }

    /**
       �����륢�����פΥꥹ�Ȥ�������ޤ���

       @return �����륢�����פΥꥹ��
    */
    public final ArrayList<U> getInArcs() {
	return inArcs;
    }

    /**
       �ֽФ륢�����פΥꥹ�Ȥ�������ޤ���

       @return �ֽФ륢�����פΥꥹ��
    */
    public final ArrayList<U> getOutArcs() {
	return outArcs;
    }

    /**
       �ե饰��������ޤ���

       @return �ե饰
    */
    public final int getFlags() {
	return flags;
    }

    /**
       �ؿ��ƤӽФ���¬�֥�å��ʥ�����ˤ��ɤ�����������ޤ���

       @return �ؿ��ƤӽФ���¬�֥�å��ʥ�����ˤξ���true��������
       �ʤ����false
    */
    public final boolean isCallSite() {
	return callSite;
    }

    /**
       �ؿ��ƤӽФ���¬�֥�å��ʥ꥿����ˤ��ɤ�����������ޤ���

       @return �ؿ��ƤӽФ���¬�֥�å��ʥ꥿����ˤξ���true������
       �Ǥʤ����false
    */
    public final boolean isCallReturn() {
	return callReturn;
    }

    /**
       ���Υ֥�å���longjmp()�ޤ���throw���������Ǥ����setjmp()�ޤ�
       ��catch�Ǥ���ˤ��ɤ�����������ޤ���

       @return ���Υ֥�å���longjmp()�ޤ���throw���������Ǥ������
       true�������Ǥʤ����false
    */
    public final boolean isNonLocalReturn() {
	return nonLocalReturn;
    }

    /**
       �ե�����դ�򤯤���ν����򤷤ޤ���

       �֥�å�����Ф뵶�Ǥʤ���������1�Ĥ����ʤ���硢���Υ�������̵
       ���ʬ�������ꤷ�ޤ�������ˡ����Υ�����������֥�å����ָƤ�
       �Ф���������פǤ��뤫�ɤ��������ꤷ�ޤ���
    */
    public final void presolve() {
	int nonFakeArcs = 0;
	U lastNonFakeArc = null;

	for (U a : outArcs) {
	    if (!a.isFake()) {
		lastNonFakeArc = a;
		++nonFakeArcs;
	    }
	}
	if (nonFakeArcs != 1) {
	    return;
	}
	U arc = lastNonFakeArc;
	AbstractBlock destBlock = arc.getEnd();
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

    /**
       �ֽФ륢�����פΥꥹ�Ȥ򤽤ν�λ�֥�å��μ��̻ҽ�˥����Ȥ���
       ����
    */
    public final void sortOutArcs() {
	TreeMap<Integer, U> map = new TreeMap<Integer, U>();
	for (U a : outArcs) {
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
    private long sumCount(final Collection<U> arcs) {
	long total = 0;
	for (U a : arcs) {
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
    private boolean validateCount(final ArrayList<U> arcs,
				  final ArrayDeque<U> unsolvedArcs) {
	if (arcs.size() > 0 && unsolvedArcs.size() == 0) {
	    count = sumCount(arcs);
	    return true;
	}
	return false;
    }

    /**
       �¹Բ������ޤ���

       �����륢�����פμ¹Բ�������٤Ƶ�ޤäƤ��뤫���ֽФ륢������
       �μ¹Բ�������٤Ƶ�ޤäƤ����硢���������¤�֥�å��μ�
       �Բ���Ȥ��Ʒ׻������֥�å��򥽥�Ф��ɲä��ޤ���

       �����륢�����פȡֽФ륢�����פΤɤ����¹Բ������ޤäƤ���
       �����ϲ��⤷�ޤ���

       @param s �ե�����ե����
    */
    public final void validate(final Solver s) {
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
    private void validateInSideBlock(final Solver s, final U arc) {
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
	U arc = unsolvedInArcs.remove();
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
    private void validateOutSideBlock(final Solver s, final U arc) {
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
	U arc = unsolvedOutArcs.remove();
	arc.setCount(count - sumCount(solvedOutArcs));
	solvedOutArcs.add(arc);
	/* arc������֥�å��ˤĤ��Ƥν��� */
	arc.getEnd().validateOutSideBlock(s, arc);
    }

    /**
       �֥�å������륢�������Ф륢�������줾��ˤĤ��ơ��¹Բ������
       ���ʤ�Τ�1�Ĥ����ʤ顢����μ¹Բ������ޤ���

       �֥�å��ϴ��˼¹Բ����Ƚ�����Ƥ���ɬ�פ�����ޤ���

       @param s �ե�����ե����
    */
    public final void validateSides(final Solver s) {
	if (unsolvedInArcs.size() == 1) {
	    validateInSide(s);
	}
	if (unsolvedOutArcs.size() == 1) {
	    validateOutSide(s);
	}
    }
}
