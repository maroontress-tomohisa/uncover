package com.maroontress.gcovparser;

/**
   �ؿ�����դΥ������Υǥե���ȼ����Ǥ����������ˤϸ��������ꡢ����
   �֥�å�����Фƽ�λ�֥�å�������ޤ���
*/
public final class DefaultArc extends AbstractArc {

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
    private DefaultBlock start;

    /** ��λ�֥�å��Ǥ��� */
    private DefaultBlock end;

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
    public DefaultArc(final DefaultBlock start, final DefaultBlock end,
		      final int flags) {
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

    /** {@inheritDoc} */
    @Override public boolean isOnTree() {
	return (flags & FLAG_ON_TREE) != 0;
    }

    /** {@inheritDoc} */
    @Override public boolean isFake() {
	return (flags & FLAG_FAKE) != 0;
    }

    /** {@inheritDoc} */
    @Override public boolean isFallThrough() {
	return (flags & FLAG_FALL_THROUGH) != 0;
    }

    /** {@inheritDoc} */
    @Override public boolean isCallNonReturn() {
	return callNonReturn;
    }

    /** {@inheritDoc} */
    @Override public boolean isNonLocalReturn() {
	return nonLocalReturn;
    }

    /**
       �������γ��ϥ֥�å���������ޤ���

       @return ���ϥ֥�å�
    */
    public DefaultBlock getStart() {
	return start;
    }

    /** {@inheritDoc} */
    @Override public DefaultBlock getEnd() {
	return end;
    }

    /** {@inheritDoc} */
    @Override public void addCount(final long delta) {
	count += delta;
    }

    /**
       �¹Բ�������ꤷ�ޤ���

       @param count �¹Բ��
    */
    public void setCount(final long count) {
	this.count = count;
    }

    /** {@inheritDoc} */
    @Override public long getCount() {
	return count;
    }

    /**
       ̵���ʬ�����ɤ��������ꤷ�ޤ���

       @param b ̵���ʬ���ʤ�true�������Ǥʤ����false
    */
    public void setUnconditional(final boolean b) {
	unconditional = b;
    }

    /** {@inheritDoc} */
    public boolean isUnconditional() {
	return unconditional;
    }
}
