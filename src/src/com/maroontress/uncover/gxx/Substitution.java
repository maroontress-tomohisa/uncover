package com.maroontress.uncover.gxx;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
*/
public final class Substitution {
    /** */
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
    */
    private Substitution() {
    }

    /**
    */
    public static void parse(final Context context,
			     final SubstitutionListener listener) {
	Matcher m;
	if ((m = context.matches(RE.SEQ_ID)) != null) {
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
