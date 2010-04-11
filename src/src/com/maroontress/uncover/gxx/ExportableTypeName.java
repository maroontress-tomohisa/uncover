package com.maroontress.uncover.gxx;

/**
*/
public final class ExportableTypeName implements TypeName {
    /** */
    private Exportable name;

    /**
    */
    public ExportableTypeName(final Exportable name) {
	this.name = name;
    }

    /**
    */
    public ExportableTypeName(final Context context) {
	Substitution.parse(context, new SubstitutionListener() {
	    public void templeateFound(final Context context) {
		name = Composite.newSubstitutionTemplate(context);
	    }
	    public void standardPrefixFound(final Context context,
					    final TemplatedComponent sub) {
		if (context.startsWith('I')) {
		    sub.parseTemplateArgument(context);
		}
		name = Composite.newStandardPrefix(sub);
	    }
	    public void substitutionFound(final Exportable e) {
		name = e;
	    }
	});
    }

    /**
    */
    public void exportName(final Exporter b) {
	name.export(b);
    }
}
