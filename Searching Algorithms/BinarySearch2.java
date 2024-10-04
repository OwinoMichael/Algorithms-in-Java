public class BinarySearch2 {

    public static int binarySearch(int [] arr, int low, int high, int target){ //Pass 4 arguments
        //Base case, Low must less than high
        if(low < high) {
            int mid;
            mid = low + (high - low) / 2; //finding the mid-point index


            if (arr[mid] == target) { // we always compare the midpoint to the target we want
                return arr[mid];
            } else if (arr[mid] > target) {
                return binarySearch(arr, low, mid - 1, target); //recursion on left side
            } else {
                return binarySearch(arr, mid + 1, high, target); //recursion on right side
            }

        }
        return -1;
    }
    public static void main(String[] args) {
        int [] arr = {1, 3, 4, 5, 7, 10, 11, 13, 15, 18, 19};

        //binarySearch(arr, 0, arr.length-1, 55);

        System.out.println(binarySearch(arr, 1, arr.length-1, 11));
    }
}
