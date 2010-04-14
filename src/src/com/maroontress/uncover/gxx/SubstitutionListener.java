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

       直前の文字は[absiod]でした。場合によっては、さらにその後に
       I{template-args}Eが続きます。

       @param context コンテキスト
       @param sub std::の次にくる名前空間の成分
    */
    void standardPrefixFound(Context context, TemplatedComponent sub);

    /**
       置換文字列の参照を発見したことを通知します。

       直前の文字列はS[0-9A-Z]*_でした。この後Iが続くかどうかは要調査。

       @param e 置換文字列
    */
    void substitutionFound(Exportable e);
}
