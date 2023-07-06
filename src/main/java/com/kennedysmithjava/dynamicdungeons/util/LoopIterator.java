package com.kennedysmithjava.dynamicdungeons.util;


import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LoopIterator<E> implements Iterator<E> {
    private final List<E> list;
    private ListIterator<E> iterator;

    public LoopIterator(List<E> list) {
        this.list = list;
        this.iterator = list.listIterator();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public E next() {
        if (!iterator.hasNext()) {
            iterator = list.listIterator();
        }
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}
