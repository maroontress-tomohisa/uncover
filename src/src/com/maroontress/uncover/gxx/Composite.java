package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
   �͡��ॹ�ڡ�����ɽ�����̾���Ǥ���
*/
public final class Composite extends Exportable {
    /** */
    private List<Component> componentList;

    /**
       ���󥹥��󥹤��������ޤ���
    */
    private Composite() {
	componentList = new ArrayList<Component>();
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	Component c;

	c = componentList.get(0);
	c.export(b);
	b.setComponent(c);
	for (int k = 1; k < componentList.size(); ++k) {
	    b.append("::");
	    c = componentList.get(k);
	    c.export(b);
	    b.setComponent(c);
	}
    }

    /**
       ����ݡ��ͥ�Ȥ��ɲä��ޤ���

       @param comp ����ݡ��ͥ��
    */
    private void add(final Component comp) {
	componentList.add(comp);
    }

    /**
       �Ǹ�Υ���ݡ��ͥ�Ȥ��񤭤����ꤷ�ޤ���

       @param comp ����ݡ��ͥ��
    */
    private void setLast(final Component comp) {
	componentList.set(componentList.size() - 1, comp);
    }

    /**
       unqualified-name����Ϥޤ륳��ƥ����Ȥǥ���ݥ��åȤ���������
       ����

       �ȥåץ�٥뤫��ƤӽФ���ޤ���

       @param context ����ƥ�����
       @return ����ݥ��å�
    */
    public static Composite newUnqualifiedName(final Context context) {
	Composite c = new Composite();
	Component e = Component.create(context);
	if (context.startsWith('I')) {
	    context.addSubstitution(e);
	    e = new TemplatedComponent(e, context);
	}
	c.add(e);
	return c;
    }

    /**
       qualified-substitution����Ϥޤ륳��ƥ����Ȥǥ���ݥ��åȤ���
       �����ޤ���

       ľ����ʸ����S�Ǥ������ִ�ʸ����³���ޤ���

       @param context ����ƥ�����
       @return ����ݥ��å�
    */
    public static Composite newQualifiedSubstitution(final Context context) {
	final Composite c = new Composite();
	Substitution.parse(context, new SubstitutionListener() {
	    public void templeateFound(final Context context) {
		Component e = Component.create(context);
		c.add(Component.STD);
		c.add(e);
		if (context.startsWith('I')) {
		    context.addSubstitution(c);
		    e = new TemplatedComponent(e, context);
		    c.setLast(e);
		}
	    }
	    public void standardPrefixFound(final Context context,
					    final Composite sub) {
		// �ȥåץ�٥�ξ��Ϥ����ˤϤ��ʤ�
		throw new RuntimeException("invalid substitution");
	    }
	    public void substitutionFound(final Exportable e) {
		// �ȥåץ�٥�ξ��Ϥ�������������㳰
	    }
	});
	context.addSubstitution(c);
	return c;
    }

    /**
       prefix����Ϥޤ륳��ƥ����Ȥǥ���ݥ��åȤ��������ޤ���

       ľ���ˤ�N {qualifiers}*������ޤ�����{prefix}��ʣ��³����E�ǽ�
       ü���ޤ���

       �ȥåץ�٥뤫��ƤӽФ���ޤ���

       @param context ����ƥ�����
       @return ����ݥ��å�
    */
    public static Composite newQualifiedName(final Context context) {
	Composite c = new Composite();
	do {
	    if (context.startsWith('S')) {
		c.parseSubstitution(context);
	    } else {
		Component e = Component.create(context);
		c.add(e);
		if (context.peekChar() != 'E') {
		    context.addSubstitution(c);
		}
		if (context.startsWith('I')) {
		    e = new TemplatedComponent(e, context);
		    c.setLast(e);
		}
	    }
	} while (!context.startsWith('E'));
	return c;
    }

    /**
       standard-prefix����Ϥޤ륳��ƥ����Ȥǥ���ݥ��åȤ��������ޤ���

       ľ���ˤ�S[absiod]{template-args}*������ޤ�����

       �ȥåץ�٥뤫��ϸƤӽФ���ޤ���

       @param prefix std::��³������ݡ��ͥ��
       @return ����ݥ��å�
    */
    public static Composite newStandardPrefix(final Component prefix) {
	Composite c = new Composite();
	c.add(Component.STD);
	c.add(prefix);
	return c;
    }

    /**
       template����Ϥޤ륳��ƥ����Ȥǥ���ݥ��åȤ��������ޤ���

       ľ���ˤ�St������ޤ�����{unqualified-name} {template-args}*��³
       ���ޤ���

       ����ƥ����Ȥϥƥ�ץ졼�ȡʰ�����ޤ��ʬ�����ʤߤޤ���

       �ȥåץ�٥뤫��ϸƤӽФ���ޤ���

       @param context ����ƥ�����
       @return ����ݥ��å�
    */
    public static Composite newSubstitutionTemplate(final Context context) {
	Composite c = new Composite();
	c.parseSubstitutionTemplate(context);
	return c;
    }

    /**
       ɸ��ƥ�ץ졼�Ȥ�ѡ������ޤ���

       ����ƥ����Ȥϥƥ�ץ졼�ȡʰ�����ޤ��ʬ�����ʤߤޤ���

       @param context ����ƥ�����
    */
    private void parseSubstitutionTemplate(final Context context) {
	Matcher m;

	if ((m = context.matches(RE.NUMBER)) == null) {
	    throw new IllegalArgumentException("can't demangle: " + context);
	}
	int len = Integer.parseInt(m.group());
	Component sub = new SourceName(context.getSequence(len));
	if (!context.startsWith('I')) {
	    add(Component.STD);
	    add(sub);
	    return;
	}
	context.addSubstitution(newStandardPrefix(sub));

	sub = new TemplatedComponent(sub, context);
	add(Component.STD);
	add(sub);
	context.addSubstitution(this);
    }

    /**
       �ִ�ʸ�����ѡ������ޤ���

       ����ƥ����Ȥ�ľ����ʸ����S�Ǥ�����Substitution.parse()����Ѥ�
       �ƥ���ݥ��åȤ�������ޤ���

       @param context ����ƥ�����
    */
    private void parseSubstitution(final Context context) {
	Substitution.parse(context, new SubstitutionListener() {
	    public void templeateFound(final Context context) {
		parseSubstitutionTemplate(context);
	    }
	    public void standardPrefixFound(final Context context,
					    final Composite prefix) {
		componentList.addAll(prefix.componentList);
	    }
	    public void substitutionFound(final Exportable e) {
		add(new SourceName(e.toString()));
	    }
	});
    }
}
