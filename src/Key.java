/**
 * Created by Moath Mohamed on 22-Nov-2018 At 18:15
 */
public class Key implements Comparable<Key> {

    int key;
    int position;

    public Key(int key, int position) {
        this.key = key;
        this.position = position;
    }

    public int compareTo(Key compareFruit) {

        int compareKey = ((Key) compareFruit).key;

        //ascending order
        return this.key - compareKey;

        //descending order
        //return compareQuantity - this.quantity;

    }

}