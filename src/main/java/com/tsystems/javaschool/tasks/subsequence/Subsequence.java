package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        /* TODO:
            1) Validation of input data
            2) Checking the lists to be sure the problem condition is met:
               - Take an element in the list "x" and go through the list "y", find an element matching the element in the list "x"
               - Take the next element in the list "x" and repeat the previous step, but start checking from the element of the list  "y" found in the previous step
               - Repeat the previous step until we reach the end of the list "x"
         */


        // Validation
        if (x==null||y==null) throw new IllegalArgumentException();

        // Checking if it is possible to get a sequence
        int index_of_equal=0;
        for (int i=0;i<x.size();i++){
            boolean check=false;
            for (int j=index_of_equal;j<y.size();j++){
                if (x.get(i).equals(y.get(j))) {
                    check=true;
                    index_of_equal=j;
                }
                if (!check&&j==y.size()-1) return false;
            }
        }

        return true;
    }
}
