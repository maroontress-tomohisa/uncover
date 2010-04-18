package com.maroontress.uncover.gxx;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
   �ִ�ʸ����Υѡ�����¹Ԥ��ޤ���
*/
public final class Substitution {
    /** ɸ��饤�֥��Υ��饹�͡���ؤΥޥåפǤ��� */
    private static Map<Character, TemplateName> map;

    /** �ִ�ʸ����Υ�������ID�δ�� */
    private static final int RADIX = 36;

    /** std::string�Υե�͡���Υƥ�ץ졼�Ȱ����Ǥ��� */
    private static final String ARGS_STRING =
	"char, std::char_traits<char>, std::allocator<char>";

    /** std::(i|o|io)stream�Υե�͡���Υƥ�ץ졼�Ȱ����Ǥ��� */
    private static final String ARGS_STREAM =
	"char, std::char_traits<char>";

    static {
	map = new HashMap<Character, TemplateName>();
	map.put('a', new TemplateName("allocator"));
	map.put('b', new TemplateName("basic_string"));
	map.put('s', new TemplateName("string",
				      "basic_string", ARGS_STRING));
	map.put('i', new TemplateName("istream",
				      "basic_istream", ARGS_STREAM));
	map.put('o', new TemplateName("ostream",
				      "basic_ostream", ARGS_STREAM));
	map.put('d', new TemplateName("iostream",
				      "basic_iostream", ARGS_STREAM));
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

       ����ˤ��Τ��ȥƥ�ץ졼�Ȱ�����³�����⤢��ޤ���

       ����ƥ����Ȥϲ�ʸ��ʬ�ʤफ�ϡ��ꥹ�ʤΤɤΥ᥽�åɤ�ƤӽФ�
       �����Ѳ����ޤ���

       @param context ����ƥ�����
       @param listener �ִ�ʸ����ꥹ��
       @throws ContextException ����ƥ����Ȥ�³��substitution��ѡ���
       �Ǥ��ʤ��ä���祹�����ޤ���
    */
    public static void parse(final Context context,
			     final SubstitutionListener listener) {
	Matcher m;
	if ((m = context.matches(RE.SEQ_ID)) != null) {
	    String id = m.group(1);
	    int num = (id.isEmpty()
		       ? 0 : Integer.parseInt(id, RADIX) + 1);
	    Exportable sub = context.getSubstitution(num);
	    if (context.startsWith('I')) {
		sub = new TemplatedComponent(sub, context);
		context.addSubstitution(sub);
	    }
	    listener.substitutionFound(sub);
	    return;
	}
	if (context.startsWith('t')) {
	    listener.templeateFound(context);
	    return;
	}
	TemplateName prefix = map.get(context.getChar());
	if (prefix == null) {
	    throw new ContextException(context);
	}
	char c = context.peekChar();
	Component sub = ((c == 'C' || c == 'D')
			 ? prefix.getFullName()
			 : prefix.getName());
	Composite name;
	if (context.startsWith('I')) {
	    sub = new TemplatedComponent(sub, context);
	    name = Composite.newStandardPrefix(sub);
	    context.addSubstitution(name);
	} else {
	    name = Composite.newStandardPrefix(sub);
	}
	listener.standardPrefixFound(context, name);
    }
}
