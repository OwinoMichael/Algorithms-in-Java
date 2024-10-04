import java.rmi.dgc.VMID;

public class MinMaxRecursion {

    static class Pair{ //staic class to store min & max value
        int min;
        int max;
    }

    static Pair minMax(int [] arr, int low, int high){ //return type should be the static class
        Pair result = new Pair(); //instantiate the static class
        Pair left; // to return both max & min
        Pair right; // to return both max & min


        if(low == high){ //index low is same as high
            result.max = arr[low];
            result.min = arr[low];
            return result;
        }

        if (low == high - 1){ //index low is adjascent to high
            if (arr[low] < arr[high]){
                result.max = arr[high];
                result.min = arr[low];
                return result; //return a pair
            }else {
                result.max = arr[low];
                result.min = arr[high];
                return result; //return a pair
            }
        }


        int mid = low + (high - low)/2; //midpoint
        left = minMax(arr, low, mid); // call the method recursively on the left
        right = minMax(arr, mid+1, high); //call the method recursively  on the right

        result.max = Math.max(left.max, right.max); //call Math class to find max btwn left & right
        result.min = Math.min(left.min, right.min); //call math class to min btwn left & right

        return result; // return the pair

    }


    public static void main(String[] args) {

        int [] arr = {23, 52, 34, 78, 50, 21, -11, 1, 27};

        Pair result = minMax(arr, 0, arr.length-1);
        System.out.println("The minimum number is " + result.min);
        System.out.println("The maximum number is " + result.max);

    }
}
