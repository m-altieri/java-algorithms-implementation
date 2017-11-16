package com.jwetherell.algorithms.data_structures;

/**
 * Linked List (doubly link). A linked list is a data structure consisting
 * of a group of nodes which together represent a sequence.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Linked_list">Linked List (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class DoublyLinkedList<T> extends List<T> {

	private int size = 0;
	private Node<T> head = null;
	private Node<T> tail = null;

	public DoublyLinkedList() {
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
			node.prev = prev;
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
		Node<T> node = head;
		while (node != null && (!node.value.equals(value))) {
			node = node.next;
		}
		if (node == null)
			return false;

		// Update the tail, if needed
		if (node.equals(tail))
			tail = node.prev;

		Node<T> prev = node.prev;
		Node<T> next = node.next;
		if (prev != null && next != null) {
			prev.next = next;
			next.prev = prev;
		} else if (prev != null && next == null) {
			prev.next = null;
		} else if (prev == null && next != null) {
			// Node is the head
			next.prev = null;
			head = next;
		} else {
			// prev==null && next==null
			head = null;
		}
		size--;
		return true;
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
		if (node!=null) {
			keys.add(node.value);
			if (node.prev!=null) 
				return false;
			Node<T> child = node.next;
			while (child!=null) {
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
		if (child!=null) {
			if (!child.prev.equals(node)) 
				return false;
		} else {
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
		return (new JavaCompatibleDoublyLinkedList<T>(this));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.Collection<T> toCollection() {
		return (new JavaCompatibleDoublyLinkedList<T>(this));
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
		private Node<T> prev = null;
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
			return "value=" + value + " previous=" + ((prev != null) ? prev.value : "NULL")
					+ " next=" + ((next != null) ? next.value : "NULL");
		}
	}

	/**
	 * 
	 *
	 * @param <T> 
	 */
	public static class JavaCompatibleDoublyLinkedList<T> extends java.util.AbstractSequentialList<T> {

		private DoublyLinkedList<T> list = null;

		/**
		 * 
		 *
		 * @param list 
		 */
		public JavaCompatibleDoublyLinkedList(DoublyLinkedList<T> list) {
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
			return (new DoublyLinkedListListIterator<T>(list));
		}

		private static class DoublyLinkedListListIterator<T> implements java.util.ListIterator<T> {

			private int index = 0;

			private DoublyLinkedList<T> list = null;
			private DoublyLinkedList.Node<T> prev = null;
			private DoublyLinkedList.Node<T> next = null;
			private DoublyLinkedList.Node<T> last = null;

			private DoublyLinkedListListIterator(DoublyLinkedList<T> list) {
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
				DoublyLinkedList.Node<T> node = new DoublyLinkedList.Node<T>(value);
				DoublyLinkedList.Node<T> n = this.next;

				if (this.prev != null) 
					this.prev.next = node;
				node.prev = this.prev;

				node.next = n;
				if (n != null) 
					n.prev = node;

				this.next = node;
				if (this.prev == null) 
					list.head = node; // new root
				list.size++;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void remove() {
				if (last == null) 
					return;

				DoublyLinkedList.Node<T> p = last.prev;
				DoublyLinkedList.Node<T> n = last.next;
				if (p != null) 
					p.next = n;
				if (n != null) 
					n.prev = p;
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
				prev = next.prev;

				return last.value;
			}
		}
	}

}