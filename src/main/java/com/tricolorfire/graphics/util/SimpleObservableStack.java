package com.tricolorfire.graphics.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;

public class SimpleObservableStack<E> implements ObservableStack<E> {
	
	ObservableList<E> innerList;
	
	@Override
	public void addFirst(E e) {
		innerList.add(0,e);
	}

	@Override
	public void addLast(E e) {
		innerList.add(e);
	}

	@Override
	public boolean offerFirst(E e) {
        addFirst(e);
        return true;
	}

	@Override
	public boolean offerLast(E e) {
        addLast(e);
        return true;
	}

	@Override
	public E removeFirst() {
        E e = pollFirst();
        if (e == null)
            throw new NoSuchElementException();
        return e;
	}

	@Override
	public E removeLast() {
        E e = pollLast();
        if (e == null)
            throw new NoSuchElementException();
        return e;
	}

	@Override
	public E pollFirst() {
		if(innerList.isEmpty()) return null;
		return getFirst();
	}

	@Override
	public E pollLast() {
		if(innerList.isEmpty()) return null;
		return getLast();
	}

	@Override
	public E getFirst() {
		return innerList.get(0);
	}

	@Override
	public E getLast() {
		return innerList.get(size() - 1);
	}

	@Override
	public E peekFirst() {
		if(innerList.isEmpty()) return null;
		return getFirst();
	}

	@Override
	public E peekLast() {
		if(innerList.isEmpty()) return null;
		return getLast();
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		if(o != null) {
			int size = size();
			for(int i = 0 ; i < size ; i++) {
				if(o.equals(get(i))) {
					innerList.remove(i);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		if(o != null) {
			int size = size();
			for(int i = size - 1 ; i >= 0 ; i--) {
				if(o.equals(get(i))) {
					innerList.remove(i);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean offer(E e) {
        return offerLast(e);
	}

	@Override
	public E remove() {
		return innerList.remove(size() - 1);
	}

	@Override
	public E poll() {
		if(innerList.isEmpty()) return null;
		return remove();
	}

	@Override
	public E element() {
		return getFirst();
	}

	@Override
	public E peek() {
		return peekFirst();
	}

	@Override
	public void push(E e) {
		addFirst(e);
	}

	@Override
	public E pop() {
		return removeFirst();
	}

	@Override
	public Iterator<E> descendingIterator() {
		return innerList.iterator();
	}

	public E get(int index) {
		return innerList.get(index);
	}

	@Override
	public int size() {
		return innerList.size();
	}

	@Override
	public boolean add(E e) {
		return innerList.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return innerList.addAll(c);
	}

	@Override
	public boolean remove(Object o) {
		return remove(o);
	}

	@Override
	public boolean contains(Object o) {
		return innerList.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return innerList.iterator();
	}

	@Override
	public boolean isEmpty() {
		return innerList.isEmpty();
	}

	@Override
	public Object[] toArray() {
		return innerList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return innerList.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return innerList.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return innerList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return innerList.retainAll(c);
	}

	@Override
	public void clear() {
		innerList.clear();
	}

	@Override
	public void addListener(InvalidationListener listener) {
		innerList.addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		innerList.removeListener(listener);
	}

}
