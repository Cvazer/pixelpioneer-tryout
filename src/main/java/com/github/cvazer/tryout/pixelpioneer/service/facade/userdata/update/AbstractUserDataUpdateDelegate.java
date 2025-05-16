package com.github.cvazer.tryout.pixelpioneer.service.facade.userdata.update;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractUserDataUpdateDelegate implements UserDataUpdateDelegate {

    /**
     * <p>Returns a pair of {@link Set} sets containing added and removed elements respectfully.</p>
     * <p>For {@code then = [1, 2, 3]} and {@code now = [2, 3, 4]} this method will return a pair of {@code [4],[1]}</p>
     * <p>Note, that method uses {@link Collection#contains(Object)} method to determine the intersection
     * between two sets</p>
     * @param then a collection of elements which will act as an initial
     * @param now a collection of elements which will act as a mutated
     * @return a pair of sets containing {@code added} and {@code removed} elements in order. (Non intersection elements)
     * @param <T> any type
     */
    protected <T> Pair<Set<T>, Set<T>> elementDifference(Collection<T> then, Collection<T> now) {
        var added = new HashSet<T>();
        var removed = new HashSet<T>();

        //searching for new elements
        for (var element: now) {
            if (then.contains(element)) continue;
            added.add(element);
        }

        //searching for removed elements
        for (var element: then) {
            if (now.contains(element)) continue;
            removed.add(element);
        }

        return Pair.of(added, removed);
    }

}
