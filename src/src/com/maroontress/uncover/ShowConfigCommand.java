package com.maroontress.uncover;

import java.util.Set;
import java.util.prefs.Preferences;

/**
   show-configコマンドです。
*/
public final class ShowConfigCommand extends Command {
    /** コマンド名です。 */
    public static final String NAME = "show-config";

    /** コマンドの引数の説明です。 */
    public static final String ARGS = "";

    /** コマンドの説明です。 */
    public static final String DESC = "Show configuration.";

    /**
       configコマンドのインスタンスを生成します。

       @param props プロパティ
       @param av コマンドの引数の配列
    */
    public ShowConfigCommand(final Properties props, final String[] av) {
	super(props);

	String[] args = parseArguments(av);
	if (args.length > 0) {
	    System.err.println("too many arguments: " + args[0]);
	    usage();
	}
    }

    /** {@inheritDoc} */
    public void run() {
	Preferences prefs = Preferences.userNodeForPackage(Uncover.class);
	Set<String> keySet = ConfigKey.keySet();
	for (String key : keySet) {
	    String value = prefs.get(key, null);
	    if (value == null) {
		System.out.println(key);
	    } else {
		System.out.printf("%s %s%n", key, value);
	    }
	}
    }
}
