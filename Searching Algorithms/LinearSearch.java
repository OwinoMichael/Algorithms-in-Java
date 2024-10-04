public class LinearSearch {

    public static int linearSearch(int [] arr, int target){ //We pass the array and the target we want to find

            for(int i = 0; i < arr.length -1; i++){ //Loop through the array from index 0
                if(arr[i] == target) //Compare
                    return arr[i];
            }
        return -1; //If not found we return -1
    }

    public static void main(String[] args) {

        int [] arr = {1,4,6,26,15};
        System.out.print("The element is " + linearSearch(arr, 26));
    }
}
