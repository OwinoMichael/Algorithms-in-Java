public class SelectionSort {

    public void selectionSort(int [] arr){

        for (int i = 0; i < arr.length-1; i++){
            int min = i;

            for (int j = i + 1; j < arr.length; j++){
                if (arr[j] < arr[min]){
                    min = j;
                }
            }
            if(arr[min] != arr[i]){
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }

    }

    public static void main(String[] args) {
        int [] arr = {45, 12, 20, 1, 9, 50, 30, 17};
        SelectionSort select = new SelectionSort();
        select.selectionSort(arr);

        for (int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }

    }
}
