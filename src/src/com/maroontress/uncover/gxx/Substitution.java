package com.maroontress.uncover.gxx;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
   置換文字列のパースを実行します。
*/
public final class Substitution {
    /** 標準ライブラリのクラス名へのマップです。 */
    private static Map<Character, String> map;

    static {
	map = new HashMap<Character, String>();
	map.put('a', "allocator");
	map.put('b', "basic_string");
	map.put('s', "string");
	map.put('i', "istream");
	map.put('o', "ostream");
	map.put('d', "iostream");
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

       さらにそのあとテンプレート引数が続く場合があるかどうかは要調査。

       コンテキストは何文字分進むかは、リスナのどのメソッドを呼び出す
       かで変化します。

       @param context コンテキスト
       @param listener 置換文字列リスナ
    */
    public static void parse(final Context context,
			     final SubstitutionListener listener) {
	Matcher m;
	if ((m = context.matches(RE.SEQ_ID)) != null) {
	    // 36進数対応が必要
	    int id = Integer.parseInt(m.group(1)) + 1;
	    listener.substitutionFound(context.getSubstitution(id));
	    return;
	}
	if (context.startsWith('t')) {
	    listener.templeateFound(context);
	    return;
	}
	String s = map.get(context.getChar());
	if (s == null) {
	    throw new IllegalArgumentException("can't demangle: " + context);
	}
	TemplatedComponent sub = TemplatedComponent.create(s);
	listener.standardPrefixFound(context, sub);
    }
}
