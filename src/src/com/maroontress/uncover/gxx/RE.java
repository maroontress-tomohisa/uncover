package com.maroontress.uncover.gxx;

import java.util.regex.Pattern;

/**
*/
public final class RE {
    /** */
    public static final Pattern ENCODING;

    /** */
    public static final Pattern GLOBAL_CTOR_DTOR;

    /** */
    public static final Pattern NUMBER;

    /** */
    public static final Pattern CTOR;

    /** */
    public static final Pattern DTOR;

    /** */
    public static final Pattern OPERATOR;

    /** */
    public static final Pattern ANONYMOUS_NS;

    /** */
    public static final Pattern SEQ_ID;

    static {
	NUMBER = Pattern.compile("[1-9][0-9]*");
	CTOR = Pattern.compile("C[123]");
	DTOR = Pattern.compile("D[012]");
	OPERATOR = Pattern.compile("[a-z][A-Za-z]");
	ANONYMOUS_NS = Pattern.compile("_GLOBAL_[._$]N");
	ENCODING = Pattern.compile("_Z");
	GLOBAL_CTOR_DTOR = Pattern.compile("_GLOBAL_[._$]([DI])_");
	SEQ_ID = Pattern.compile("([0-9A-Z]+)_");
    }

    /**
    */
    private RE() {
    }
}
