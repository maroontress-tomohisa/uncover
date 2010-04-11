package com.maroontress.uncover.gxx;

import java.util.HashMap;
import java.util.Map;

/**
*/
public final class Operator extends Component {
    /** */
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
    */
    public static String getName(final String key) {
	return map.get(key);
    }

    /**
    */
    public static Operator create(final String key) {
	return new Operator(key);
    }

    /** */
    private String name;

    /**
    */
    private Operator(final String key) {
	name = getName(key);
	if (name == null) {
	    throw new IllegalArgumentException("unknown name: " + key);
	}
    }

    /**
    */
    private boolean needsSpace() {
	return Character.isLowerCase(name.charAt(0));
    }

    /**
    */
    @Override public void export(final Exporter b) {
	b.append("operator");
	if (needsSpace()) {
	    b.append(" ");
	}
	b.append(name);
    }
}
