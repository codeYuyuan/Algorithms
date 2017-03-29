import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by yuyuanliu on 2017-01-09.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a ;
    private int size;
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
        size =0;
    }                 // construct an empty randomized queue
    public boolean isEmpty(){
        return size == 0;
    }                 // is the queue empty?
    public int size() {
        return size;
    }                       // return the number of items on the queue
    private void resize(int max){
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < size; i++)
            temp[i] = a[i];
        a = temp;
    }
    public void enqueue(Item item){
        if(item == null)
            throw new NullPointerException();
        if (size == a.length) resize(2*a.length);
        a[size++] = item;
    }           // add the item
    public Item dequeue() {
        if(isEmpty())
            throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item temp = a[index];
        if(index != size - 1){
            a[index] = a[size -1];
        }
        a[--size] = null;
        if(size >=0 && size == a.length/4) resize(a.length/2);
        return temp;
    }                   // remove and return a random item
    public Item sample(){
        if(isEmpty())
            throw new NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item temp = a[index];
        return temp;
    }                     // return (but do not remove) a random item
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }
    private class RandomizedQueueIterator implements Iterator<Item>{
        private Item[] r;
        private int index;
        public RandomizedQueueIterator() {
            r = (Item[]) new Object[size];
            for(int i=0; i<size; i++)
                r[i] = a[i];
            StdRandom.shuffle(r);
        }
        public boolean hasNext() {
            return index < size;
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if(!hasNext()) throw new java.util.NoSuchElementException();
            Item item = r[index++];
            return item;
        }

    }// return an independent iterator over items in random order
    /*
    public static void main(String[] args){
        RandomizedQueue<String> deque = new RandomizedQueue<String> ();
        deque.enqueue("aa");
        deque.enqueue("bb");
        deque.enqueue("cc");
        deque.enqueue("dd");
        deque.enqueue("ee");
        deque.enqueue("ff");
        deque.enqueue("gg");
        deque.enqueue("hh");
        deque.enqueue("ii");
        deque.enqueue("jj");
        deque.enqueue("kk");
        deque.enqueue("ll");
        deque.dequeue();
        for(String a : deque){
            StdOut.print(a + " ");
        }
        StdOut.println();
        deque.dequeue();
        for(String a : deque){
            StdOut.print(a + " ");
        }
        StdOut.println();
        deque.dequeue();
        for(String a : deque){
            StdOut.print(a + " ");
        }
        StdOut.println();
        deque.dequeue();
        for(String a : deque){
            StdOut.print(a + " ");
        }
        StdOut.println();
        //deque.dequeue();
        StdOut.print("size:"+deque.size());
    }   // unit testing (optional)
    */
}
