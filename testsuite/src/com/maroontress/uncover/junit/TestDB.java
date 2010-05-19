package com.maroontress.uncover.junit;

import com.maroontress.uncover.Build;
import com.maroontress.uncover.CommitSource;
import com.maroontress.uncover.DB;
import com.maroontress.uncover.DBException;
import com.maroontress.uncover.Graph;
import com.maroontress.uncover.Revision;

public class TestDB implements DB {
    public void close() throws DBException {
    }
    public void initialize() throws DBException {
    }
    public void commit(final CommitSource source) throws DBException {
    }
    public String[] getProjectNames() throws DBException {
	return null;
    }
    public Build[] getBuilds(final String projectName,
                             final String revision) throws DBException {
	return null;
    }
    public Build getBuild(final String projectName,
                          final String id) throws DBException {
	return null;
    }
    public Revision getRevision(final String id) throws DBException {
	return null;
    }
    public String[] getRevisionNames(final String projectName)
	throws DBException {
	return null;
    }
    public void deleteBuild(final String projectName,
                            final String id) throws DBException {
    }
    public void deleteBuilds(final String projectName,
                             final String revision) throws DBException {
    }
    public void deleteProject(final String projectName) throws DBException {
    }
    public Graph getGraph(final String projectName, final String id,
			  final String function, final String gcnoFile)
	throws DBException {
	return null;
    }
}
