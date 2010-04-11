package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
*/
public final class Context {
    /** */
    private static Map<Character, String> qualifierMap;

    static {
	qualifierMap = new HashMap<Character, String>();
	qualifierMap.put('r', "restrict");
	qualifierMap.put('K', "const");
	qualifierMap.put('V', "volatile");
    }

    /** */
    private CharSequence sequence;

    /** */
    private List<Exportable> substitution;

    /**
    */
    public Context(final String sequence) {
	this.sequence = sequence;
	substitution = new ArrayList<Exportable>();
    }

    /**
    */
    public String toString() {
	return sequence.toString();
    }

    /**
    */
    public void addSubstitution(final Exportable e) {
	//System.err.printf("[%d] %s%n", substitution.size(), e.toString());
	substitution.add(e.clone());
    }

    /**
    */
    public Exportable getSubstitution(final int k) {
	return substitution.get(k);
    }

    /**
    */
    private void advanceSequence(final int k) {
	sequence = sequence.subSequence(k, sequence.length());
    }

    /**
    */
    public void parseQualifier(final Collection<String> qualifiers) {
	int k;
	String q;

	for (k = 0; (q = qualifierMap.get(sequence.charAt(k))) != null; ++k) {
	    qualifiers.add(q);
	}
	advanceSequence(k);
    }

    /**
    */
    public CharSequence getSequence(final int len) {
	CharSequence seq;
	try {
	    seq = sequence.subSequence(0, len);
	} catch (IndexOutOfBoundsException e) {
	    throw new IllegalArgumentException("can't demangle: " + this, e);
	}
	advanceSequence(len);
	return seq;
    }

    /**
    */
    public Matcher matches(final Pattern pattern) {
	Matcher m = pattern.matcher(sequence);
	if (!m.lookingAt()) {
	    return null;
	}
	advanceSequence(m.end());
	return m;
    }

    /**
    */
    public boolean startsWith(final char c) {
	if (sequence.charAt(0) != c) {
	    return false;
	}
	advanceSequence(1);
	return true;
    }

    /**
    */
    public char getChar() {
	char c = sequence.charAt(0);
	advanceSequence(1);
	return c;
    }
}
