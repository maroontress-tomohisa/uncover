package com.maroontress.uncover;

/**
   コマンドの終了をスローします。
*/
public class ExitException extends RuntimeException {
    /** 終了ステータスです。 */
    private int exitStatus;

    /**
       インスタンスを生成します。

       @param exitStatus 終了ステータス
    */
    public ExitException(final int exitStatus) {
        this.exitStatus = exitStatus;
    }

    /**
       終了ステータスを取得します。

       @return 終了ステータス
    */
    public final int getExitStatus() {
        return exitStatus;
    }
}
