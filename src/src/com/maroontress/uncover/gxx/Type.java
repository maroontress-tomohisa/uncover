package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;

/**
*/
public final class Type extends Exportable {
    /** */
    private TypeName name;

    /** */
    private List<String> qualifierList;

    /**
    */
    private Type() {
	qualifierList = new ArrayList<String>();
    }

    /**
    */
    private Type(final String name) {
	this();
	this.name = new StringTypeName(name);
    }

    /**
    */
    private Type(final Exportable name) {
	this();
	this.name = new ExportableTypeName(name);
    }

    /**
    */
    private Type(final Context context) {
	this();
	this.name = new ExportableTypeName(context);
    }

    /**
    */
    public static Type create(final Context context) {
	List<String> qualifiers = new ArrayList<String>();
	context.parseQualifier(qualifiers);
	if (!qualifiers.isEmpty()) {
	    Type type = create(context);
	    type.add(qualifiers);
	    context.addSubstitution(type);
	    return type;
	}
	char c = context.getChar();
	String name;
	if ((name = BuiltinType.getName(c)) != null) {
	    return new Type(name);
	}
	if (c == 'P') {
	    Type type = create(context);
	    type.add("*");
	    context.addSubstitution(type);
	    return type;
	}
	if (c == 'N') {
	    return new Type(Composite.newQualifiedName(context));
	}
	if (c == 'S') {
	    return new Type(context);
	}
	throw new IllegalArgumentException("can't demangle: " + context);
    }

    /**
    */
    private void add(final List<String> qualifiers) {
	qualifierList.addAll(qualifiers);
    }

    /**
    */
    private void add(final String qualifier) {
	qualifierList.add(qualifier);
    }

    /** */
    @Override public void export(final Exporter b) {
	name.exportName(b);
	for (String q : qualifierList) {
	    b.append(" ");
	    b.append(q);
	}
    }
}
