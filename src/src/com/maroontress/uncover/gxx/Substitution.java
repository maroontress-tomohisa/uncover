package com.maroontress.uncover.gxx;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
   置換文字列のパースを実行します。
*/
public final class Substitution {
    /** 標準ライブラリのクラスネームへのマップです。 */
    private static Map<Character, TemplateName> map;

    /** 置換文字列のシーケンスIDの基数 */
    private static final int RADIX = 36;

    /** std::stringのフルネームのテンプレート引数です。 */
    private static final String ARGS_STRING =
	"char, std::char_traits<char>, std::allocator<char>";

    /** std::(i|o|io)streamのフルネームのテンプレート引数です。 */
    private static final String ARGS_STREAM =
	"char, std::char_traits<char>";

    static {
	map = new HashMap<Character, TemplateName>();
	map.put('a', new TemplateName("allocator"));
	map.put('b', new TemplateName("basic_string"));
	map.put('s', new TemplateName("string",
				      "basic_string", ARGS_STRING));
	map.put('i', new TemplateName("istream",
				      "basic_istream", ARGS_STREAM));
	map.put('o', new TemplateName("ostream",
				      "basic_ostream", ARGS_STREAM));
	map.put('d', new TemplateName("iostream",
				      "basic_iostream", ARGS_STREAM));
    }

    /**
       コンストラクタは使用できません。
    */
    private Substitution() {
    }

    /**
       置換文字列をパースして、結果をリスナに通知します。

       直前の文字はSでした。この後は3つの可能性があります。一つ目は置
       換文字列の参照が続く場合です。[0-9A-Z]*_が続きます。二つ目はテ
       ンプレートが続く場合です。tが続きます。三つ目はそれ以外の場合で、
       [absiod]が続きます。

       さらにそのあとテンプレート引数が続く場合もあります。

       コンテキストは何文字分進むかは、リスナのどのメソッドを呼び出す
       かで変化します。

       @param context コンテキスト
       @param listener 置換文字列リスナ
       @throws ContextException コンテキストに続くsubstitutionをパース
       できなかった場合スローします。
    */
    public static void parse(final Context context,
			     final SubstitutionListener listener) {
	Matcher m;
	if ((m = context.matches(RE.SEQ_ID)) != null) {
	    String id = m.group(1);
	    int num = (id.isEmpty()
		       ? 0 : Integer.parseInt(id, RADIX) + 1);
	    Exportable sub = context.getSubstitution(num);
	    if (context.startsWith('I')) {
		sub = new TemplatedComponent(sub, context);
		context.addSubstitution(sub);
	    }
	    listener.substitutionFound(sub);
	    return;
	}
	if (context.startsWith('t')) {
	    listener.templeateFound(context);
	    return;
	}
	TemplateName prefix = map.get(context.getChar());
	if (prefix == null) {
	    throw new ContextException(context);
	}
	char c = context.peekChar();
	Component sub = ((c == 'C' || c == 'D')
			 ? prefix.getFullName()
			 : prefix.getName());
	Composite name;
	if (context.startsWith('I')) {
	    sub = new TemplatedComponent(sub, context);
	    name = Composite.newStandardPrefix(sub);
	    context.addSubstitution(name);
	} else {
	    name = Composite.newStandardPrefix(sub);
	}
	listener.standardPrefixFound(context, name);
    }
}
