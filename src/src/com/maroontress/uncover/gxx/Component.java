package com.maroontress.uncover.gxx;

import java.util.regex.Matcher;

/**
*/
public abstract class Component extends Exportable {
    /** */
    public static final Component STD = new ConstantComponent("std");

    /** */
    public static final Component ANONYMOUS_NS
        = new ConstantComponent("(anonymous namespace)");

    /** */
    public static final Component CTOR = new Constructor();

    /** */
    public static final Component DTOR = new Destructor();

    /**
    */
    protected Component() {
    }

    /**
    */
    public static Component create(final Exportable e) {
	return TemplatedComponent.create(e.toString());
    }

    /**
    */
    public static Component create(final Context context) {
	Matcher m;

	if ((m = context.matches(RE.NUMBER)) != null) {
	    int len = Integer.parseInt(m.group());
	    CharSequence sub = context.getSequence(len);
	    if (RE.ANONYMOUS_NS.matcher(sub).lookingAt()) {
		return Component.ANONYMOUS_NS;
	    }
	    return TemplatedComponent.create(sub);
	}
	if ((m = context.matches(RE.CTOR)) != null) {
	    return CTOR;
	}
	if ((m = context.matches(RE.DTOR)) != null) {
	    return DTOR;
	}
	if ((m = context.matches(RE.OPERATOR)) != null) {
	    return Operator.create(m.group());
	}
	throw new IllegalArgumentException("can't demangle: " + context);
    }
}
