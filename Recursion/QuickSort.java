public class QuickSort {

    private static void QuickSorts(int [] arr, int low, int high){

        if(low > high){ //Out of range
            return;
        }
        int mid = low + (high - low)/2; //Preferred over (low + high)/2
        int pivot = arr[mid];
        int i = low;
        int j = high;
        while (i <= j){
            while(arr[i] < pivot) //follow the rule
                i++;
            while (arr[j] > pivot)
                j--;
            if(i <= j){
                int temp = arr[i]; //Do the swapping
                arr[i] = arr[j];
                arr[j] = temp;
                i++; //After swapping move the i & j pointers
                j--;
            }
        }

        if(low < j){
            QuickSorts(arr, low, j);
        }
        if(high > i){
            QuickSorts(arr, i, high);
        }
    }

    public static void main(String[] args) {
        int [] arr = {1, 50, 30, 10, 60, 80};
        System.out.println("Array before sorting");
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + "\t");
        QuickSorts(arr, 0, arr.length-1);
        System.out.println(" \n After sorting Array");
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + "\t");
    }
}
