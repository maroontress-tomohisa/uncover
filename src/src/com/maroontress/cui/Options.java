package com.maroontress.cui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
   コマンドラインオプションの定義です。
*/
public final class Options {
    /** ヘルプメッセージのインデント幅の最小値です。 */
    private static final int MIN_INDENT_WIDTH = 2;

    /**
       オプション名とオプションのマップです。オプションはすべてこのマッ
       プに登録されます。
    */
    private Map<String, Option> nameMap;

    /**
       オプションの短縮名とオプションのマップです。短縮名をもつオプショ
       ンだけがこのマップに登録されます。
    */
    private Map<Character, Option> shortNameMap;

    /**
       コマンドラインオプションの定義を生成します。
    */
    public Options() {
	nameMap = new TreeMap<String, Option>();
	shortNameMap = new TreeMap<Character, Option>();
    }

    /**
       ヘルプメッセージのマップを取得します。マップのキーはオプション
       名、値はヘルプメッセージになります。引数ありのオプションでは、
       オプション名は「名前=引数名」になります。

       @return ヘルプメッセージのマップ
    */
    @Deprecated public Map<String, String> getHelpMap() {
	Map<String, String> map = new TreeMap<String, String>();
	Collection<Option> allOptions = nameMap.values();
	for (Option o : allOptions) {
	    map.put(o.getHelpName(), o.getHelpDesc());
	}
	return map;
    }

    /**
       オプションを追加します。

       @param option オプション
    */
    public void add(final Option option) {
	String name = option.getName();
	nameMap.put(name, option);

	Character shortName = option.getShortName();
	if (shortName != null) {
	    shortNameMap.put(shortName, option);
	}
    }

    /**
       引数なしのオプションの定義を追加します。

       @param name オプション名
       @param listener オプションリスナ
       @param help ヘルプメッセージ
    */
    public void add(final String name, final OptionListener listener,
		    final String help) {
	add(new Option(name, null, listener, null, help));
    }

    /**
       引数なしのオプションの定義を追加します。

       @param name オプション名
       @param help ヘルプメッセージ
    */
    public void add(final String name, final String help) {
	add(new Option(name, null, null, null, help));
    }

    /**
       引数ありのオプションの定義を追加します。

       @param name オプション名
       @param listener オプションリスナ
       @param argName 引数の名前
       @param help ヘルプメッセージ
    */
    public void add(final String name, final OptionListener listener,
		    final String argName, final String help) {
	add(new Option(name, null, listener, argName, help));
    }

    /**
       引数ありのオプションの定義を追加します。

       @param name オプション名
       @param argName 引数の名前
       @param help ヘルプメッセージ
    */
    public void add(final String name, final String argName,
		    final String help) {
	add(new Option(name, null, null, argName, help));
    }

    /**
       ロング形式オプションをパースします。

       @param s オプション
       @throws OptionsParsingException オプションのパースに失敗したと
       きにスローします。
    */
    private void parseOption(final String s) throws OptionsParsingException {
	String argName;
	String argValue;
	boolean hasArg;
	int n = s.indexOf('=');
	if (n < 0) {
	    argName = s.substring(2);
	    argValue = null;
	    hasArg = false;
	} else {
	    argName = s.substring(2, n);
	    argValue = s.substring(n + 1);
	    hasArg = true;
	}
	Option opt = nameMap.get(argName);
	if (opt == null || opt.hasArgument() != hasArg) {
	    throw new OptionsParsingException("invalid option: " + s);
	}
	opt.setValue(argValue);
    }

    /**
       短縮名のオプションをパースします。

       @param c オプション名
       @param av オプションの配列
       @param offset オプションのインデックス
       @return オプションとして消費した配列の要素数
       @throws OptionsParsingException オプションのパースに失敗したと
       きにスローします。
    */
    private int parseShortOption(final char c, final String[] av,
				 final int offset)
	throws OptionsParsingException {
	String s = av[offset];
	Option opt = shortNameMap.get(c);
	if (opt == null) {
	    throw new OptionsParsingException("invalid option: " + s);
	}
	if (!opt.hasArgument()) {
	    opt.setValue(null);
	    return 1;
	}
	int k = offset + 1;
	if (k >= av.length) {
	    throw new OptionsParsingException("argument not found: " + s);
	}
	opt.setValue(av[k]);
	return 2;
    }

