public class BinarySearch {


    private static int binarySearch(int [] arr, int x){
        int i = 0;
        int j = arr.length - 1;
        int mid = i + (j - i)/2;

        while (i <= j ){
            if (x == arr[mid]){
                return mid;
            }else if (x < arr[mid]){
                j = mid - 1;
                //binarySearch(arr, x);
            }else{
                i = mid + 1;
                //binarySearch(arr, x);
            }
        }

        return -1;

    }

    public static void main(String[] args) {

        int [] arr = {1, 3, 5, 7, 10, 13,14, 16, 17};

        System.out.println(binarySearch( arr, 10));
    }
}
