package com.maroontress.uncover.gxx;

import com.maroontress.uncover.CxxDemangler;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

/**
*/
public final class GxxV3Demangler implements CxxDemangler {
    /** */
    private Context context;

    /** */
    private Set<String> qualifiers;

    /** */
    private Composite composite;

    /** */
    private CharSequence global;

    /** */
    private String original;

    /**
    */
    public GxxV3Demangler(final String name) {
	qualifiers = new HashSet<String>();
	composite = null;
	global = null;
	original = name;
	context = new Context(name);
	try {
	    parseMangledName();
	} catch (RuntimeException e) {
	    e.printStackTrace();
	    composite = null;
	}
    }

    /**
    */
    private void parseMangledName() {
	Matcher m;

	if ((m = context.matches(RE.GLOBAL_CTOR_DTOR)) != null) {
	    global = String.format("global %s keyed to ",
				   (m.group(1).equals("I")
				    ? "constructors"
				    : "destructors"));
	}
	if ((m = context.matches(RE.ENCODING)) != null) {
	    parseEncoding();
	    return;
	}
    }

    /**
    */
    private void parseEncoding() {
	if (context.startsWith('G')
	    || context.startsWith('T')) {
	    throw new RuntimeException("special name not implemented.");
	} else if (context.startsWith('Z')) {
	    throw new RuntimeException("local name not implemented.");
	} else if (context.startsWith('N')) {
	    context.parseQualifier(qualifiers);
	    composite = Composite.newQualifiedName(context);
	} else if (context.startsWith('S')) {
	    composite = Composite.newQualifiedSubstitution(context);
	} else {
	    composite = Composite.newUnqualifiedName(context);
	}
    }

    /**
    */
    public String getName() {
	if (composite == null) {
	    return original;
	}
	Exporter b = new Exporter();
	if (global != null) {
	    b.append(global);
	}
	composite.export(b);
	return b.toString();
    }
}
