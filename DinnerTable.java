/**
Date: 10/20/2013

The MIT License (MIT)

Copyright (c) 2013 Andrew Helenius

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
**/

import java.io.*;
import java.lang.Math;
import java.util.Scanner;

public class DinnerTable
{
	public Personality[] spots;
	
	public DinnerTable(int guests)
	{
		spots = new Personality[guests];
		for (int i = 0; i < guests; ++i) {
			boolean gender = i < guests / 2;
			spots[i] = new Personality(gender, i);
		}
	}
	
	public void readGuests(Scanner scan)
	{
		for (int i = 0; i < spots.length; ++i) {
			spots[i].readIn(scan, spots.length);			
		}

		// put the grumpiest guys in end positions:
		int l2 = spots.length / 2;
		for (int i = 0; i < spots.length; ++i) {
			int k = spots[i].getNetHappiness();
			if (k < spots[0].getNetHappiness()) {
				swap(0, i);
			}
			else if (k < spots[l2].getNetHappiness()) {
				swap(l2, i);
			}
			else if (k < spots[spots.length-1].getNetHappiness()) {
				swap(spots.length-1, i);
			}
			else if (k < spots[l2-1].getNetHappiness()) {
				swap(l2-1, i);
			}
		}
	}
	
	// Calculates the overall happiness level of the table, > 0 is good.
	public int getValue() {
		int score = 0;
		for (int i = 0; i < spots.length; i++) {
			if (i != spots.length / 2 - 1 && i != spots.length-1) {
				// adjacent people are talking:
				score += spots[i].talk(spots[i+1]);
				score += spots[i].compare(spots[i+1]);
			}
			
			int opp = (i + spots.length/2) % spots.length;
			
			// yes this counts opposites twice, but it's 1 point twice
			// which satisfies the 2 points opposite rule.
			score += spots[i].compare(spots[opp]);
		}
		return score;
	}
	
	public boolean isCorner(int a)
	{
		return a == 0 || a == spots.length-1 ||
				a == spots.length/2 || a == spots.length/2-1;
	}
	
	public void solve() {
		int score = 0;
		int[] layout = getLayout();
	
		// yes I am throwing runtime into this; gotta use my 60 secs. :)
		for (int k = 0; k < 50000; ++k) {
			for (int l = 0; l < spots.length/2; ++l) {
				for (int i = 1; i < spots.length-2; i++)
				{
					int A = -1, B = -1, C = -1;
					int opp = (i + spots.length/2) % spots.length;
				
					// to my left?
					swap(i, i - 1);
					A = getValue();
					swap(i, i - 1);
					
					// to my right?
					swap(i, i + 1);
					B = getValue();
					swap(i, i + 1);
					
					// to the opposite side?
					swap(i, opp);
					C = getValue();
					swap(i, opp);

					// if A was the best; choose it.
					if (A > B && A > C && A > score) {
						score = A;
						swap(i, i - 1);
						layout = getLayout();
					}
					
					// if B was the best; choose it.
					if (B > A && B > C && B > score) {
						score = B;
						swap(i, i + 1);
						layout = getLayout();
					}
					
					// if C was the best; choose it.
					if (C > A && C > B && C > score) {
						score = C;
						swap(i, opp);
						layout = getLayout();
					}
					// ...else stay put.
				}
			}
			shuffle();
		}
		
		System.out.println(score);
		for (int i = 0; i < layout.length; ++i) {
			System.out.print(layout[i] + " " + i + "\n");
		}
	}
	
	// swaps two seats.
	public void swap(int a, int b) {
		Personality p = spots[a];
		spots[a] = spots[b];
		spots[b] = p;
	}
	
	// returns an array of the seating order: 0 = person 0, etc.
	public int[] getLayout()
	{
		int[] layout = new int[spots.length];
		for (int i = 0; i < spots.length; ++i) {
			layout[i] = spots[i].getID();
		}
		return layout;
	}
	
	// do the Knuth shuffle ignoring end spots
	public void shuffle()
	{
		for (int i = spots.length-2; i > 1; i--) {
			int j = (int)Math.floor(Math.random()*i);
			if (isCorner(i) || isCorner(j)) continue;
			swap(j, i);
		}
	}

	public static void main(String[] args) throws FileNotFoundException
	{
		if (args.length == 0) {
			System.out.println("No input file.");
			return;
		}
		
		Scanner scanner = null;
		scanner = new Scanner(new File(args[0]));
		int num = scanner.nextInt();

		DinnerTable table = new DinnerTable(num);
		table.readGuests(scanner);
		table.solve();
		
		scanner.close();
	}
}
