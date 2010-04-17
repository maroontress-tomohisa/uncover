package com.maroontress.uncover.gxx;

/**
   置換文字列のパース結果を通知するインタフェイスです。
*/
public interface SubstitutionListener {
    /**
       テンプレートを発見したことを通知します。

       直前の文字はtでした。テンプレート名が続きます。場合によっては、
       さらにその後にI{template-args}Eが続きます。

       @param context コンテキスト
    */
    void templeateFound(Context context);

    /**
       標準接頭辞を発見したことを通知します。

       コンテキストは標準接頭辞の分進みます。テンプレート引数が続く場
       合は、それも含めて進みます。

       @param context コンテキスト
       @param prefix std::で始まる名前
    */
    void standardPrefixFound(Context context, Composite prefix);

    /**
       置換文字列の参照を発見したことを通知します。

       直前の文字列はS[0-9A-Z]*_でした。コンテキストは置換文字列の分進
       みます。テンプレート引数が続く場合は、それも含めて進みます。

       @param e 置換文字列
    */
    void substitutionFound(Exportable e);
}
