/*
    Ahmed Elshafey
    900131045
    Question 2
 */

import java.util.ArrayList;

public class Main {

    public static int weightOfHeaviest(int[] weights, int[] map, int k) {
        ArrayList<ArrayList<Integer>> boxes = new ArrayList<>();
        ArrayList<Integer> boxWeights = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            boxes.add(new ArrayList<>());
            boxWeights.add(0);
        }
        for (int i = 0; i < map.length; i++) {
            boxes.get(map[i]).add(i);
        }
        int sum ;
        for (int i = 0; i < k; i++) {
            sum = 0;
            for (int j = 0; j < boxes.get(i).size(); j++) {
                sum += weights[boxes.get(i).get(j)];
            }
            boxWeights.set(i, sum);
        }
        int max = 0;
        for (int i = 0; i < boxWeights.size(); i++) {
            if (boxWeights.get(i) > max) {
                max = boxWeights.get(i);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] weights = {10, 50, 30, 10, 40, 20, 10, 10};
        int[] map = {2, 0, 1, 0, 0, 1, 2, 2};
        System.out.println(weightOfHeaviest(weights, map, 3));
    }
}
