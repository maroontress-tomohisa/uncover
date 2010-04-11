package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
*/
public final class Composite extends Exportable {
    /** */
    private List<Component> componentList;

    /**
    */
    private Composite() {
	componentList = new ArrayList<Component>();
    }

    /** */
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
    */
    private void add(final Component comp) {
	componentList.add(comp);
    }

    /**
       unqualified-name����Ϥޤ륳��ƥ����Ȥ�Composite���������ޤ���

       �ȥåץ�٥뤫��ƤӽФ���ޤ���
    */
    public static Composite newUnqualifiedName(final Context context) {
	Composite c = new Composite();
	c.add(Component.create(context));
	return c;
    }

    /**
       qualified-substitution����Ϥޤ륳��ƥ����Ȥ�Composite��������
       �ޤ���

       ľ����ʸ����S�Ǥ�����substition��³���ޤ���

       �ȥåץ�٥뤫��ƤӽФ����Τǡ��ѡ�����Υƥ�ץ졼�Ȱ�����
       ̵�뤷�ޤ���
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
       prefix����Ϥޤ륳��ƥ����Ȥ�Composite���������ޤ���

       ľ���ˤ�N {qualifiers}*������ޤ�����{prefix}��ʣ��³����E�ǽ�
       ü���ޤ���

       �ȥåץ�٥뤫��ƤӽФ���ޤ���
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
       standard-prefix����Ϥޤ륳��ƥ����Ȥ�Composite���������ޤ���

       ľ���ˤ�S[absiod]{template-args}*������ޤ�����

       �ȥåץ�٥뤫��ϸƤӽФ���ޤ���
    */
    public static Composite newStandardPrefix(final Component prefix) {
	Composite c = new Composite();
	c.add(Component.STD);
	c.add(prefix);
	return c;
    }

    /**
       template����Ϥޤ륳��ƥ����Ȥ�Composite���������ޤ���

       ľ���ˤ�St������ޤ�����{unqualified-name} {template-args}*��³
       ���ޤ���

       �ȥåץ�٥뤫��ϸƤӽФ���ޤ���
    */
    public static Composite newSubstitutionTemplate(final Context context) {
	Composite c = new Composite();
	c.parseSubstitutionTemplate(context);
	return c;
    }

    /**
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
		add(Component.create(e));
	    }
	});
    }
}
