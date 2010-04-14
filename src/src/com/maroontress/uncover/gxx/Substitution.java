package com.maroontress.uncover.gxx;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
   �ִ�ʸ����Υѡ�����¹Ԥ��ޤ���
*/
public final class Substitution {
    /** ɸ��饤�֥��Υ��饹̾�ؤΥޥåפǤ��� */
    private static Map<Character, String> map;

    static {
	map = new HashMap<Character, String>();
	map.put('a', "allocator");
	map.put('b', "basic_string");
	map.put('s', "string");
	map.put('i', "istream");
	map.put('o', "ostream");
	map.put('d', "iostream");
    }

    /**
       ���󥹥ȥ饯���ϻ��ѤǤ��ޤ���
    */
    private Substitution() {
    }

    /**
       �ִ�ʸ�����ѡ������ơ���̤�ꥹ�ʤ����Τ��ޤ���

       ľ����ʸ����S�Ǥ��������θ��3�Ĥβ�ǽ��������ޤ�������ܤ���
       ��ʸ����λ��Ȥ�³�����Ǥ���[0-9A-Z]*_��³���ޤ�������ܤϥ�
       ��ץ졼�Ȥ�³�����Ǥ���t��³���ޤ��������ܤϤ���ʳ��ξ��ǡ�
       [absiod]��³���ޤ���

       ����ˤ��Τ��ȥƥ�ץ졼�Ȱ�����³����礬���뤫�ɤ�������Ĵ����

       ����ƥ����Ȥϲ�ʸ��ʬ�ʤफ�ϡ��ꥹ�ʤΤɤΥ᥽�åɤ�ƤӽФ�
       �����Ѳ����ޤ���

       @param context ����ƥ�����
       @param listener �ִ�ʸ����ꥹ��
    */
    public static void parse(final Context context,
			     final SubstitutionListener listener) {
	Matcher m;
	if ((m = context.matches(RE.SEQ_ID)) != null) {
	    // 36�ʿ��б���ɬ��
	    int id = Integer.parseInt(m.group(1)) + 1;
	    listener.substitutionFound(context.getSubstitution(id));
	    return;
	}
	if (context.startsWith('t')) {
	    listener.templeateFound(context);
	    return;
	}
	String s = map.get(context.getChar());
	if (s == null) {
	    throw new IllegalArgumentException("can't demangle: " + context);
	}
	TemplatedComponent sub = TemplatedComponent.create(s);
	listener.standardPrefixFound(context, sub);
    }
}
