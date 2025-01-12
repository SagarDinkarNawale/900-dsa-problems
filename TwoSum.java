import java.util.Arrays;

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};  // Return the indices
                }
            }
        }
        return null;

    }

    public static void main(String[] args) {
        int arr[] = {3, 2, 4};
        int target = 6;
        int[] result = TwoSum.twoSum(arr, target);

        if (result.length == 2) {
            System.out.println("Indices of numbers that sum up to " + target + ": "
                    + result[0] + " and " + result[1]);
            System.out.println("Numbers are: " + arr[result[0]] + " and " + arr[result[1]]);
        } else {
            System.out.println("No two numbers in the array add up to " + target + ".");
        }
    }
}
