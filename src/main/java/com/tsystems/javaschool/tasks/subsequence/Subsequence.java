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
        // TODO: Implement the logic here

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
