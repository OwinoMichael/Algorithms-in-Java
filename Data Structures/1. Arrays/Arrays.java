package org.mike.com;

public class Arrays {

    private int [] items;
    private int count;

    public Arrays(int length){
        items = new int[length];
    }

    public void insert(int item){
        //If array is full, resize it
        if (items.length == count){
            //Create a new array (twice the size)
            int [] newItems = new int[count * 2];

            //Copy all existing items
            for (int i = 0; i < count; i++){
                newItems[i] = items[i];
            }
            //Set "items" to this new array
            items = newItems;
        }

        //Add the new item at the end of the current array
        items[count] = item;
        count++;
    }

    public void removeAt(int index){
        //Validate the Index
        if (index < 0 || index >= count){
            throw new IllegalArgumentException();
        }
        //Shift the items to the left to fill the whole
        // [10, 20, 30, 40]
        // e.g. delete 20
        //1 <- 2
        //2 <- 3
        for (int i = index; i < count; i++){
            items[i] = items[i + 1];
        }
        count --;
    }

    public int indexOf(int item){
        //If we find it, return index
        for (int i = 0; i < count; i++){
            if (items[i] == item){
                return i;
            }
        }
        //Otherwise return -1
        return -1;
    }

    public void print(){
        for (int i = 0; i < count; i++){
            System.out.println(items[i]);
        }
    }
}
