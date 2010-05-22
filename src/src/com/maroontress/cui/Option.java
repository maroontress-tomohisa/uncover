package com.maroontress.cui;

/**
   オプションです。
*/
public final class Option {
    /** オプション名です。 */
    private final String name;

    /** オプションの短縮名です。 */
    private final Character shortName;

    /** オプションリスナです。 */
    private final OptionListener listener;

    /** オプションの引数名です。 */
    private final String argName;

    /** ヘルプで表示する説明です。 */
    private final String helpDesc;

    /** ヘルプの名前です。 */
    private final String helpName;

    /** オプション引数の値です。 */
    private String value;

    /** オプションが設定されているかどうかです。 */
    private boolean specified;

    /**
       @param name オプション名
       @param shortName オプションの短縮名
       @param listener オプションリスナ
       @param argName オプションの引数名
       @param helpDesc ヘルプで表示する説明
    */
    public Option(final String name, final Character shortName,
		  final OptionListener listener, final String argName,
		  final String helpDesc) {
	this.name = name;
	this.shortName = shortName;
	this.listener = listener;
	this.argName = argName;
	this.helpDesc = helpDesc;

	value = null;
	specified = false;
	if (argName == null && shortName == null) {
	    helpName = String.format("--%s", name);
	} else if (argName == null) {
	    helpName = String.format("--%s, -%c", name, shortName);
	} else if (shortName == null) {
	    helpName = String.format("--%s=%s", name, argName);
	} else {
	    helpName = String.format("--%s=%s, -%c %s",
				     name, argName, shortName, argName);
	}
    }

    /**
       オプション名を取得します。

       @return オプション名
    */
    public String getName() {
	return name;
    }

    /**
       オプションの短縮名を取得します。

       @return オプションの短縮名
    */
    public Character getShortName() {
	return shortName;
    }

    /**
       引数を設定し、オプションを設定済みの状態にします。

       @param value オプション引数の値、またはnull
       @throws OptionsParsingException オプションの値が不正
    */
    public void setValue(final String value) throws OptionsParsingException {
	this.value = value;
	specified = true;
	if (listener == null) {
	    return;
	}
	listener.run(name, value);
    }

    /**
       オプションに設定されている引数を取得します。

       オプションが設定されていないか、オプションが引数をもたない場合
       はnullを返します。

       @return value オプション引数の値、またはnull
    */
    public String getValue() {
	return value;
    }

    /**
       オプションが設定されているかどうかを取得します。

       @return オプションが設定されている場合はtrue
    */
    public boolean isSpecified() {
	return specified;
    }

    /**
       オプションが引数をとるかどうかを取得します。

       @return 引数をとる場合はtrue
    */
    public boolean hasArgument() {
	return argName != null;
    }

    /**
       ヘルプで表示する名前を取得します。

       @return ヘルプの名前
    */
    public String getHelpName() {
	return helpName;
    }

    /**
       ヘルプで表示する説明を取得します。

       @return ヘルプの説明
    */
    public String getHelpDesc() {
	return helpDesc;
    }
}
