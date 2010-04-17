package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
*/
public final class ListArguments extends TemplateArguments {
    /** �ƥ�ץ졼�Ȥΰ����Υꥹ�ȤǤ��� */
    private List<Type> argList;

    /**
       ���󥹥��󥹤��������ޤ���

       ����ƥ����Ȥ���ƥ�ץ졼�Ȱ�����ѡ������ޤ����ѡ�����������
       �����Ȥ����ɲä��ޤ���

       ����ƥ����Ȥϥƥ�ץ졼�Ȱ����ν�ü�Ȥʤ�E�ޤǿʤߤޤ���

       @param context ����ƥ�����
    */
    public ListArguments(final Context context) {
	argList = new ArrayList<Type>();
	do {
	    argList.add(Type.create(context));
	} while (!context.startsWith('E'));
    }

    /** {@inheritDoc} */
    @Override public void exportArgs(final Exporter b) {
	argList.get(0).export(b);
	for (int k = 1; k < argList.size(); ++k) {
	    b.append(", ");
	    argList.get(k).export(b);
	}
    }
}
