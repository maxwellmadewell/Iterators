package vandy.cs5278;

import java.lang.ArrayIndexOutOfBoundsException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides a generic dynamically-(re)sized array abstraction.
 */
public class ListArray<T extends Comparable<T>> implements Comparable<ListArray<T>>, Iterable<T> {
	/**
	 * The underlying list of type T.
	 */
	private Node nodeHead = new Node();

	/**
	 * The current size of the array.
	 */
	private int arraySize;

	/**
	 * Default value for elements in the array.
	 */
	private T defaultElemValue;

	/**
	 * Constructs an array of the given size.
	 * 
	 * @param size Nonnegative integer size of the desired array.
	 * @throws NegativeArraySizeException if the specified size is negative.
	 */
	public ListArray(int size) throws NegativeArraySizeException {
		this(size, null);
	}

	/**
	 * Constructs an array of the given size, filled with the provided default
	 * value.
	 * 
	 * @param size         Nonnegative integer size of the desired array.
	 * @param defaultValue A default value for the array.
	 * @throws NegativeArraySizeException if the specified size is negative.
	 */
	public ListArray(int size, T defaultValue) throws NegativeArraySizeException {
		if (size < 0)
			throw new NegativeArraySizeException("Size is less than zero");

		Node here = nodeHead;
		for (int i = 0; i < size; i++) {
			new Node(defaultValue, here);
		}
		defaultElemValue = defaultValue;
		arraySize = size;
	}

	/**
	 * Copy constructor; creates a deep copy of the provided array.
	 * 
	 * @param s The array to be copied.
	 */
	public ListArray(ListArray<T> s) {
		arraySize = s.size();
		defaultElemValue = s.defaultElemValue;
		
		Iterator<T> iter = s.iterator();
		
		T lhs;
		Node rhs = nodeHead;
		
		while(iter.hasNext()) {
			lhs = iter.next();
			new Node(lhs, rhs);
			rhs = rhs.next;
		}
	}

	/**
	 * @return The current size of the array.
	 */
	public int size() {
		return arraySize;
	}

	/**
	 * Resizes the array to the requested size.
	 *
	 * Changes the size of this ListArray to hold the requested number of elements.
	 * 
	 * @param size Nonnegative requested new size.
	 */
	public void resize(int size) {
		Node last;
		
		if (size < 0)
			throw new NegativeArraySizeException();
		
		if (size < size()) {
			last = (size != 0 ? seek(size - 1) : nodeHead);
		}
		else if(size > size()) {
			last = (size() != 0 ? seek(size() - 1) : nodeHead);
			for (int i = size(); i < size; i++) {
				new Node(defaultElemValue, last);
			}
		}
		arraySize = size;
	}

	/**
	 * @return the element at the requested index.
	 * @param index Nonnegative index of the requested element.
	 * @throws ArrayIndexOutOfBoundsException If the requested index is outside the
	 *                                        current bounds of the array.
	 */
	public T get(int index) {
		return seek(index).nodeDataVal;
	}

	/**
	 * Sets the element at the requested index with a provided value.
	 * 
	 * @param index Nonnegative index of the requested element.
	 * @param value A provided value.
	 * @throws ArrayIndexOutOfBoundsException If the requested index is outside the
	 *                                        current bounds of the array.
	 */
	public void set(int index, T value) {
		rangeCheck(index);
		seek(index).nodeDataVal = value;
	}

	private Node seek(int index) {
		rangeCheck(index);
		
		Node n = nodeHead;
		
		for (int i = 0; i <= index; i++) {
			n = n.next;
		}
		
		return n;
	}

	/**
	 * Removes the element at the specified position in this ListArray. Shifts any
	 * subsequent elements to the left (subtracts one from their indices). Returns
	 * the element that was removed from the ListArray.
	 *
	 * @param index the index of the element to remove
	 * @return element that was removed
	 * @throws ArrayIndexOutOfBoundsException if the index is out of range.
	 */
	public T remove(int index) {
		rangeCheck(index);
		
		Iterator<T> iter = iterator();
		T curr = null;
		
		for (int i = 0; i <= index; i++) {
			curr = iter.next();
		}
		
		iter.remove();
		return curr;
	}

	/**
	 * Compares this array with another array.
	 * <p>
	 * This is a requirement of the Comparable interface. It is used to provide an
	 * ordering for ListArray elements.
	 * 
	 * @return a negative value if the provided array is "greater than" this array,
	 *         zero if the arrays are identical, and a positive value if the
	 *         provided array is "less than" this array.
	 */
	@Override
	public int compareTo(ListArray<T> s) {
		Node lhs = nodeHead;
		Node rhs = s.nodeHead;
		for (int i = 0; i < Math.min(size(), s.size()); i++) {
			lhs = lhs.next;
			rhs = rhs.next;
			int c = lhs.nodeDataVal.compareTo(rhs.nodeDataVal);
			if (c != 0)
				return c;
		}
		return size() - s.size();
	}

	/**
	 * Throws an exception if the index is out of bound.
	 */
	private void rangeCheck(int index) {
		if (index < 0 || index >= arraySize)
			throw new ArrayIndexOutOfBoundsException();
	}

	/**
	 * Factory method that returns an Iterator.
	 */
	public Iterator<T> iterator() {
		return new ListIterator();
	}

	private class Node {
		T nodeDataVal;
		Node next;

		/**
		 * Default constructor (no op).
		 */
		Node() {
		}

		/**
		 * Construct a Node from a @a prev Node.
		 */
		Node(Node prev) {
			next = prev.next;	
			prev.next = this;
		}

		/**
		 * Construct a Node from a @a value and a @a prev Node.
		 */
		Node(T value, Node prev) {
			this(prev);
			nodeDataVal = value;
		}
	}


	/**
	 * @brief This class implements an iterator for the list.
	 */
	private class ListIterator implements Iterator<T> {
		private Node currentNode = nodeHead;
		private Node prev;
		private Iterator<Node> iList;

		/**
		 * Returns the next element in the iteration.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if the iteration has no more elements
		 */
		@Override
		public T next() {
			if(!hasNext())
				 throw new NoSuchElementException();
			prev = currentNode;
			currentNode = currentNode.next;
			return currentNode.nodeDataVal;
		}

		/**
		 * Removes from the underlying collection the last element returned by this
		 * iterator (optional operation). This method can be called only once per call
		 * to {@link #next}. The behavior of an iterator is unspecified if the
		 * underlying collection is modified while the iteration is in progress in any
		 * way other than by calling this method.
		 *
		 * @throws UnsupportedOperationException if the {@code remove} operation is not
		 *                                       supported by this iterator
		 * @throws IllegalStateException         if the {@code next} method has not yet
		 *                                       been called, or the {@code remove}
		 *                                       method has already been called after
		 *                                       the last call to the {@code next}
		 *                                       method
		 * @implSpec The default implementation throws an instance of
		 *           {@link UnsupportedOperationException} and performs no other action.
		 */
		@Override
		public void remove() {
			if(prev == null)
				throw new IllegalStateException();	
			prev.next = currentNode.next;	
            prev = null;
            arraySize--;

		}

		/**
		 * Returns {@code true} if the iteration has more elements. (In other words,
		 * returns {@code true} if {@link #next} would return an element rather than
		 * throwing an exception.)
		 *
		 * @return {@code true} if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			return currentNode.next != null;
		}
	}
}