package com.maroontress.uncover.gxx;

/**
*/
public interface SubstitutionListener {
    /**
    */
    void templeateFound(Context context);

    /**
    */
    void standardPrefixFound(Context context, TemplatedComponent sub);

    /**
    */
    void substitutionFound(Exportable e);
}
