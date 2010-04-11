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
       unqualified-nameから始まるコンテキストでCompositeを生成します。

       トップレベルから呼び出されます。
    */
    public static Composite newUnqualifiedName(final Context context) {
	Composite c = new Composite();
	c.add(Component.create(context));
	return c;
    }

    /**
       qualified-substitutionから始まるコンテキストでCompositeを生成し
       ます。

       直前の文字はSでした。substitionが続きます。

       トップレベルから呼び出されるので、パース後のテンプレート引数は
       無視します。
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
		// トップレベルの場合はここにはこない
		throw new RuntimeException("invalid substitution");
	    }
	    public void substitutionFound(final Exportable e) {
		// トップレベルの場合はここに来る前に例外
	    }
	});
	context.addSubstitution(c);
	return c;
    }

    /**
       prefixから始まるコンテキストでCompositeを生成します。

       直前にはN {qualifiers}*がありました。{prefix}が複数続き、Eで終
       端します。

       トップレベルから呼び出されます。
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
       standard-prefixから始まるコンテキストでCompositeを生成します。

       直前にはS[absiod]{template-args}*がありました。

       トップレベルからは呼び出されません。
    */
    public static Composite newStandardPrefix(final Component prefix) {
	Composite c = new Composite();
	c.add(Component.STD);
	c.add(prefix);
	return c;
    }

    /**
       templateから始まるコンテキストでCompositeを生成します。

       直前にはStがありました。{unqualified-name} {template-args}*が続
       きます。

       トップレベルからは呼び出されません。
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
