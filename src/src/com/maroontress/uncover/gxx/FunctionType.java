package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
   �ؿ��η��Ǥ���
*/
public final class FunctionType extends Type {
    /** ����ͤη��Ǥ��� */
    private Type returnType;

    /** �����η��Υꥹ�ȤǤ��� */
    private List<Type> argTypeList;

    /**
       ��������Ǥη��ȿ��ǥ��󥹥��󥹤��������ޤ���

       @param context ����ƥ�����
    */
    public FunctionType(final Context context) {
	argTypeList = new ArrayList<Type>();
	returnType = Type.create(context);
	while (!context.startsWith('E')) {
	    argTypeList.add(Type.create(context));
	}
    }

    /**
       �����η�����Ϥ��ޤ���

       @param b �������ݡ���
    */
    private void exportArgs(final Exporter b) {
	int n = argTypeList.size();
	if (n == 0
	    || (n == 1 && argTypeList.get(0).toString().equals("void"))) {
	    return;
	}
	argTypeList.get(0).export(b);
	for (int k = 1; k < n; ++k) {
	    b.append(", ");
	    argTypeList.get(k).export(b);
	}
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	returnType.export(b);
	b.append(" (");
	if (hasQualifiers()) {
	    exportQualifiers(b);
	}
	b.append(")(");
	exportArgs(b);
	b.append(")");
    }
}
