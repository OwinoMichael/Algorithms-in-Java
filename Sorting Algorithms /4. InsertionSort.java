public class InsertionSort {

    public void insertionSort(int [] arr){

        for (int i= 0; i < arr.length-1; i++){
            int j = i;
            while (j > 0 && arr[j] < arr[j-1]){
                int temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j = j -1;
            }
        }

    }

    public static void main(String[] args) {
        int [] arr = {34, 58, 10, 1, 17, 20, 28};

        InsertionSort insert = new InsertionSort();
        insert.insertionSort(arr);
        for (int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
    }
}
