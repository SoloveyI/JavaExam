package com.tsystems.javaschool.tasks.pyramid;

import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException {
        // TODO : Implement your solution here


        // Input data validation
        if (inputNumbers.isEmpty()) throw new CannotBuildPyramidException();
        for (Integer x:inputNumbers){if (x==null) throw new CannotBuildPyramidException();}


        // Calculating number of possible rows
        int count_rows=0; // Count of rows
        int count_numbers_inside_pyramid=0; // Count of numbers inside pyramid for "count_rows" number of rows
        while (count_numbers_inside_pyramid<inputNumbers.size()){
            /**
             * If the condition is true, then increase the number of rows.
             * The count of numbers inside the pyramid for each new row is increased by the number of that row
             */
            count_rows++;
            count_numbers_inside_pyramid += count_rows;
        }
        if (count_numbers_inside_pyramid!=inputNumbers.size()) throw new CannotBuildPyramidException();


        // InsertionSort of "inputNumbers" list
        for (int i = 1; i < inputNumbers.size(); i++){
            if (inputNumbers.get(i - 1).equals(inputNumbers.get(i))) throw new CannotBuildPyramidException(); // Validation that all elements are different
            int current = inputNumbers.get(i);
            int j = i - 1;
            while(j >= 0 && current > inputNumbers.get(j)) {
                inputNumbers.set(j+1,inputNumbers.get(j));
                j--;
            }
            inputNumbers.set(j+1,current);
        }

        // Building pyramid
        int[][] pyramid=new int[count_rows][count_rows*2-1];
        int simple_count=0;

        /**   Building pyramid from the end to start. "inputNumbers" is already sorted by decreasing    */
        for (int i = count_rows-1; i>-1;i--){
            for (int j=(count_rows*2-1)-(count_rows-i);j>-2+(count_rows-i);j-=2){
                pyramid[i][j]=inputNumbers.get(simple_count);
                simple_count++;
                if (simple_count==inputNumbers.size()) break;
            }
        }

        return pyramid;
    }


}
