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

       ����ƥ����Ȥ�ɸ����Ƭ����ʬ�ʤߤޤ����ƥ�ץ졼�Ȱ�����³����
       ��ϡ������ޤ�ƿʤߤޤ���

       @param context ����ƥ�����
       @param prefix std::�ǻϤޤ�̾��
    */
    void standardPrefixFound(Context context, Composite prefix);

    /**
       �ִ�ʸ����λ��Ȥ�ȯ���������Ȥ����Τ��ޤ���

       ľ����ʸ�����S[0-9A-Z]*_�Ǥ���������ƥ����Ȥ��ִ�ʸ�����ʬ��
       �ߤޤ����ƥ�ץ졼�Ȱ�����³�����ϡ������ޤ�ƿʤߤޤ���

       @param e �ִ�ʸ����
    */
    void substitutionFound(Exportable e);
}
