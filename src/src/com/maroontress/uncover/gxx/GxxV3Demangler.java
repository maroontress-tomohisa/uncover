package com.maroontress.uncover.gxx;

import com.maroontress.uncover.CxxDemangler;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

/**
   g++v3�Υǥޥ󥰥�ѡ����Ǥ���
*/
public final class GxxV3Demangler implements CxxDemangler {
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

    /** �ǥޥ󥰥�����ʸ����Ǥ��� */
    private String original;

    /**
       �ǥޥ󥰥뤹��ʸ�������ꤷ�ơ����󥹥��󥹤��������ޤ���

       @param name �ǥޥ󥰥뤹��ʸ����
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

    /** {@inheritDoc} */
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
