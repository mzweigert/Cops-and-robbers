package com.copsandrobber.algorithm.common;

import com.graphrodite.model.Vertex;

import java.util.*;

public abstract class ConfigurationsWrapper<CONFIGURATION_TYPE, VERTEX_TYPE> {

    protected Set<CONFIGURATION_TYPE> marked;
    protected Set<CONFIGURATION_TYPE> unmarked;

    protected ConfigurationsWrapper(Set<Vertex<VERTEX_TYPE>> vertices) {
        this.marked = new HashSet<>();
        this.unmarked = new HashSet<>();
        initFrom(vertices);
    }

    /**
     * Method return marked configurations
     * @return Set&lt;CONFIGURATION_TYPE&gt; marked configurations
     */
    public Set<CONFIGURATION_TYPE> getMarked() {
        return marked;
    }

    /**
     * Method return unmarked configurations
     * @return Set&lt;CONFIGURATION_TYPE&gt; unmarked configurations
     */
    public Set<CONFIGURATION_TYPE> getUnmarked() {
        return unmarked;
    }

    /**
     * Method add configuration to marked configurations
     * @param element configuration to add
     */
    public void addToMarked(CONFIGURATION_TYPE element) {
        marked.add(element);
    }

    /**
     * Method return size of marked configurations
     * @return marked configurations size
     */
    public int getMarkedSize() {
        return marked.size();
    }

    /**
     * Method return size of unmarked configurations
     * @return unmarked configurations size
     */
    public int getUnmarkedSize() {
        return unmarked.size();
    }

    /**
     * Method return iterator of unmarked configurations
     * @return Iterator&lt;CONFIGURATION_TYPE&gt; unmarked configurations iterator
     */
    public Iterator<CONFIGURATION_TYPE> getUnmarkedIterator() {
        return unmarked.iterator();
    }

    /**
     * Method answer for question if unmarked configurations list is not empty.
     * @return true if unmarked configurations is not empty, false otherwise.
     */
    public boolean isUnmarkedNotEmpty() {
        return !isUnmarkedEmpty();
    }

    /**
     * Method answer for question if unmarked configurations list is empty.
     * @return true if unmarked configurations is empty, false otherwise.
     */
    public boolean isUnmarkedEmpty() {
        return unmarked.isEmpty();
    }

    /**
     * Method create and add to marked configurations which ending game of cops and robbers
     * based on given as param vertices.
     * @param vertices vertices on which marked configurations will be based.
     */
    protected abstract void initFrom(Set<Vertex<VERTEX_TYPE>> vertices);

    /**
     * Method answer for question if given as param configuration can be moved from unmarked to marked configurations.
     * This should usually happen if given configuration representing round before any marked configuration round.
     * @param currentConfiguration considered configuration to mark.
     * @return true if given configuration can be marked, false otherwise.
     */
    public abstract boolean canMarkConfiguration(CONFIGURATION_TYPE currentConfiguration);

}
