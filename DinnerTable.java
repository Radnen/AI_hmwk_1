/**
	The table is where it's at!
	By: Andrew Helenius
	Date: 10/13/2013
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
	
	public void solve() {
		int score = 0;
		int[] layout = getLayout();
	
		// yes I am throwing runtime into this; gotta use my 60 secs. :)
		for (int l = 0; l < 10000; ++l)
		{
			for (int k = 0; k < spots.length; ++k)
			{
				for (int i = 0; i < spots.length; ++i)
				{
					int A = -1, B = -1, C = -1;
				
					// to my left?
					if (i - 1 != -1 && i - 1 != spots.length/2-1) {
						swap(i, i - 1);
						A = getValue();
						swap(i, i - 1);
					}
					
					// to my right?
					if (i + 1 != spots.length/2 && i + 1 != spots.length) {
						swap(i, i + 1);
						B = getValue();
						swap(i, i + 1);
					}
					
					// to the opposite side?
					int opp = (i + spots.length/2) % spots.length;
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
			shuffle(); // for good measure.
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
	
	// do the Knuth shuffle
	public void shuffle()
	{
		for (int i = spots.length-1; i > 0; i--) {
			int j = (int)Math.floor(Math.random()*i);
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
