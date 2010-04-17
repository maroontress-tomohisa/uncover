package com.maroontress.uncover.gxx;

/**
   �ե�͡����û�̷���̾�����ݻ����ޤ���
*/
public final class TemplateName {
    /** ����ݡ��ͥ�Ȥ��̾ΤǤ��� */
    private Component name;

    /** ����ݡ��ͥ�ȤΥե�͡���Ǥ��� */
    private Component fullName;

    /**
       ���󥹥��󥹤��������ޤ���

       @param name �̾�
       @param fullName �ե�͡���
       @param args ����
    */
    public TemplateName(final CharSequence name,
			final CharSequence fullName,
			final String args) {
	this.name = new SourceName(name);
	this.fullName = new TemplatedComponent(new SourceName(fullName), args);
    }

    /**
       ���󥹥��󥹤��������ޤ���

       @param name ̾��
    */
    public TemplateName(final CharSequence name) {
	this.name = new SourceName(name);
	this.fullName = this.name;
    }

    /**
       �̾ΤΥ���ݡ��ͥ�Ȥ�������ޤ���

       @return �̾ΤΥ���ݡ��ͥ��
    */
    public Component getName() {
	return name;
    }

    /**
       �ե�͡���Υ���ݡ��ͥ�Ȥ�������ޤ���

       @return �ե�͡���Υ���ݡ��ͥ��
    */
    public Component getFullName() {
	return fullName;
    }
}
