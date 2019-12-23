package org.springframework.data.gigaspaces.querydsl;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.client.CustomChangeOperation;
import com.querydsl.core.types.Path;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import static org.springframework.data.gigaspaces.querydsl.GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString;

/**
 * <p>Wrapper around native ChangeSet that supports type safe path.</p>
 * <p>Use this class to perform change operations in Querydsl style.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * repository.change(...query..., changeSet().increment(person.age, 5)) will change age field of found Persons
 * </pre></blockquote>
 *
 * @author Oleksiy_Dyagilev
 * @see GigaspacesQueryDslPredicateExecutor
 */
public class QChangeSet {
    private ChangeSet delegate = new ChangeSet();

    public static QChangeSet changeSet() {
        return new QChangeSet();
    }

    public QChangeSet custom(CustomChangeOperation changeOperation) {
        delegate.custom(changeOperation);
        return this;
    }

    public QChangeSet set(Path<?> path, Serializable value) {
        delegate.set(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), value);
        return this;
    }

    public QChangeSet unset(Path<?> path) {
        delegate.unset(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path));
        return this;
    }

    public QChangeSet increment(Path<Byte> path, byte delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), delta);
        return this;
    }

    public QChangeSet increment(Path<Short> path, short delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), delta);
        return this;
    }

    public QChangeSet increment(Path<Integer> path, int delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), delta);
        return this;
    }

    public QChangeSet increment(Path<Long> path, long delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), delta);
        return this;
    }

    public QChangeSet increment(Path<Float> path, float delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), delta);
        return this;
    }

    public QChangeSet increment(Path<Double> path, double delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), delta);
        return this;
    }

    public QChangeSet increment(Path<Number> path, Number delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), delta);
        return this;
    }

    public QChangeSet decrement(Path<Byte> path, byte delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), -delta);
        return this;
    }

    public QChangeSet decrement(Path<Short> path, short delta) {
        delegate.increment(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), -delta);
        return this;
    }

    public QChangeSet decrement(Path<Integer> path, int delta) {
        return this.increment(path, (int) (-delta));
    }

    public QChangeSet decrement(Path<Long> path, long delta) {
        return this.increment(path, -delta);
    }

    public QChangeSet decrement(Path<Float> path, float delta) {
        return this.increment(path, -delta);
    }

    public QChangeSet decrement(Path<Double> path, double delta) {
        return this.increment(path, -delta);
    }

    public QChangeSet addToCollection(Path<? extends Collection> path, Serializable newItem) {
        delegate.addToCollection(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), newItem);
        return this;
    }

    public QChangeSet addAllToCollection(Path<? extends Collection> path, Serializable... newItems) {
        delegate.addAllToCollection(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), newItems);
        return this;
    }

    public QChangeSet addAllToCollection(Path<? extends Collection> path, Collection<? extends Serializable> newItems) {
        delegate.addAllToCollection(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), newItems);
        return this;
    }

    public QChangeSet removeFromCollection(Path<? extends Collection> path, Serializable itemToRemove) {
        delegate.removeFromCollection(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), itemToRemove);
        return this;
    }

    public QChangeSet putInMap(Path<? extends Map> path, Serializable key, Serializable value) {
        delegate.putInMap(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), key, value);
        return this;
    }

    public QChangeSet removeFromMap(Path<? extends Map> path, Serializable key) {
        delegate.removeFromMap(GigaspacesQueryDslUtils.convertPathToGigaspacesFieldString(path), key);
        return this;
    }

    public QChangeSet lease(long lease) {
        delegate.lease(lease);
        return this;
    }

    public ChangeSet getNativeChangeSet() {
        return delegate;
    }

}