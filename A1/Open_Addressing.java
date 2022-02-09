import java.io.*;
import java.util.*;

public class Open_Addressing {
	public int m; // number of SLOTS AVAILABLE
	public int A; // the default random number
	int w;
	int r;
	int seed;
	public int[] Table;
	public static final double MAX_LOAD_FACTOR = 0.75;
	int size; // number of elements stored in the hash table

	protected Open_Addressing(int w, int seed, int A) {
		this.seed = seed;
		this.w = w;
		this.r = (int) (w - 1) / 2 + 1;
		this.m = power2(r);
		if (A == -1) {
			this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
		} else {
			this.A = A;
		}
		this.Table = new int[m];
		for (int i = 0; i < m; i++) {
			Table[i] = -1;
		}
		this.size = 0;
	}

	/**
	 * Calculate 2^w
	 */
	public static int power2(int w) {
		return (int) Math.pow(2, w);
	}

	public static int generateRandom(int min, int max, int seed) {
		Random generator = new Random();
		if (seed >= 0) {
			generator.setSeed(seed);
		}
		int i = generator.nextInt(max - min - 1);
		return i + min + 1;
	}

	/**
	 * Implements the hash function g(k)
	 */
	public int probe(int key, int i) {
		//TODO: implement this function and change the return statement.
		return (((A * key) % power2(w)) / power2(w - r) + i ) % power2(r);
	}

	/**
	 * Inserts key k into hash table. Returns the number of collisions encountered
	 */
	public int insertKey(int key) {
		//TODO: implement this function and change the return statement.

		//linear probing
		int temp_index = probe(key,0);

		int collision = 0;

		while (Table[temp_index] != -1)
		{
			//temp_index = temp_index + 1;
			collision = collision + 1;
			if (collision == Table.length)
				return collision;
			temp_index = probe(key,collision);
		}
		//int insert_index = probe(key,temp_index);
		Table[temp_index] = key;
		size = size+1;
		return collision;
	}


	/**
	 * Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions
	 */
	public int insertKeyArray(int[] keyArray) {
		int collision = 0;
		for (int key : keyArray) {
			collision += insertKey(key);
		}
		return collision;
	}

	/**
	 * @param the key k to be searched
	 * @return an int array containing 2 elements:
	 * first element = index of k in this.Table if the key is present, = -1 if not present
	 * second element = number of collisions occured during the search
	 */
	public int[] searchKey(int k) {
		//TODO: implement this function and change the return statement
		int[] output = {-1, -1};

		int search_value = probe(k,0);
		int collision = 0;

		//if no collision
		while (Table[search_value] != k)
		{
			collision = collision+1;
			search_value = probe(k,collision);
			if (collision == Table.length)
			{
				output[1] = collision;
				return output;
			}
			//return output;
		}
		output[0] = search_value;
		output[1] = collision;

		return output;
	}
	/**
	 * Removes key k from hash table. Returns the number of collisions encountered
	 */
	public int removeKey(int k){
		//TODO: implement this function and change the return statement.

		int collision = 0;
		int[] search_K = searchKey(k);

		if (search_K[0] == -1)
		{
			collision = search_K[1];
			return collision;
		}

		int remove_index = search_K[0];
		Table[remove_index] = -1;
		collision = search_K[1];
		size=size-1;
		return collision;

		//linear probing
		/*int temp_index = probe(k,0);

		int collision = 0;

		while (Table[temp_index] != k)
		{
			collision = collision +1;
			if (collision = size)
				return
		}

		int removeKey_index = probe(k,temp_index);
		Table[removeKey_index] = -1;*/

		//return collision;
	}

	/**
	 * Inserts key k into hash table. Returns the number of collisions encountered,
	 * and resizes the hash table if needed
	 */

	public int insertKeyResize(int key) {
		//TODO: implement this function and change the return statement
		float load_factor = (float) (size+1)/(float) m;
		int collision = 0;
		if (load_factor > 0.75)
		{
			this.m = m*2;
			this.r = r+1;
			this.w = (r-1) * 2 + 2;
			//this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
			if (A == -1) {
				this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
			} else {
				this.A = A;
			}

			int[] copy_table = Table;
			int[] resize_table = new int[m];
			int[] resize_table2 = new int[m];
			System.arraycopy(Table,0,resize_table,0,Table.length);
			Table = resize_table2;

			for (int i = 0;i<Table.length;i++)
			{
				Table[i] = -1;
			}

			for (int i = 0;i<resize_table.length;i++)
			{
				if (resize_table[i] == 0)
				{
					resize_table[i] = -1;
				}
			}

			for (int i = 0; i < resize_table.length;i++)
			{
				if (resize_table[i] != -1)
				{
					insertKey(resize_table[i]);
				}
			}
			collision = insertKey(key);

			size = 0;

			for (int i = 0; i < Table.length; i++)
			{
				if (Table[i] != -1)
					size++;
			}
			return collision;
		}
		else
		{
			return insertKey(key);
		}

	}



	/**
	 * Sequentially inserts a list of keys into the HashTable, and resize the hash table
	 * if needed. Outputs total number of collisions
	 */
	public int insertKeyArrayResize(int[] keyArray) {
		int collision = 0;
		for (int key : keyArray) {
			collision += insertKeyResize(key);
		}
		return collision;
	}

	/**
	 * @param the key k to be searched (and relocated if needed)
	 * @return an int array containing 2 elements:
	 * first element = index of k in this.Table (after the relocation) if the key is present, 
	 * 				 = -1 if not present
	 * second element = number of collisions occured during the search
	 */
	public int[] searchKeyOptimized(int k) {
		//TODO: implement this function and change the return statement
		int[] output = {-1, -1};
		int temp_index = probe(k,0);
		int[] searchKey_output = searchKey(k);
		int original_collision = searchKey_output[1];

		int collision = 0;

		if (searchKey_output[0] == -1)
		{
			return searchKey_output;
		}

		//if removed and the slot is empty, optimizing

		while (Table[temp_index] != -1)
		{

			if (collision == Table.length)
			{
				output[1] = collision;
				return output;
			}
			if (collision == original_collision)
			{
				output = searchKey_output;
				return output;
			}
			collision = collision+1;
			temp_index = probe(k,collision);

		}
		Table[temp_index] = k;
		Table[searchKey_output[0]] = -1;
		output[1] = collision;
		output[0] = temp_index;

		return output;


	}

	/**
	 * @return an int array of n keys that would collide with key k
	 */
	public int[] collidingKeys(int k, int n, int w) {
		//TODO: implement this function and change the return statement
		int[] output = new int[n];
		//int[] target_Key = searchKeyOptimized(k);
		//int target_Value = target_Key[0];

		for (int i = 0; i < output.length;i++)
		{

			//k = k + 1;
			int attack_key =  k + i*power2(w+1);
			//System.out.println(probe(attack_key,0));
			output[i] = attack_key;
		}

		return output;
	}

	// add a help method,clear the table
	public void clearAll()
	{
		for (int i = 0;i<Table.length;i++)
		{
			Table[i] = -1;
			size=size-1;
		}
	}
}
