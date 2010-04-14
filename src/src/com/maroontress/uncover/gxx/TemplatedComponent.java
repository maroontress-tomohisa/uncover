package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
   �ƥ�ץ졼�Ȱ����դ�����ݡ��ͥ�ȤǤ���
*/
public final class TemplatedComponent extends Component {
    /**
       ʸ���󥷡����󥹤���ƥ�ץ졼�Ȱ����դ�����ݡ��ͥ�Ȥ�������
       �ޤ���

       @param name ʸ���󥷡�����
       @return �ƥ�ץ졼�Ȱ����դ�����ݡ��ͥ��
    */
    public static TemplatedComponent create(final CharSequence name) {
	return new TemplatedComponent(name);
    }

    /**
       ����ƥ����Ȥ���ƥ�ץ졼�Ȱ����դ�����ݡ��ͥ�Ȥ��������ޤ���

       @param context ����ƥ�����
       @return �ƥ�ץ졼�Ȱ����դ�����ݡ��ͥ��
    */
    public static TemplatedComponent create(final Context context) {
	Matcher m;

	if ((m = context.matches(RE.NUMBER)) != null) {
	    int len = Integer.parseInt(m.group());
	    return new TemplatedComponent(context.getSequence(len));
	}
	throw new IllegalArgumentException("can't demangle: " + context);
    }

    /** ʸ���󥷡����󥹤Ǥ��� */
    private CharSequence name;

    /** �ƥ�ץ졼�Ȥΰ����Υꥹ�ȤǤ��� */
    private List<Type> argList;

    /**
       ���󥹥��󥹤��������ޤ���

       @param name ����ݡ��ͥ��̾
    */
    private TemplatedComponent(final CharSequence name) {
	this.name = name;
    }

    /**
       �ƥ�ץ졼�Ȱ������ɲä��ޤ���

       @param arg ����
    */
    private void add(final Type arg) {
	argList.add(arg);
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	b.append(name);
	if (argList == null) {
	    return;
	}
	char c = b.lastChar();
	if (!Character.isLetterOrDigit(c) && c != '_') {
	    b.append(" ");
	}
	b.append("<");
	argList.get(0).export(b);
	for (int k = 1; k < argList.size(); ++k) {
	    b.append(", ");
	    argList.get(k).export(b);
	}
	b.append(">");
    }

    /**
       ����ƥ����Ȥ���ƥ�ץ졼�Ȱ�����ѡ������ޤ����ѡ�����������
       �����Ȥ����ɲä��ޤ���

       ����ƥ����Ȥϥƥ�ץ졼�Ȱ����ν�ü�Ȥʤ�E�ޤǿʤߤޤ���

       @param context ����ƥ�����
    */
    public void parseTemplateArgument(final Context context) {
	argList = new ArrayList<Type>();
	do {
	    add(Type.create(context));
	} while (!context.startsWith('E'));
    }
}
