// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = last = fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (this.isFull()){
            throw new RuntimeException("Ring Buffer Overflow");
        }
        else{
            rb[last] = x;
            fillCount++;
            last = (last + 1) % capacity;
        }
    }

    /**
     * Dequeue the oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (this.isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        else{
            T retValue = rb[first];
            fillCount--;
            first = (first + 1) % capacity;
            return retValue;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (this.isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    private class queueIterator implements Iterator<T>{
        private int pos, cnt;
        public queueIterator(){
            pos = first;
            cnt = 0;
        }

        public boolean hasNext(){
            return cnt != fillCount;
        }

        public T next(){
            T res = rb[pos];
            pos = (pos + 1) % capacity;
            cnt++;
            return res;
        }
    }
    @Override
    public Iterator<T> iterator(){
        return new queueIterator();
    }
}
