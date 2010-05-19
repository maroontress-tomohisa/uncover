package com.maroontress.uncover.junit;

import com.maroontress.uncover.BuildSource;

public class TestBuildSource implements BuildSource {
    private String id;
    private String revision;
    private String timestamp;
    private String platform;

    public TestBuildSource(final String id,
			   final String revision,
			   final String timestamp,
			   final String platform) {
	this.id = id;
	this.revision = revision;
	this.timestamp = timestamp;
	this.platform = platform;
    }
    public String getID() {
	return id;
    }
    public String getRevision() {
	return revision;
    }
    public String getTimestamp() {
	return timestamp;
    }
    public String getPlatform() {
	return platform;
    }
}
