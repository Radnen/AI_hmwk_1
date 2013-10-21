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
	// and netH is their grumpiness.
	private int ID, netH;
	
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
			netH += likes[i];
		}
	}
	
	// by talking, people find out about each other.
	public int talk(Personality other)
	{
		return likes[other.ID];
	}
	
	// compares this personalities gender to the other.
	public int compare(Personality other)
	{
		return (gender == other.gender) ? 0 : 1; 
	}
	
	// gets the net happiness of this individual
	public int getNetHappiness()
	{
		return netH;
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
