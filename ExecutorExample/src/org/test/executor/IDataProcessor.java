package org.test.executor;

import com.gigaspaces.async.AsyncFuture;

public interface IDataProcessor {
    Object processData(Object data);
    AsyncFuture<Object>  asyncProcessData(Object data);
}