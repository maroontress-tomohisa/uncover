package com.maroontress.gcovparser;

/**
   �ؿ�����դΥ��å��Ȥʤ륢�����Υǥե���ȼ����Ǥ���
*/
public final class DefaultArc extends AbstractArc<DefaultBlock, DefaultArc> {

    /**
       ���������������ޤ��������������󥹥��󥹤ϳ��ϥ֥�å��ΡֽФ�
       �������ס���λ�֥�å��Ρ����륢�����פ��ɲä���ޤ���

       @param start ���ϥ֥�å�
       @param end ��λ�֥�å�
       @param flags �ե饰
    */
    public DefaultArc(final DefaultBlock start, final DefaultBlock end,
		      final int flags) {
	super(start, end, flags);
    }
}
