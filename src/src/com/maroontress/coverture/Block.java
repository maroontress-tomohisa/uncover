package com.maroontress.coverture;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;

/**
   �ؿ�����դΥΡ��ɤȤʤ���ܥ֥�å��Ǥ���
*/
public final class Block {

    /** �ѡ�����Ȥ��Ѵ����뤿��η����Ǥ��� */
    private static final double PERCENT = 100;

    /** �֥�å��μ��̻ҤǤ��� */
    private int id;

    /** �֥�å��Υե饰�Ǥ��� */
    private int flags;

    /** �����륢�����פΥꥹ�ȤǤ��� */
    private ArrayList<Arc> inArcs;

    /** �¹Բ����Ƚ�����������륢�����פν���Ǥ��� */
    private LinkedList<Arc> solvedInArcs;

    /** �¹Բ���������ʡ����륢�����פν���Ǥ��� */
    private LinkedList<Arc> unsolvedInArcs;

    /** �ֽФ륢�����פΥꥹ�ȤǤ��� */
    private ArrayList<Arc> outArcs;

    /** �¹Բ����Ƚ�������ֽФ륢�����פΥꥹ�ȤǤ��� */
    private LinkedList<Arc> solvedOutArcs;

    /** �¹Բ���������ʡֽФ륢�����פΥꥹ�ȤǤ��� */
    private LinkedList<Arc> unsolvedOutArcs;

    /** �֥�å��μ¹Բ���Ǥ��� */
    private long count;

    /**
       Block is a call instrumenting site; does the call: �ؿ���Ƥӽ�
       ���֥�å��Ǥ��뤳�Ȥ򼨤��ޤ���
    */
    private boolean callSite;

    /**
       Block is a call instrumenting site; is the return. �ƤӽФ�����
       �����Ȥʤ�֥�å��Ǥ��뤳�Ȥ򼨤��ޤ���
    */
    private boolean callReturn;

    /**
       Block is a landing pad for longjmp or throw: �������ν�����
       catch�ޤ���setjmp()�Ǥ��뤳�Ȥ򼨤��ޤ���
    */
    private boolean nonLocalReturn;

    /** �֥�å����б����륽���������ɤιԥ���ȥ������Ǥ��� */
    private LineEntry[] lines;

    /**
       ���ֹ���μ¹Բ���򥽡����ꥹ�Ȥ˥ޡ������ޤ���

       �����˼¹Բ���Υ�����Ȥ�ͭ���ˤʤäƤ���ɬ�פ�����ޤ���

       @param sourceList �������ꥹ��
    */
    public void addLineCounts(final SourceList sourceList) {
	assert (count >= 0);
	if (lines == null) {
	    return;
	}
	// ����for�����LineEntry�˰ܤ���...
	// e.addLineCounts(sourcelist, count);
	for (LineEntry e : lines) {
	    String fileName = e.getFileName();
	    int[] nums = e.getLines();
	    if (nums.length == 0) {
		continue;
	    }
	    Source source = sourceList.getSource(fileName);
	    for (int k = 0; k < nums.length; ++k) {
		source.addLineCount(nums[k], count);
	    }
	}
    }

