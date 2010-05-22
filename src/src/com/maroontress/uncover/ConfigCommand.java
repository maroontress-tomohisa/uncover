package com.maroontress.uncover;

import java.util.prefs.Preferences;

/**
   configコマンドです。
*/
public final class ConfigCommand extends Command {
    /** コマンド名です。 */
    public static final String NAME = "config";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "KEY VALUE";

    /** コマンドの説明です。 */
    public static final String DESC = "Configure global options.";

    /** 設定する項目のキーです。 */
    private String key;

    /** 設定する項目の値です。 */
    private String value;

    /**
       configコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public ConfigCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length < 2) {
	    System.err.println("too few arguments.");
	    usage();
	}
	if (args.length > 2) {
	    System.err.println("too many arguments: " + args[2]);
	    usage();
	}

	key = args[0];
	value = args[1];

	if (!ConfigKey.keySet().contains(key)) {
	    System.err.println("unknown key: " + key);
	    usage();
	}
	if (value.length() > Preferences.MAX_VALUE_LENGTH) {
	    System.err.println("too long value: " + value.length());
	    usage();
	}
    }

    /** {@inheritDoc} */
    public void run() {
	Preferences prefs = Preferences.userNodeForPackage(Uncover.class);
	prefs.put(key, value);
    }
}
