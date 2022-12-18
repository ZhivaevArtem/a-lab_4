package root.util;

import java.util.*;
import java.util.function.UnaryOperator;

public class CyclicList<T> implements List<T> {
    private final List<T> list;

    public CyclicList(List<T> list) {
        this.list = list;
    }

    private int getIndex(int index) {
        return ((index % list.size()) + list.size()) % list.size();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator<T> iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    public boolean add(T t) {
        return list.add(t);
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(getIndex(index), c);
    }

    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public void replaceAll(UnaryOperator<T> operator) {
        list.replaceAll(operator);
    }

    public void sort(Comparator<? super T> c) {
        list.sort(c);
    }

    public void clear() {
        list.clear();
    }

    public boolean equals(Object o) {
        return list.equals(o);
    }

    public int hashCode() {
        return list.hashCode();
    }

    public T get(int index) {
        return list.get(getIndex(index));
    }

    public T set(int index, T element) {
        return list.set(getIndex(index), element);
    }

    public void add(int index, T element) {
        list.add(getIndex(index), element);
    }

    public T remove(int index) {
        return list.remove(getIndex(index));
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return list.listIterator(getIndex(index));
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    public Spliterator<T> spliterator() {
        return list.spliterator();
    }
}
