package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

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
       unqualified-name����Ϥޤ륳��ƥ����Ȥǥ���ݥ��åȤ���������
       ����

       �ȥåץ�٥뤫��ƤӽФ���ޤ���

       @param context ����ƥ�����
       @return ����ݥ��å�
    */
    public static Composite newUnqualifiedName(final Context context) {
	Composite c = new Composite();
	c.add(Component.create(context));
	return c;
    }

    /**
       qualified-substitution����Ϥޤ륳��ƥ����Ȥǥ���ݥ��åȤ���
       �����ޤ���

       ľ����ʸ����S�Ǥ������ִ�ʸ����³���ޤ���

       �ȥåץ�٥뤫��ƤӽФ����Τǡ��ѡ�����Υƥ�ץ졼�Ȱ�����
       ̵�뤷�ޤ���

       @param context ����ƥ�����
       @return ����ݥ��å�
    */
    public static Composite newQualifiedSubstitution(final Context context) {
	final Composite c = new Composite();
	Substitution.parse(context, new SubstitutionListener() {
	    public void templeateFound(final Context context) {
		c.add(Component.STD);
		c.add(Component.create(context));
	    }
	    public void standardPrefixFound(final Context context,
					    final TemplatedComponent sub) {
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
		c.add(Component.create(context));
		context.addSubstitution(c);
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
	TemplatedComponent sub = TemplatedComponent.create(context);
	add(Component.STD);
	add(sub);
	context.addSubstitution(this);
	if (!context.startsWith('I')) {
	    throw new IllegalArgumentException("can't demangle: " + context);
	}
	sub.parseTemplateArgument(context);
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
					    final TemplatedComponent sub) {
		add(Component.STD);
		add(sub);
	    }
	    public void substitutionFound(final Exportable e) {
		add(TemplatedComponent.create(e.toString()));
	    }
	});
    }
}
