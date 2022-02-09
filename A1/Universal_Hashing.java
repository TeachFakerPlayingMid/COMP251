import java.io.*;
import java.util.*;

public class Universal_Hashing extends Open_Addressing{
	int a;
	int b;
	int p;

	protected Universal_Hashing(int w, int seed) {
		super(w, seed, -1);
		int temp = this.m+1; // m is even, so temp is odd here
		while(!isPrime(temp)) {
			temp += 2;
		}
		this.p = temp;
		a = generateRandom(0, p, seed);
		b = generateRandom(-1, p, seed);
	}
	
	/**
	 * Checks if the input int is prime
	 */
	public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i*i <= n; i++) {
        	if (n % i == 0) return false;
        }
        return true;
    }
	
	/**
     * Implements universal hashing
     */
	@Override
    public int probe(int key, int i) {
    	//TODO: implement this function and change the return statement
		return ((((a * key + b) % p) % m) + i) % power2(r);
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions encountered,
     * and resizes the hash table if needed
     */
	@Override
    public int insertKeyResize(int key)
	{
    	//TODO: implement this function and change the return statement
		float load_factor = (float) (size+1)/(float) m;
		int collision = 0;

		if (load_factor > 0.75)
		{
			this.m = this.m *2;
			this.r = r+1;
			this.w = (r-1) * 2 + 2;
			int temp_new = this.m+1;
			while (!isPrime(temp_new))
			{
				temp_new += 2;
			}
			this.p = temp_new;
			a = generateRandom(0, p, seed);
			b = generateRandom(-1, p, seed);

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


    	//return -1;
    }

    /*
    @Override
	public int insertKey(int key)
	{
		int temp_index = this.probe(key,0);

		int collision = 0;

		while (this.Table[temp_index] != -1)
		{
			//temp_index = temp_index + 1;
			collision = collision + 1;
			if (collision == this.Table.length)
				return collision;
			temp_index = probe(key,collision);
		}
		//int insert_index = probe(key,temp_index);
		this.Table[temp_index] = key;
		size = size+1;
		return collision;
	}
     */
}