    /**
       ������Ȥ�������ޤ���������Ȥ�ͭ���Ǥʤ��Ȥ���0���֤��ޤ���

       @return �������
    */
    public long getCount() {
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
       �¹Գ��ʥѡ�����ȡˤ�������ޤ���

       @param c �¹Բ��
       @return �¹Գ��
    */
    private double getRate(final long c) {
	return (count == 0) ? 0 : PERCENT * c / count;
    }

    /**
       XML�ǥ֥�å�����Ϥ��ޤ���

       @param out ������
    */
    public void printXML(final PrintWriter out) {
	final boolean countValid = getCountValid();

	out.printf("<block id='%d' flags='0x%x' callSite='%b' "
		   + "callReturn='%b' nonLocalReturn='%b'",
		   id, flags, callSite, callReturn, nonLocalReturn);
	if (countValid) {
	    out.printf(" count='%d'", count);
	}
	out.printf(">\n");

	for (Arc arc : outArcs) {
	    out.printf("<arc destination='%d' fake='%b' onTree='%b' "
		       + "fallThrough='%b' callNonReturn='%b' "
		       + "nonLocalReturn='%b' unconditional='%b'",
		       arc.getEnd().getId(), arc.isFake(), arc.isOnTree(),
		       arc.isFallThrough(), arc.isCallNonReturn(),
		       arc.isNonLocalReturn(), arc.isUnconditional());
	    if (countValid) {
		long c = arc.getCount();
		out.printf(" count='%d' rate='%.2f'", c, getRate(c));
	    }
	    out.printf("/>\n");
	}
	if (lines != null) {
	    // ����for�����LineEntry�˰ܤ���...
	    // e.printXML();
	    for (LineEntry e : lines) {
		String fileName = e.getFileName();
		int[] nums = e.getLines();
		if (nums.length == 0) {
		    continue;
		}
		out.printf("<lines fileName='%s'>\n", XML.escape(fileName));
		for (int k = 0; k < nums.length; ++k) {
		    out.printf("<line number='%d' />\n", nums[k]);
		}
		out.printf("</lines>\n");
	    }
	}
	out.printf("</block>\n");
    }

    /**
       �֥�å����������ޤ���

       @param id �֥�å��μ��̻�
       @param flags �֥�å��Υե饰
    */
    public Block(final int id, final int flags) {
	this.id = id;
	this.flags = flags;
	this.count = -1;
	inArcs = new ArrayList<Arc>();
	solvedInArcs = new LinkedList<Arc>();
	unsolvedInArcs = new LinkedList<Arc>();
	outArcs = new ArrayList<Arc>();
	solvedOutArcs = new LinkedList<Arc>();
	unsolvedOutArcs = new LinkedList<Arc>();
    }

    /**
       �ؿ���ƤӽФ��֥�å����ɤ��������ꤷ�ޤ���

       @param b �ؿ���ƤӽФ��֥�å��ξ���true�������Ǥʤ����
       false
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
       ���Υ֥�å��˥��������ɲä��ޤ���

       @param arcs �������Υꥹ��
       @param unsolvedArcs �¹Բ���������ʥ������ν���
       @param solvedArcs �¹Բ����Ƚ�����륢�����ν���
       @param arc ������
    */
    private void addArc(final ArrayList<Arc> arcs,
			final LinkedList<Arc> unsolvedArcs,
			final LinkedList<Arc> solvedArcs,
			final Arc arc) {
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
    public void addInArc(final Arc arc) {
	addArc(inArcs, unsolvedInArcs, solvedInArcs, arc);
    }

    /**
       ���Υ֥�å�����Ф륢�������ɲä��ޤ���

       @param arc ������
    */
    public void addOutArc(final Arc arc) {
	addArc(outArcs, unsolvedOutArcs, solvedOutArcs, arc);
    }

    /**
       ���Υ֥�å��ιԥ���ȥ����������ꤷ�ޤ���

       @param lines �ԥ���ȥ������
    */
    public void setLines(final LineEntry[] lines) {
	this.lines = lines;
    }

    /**
       ���̻Ҥ�������ޤ���

       @return ���̻�
    */
    public int getId() {
	return id;
    }

    /**
       �����륢�����פΥꥹ�Ȥ�������ޤ���

       @return �����륢�����פΥꥹ��
    */
    public ArrayList<Arc> getInArcs() {
	return inArcs;
    }

    /**
       �ֽФ륢�����פΥꥹ�Ȥ�������ޤ���

       @return �ֽФ륢�����פΥꥹ��
    */
    public ArrayList<Arc> getOutArcs() {
	return outArcs;
    }

    /**
       �ؿ���ƤӽФ��֥�å����ɤ�����������ޤ���

       @return �ؿ���ƤӽФ��֥�å��ξ���true�������Ǥʤ����false
    */
    public boolean isCallSite() {
	return callSite;
    }

    /**
       �ե�����դ�򤯤���ν����򤷤ޤ���

       �֥�å�����Ф뵶�Ǥʤ���������1�Ĥ����ʤ���硢���Υ�������̵
       ���ʬ�������ꤷ�ޤ�������ˡ����Υ�����������֥�å����ָƤ�
       �Ф���������פǤ��뤫�ɤ��������ꤷ�ޤ���
    */
    public void presolve() {
	int nonFakeArcs = 0;
	Arc lastNonFakeArc = null;

	for (Arc a : outArcs) {
	    if (!a.isFake()) {
		lastNonFakeArc = a;
		++nonFakeArcs;
	    }
	}
	if (nonFakeArcs != 1) {
	    return;
	}
	Arc arc = lastNonFakeArc;
	Block destBlock = arc.getEnd();
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
       �ֽФ륢�����פΥꥹ�Ȥ򤽤ν�λ�֥�å��μ��̻ҽ�˥����Ȥ��롣
    */
    public void sortOutArcs() {
	TreeMap<Integer, Arc> map = new TreeMap<Integer, Arc>();
	for (Arc a : outArcs) {
	    map.put(a.getEnd().id, a);
	}
	outArcs.clear();
	outArcs.addAll(map.values());
    }

    /**
       �������ν��礫��¹Բ������ޤ���

       @param arcs �������ν���
       @return ��¹Բ��
    */
    private long sumCount(final Collection<Arc> arcs) {
	long total = 0;
	for (Arc a : arcs) {
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
    private boolean validateCount(final ArrayList<Arc> arcs,
				  final LinkedList<Arc> unsolvedArcs) {
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
    public void validate(final Solver s) {
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
    private void validateInSideBlock(final Solver s, final Arc arc) {
	unsolvedOutArcs.remove(arc);
	solvedOutArcs.add(arc);
	if (inArcs.size() > 0 && unsolvedInArcs.size() == 0) {
	    // ���˼¹Բ����Ƚ�����Ƥ���
	    if (unsolvedOutArcs.size() == 1) {
		validateOutSide(s);
	    }
	} else {
	    // �ޤ��¹Բ��������
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
	Arc arc = unsolvedInArcs.remove();
	arc.setCount(count - sumCount(solvedInArcs));
	solvedInArcs.add(arc);
	// arc���Ф�֥�å��ˤĤ��Ƥν���
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
    private void validateOutSideBlock(final Solver s, final Arc arc) {
	unsolvedInArcs.remove(arc);
	solvedInArcs.add(arc);
	if (outArcs.size() > 0 && unsolvedOutArcs.size() == 0) {
	    // ���˼¹Բ����Ƚ�����Ƥ���
	    if (unsolvedInArcs.size() == 1) {
		validateInSide(s);
	    }
	} else {
	    // �ޤ��¹Բ��������
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
	Arc arc = unsolvedOutArcs.remove();
	arc.setCount(count - sumCount(solvedOutArcs));
	solvedOutArcs.add(arc);
	// arc������֥�å��ˤĤ��Ƥν���
	arc.getEnd().validateOutSideBlock(s, arc);
    }

    /**
       �֥�å������륢�������Ф륢�������줾��ˤĤ��ơ��¹Բ������
       ���ʤ�Τ�1�Ĥ����ʤ顢����μ¹Բ������ޤ���

       �֥�å��ϴ��˼¹Բ����Ƚ�����Ƥ���ɬ�פ�����ޤ���

       @param s �ե�����ե����
    */
    public void validateSides(final Solver s) {
	if (unsolvedInArcs.size() == 1) {
	    validateInSide(s);
	}
	if (unsolvedOutArcs.size() == 1) {
	    validateOutSide(s);
	}
    }
}
