package org.springframework.data.xap.querydsl;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.client.CustomChangeOperation;
import com.mysema.query.types.Path;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import static org.springframework.data.xap.querydsl.Utils.convertPathToXapFieldString;

/**
 * Wrapper around native ChangeSet that supports type safe path.
 *
 * @author Oleksiy_Dyagilev
 */
public class QChangeSet {

    private ChangeSet delegate = new ChangeSet();

    public ChangeSet custom(CustomChangeOperation changeOperation) {
        return delegate.custom(changeOperation);
    }

    public ChangeSet set(Path<?> path, Serializable value) {
        return delegate.set(convertPathToXapFieldString(path), value);
    }

    public ChangeSet unset(Path<?> path) {
        return delegate.unset(convertPathToXapFieldString(path));
    }

    public ChangeSet increment(Path<Byte> path, byte delta) {
        return delegate.increment(convertPathToXapFieldString(path), delta);
    }

    public ChangeSet increment(Path<Short> path, short delta) {
        return delegate.increment(convertPathToXapFieldString(path), delta);
    }

    public ChangeSet increment(Path<Integer> path, int delta) {
        return delegate.increment(convertPathToXapFieldString(path), delta);
    }

    public ChangeSet increment(Path<Long> path, long delta) {
        return delegate.increment(convertPathToXapFieldString(path), delta);
    }

    public ChangeSet increment(Path<Float> path, float delta) {
        return delegate.increment(convertPathToXapFieldString(path), delta);
    }

    public ChangeSet increment(Path<Double> path, double delta) {
        return delegate.increment(convertPathToXapFieldString(path), delta);
    }

    public ChangeSet increment(Path<Number> path, Number delta) {
        return delegate.increment(convertPathToXapFieldString(path), delta);
    }

    public ChangeSet decrement(Path<Byte> path, byte delta) {
        return delegate.increment(convertPathToXapFieldString(path), -delta);
    }

    public ChangeSet decrement(Path<Short> path, short delta) {
        return delegate.increment(convertPathToXapFieldString(path), -delta);
    }

    public ChangeSet decrement(Path<Integer> path, int delta) {
        return this.increment(path, (int) (-delta));
    }

    public ChangeSet decrement(Path<Long> path, long delta) {
        return this.increment(path, -delta);
    }

    public ChangeSet decrement(Path<Float> path, float delta) {
        return this.increment(path, -delta);
    }

    public ChangeSet decrement(Path<Double> path, double delta) {
        return this.increment(path, -delta);
    }

    public ChangeSet addToCollection(Path<? extends Collection> path, Serializable newItem) {
        return delegate.addToCollection(convertPathToXapFieldString(path), newItem);
    }

    public ChangeSet addAllToCollection(Path<? extends Collection> path, Serializable... newItems) {
        return delegate.addAllToCollection(convertPathToXapFieldString(path), newItems);
    }

    public ChangeSet addAllToCollection(Path<? extends Collection> path, Collection<? extends Serializable> newItems) {
        return delegate.addAllToCollection(convertPathToXapFieldString(path), newItems);
    }

    public ChangeSet removeFromCollection(Path<? extends Collection> path, Serializable itemToRemove) {
        return delegate.removeFromCollection(convertPathToXapFieldString(path), itemToRemove);
    }

    public ChangeSet putInMap(Path<? extends Map> path, Serializable key, Serializable value) {
        return delegate.putInMap(convertPathToXapFieldString(path), key, value);
    }

    public ChangeSet removeFromMap(Path<? extends Map> path, Serializable key) {
        return delegate.removeFromMap(convertPathToXapFieldString(path), key);
    }

    public ChangeSet lease(long lease) {
        return delegate.lease(lease);
    }

    public ChangeSet getNativeChangeSet(){
        return delegate;
    }


}
