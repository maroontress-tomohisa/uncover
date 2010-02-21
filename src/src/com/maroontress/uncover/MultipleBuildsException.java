package com.maroontress.uncover;

import java.io.PrintStream;

/**
   複数のビルドが存在する場合の例外です。
*/
public class MultipleBuildsException extends Exception {
    /** リビジョンです。 */
    private String revision;

    /** ビルドの配列です。 */
    private Build[] builds;

    /** 修正方法です。 */
    private String howToFix;

    /**
       例外を生成します。

       @param revision リビジョン
       @param builds ビルドの配列
       @param howToFix 修正方法
    */
    public MultipleBuildsException(final String revision, final Build[] builds,
				   final String howToFix) {
	super();
	this.revision = revision;
	this.builds = builds;
	this.howToFix = howToFix;
    }

    /**
       例外の説明を出力します。

       @param out 出力ストリーム
    */
    public final void printDescription(final PrintStream out) {
	out.printf("revision '%s' has %d results:\n\n",
		   revision, builds.length);
	for (Build b : builds) {
	    out.printf("ID: @%s\n"
		       + "Platform: %s\n"
		       + "Timestamp: %s\n"
		       + "\n",
		       b.getID(), b.getPlatform(), b.getTimestamp());
	}
	out.printf(howToFix);
    }
}
