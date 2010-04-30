package com.maroontress.uncover;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
   コマンドのバンクです。
*/
public final class CommandBank {
    /** コマンド名とコマンドクラスのマップです。 */
    private Map<String, Class<? extends Command>> classMap;

    /**
       インスタンスを生成します。
    */
    public CommandBank() {
	classMap = new HashMap<String, Class<? extends Command>>();
    }

    /**
       コマンドクラスを追加します。

       @param clazz コマンドクラス
    */
    public void addCommandClass(final Class<? extends Command> clazz) {
	try {
	    Field nameField = clazz.getField("NAME");
	    String name = (String) nameField.get(null);
	    classMap.put(name, clazz);
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (NoSuchFieldException e) {
	    e.printStackTrace();
	}
    }

    /**
       指定した名前のコマンドを生成します。

       @param name コマンド名
       @param props プロパティ
       @param args コマンドの引数
       @return コマンド
    */
    public Command createCommand(final String name, final Properties props,
				 final String[] args) {
	Class<? extends Command> clazz = classMap.get(name);
	if (clazz == null) {
	    return null;
	}

	Command command = null;
	try {
	    command = clazz.getConstructor(Properties.class, String[].class)
		.newInstance(props, args);
	} catch (InvocationTargetException e) {
	    Throwable cause = e.getCause();
	    if (cause != null && cause instanceof RuntimeException) {
		throw (RuntimeException) cause;
	    }
	    e.printStackTrace();
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    e.printStackTrace();
	} catch (InstantiationException e) {
	    e.printStackTrace();
	}
	return command;
    }

    /**
       コマンド名の配列を取得します。

       @return コマンド名の配列
    */
    private String[] getCommandNames() {
	String[] names
	    = classMap.keySet().toArray(new String[classMap.size()]);
	Arrays.sort(names);
	return names;
    }

    /**
       コマンドの説明を取得します。

       @param name コマンド名
       @return コマンドの説明
    */
    private String getCommandDesc(final String name) {
	String desc = "";
	Class<? extends Command> clazz = classMap.get(name);
	try {
	    Field descField = clazz.getField("DESC");
	    desc = (String) descField.get(null);
	} catch (IllegalAccessException e) {
	    e.printStackTrace();
	} catch (NoSuchFieldException e) {
	    e.printStackTrace();
	}
	return desc;
    }

    /**
       ヘルプメッセージを取得します。

       @param width インデント幅
       @return ヘルプメッセージ
    */
    public String getHelpMessage(final int width) {
	String[] commands = getCommandNames();
	String m = "";
	for (String s : commands) {
	    String desc = getCommandDesc(s);
	    m += String.format("%-" + width + "s%s\n", s, desc);
	}
	return m;
    }
}
