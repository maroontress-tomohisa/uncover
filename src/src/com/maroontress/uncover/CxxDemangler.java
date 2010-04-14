package com.maroontress.uncover;

/**
   C++のシンボルをデマングルした結果から値を取得するインタフェイスです。
*/
public interface CxxDemangler {
    /**
       シンボルの名前を取得します。

       デマングルに失敗した場合は、デマングル前の文字列を返します。

       @return シンボルの名前
    */
    String getName();
}
