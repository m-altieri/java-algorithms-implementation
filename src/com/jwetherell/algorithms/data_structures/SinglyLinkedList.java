package com.jwetherell.algorithms.data_structures;

import com.jwetherell.algorithms.data_structures.interfaces.IList;

/**
 * Linked List (Singly link). A linked list is a data structure consisting
 * of a group of nodes which together represent a sequence.
 * <p>
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @param <T> the generic type
 * @see <a href="https://en.wikipedia.org/wiki/Linked_list">Linked List (Wikipedia)</a>
 * <br>
 */
public class SinglyLinkedList<T> implements IList<T> {

	private int size = 0;
	private Node<T> head = null;
	private Node<T> tail = null;

	/**
	 * Instantiates a new singly linked list.
	 */
	public SinglyLinkedList() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean add(T value) {
		return add(new Node<T>(value));
	}

	/**
	 * Add node to list.
	 * 
	 * @param node
	 *            to add to list.
	 */
	private boolean add(Node<T> node) {
		if (head == null) {
			head = node;
			tail = node;
		} else {
			Node<T> prev = tail;
			prev.next = node;
			tail = node;
		}
		size++;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(T value) {
		// Find the node
		Node<T> prev = null;
		Node<T> node = head;
		while (node != null && (!node.value.equals(value))) {
			prev = node;
			node = node.next;
		}

		if (node == null)
			return false;

		// Update the tail, if needed
		if (node.equals(tail)) {
			tail = prev;
			prev = prevNullCheck(prev);
		}

		Node<T> next = node.next;
		if (removeCheck1(prev, next)) {
			prev.next = next;
		} else if (removeCheck2(prev, next)) {
			prev.next = null;
		} else if (removeCheck3(prev, next)) {
			// Node is the head
			head = next;
		} else {
			// prev==null && next==null
			head = null;
		}

		size--;
		return true;
	}
	
	private Node<T> prevNullCheck(Node<T> prev) {
		if (prev != null) {
			prev.next = null;
		}
		return prev;
	}
	
	private boolean removeCheck1(Node<T> prev, Node<T> next) {
		return prev != null && next != null;
	}
	
	private boolean removeCheck2(Node<T> prev, Node<T> next) {
		return prev != null && next == null;
	}
	
	private boolean removeCheck3(Node<T> prev, Node<T> next) {
		return prev == null && next != null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(T value) {
		Node<T> node = head;
		while (node != null) {
			if (node.value.equals(value))
				return true;
			node = node.next;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validate() {
		java.util.Set<T> keys = new java.util.HashSet<T>();
		Node<T> node = head;
		if (node != null) {
			keys.add(node.value);

			Node<T> child = node.next;
			while (child != null) {
				if (!validate(child,keys)) 
					return false;
				child = child.next;
			}
		}
		return (keys.size()==size);
	}

	private boolean validate(Node<T> node, java.util.Set<T> keys) {
		if (node.value==null) 
			return false;

		keys.add(node.value);

		Node<T> child = node.next;
		if (child==null) {
			if (!node.equals(tail)) 
				return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<T> toList() {
		return (new JavaCompatibleSinglyLinkedList<T>(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.Collection<T> toCollection() {
		return (new JavaCompatibleSinglyLinkedList<T>(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Node<T> node = head;
		while (node != null) {
			builder.append(node.value).append(", ");
			node = node.next;
		}
		return builder.toString();
	}

	private static class Node<T> {

		private T value = null;
		private Node<T> next = null;

		//            private Node() { }

		private Node(T value) {
			this.value = value;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "value=" + value + " next=" + ((next != null) ? next.value : "NULL");
		}
	}

	/**
	 * Linked List (singly link). A linked list is a data structure consisting
	 * of a group of nodes which together represent a sequence.
	 * <p>
	 *
	 * @author Justin Wetherell <phishman3579@gmail.com>
	 * @param <T> the generic type
	 * @see <a href="https://en.wikipedia.org/wiki/Linked_list">Linked List (Wikipedia)</a>
	 * <br>
	 */
	public static class JavaCompatibleSinglyLinkedList<T> extends java.util.AbstractSequentialList<T> {

		private SinglyLinkedList<T> list = null;

		/**
		 * Instantiates a new java compatible singly linked list.
		 *
		 * @param list the list
		 */
		public JavaCompatibleSinglyLinkedList(SinglyLinkedList<T> list) {
			this.list = list;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean add(T value) {
			return list.add(value);
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean remove(Object value) {
			return list.remove((T)value);
		}

		/**
		 * {@inheritDoc}
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean contains(Object value) {
			return list.contains((T)value);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int size() {
			return list.size();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public java.util.ListIterator<T> listIterator(int index) {
			return (new SinglyLinkedListListIterator<T>(list));
		}

		private static class SinglyLinkedListListIterator<T> implements java.util.ListIterator<T> {

			private int index = 0;

			private SinglyLinkedList<T> list = null;
			private SinglyLinkedList.Node<T> prev = null;
			private SinglyLinkedList.Node<T> next = null;
			private SinglyLinkedList.Node<T> last = null;

			private SinglyLinkedListListIterator(SinglyLinkedList<T> list) {
				this.list = list;
				this.next = list.head;
				this.prev = null;
				this.last = null;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void add(T value) {
				SinglyLinkedList.Node<T> node = new SinglyLinkedList.Node<T>(value);

				if (list.head == null) {
					list.head = node;
					list.tail = node;
				} else {
					SinglyLinkedList.Node<T> p = null;
					SinglyLinkedList.Node<T> n = list.head;
					while (n!= null && !(n.equals(next))) {
						p = node;
						n = node.next;
					}
					if (p != null) {
						p.next = node;
					} else {
						// replacing head
						list.head = node;
					}
					node.next = n;
				}
				this.next = node;

				list.size++;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void remove() {
				if (last == null) 
					return;

				SinglyLinkedList.Node<T> p = null;
				SinglyLinkedList.Node<T> node = this.last;
				while (node!= null && !(node.equals(last))) {
					p = node;
					node = node.next;
				}

				SinglyLinkedList.Node<T> n = last.next;
				if (p != null) 
					p.next = n;

				if (last.equals(list.head)) 
					list.head = n;
				if (last.equals(list.tail)) 
					list.tail = p;

				list.size--;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void set(T value) {
				if (last != null) 
					last.value = value;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean hasNext() {
				return (next!=null);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public boolean hasPrevious() {
				return (prev!=null);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public int nextIndex() {
				return index;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public int previousIndex() {
				return index-1;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public T next() {
				if (next == null) 
					throw new java.util.NoSuchElementException();

				index++;
				last = next;
				prev = next;
				next = next.next;

				return last.value;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public T previous() {
				if (prev == null) 
					throw new java.util.NoSuchElementException();

				index--;
				last = prev;
				next = prev;

				SinglyLinkedList.Node<T> p = null;
				SinglyLinkedList.Node<T> node = this.list.head;
				while (node!= null && !(node.equals(prev))) {
					p = node;
					node = node.next;
				}
				prev = p;

				return last.value;
			}
		}	
	}
}
