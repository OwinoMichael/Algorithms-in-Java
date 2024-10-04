public class MergeSort {

    int [] array;   //Main array
    int [] tempArray; //New Array to store the sorted elemnts

    void PrepareForSort(int [] arr){
        this.array = arr;
        this.tempArray = new int[arr.length]; //lenght of the temporary to match

        doMergeSort(0, arr.length-1); //perform the sort
    }

    void doMergeSort(int LowerIndex, int HigherIndex){
        if (LowerIndex < HigherIndex ){   //must fullfill this condition
            int middleIndex = LowerIndex + (HigherIndex-LowerIndex)/2;  //Preferred over (low + high)/2
            doMergeSort(LowerIndex, middleIndex); //perform recursion on the right side
            doMergeSort(middleIndex+1, HigherIndex); //perform recursion on the left side
            MergeParts(LowerIndex, middleIndex, HigherIndex); //MERGE THE SORTED ARRAY
        }
    }

    void MergeParts(int LowerIndex, int middleIndex, int HigherIndex){
        for (int i = LowerIndex; i <= HigherIndex; i++)
            tempArray[i] = array[i];
        int i = LowerIndex;
        int j = middleIndex + 1;
        int k = LowerIndex;
        while(i <= middleIndex && j <= HigherIndex){
            if (tempArray[i] <= tempArray[j]){
                array[k] = tempArray[i];
                i++;
            }else {
                array[k] = tempArray[j];
                j++;
            }
        k++;
        }
        while(i <= middleIndex){
            array[k] = tempArray[i];
            k++;
            i++;
        }
    }

    public static void main(String[] args) {
        int [] arr = {1, 50, 30, 10, 60, 80};
        System.out.println("Array before sorting");
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + "\t");

        new MergeSort().PrepareForSort(arr);
        System.out.println(" \n After sorting Array");
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + "\t");
    }
}