    /**
       オプションをパースします。

       @param av オプションの配列
       @param offset オプションのインデックス
       @return オプションとして消費した配列の要素数、非オプションでは0
       @throws OptionsParsingException オプションのパースに失敗したと
       きにスローします。
    */
    private int parseOption(final String[] av,
			    final int offset) throws OptionsParsingException {
	String s = av[offset];
	if (s.startsWith("--")) {
	    parseOption(s);
	    return 1;
	}
	if (!s.startsWith("-")) {
	    return 0;
	}
	if (s.length() != 2) {
	    throw new OptionsParsingException("invalid option: " + s);
	}
	return parseShortOption(s.charAt(1), av, offset);
    }

    /**
       コマンドラインの引数をパースします。

       -または--で始まる引数をオプションとして解釈します。それ以外の引
       数が出現した段階でパースを終了します。

       @param av コマンドラインの引数の配列
       @return コマンドラインの引数のうち、最初に出現した非オプション
       の引数から最後の引数までの配列
       @throws OptionsParsingException 不正なオプションの指定
    */
    public String[] parseFore(final String[] av)
	throws OptionsParsingException {
	int k;
	int d;

	for (k = 0; k < av.length && (d = parseOption(av, k)) > 0; k += d) {
	    continue;
	}
	return Arrays.copyOfRange(av, k, av.length);
    }

    /**
       コマンドラインの引数をパースします。

       -または--で始まる引数をオプションとして解釈します。それ以外の引
       数はオプションとして解釈せず、スキップします。

       @param av コマンドラインの引数の配列
       @return オプションではない引数の配列
       @throws OptionsParsingException 不正なオプションの指定
    */
    public String[] parse(final String[] av) throws OptionsParsingException {
	ArrayList<String> args = new ArrayList<String>();
	int k;
	int d;

	for (k = 0; k < av.length && (d = parseOption(av, k)) >= 0; k += d) {
	    if (d == 0) {
		args.add(av[k]);
		++d;
	    }
	}
	return args.toArray(new String[args.size()]);
    }

    /**
       指定した名前のオプションを取得します。

       @param name オプション名
       @return オプション
    */
    private Option getOption(final String name) {
	Option option = nameMap.get(name);
	if (option == null) {
	    throw new IllegalArgumentException("not found: " + name);
	}
	return option;
    }

    /**
       オプションの引数の値を取得します。オプションをパースした後に呼
       び出す必要があります。

       オプションが指定されないか、引数なしのオプションの場合は、null
       を返します。

       @param name オプションの名前
       @return オプションの値、またはnull
    */
    public String getValue(final String name) {
	return getOption(name).getValue();
    }

    /**
       オプションの指定の有無を取得します。オプションをパースした後に
       呼び出す必要があります。

       @param name オプションの名前
       @return オプションが指定されていればtrue、そうでなければfalse
    */
    public boolean specified(final String name) {
	return getOption(name).isSpecified();
    }

    /**
       オプションの説明を取得します。

       @param indentWidth オプションの説明のインデント幅
       @return 説明
    */
    public String getHelpMessage(final int indentWidth) {
	int width = Math.max(MIN_INDENT_WIDTH, indentWidth);
	StringBuilder b = new StringBuilder("\n");
	for (int k = 0; k < width; ++k) {
	    b.append(" ");
	}
	String helpIndent = b.toString();
	String format = "%-" + (width - MIN_INDENT_WIDTH) + "s  %s\n";
	b = new StringBuilder();

	Set<String> nameSet = nameMap.keySet();
	for (String name : nameSet) {
	    Option opt = nameMap.get(name);
	    String key = opt.getHelpName();
	    String desc = opt.getHelpDesc().replace("\n", helpIndent);
	    b.append(String.format(format, key, desc));
	}
	return b.toString();
    }
}
