package com.maroontress.uncover.gxx;

import com.maroontress.uncover.CxxDemangler;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
   g++v3�Υǥޥ󥰥�ѡ����Ǥ���
*/
public final class GxxV3Demangler implements CxxDemangler {
    /** */
    private static Logger logger;

    /** �ѡ����Υ���ƥ����ȤǤ��� */
    private Context context;

    /** �����ҤΥ��åȤǤ��� */
    private Set<String> qualifiers;

    /** ʣ��̾�Ǥ��� */
    private Composite composite;

    /**
       �����Х륳�󥹥ȥ饯�����ǥ��ȥ饯���ξ��Ϥ����ɽ��ʸ����
       �����Ǥʤ����null�ˤʤ�ޤ���
    */
    private CharSequence global;

    static {
	initializeLogging();
    }

    /**
       ���Υѥå������Υ��󥰤ε�ǽ���������ޤ���
    */
    private static void initializeLogging() {
	String name = GxxV3Demangler.class.getPackage().getName();
	logger = Logger.getLogger(name);
	logger.setUseParentHandlers(false);
	logger.setLevel(Level.OFF);

	String log = System.getProperty(name + ".log");
	if (log == null) {
	    return;
	}
	logger.addHandler(new ConsoleHandler());
	logger.setLevel(Level.parse(log));
    }

    /**
       G++V3�ǥޥ󥰥�ѡ�����¹Ԥ��ޤ���

       @param av ���ޥ�ɥ饤�󥪥ץ����
    */
    public static void main(final String[] av) {
	if (av.length == 0) {
	    return;
	}
	GxxV3Demangler d = new GxxV3Demangler(av[0]);
	System.out.println(d.getName());
    }

    /**
       �ǥޥ󥰥뤹��ʸ�������ꤷ�ơ����󥹥��󥹤��������ޤ���

       @param name �ǥޥ󥰥뤹��ʸ����
    */
    public GxxV3Demangler(final String name) {
	logger.info(name);
	qualifiers = new HashSet<String>();
	composite = null;
	global = null;
	context = new Context(name);
	try {
	    parseMangledName();
	} catch (ContextException e) {
	    logger.log(Level.INFO, name, e);
	    composite = null;
	} catch (RuntimeException e) {
	    logger.log(Level.WARNING, name, e);
	    throw e;
	}
    }

    /**
       �ޥ󥰥뤵�줿ʸ�����ѡ������ޤ���

       _Z�ǻϤޤ뤫��_GLOBAL_[._$][DI]__Z�ǻϤޤ�ʸ�����ޥ󥰥뤵��
       ��ʸ����Ȥ��ƥѡ������ޤ����ޥ󥰥뤵�줿ʸ����Ǥʤ����ϲ�
       �⤷�ޤ���

       _GLOBAL_[._$][DI]_�ǻϤޤ뤬�����Τ��Ȥ����̤Υ���ܥ�ξ���
       �ɤ��ʤ�Τ�����Ĵ����
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
       �ȥåץ�٥뤫��ʣ��̾���������ޤ���

       ľ���ˤ�_Z������ޤ�����
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
