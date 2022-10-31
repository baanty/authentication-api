package com.ing.fraud.authentication.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CalculateLongestBiValuedArraySlice {
	
	public static void main(String[] args) {
		CalculateLongestBiValuedArraySlice e = new CalculateLongestBiValuedArraySlice();
		/*
		 * System.out.println(e.solution(new int[]{4,2,2,4,2}));
		 * System.out.println(e.solution(new int[]{1,2,3,2}));
		 */
		
		  System.out.println(e.solution(new int[]{0,5,4,4,5,12}));
		  //System.out.println(e.solution(new int[]{4,4}));
		 
	}
	
	public int solution(int[] A) {

		List<Integer> sizes = new ArrayList<Integer>();
		List<Integer> parameter = Arrays.stream(A).boxed().collect(Collectors.toList());
								
		
		for (int outerIndex = 0 ; outerIndex < A.length ; outerIndex++ ) {
			Set<Integer> intern = new TreeSet<Integer>();
			
			for ( int innerIndex = outerIndex ; innerIndex < A.length ; innerIndex++  ) {
				intern.add(A[innerIndex]);
				if ( intern.size() <= 2 ) {
					sizes.add(A.length - outerIndex);
				}
			}
			
		}
		
		Collections.reverse(parameter);
		
		for (int outerIndex = 0 ; outerIndex < A.length ; outerIndex++ ) {
			Set<Integer> intern = new TreeSet<Integer>();
			for ( int innerIndex = outerIndex ; innerIndex < A.length ; innerIndex++  ) {
				intern.add(parameter.get(innerIndex));
			}
			if ( intern.size() <= 2 ) {
				sizes.add(A.length - outerIndex);
			}
		}
		
		
		
		Collections.sort(sizes);
		Collections.reverse(sizes);
		return sizes.get(0);
	}
	
	
	
}
