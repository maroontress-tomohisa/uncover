package com.maroontress.uncover.sqlite;

import com.maroontress.uncover.BuildSource;

/**
   クエリ結果からビルドを生成するためのビルドソースです。
*/
public final class ResultSetBuildSource extends Row implements BuildSource {
    /** ビルドIDです。 */
    private String id;

    /** リビジョンです。 */
    private String revision;

    /** タイムスタンプです。 */
    private String timestamp;

    /** プラットフォームです。 */
    private String platform;

    /**
       インスタンスを生成します。
    */
    public ResultSetBuildSource() {
    }

    /** {@inheritDoc} */
    public String getID() {
	return id;
    }

    /** {@inheritDoc} */
    public String getRevision() {
	return revision;
    }

    /** {@inheritDoc} */
    public String getTimestamp() {
	return timestamp;
    }

    /** {@inheritDoc} */
    public String getPlatform() {
	return platform;
    }
}
