package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An interval tree is an ordered tree data structure to hold intervals.
 * Specifically, it allows one to efficiently find all intervals that overlap
 * with any given interval or point. 
 * <p>
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @param <O> the generic type
 * @see <a href="https://en.wikipedia.org/wiki/Interval_tree">Interval Tree (Wikipedia)</a>
 * <br>
 */
public class IntervalTree<O extends Object> {

    private Interval<O> root = null;

    private static final Comparator<IntervalData<?>> START_COMPARATOR = new Comparator<IntervalData<?>>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(IntervalData<?> theArg0, IntervalData<?> theArg1) {
        	// Compare start first
            if (theArg0.start < theArg1.start)
                return -1;
            if (theArg1.start < theArg0.start)
                return 1;
            return 0;
        }
    };

    private static final Comparator<IntervalData<?>> END_COMPARATOR = new Comparator<IntervalData<?>>() {
        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(IntervalData<?> theArg0, IntervalData<?> theArg1) {
        	// Compare end first
            if (theArg0.end > theArg1.end)
                return -1;
            if (theArg1.end > theArg0.end)
            	return 1;
            return 0;
        }
    };

    /**
     * Create interval tree from list of IntervalData objects;.
     *
     * @param intervals            is a list of IntervalData objects
     */
    public IntervalTree(List<IntervalData<O>> intervals) {
        if (intervals.size() <= 0)
            return;

        root = createFromList(intervals);
    }

    protected static final <O extends Object> Interval<O> createFromList(List<IntervalData<O>> intervals) {
        Interval<O> newInterval = new Interval<O>();
        if (intervals.size()==1) {
        	IntervalData<O> middle = intervals.get(0);
        	newInterval.center = ((middle.start + middle.end) / 2);
        	newInterval.add(middle);
        	return newInterval;
        }

        int half = intervals.size() / 2;
        IntervalData<O> middle = intervals.get(half);
        newInterval.center = ((middle.start + middle.end) / 2);
        List<IntervalData<O>> leftIntervals = new ArrayList<IntervalData<O>>();
        List<IntervalData<O>> rightIntervals = new ArrayList<IntervalData<O>>();
        for (IntervalData<O> interval : intervals) {
        	if (interval.end < newInterval.center) {
                leftIntervals.add(interval);
            } else if (interval.start > newInterval.center) {
                rightIntervals.add(interval);
            } else {
                newInterval.add(interval);
            }
        }

        if (leftIntervals.size() > 0)
            newInterval.left = createFromList(leftIntervals);
        if (rightIntervals.size() > 0)
            newInterval.right = createFromList(rightIntervals);

        return newInterval;
    }

    /**
     * Stabbing query.
     *
     * @param index            to query for.
     * @return data at index.
     */
    public IntervalData<O> query(long index) {
        return root.query(index);
    }

    /**
     * Range query.
     *
     * @param start            of range to query for.
     * @param end            of range to query for.
     * @return data for range.
     */
    public IntervalData<O> query(long start, long end) {
        return root.query(start, end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(IntervalTreePrinter.getString(this));
        return builder.toString();
    }

    protected static class IntervalTreePrinter {

        public static <O extends Object> String getString(IntervalTree<O> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static <O extends Object> String getString(Interval<O> interval, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            builder.append(prefix + getStringUtility(isTail) + interval.toString() + System.getProperty("line.separator"));
            List<Interval<O>> children = new ArrayList<Interval<O>>();
            if (interval.left != null)
                children.add(interval.left);
            if (interval.right != null)
                children.add(interval.right);
            if (children.size() > 0) {
            	int size = children.size();
                for (int i = 0; i < size - 1; i++)
                    builder.append(getString(children.get(i), prefix + getStringUtility(isTail), false));
                if (children.size() > 0)
                    builder.append(getString(children.get(children.size() - 1), prefix + getStringUtility(isTail), true));
            }

            return builder.toString();
        }
    }
    
    private static String getStringUtility(boolean isTail) {
    	return isTail ? "└── " : "├── ";
    }

    /**
     * The Class Interval.
     *
     * @param <O> the generic type
     */
    public static final class Interval<O> {

        private long center = Long.MIN_VALUE;
        private Interval<O> left = null;
        private Interval<O> right = null;
        private List<IntervalData<O>> overlap = new ArrayList<IntervalData<O>>();

        private void add(IntervalData<O> data) {
        	overlap.add(data);
        }

        /**
         * Stabbing query.
         *
         * @param index            to query for.
         * @return data at index.
         */
        public IntervalData<O> query(long index) {
            IntervalData<O> results = null;
            if (index < center) {
                // overlap is sorted by start point
            	Collections.sort(overlap,START_COMPARATOR);
                for (IntervalData<O> data : overlap) {
                    if (data.start > index)
                        break;

                    IntervalData<O> temp = data.query(index);
                    results = getResultsUtility1(results, temp);
                }
            } else if (index >= center) {
                // overlap is reverse sorted by end point
            	Collections.sort(overlap,END_COMPARATOR);
                for (IntervalData<O> data : overlap) {
                    if (data.end < index)
                        break;

                    IntervalData<O> temp = data.query(index);
                    results = getResultsUtility1(results, temp);
                }
            }

            results = getResultsFromIndex(results, index);
            
            return results;
        }
        
        private IntervalData<O> getResultsFromIndex(IntervalData<O> results, long index) {
        	if (index < center) {
                if (left != null) {
                    IntervalData<O> temp = left.query(index);
                    results = getResultsUtility1(results, temp);
                }
            } else if (index >= center) {
                if (right != null) {
                    IntervalData<O> temp = right.query(index);
                    results = getResultsUtility1(results, temp);
                }
            }
        	return results;
        }

        /**
         * Range query.
         *
         * @param start            of range to query for.
         * @param end            of range to query for.
         * @return data for range.
         */
        public IntervalData<O> query(long start, long end) {
            IntervalData<O> results = null;
            for (IntervalData<O> data : overlap) {
                if (data.start > end)
                    break;
                IntervalData<O> temp = data.query(start, end);
                results = getResultsUtility1(results, temp);
            }

            if (left != null && start < center) {
                IntervalData<O> temp = left.query(start, end);
                results = getResultsUtility2(results, temp);
            }

            if (right != null && end >= center) {
                IntervalData<O> temp = right.query(start, end);
                results = getResultsUtility2(results, temp);
            }

            return results;
        }
	        
		private IntervalData<O> getResultsUtility1(IntervalData<O> results, IntervalData<O> temp) {
			if (results == null && temp != null)
                results = temp;
            else if (results != null && temp != null)
                results.combined(temp);
			return results;
        }
		        
		private IntervalData<O> getResultsUtility2(IntervalData<O> results, IntervalData<O> temp) {
			if (temp != null && results == null)
                results = temp;
            else if (results != null && temp != null)
                results.combined(temp);
			return results;
		}
	
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Center=").append(center);
            builder.append(" Set=").append(overlap);
            return builder.toString();
        }
    }

    /**
     * Data structure representing an interval.
     *
     * @param <O> the generic type
     */
    public static class IntervalData<O> implements Comparable<IntervalData<O>> {

        private long start = Long.MIN_VALUE;
        private long end = Long.MAX_VALUE;
        private Set<O> set = new HashSet<O>();

        /**
         * Interval data using O as it's unique identifier.
         *
         * @param index the index
         * @param object            Object which defines the interval data
         */
        public IntervalData(long index, O object) {
            this.start = index;
            this.end = index;
            this.set.add(object);
        }

        /**
         * Interval data using O as it's unique identifier.
         *
         * @param start the start
         * @param end the end
         * @param object            Object which defines the interval data
         */
        public IntervalData(long start, long end, O object) {
            this.start = start;
            this.end = end;
            this.set.add(object);
        }

        /**
         * Interval data list which should all be unique.
         *
         * @param start the start
         * @param end the end
         * @param set the set
         */
        public IntervalData(long start, long end, Set<O> set) {
            this.start = start;
            this.end = end;
            this.set = set;
        }

        /**
         * Get the start of this interval.
         *
         * @return Start of interval
         */
        public long getStart() {
            return start;
        }

        /**
         * Get the end of this interval.
         *
         * @return End of interval
         */
        public long getEnd() {
            return end;
        }

        /**
         * Get the data set in this interval.
         *
         * @return Unmodifiable collection of data objects
         */
        public Collection<O> getData() {
            return Collections.unmodifiableCollection(this.set);
        }

        /**
         * Clear the indices.
         */
        public void clear() {
            this.start = Long.MIN_VALUE;
            this.end = Long.MAX_VALUE;
            this.set.clear();
        }

        /**
         * Combined this IntervalData with data.
         * 
         * @param data
         *            to combined with.
         * @return Data which represents the combination.
         */
        public IntervalData<O> combined(IntervalData<O> data) {
            if (data.start < this.start)
                this.start = data.start;
            if (data.end > this.end)
                this.end = data.end;
            this.set.addAll(data.set);
            return this;
        }

        /**
         * Deep copy of data.
         * 
         * @return deep copy.
         */
        public IntervalData<O> copy() {
            Set<O> copy = new HashSet<O>();
            copy.addAll(set);
            return new IntervalData<O>(start, end, copy);
        }

        /**
         * Query inside this data object.
         *
         * @param index the index
         * @return Data queried for or NULL if it doesn't match the query.
         */
        public IntervalData<O> query(long index) {
            if (index < this.start || index > this.end)
                return null;

            return copy();
        }

        /**
         * Query inside this data object.
         * 
         * @param startOfQuery
         *            of range to query for.
         * @param endOfQuery
         *            of range to query for.
         * @return Data queried for or NULL if it doesn't match the query.
         */
        public IntervalData<O> query(long startOfQuery, long endOfQuery) {
            if (endOfQuery < this.start || startOfQuery > this.end)
                return null;

            return copy();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 31 * ((int)(this.start + this.end)) + this.set.size();
        }
 
        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (getClass() != obj.getClass()) {
            	return false;
            }

            IntervalData<O> data = (IntervalData<O>) obj;
            if (this.start == data.start && this.end == data.end) {
                if (this.set.size() != data.set.size())
                    return false;
                for (O o : set) {
                    if (!data.set.contains(o))
                        return false;
                }
                return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(IntervalData<O> d) {
            if (this.end < d.end)
                return -1;
            if (d.end < this.end)
                return 1;
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(start).append("->").append(end);
            builder.append(" set=").append(set);
            return builder.toString();
        }
    }
}
