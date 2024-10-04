public class BubbleSort {

    public void bubbleSort(int [] arr ){ //Pass the array

        for(int i = 0; i < arr.length-1; i ++){ //first loop
            for(int j = 0; j < arr.length-1 - i; j++){ //Second loop will go for length - i times
                                                        //Because the i elements sorted will not need scanning
                if(arr[j] > arr[j + 1]){
                    int temp = arr[j]; //Do the swapping of J & J + 1
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }


    public static void main(String[] args) {
        int [] arr = {23, 15, 34, 1, 4, 50, 17};

        BubbleSort bubble = new BubbleSort();
        bubble.bubbleSort(arr);
        for (int i = 0; i < arr.length ; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();



    }
}
