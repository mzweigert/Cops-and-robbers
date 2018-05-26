package com.copsandrobber.algorithm.common;

import com.graphrodite.model.Vertex;

import java.util.*;

public abstract class ConfigurationsWrapper<CONFIGURATION_TYPE, VERTEX_TYPE> {

    protected Set<CONFIGURATION_TYPE> marked;
    protected List<CONFIGURATION_TYPE> unmarked;

    protected ConfigurationsWrapper() {
        this.marked = new LinkedHashSet<>();
        this.unmarked = new LinkedList<>();
    }

    public Set<CONFIGURATION_TYPE> getMarked() {
        return marked;
    }

    public List<CONFIGURATION_TYPE> getUnmarked() {
        return unmarked;
    }

    public void addToMarked(CONFIGURATION_TYPE element) {
        marked.add(element);
    }

    public int getMarkedSize() {
        return marked.size();
    }

    public int getUnmarkedSize() {
        return unmarked.size();
    }

    public Iterator<CONFIGURATION_TYPE> getUnmarkedIterator() {
        return unmarked.iterator();
    }

    public boolean isUnmarkedNotEmpty() {
        return !isUnmarkedEmpty();
    }

    public boolean isUnmarkedEmpty() {
        return unmarked.isEmpty();
    }

    protected abstract void initFrom(Set<Vertex<VERTEX_TYPE>> vertices);

    public abstract boolean canMarkConfiguration(CONFIGURATION_TYPE currentConfiguration);

}
