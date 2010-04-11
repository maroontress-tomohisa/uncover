package com.maroontress.uncover.gxx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
*/
public final class TemplatedComponent extends Component {
    /**
    */
    public static TemplatedComponent create(final CharSequence name) {
	return new TemplatedComponent(name);
    }

    /**
    */
    public static TemplatedComponent create(final Context context) {
	Matcher m;

	if ((m = context.matches(RE.NUMBER)) != null) {
	    int len = Integer.parseInt(m.group());
	    return new TemplatedComponent(context.getSequence(len));
	}
	throw new IllegalArgumentException("can't demangle: " + context);
    }

    /** */
    private CharSequence name;

    /** */
    private List<Type> argList;

    /**
    */
    private TemplatedComponent(final CharSequence name) {
	this.name = name;
    }

    /**
    */
    private void add(final Type arg) {
	argList.add(arg);
    }

    /**
    */
    @Override public void export(final Exporter b) {
	b.append(name);
	if (argList == null) {
	    return;
	}
	char c = b.lastChar();
	if (!Character.isLetterOrDigit(c) && c != '_') {
	    b.append(" ");
	}
	b.append("<");
	argList.get(0).export(b);
	for (int k = 1; k < argList.size(); ++k) {
	    b.append(", ");
	    argList.get(k).export(b);
	}
	b.append(">");
    }

    /**
    */
    public void parseTemplateArgument(final Context context) {
	argList = new ArrayList<Type>();
	do {
	    add(Type.create(context));
	} while (!context.startsWith('E'));
    }
}
