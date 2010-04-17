package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

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
       最後のコンポーネントを上書きで設定します。

       @param comp コンポーネント
    */
    private void setLast(final Component comp) {
	componentList.set(componentList.size() - 1, comp);
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
	Component e = Component.create(context);
	if (context.startsWith('I')) {
	    context.addSubstitution(e);
	    e = new TemplatedComponent(e, context);
	}
	c.add(e);
	return c;
    }

    /**
       qualified-substitutionから始まるコンテキストでコンポジットを生
       成します。

       直前の文字はSでした。置換文字列が続きます。

       @param context コンテキスト
       @return コンポジット
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
					    final Composite prefix) {
		componentList.addAll(prefix.componentList);
	    }
	    public void substitutionFound(final Exportable e) {
		add(new SourceName(e.toString()));
	    }
	});
    }
}
