package org.openspaces.test.persistency.cassandra.helper;

import java.rmi.Remote;

public interface IEmbeddedCassandra extends Remote
{
    void createKeySpace(String keySpace);
    void dropKeySpace(String keySpace);
    void destroy();
}