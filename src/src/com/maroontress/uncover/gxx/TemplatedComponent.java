package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
   テンプレート引数付きコンポーネントです。
*/
public final class TemplatedComponent extends Component {
    /**
       文字列シーケンスからテンプレート引数付きコンポーネントを生成し
       ます。

       @param name 文字列シーケンス
       @return テンプレート引数付きコンポーネント
    */
    public static TemplatedComponent create(final CharSequence name) {
	return new TemplatedComponent(name);
    }

    /**
       コンテキストからテンプレート引数付きコンポーネントを生成します。

       @param context コンテキスト
       @return テンプレート引数付きコンポーネント
    */
    public static TemplatedComponent create(final Context context) {
	Matcher m;

	if ((m = context.matches(RE.NUMBER)) != null) {
	    int len = Integer.parseInt(m.group());
	    return new TemplatedComponent(context.getSequence(len));
	}
	throw new IllegalArgumentException("can't demangle: " + context);
    }

    /** 文字列シーケンスです。 */
    private CharSequence name;

    /** テンプレートの引数のリストです。 */
    private List<Type> argList;

    /**
       インスタンスを生成します。

       @param name コンポーネント名
    */
    private TemplatedComponent(final CharSequence name) {
	this.name = name;
    }

    /**
       テンプレート引数を追加します。

       @param arg 引数
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
       コンテキストからテンプレート引数をパースします。パースした型を
       引数として追加します。

       コンテキストはテンプレート引数の終端となるEまで進みます。

       @param context コンテキスト
    */
    public void parseTemplateArgument(final Context context) {
	argList = new ArrayList<Type>();
	do {
	    add(Type.create(context));
	} while (!context.startsWith('E'));
    }
}
