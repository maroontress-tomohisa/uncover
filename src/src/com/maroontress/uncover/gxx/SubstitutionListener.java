package com.maroontress.uncover.gxx;

/**
   �ִ�ʸ����Υѡ�����̤����Τ��륤�󥿥ե������Ǥ���
*/
public interface SubstitutionListener {
    /**
       �ƥ�ץ졼�Ȥ�ȯ���������Ȥ����Τ��ޤ���

       ľ����ʸ����t�Ǥ������ƥ�ץ졼��̾��³���ޤ������ˤ�äƤϡ�
       ����ˤ��θ��I{template-args}E��³���ޤ���

       @param context ����ƥ�����
    */
    void templeateFound(Context context);

    /**
       ɸ����Ƭ����ȯ���������Ȥ����Τ��ޤ���

       ľ����ʸ����[absiod]�Ǥ��������ˤ�äƤϡ�����ˤ��θ��
       I{template-args}E��³���ޤ���

       @param context ����ƥ�����
       @param sub std::�μ��ˤ���̾�����֤���ʬ
    */
    void standardPrefixFound(Context context, TemplatedComponent sub);

    /**
       �ִ�ʸ����λ��Ȥ�ȯ���������Ȥ����Τ��ޤ���

       ľ����ʸ�����S[0-9A-Z]*_�Ǥ��������θ�I��³�����ɤ�������Ĵ����

       @param e �ִ�ʸ����
    */
    void substitutionFound(Exportable e);
}
