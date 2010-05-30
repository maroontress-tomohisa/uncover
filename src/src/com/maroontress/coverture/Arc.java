package com.maroontress.coverture;

/**
   �ؿ�����դΥ������Ǥ����������ˤϸ��������ꡢ���ϥ֥�å�����Ф�
   ��λ�֥�å�������ޤ���
*/
public final class Arc {

    /**
       ���ѥ˥󥰥ĥ꡼�������륢������ɽ���ե饰�Ǥ���
    */
    private static final int FLAG_ON_TREE = 0x1;

    /**
       ���Υ�������ɽ���ե饰�Ǥ������Υ������ϡ��㳰��longjmp()�ˤ��
       �ơ����ߤδؿ�����ȴ������䡢exit()�ʤɤΤ褦�����ʤ��ؿ�
       �θƤӽФ��η�ϩ��ɽ���ޤ���
    */
    private static final int FLAG_FAKE = 0x2;

    /**
       ��������ʬ�����ʤ��ä���ϩ�Ǥ��뤳�Ȥ�ɽ���ե饰�Ǥ���
    */
    private static final int FLAG_FALL_THROUGH = 0x4;

    /** ���ϥ֥�å��Ǥ��� */
    private Block start;

    /** ��λ�֥�å��Ǥ��� */
    private Block end;

    /**
       �������Υե饰�Ǥ���FLAG_ON_TREE, FLAG_FAKE, FLAG_FALL_THROUGH
       �������¤ˤʤ�ޤ���
    */
    private int flags;

    /**
       Arc is for a function that abnormally returns: ���Υ�������
       �ؿ��θƤӽФ��������ʤ����Ȥ򼨤��ޤ���
    */
    private boolean callNonReturn;

    /**
       Arc is for catch/setjmp: ���Υ������ιԤ��褬catch�ޤ���
       setjmp()�Ǥ��뤳�Ȥ򼨤��ޤ���
    */
    private boolean nonLocalReturn;

    /**
       Arc is an unconditional branch.
    */
    private boolean unconditional;

    /** �������μ¹Բ���Ǥ��� */
    private long count;

    /**
       ���������������ޤ��������������󥹥��󥹤ϳ��ϥ֥�å��ΡֽФ�
       �������ס���λ�֥�å��Ρ����륢�����פ��ɲä���ޤ���

       @param start ���ϥ֥�å�
       @param end ��λ�֥�å�
       @param flags �ե饰
    */
    public Arc(final Block start, final Block end, final int flags) {
	this.start = start;
	this.end = end;
	this.flags = flags;
	this.count = 0;
	start.addOutArc(this);
	end.addInArc(this);
	if (isFake()) {
	    if (start.getId() != 0) {
		/*
		  Exceptional exit from this function, the source
		  block must be a call.
		*/
		start.setCallSite(true);
		callNonReturn = true;
	    } else {
		/*
		  Non-local return from a callee of this function. The
		  destination block is a catch or setjmp.
		*/
		end.setNonLocalReturn(true);
		nonLocalReturn = true;
	    }
	}
    }

    /**
       �����������ѥ˥󥰥ĥ꡼�������뤫�ɤ����������ޤ���

       @return ���ѥ˥󥰥ĥ꡼�����������true�������Ǥʤ����
       false
    */
    public boolean isOnTree() {
	return (flags & FLAG_ON_TREE) != 0;
    }

    /**
       �����������Υ��������ɤ����������ޤ���

       @return ���Υ������ξ���true�������Ǥʤ����false
    */
    public boolean isFake() {
	return (flags & FLAG_FAKE) != 0;
    }

    /**
       ��������ʬ�����ʤ��ä���ϩ�Ǥ��뤫�ɤ����������ޤ���

       @return ��������ʬ�����ʤ��ä���ϩ�ξ���true�������Ǥʤ����
       false
    */
    public boolean isFallThrough() {
	return (flags & FLAG_FALL_THROUGH) != 0;
    }

    /**
       ��������exit()�ʤɤΤ褦�����ʤ��ؿ��θƤӽФ��Ǥ��뤫�ɤ���
       �������ޤ���

       @return ���ʤ��ؿ��θƤӽФ��ξ���true�������Ǥʤ����false
    */
    public boolean isCallNonReturn() {
	return callNonReturn;
    }

    /**
       �������ιԤ��褬catch�ޤ���setjmp()�Ǥ��뤫�ɤ�����������ޤ���

       @return �������ιԤ��褬catch�ޤ���setjmp()�Ǥ������true����
       ���Ǥʤ����false
    */
    public boolean isNonLocalReturn() {
	return nonLocalReturn;
    }

    /**
       �������γ��ϥ֥�å���������ޤ���

       @return ���ϥ֥�å�
    */
    public Block getStart() {
	return start;
    }

    /**
       �������ν�λ�֥�å���������ޤ���

       @return ��λ�֥�å�
    */
    public Block getEnd() {
	return end;
    }

    /**
       �¹Բ�����ɲä��ޤ���

       @param delta �ɲä���¹Բ��
    */
    public void addCount(final long delta) {
	count += delta;
    }

    /**
       �¹Բ�������ꤷ�ޤ���

       @param count �¹Բ��
    */
    public void setCount(final long count) {
	this.count = count;
    }

    /**
       �¹Բ����������ޤ���

       @return �¹Բ��
    */
    public long getCount() {
	return count;
    }

    /**
       ̵���ʬ�����ɤ��������ꤷ�ޤ���

       @param b ̵���ʬ���ʤ�true�������Ǥʤ����false
    */
    public void setUnconditional(final boolean b) {
	unconditional = b;
    }

    /**
       ̵���ʬ�����ɤ�����������ޤ���

       @return ̵���ʬ���ʤ�true�������Ǥʤ����false
    */
    public boolean isUnconditional() {
	return unconditional;
    }
}
