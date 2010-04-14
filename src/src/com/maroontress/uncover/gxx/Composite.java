package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
   ネームスペースで表される名前です。
*/
public final class Composite extends Exportable {
    /** */
    private List<Component> componentList;

    /**
       インスタンスを生成します。
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
       コンポーネントを追加します。

       @param comp コンポーネント
    */
    private void add(final Component comp) {
	componentList.add(comp);
    }

    /**
       unqualified-nameから始まるコンテキストでコンポジットを生成しま
       す。

       トップレベルから呼び出されます。

       @param context コンテキスト
       @return コンポジット
    */
    public static Composite newUnqualifiedName(final Context context) {
	Composite c = new Composite();
	c.add(Component.create(context));
	return c;
    }

    /**
       qualified-substitutionから始まるコンテキストでコンポジットを生
       成します。

       直前の文字はSでした。置換文字列が続きます。

       トップレベルから呼び出されるので、パース後のテンプレート引数は
       無視します。

       @param context コンテキスト
       @return コンポジット
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
       prefixから始まるコンテキストでコンポジットを生成します。

       直前にはN {qualifiers}*がありました。{prefix}が複数続き、Eで終
       端します。

       トップレベルから呼び出されます。

       @param context コンテキスト
       @return コンポジット
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
       standard-prefixから始まるコンテキストでコンポジットを生成します。

       直前にはS[absiod]{template-args}*がありました。

       トップレベルからは呼び出されません。

       @param prefix std::に続くコンポーネント
       @return コンポジット
    */
    public static Composite newStandardPrefix(final Component prefix) {
	Composite c = new Composite();
	c.add(Component.STD);
	c.add(prefix);
	return c;
    }

    /**
       templateから始まるコンテキストでコンポジットを生成します。

       直前にはStがありました。{unqualified-name} {template-args}*が続
       きます。

       コンテキストはテンプレート（引数を含む）分だけ進みます。

       トップレベルからは呼び出されません。

       @param context コンテキスト
       @return コンポジット
    */
    public static Composite newSubstitutionTemplate(final Context context) {
	Composite c = new Composite();
	c.parseSubstitutionTemplate(context);
	return c;
    }

    /**
       標準テンプレートをパースします。

       コンテキストはテンプレート（引数を含む）分だけ進みます。

       @param context コンテキスト
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
       置換文字列をパースします。

       コンテキストの直前の文字はSでした。Substitution.parse()を使用し
       てコンポジットを作成します。

       @param context コンテキスト
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
