/**
	Personalities hold the likeness to others.
	By: Andrew Helenius
	Date: 10/13/2013
**/

import java.util.Scanner;

public class Personality
{
	// this is a table where the index is the other person's ID
	// and the value is how much they like that person.
	private int[] likes;
	
	// gender is used in the overall happiness calculation.
	private boolean gender; // T = Male; F = Female;
	
	// this is like their name; it's used to indicate how much
	// person A likes person B.
	private int ID;
	
	// initializes this personality.
	public Personality(boolean gen, int id)
	{
		gender = gen;
		ID = id;
	}

	// sets up this personality: (likes who & by how much).
	public void readIn(Scanner scan, int total)
	{
		likes = new int[total];
		for(int i = 0; i < total; ++i) {
			likes[i] = scan.nextInt();
		}
	}
	
	// by talking, people find out about each other.
	public int talk(Personality other)
	{
		return likes[other.ID] + other.likes[ID];
	}
	
	// compares this personalities gender to the other.
	public int compare(Personality other)
	{
		return (gender == other.gender) ? 0 : 1; 
	}
	
	// gets the id of this person.
	public int getID()
	{
		return ID;
	}
	
	public boolean getGender()
	{
		return gender;
	}
}
