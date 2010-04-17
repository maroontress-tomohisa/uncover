package com.maroontress.uncover.gxx;

import java.util.HashMap;
import java.util.Map;

/**
   演算子コンポーネントです。
*/
public final class Operator extends SimpleComponent {
    /** 文字列と演算子のマップです。 */
    private static Map<String, String> map;

    static {
	map = new HashMap<String, String>();
	map.put("aN", "&=");
	map.put("aS", "=");
	map.put("aa", "&&");
	map.put("ad", "&");
	map.put("an", "&");
	map.put("cl", "()");
	map.put("cm", ",");
	map.put("co", "~");
	map.put("dV", "/=");
	map.put("da", "delete[]");
	map.put("de", "*");
	map.put("dl", "delete");
	map.put("dv", "/");
	map.put("eO", "^=");
	map.put("eo", "^");
	map.put("eq", "==");
	map.put("ge", ">=");
	map.put("gt", ">");
	map.put("ix", "[]");
	map.put("lS", "<<=");
	map.put("le", "<=");
	map.put("ls", "<<");
	map.put("lt", "<");
	map.put("mI", "-=");
	map.put("mL", "*=");
	map.put("mi", "-");
	map.put("ml", "*");
	map.put("mm", "--");
	map.put("na", "new[]");
	map.put("ne", "!=");
	map.put("ng", "-");
	map.put("nt", "!");
	map.put("nw", "new");
	map.put("oR", "|=");
	map.put("oo", "||");
	map.put("or", "|");
	map.put("pL", "+=");
	map.put("pl", "+");
	map.put("pm", "->*");
	map.put("pp", "++");
	map.put("ps", "+");
	map.put("pt", "->");
	map.put("qu", "?");
	map.put("rM", "%=");
	map.put("rS", ">>=");
	map.put("rm", "%");
	map.put("rs", ">>");
	map.put("st", "sizeof");
	map.put("sz", "sizeof");
    }

    /**
       オペレータを生成します。

       @param context コンテキスト
       @param key オペレータを表す文字列
       @return オペレータ
    */
    public static Component create(final Context context, final String key) {
	Component c;
	String name = map.get(key);
	if (name != null) {
	    c = new Operator(name);
	} else if (key.equals("cv")) {
	    Type type = Type.create(context);
	    c = new Operator(type.toString());
	} else {
	    throw new IllegalArgumentException("unknown name: " + key);
	}
	return c;
    }

    /** 演算子名です。 */
    private String name;

    /**
       インスタンスを生成します。

       @param name オペレータを表す文字列
    */
    private Operator(final String name) {
	this.name = name;
    }

    /**
       演算子の名前の前に空白が必要かどうかを取得します。

       @return 文字列 "operator" と演算子名の間にスペースが必要なら
       true
    */
    private boolean needsSpace() {
	return Character.isLowerCase(name.charAt(0));
    }

    /** {@inheritDoc} */
    @Override public void export(final Exporter b) {
	b.append("operator");
	if (needsSpace()) {
	    b.append(" ");
	}
	b.append(name);
    }
}
