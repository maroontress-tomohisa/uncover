package com.maroontress.uncover.gxx;

import com.maroontress.uncover.CxxDemangler;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
   g++v3のデマングルパーサです。
*/
public final class GxxV3Demangler implements CxxDemangler {
    /** */
    private static Logger logger;

    static {
	String name = GxxV3Demangler.class.getPackage().getName();
	logger = Logger.getLogger(name);
	logger.setUseParentHandlers(false);
	logger.setLevel(Level.OFF);
    }

    /** パーサのコンテキストです。 */
    private Context context;

    /** 修飾子のセットです。 */
    private Set<String> qualifiers;

    /** 複合名です。 */
    private Composite composite;

    /**
       グローバルコンストラクタ、デストラクタの場合はそれを表す文字列、
       そうでなければnullになります。
    */
    private CharSequence global;

    /**
       デマングルする文字列を指定して、インスタンスを生成します。

       @param name デマングルする文字列
    */
    public GxxV3Demangler(final String name) {
	qualifiers = new HashSet<String>();
	composite = null;
	global = null;
	context = new Context(name);
	try {
	    parseMangledName();
	} catch (ContextException e) {
	    if (logger.isLoggable(Level.INFO)) {
		logger.log(Level.INFO, getClass().getName(), e);
	    }
	    composite = null;
	}
    }

    /**
       マングルされた文字列をパースします。

       _Zで始まるか、_GLOBAL_[._$][DI]__Zで始まる文字列をマングルされ
       た文字列としてパースします。マングルされた文字列でない場合は何
       もしません。

       _GLOBAL_[._$][DI]_で始まるが、そのあとは普通のシンボルの場合は
       どうなるのか、要調査。
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
       トップレベルから複合名を生成します。

       直前には_Zがありました。
    */
    private void parseEncoding() {
	if (context.startsWith('G')
	    || context.startsWith('T')) {
	    throw new ContextException("special name not implemented.");
	} else if (context.startsWith('Z')) {
	    throw new ContextException("local name not implemented.");
	} else if (context.startsWith('N')) {
	    context.parseQualifier(qualifiers);
	    composite = Composite.newQualifiedName(context);
	} else if (context.startsWith('S')) {
	    composite = Composite.newQualifiedSubstitution(context);
	} else {
	    composite = Composite.newUnqualifiedName(context);
	}
    }

    /** {@inheritDoc} */
    public String getName() {
	if (composite == null) {
	    return context.getSource();
	}
	Exporter b = new Exporter();
	if (global != null) {
	    b.append(global);
	}
	composite.export(b);
	return b.toString();
    }
}
