import java.io.*;
import java.util.*;


public class main {     


	public static void main(String[] args) {
		//TODO:build the hash table and insert keys using the insertKeyArray function.
		Open_Addressing testTable = new Open_Addressing(5, 0,10);
		//Open_Addressing testTable2 = new Open_Addressing(5,0,10);
		Universal_Hashing testTable3 = new Universal_Hashing(5,0);


		int collision1 = testTable.insertKey(23);
		//testTable.insertKey(23);
		testTable.insertKey(17);
		testTable.insertKey(516);
		testTable.insertKey(89);
		testTable.insertKey(3000);
		testTable.insertKey(2);
		int collision2 = testTable.insertKey(12);
		testTable.insertKey(12);
		int collision3 = testTable.insertKey(43);
		testTable.insertKey(43);

		//int[] a = {11,24,787,99,80,21,35,40,900,1010,1,13,171,46,999,9999};

		//testTable.insertKeyArray(a);

		//testTable.removeKey(43);
		//testTable.removeKey(516);
		//int insert = testTable.insertKey(3000);
		//int[] search = testTable.searchKey(12);
		//int[] Optimized = testTable.searchKeyOptimized(43);
		//testTable.clearAll();

		int ResizeCollision = testTable.insertKeyResize(957);

		//System.out.println("number of SLOTS AVAILABLE : "+ testTable.m);
		//System.out.println("A value = " + testTable.A);
		//System.out.println("number of elements stored in the hash table: " + testTable.size);

		//System.out.println("insert oversize test: " + insert);
		//System.out.println("Search Key: " + Arrays.toString(search));

		//System.out.println("Resize Collision: " + ResizeCollision);

		//System.out.println(Arrays.toString(Optimized));

		//int[] collideKey = testTable.collidingKeys(0,10,10);

		System.out.println(Arrays.toString(testTable.Table));
		//System.out.println(Arrays.toString(collideKey));


		//System.out.println("probe: " + testTable.probe(12,0));

		//System.out.println("collision: " + collision);
		//System.out.println();

		//-----------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------

		/*
		int collision1 = testTable2.insertKey(39);
		int collision2 = testTable2.insertKey(23);
		int collision3 = testTable2.insertKey(55);

		testTable2.removeKey(23);

		int[] Optimized = testTable2.searchKeyOptimized(39);

		//int[] search = testTable2.searchKey(39);
		//System.out.println("search key: " + Arrays.toString(search));

		System.out.println(Arrays.toString(testTable2.Table));
		System.out.println(Arrays.toString(Optimized));
		System.out.println("probe: " + testTable2.probe(39,0));
		//System.out.println("optimized collision: " + collision3);
		*/



		//int collision1 = testTable3.insertKey(23);
		testTable3.insertKey(23);
		testTable3.insertKey(17);
		testTable3.insertKey(516);
		testTable3.insertKey(89);
		testTable3.insertKey(3000);
		testTable3.insertKey(2);
		//int collision2 = testTable3.insertKey(12);
		testTable3.insertKey(12);
		//int collision3 = testTable3.insertKey(43);
		testTable3.insertKey(43);
		/*
		testTable3.insertKey(1001);
		testTable3.insertKey(777);
		testTable3.insertKey(4396);
		testTable3.insertKey(7);
		testTable3.insertKey(35);
		testTable3.insertKey(453);

		 */

		//testTable3.clearAll();



		//testTable3.insertKeyResize();

		int Resize = testTable3.insertKeyResize(999);
		//System.out.println(testTable3.probe(999,0));
		//System.out.println("Resize collision: " + Resize);

		System.out.println(Arrays.toString(testTable3.Table));

		//System.out.println(testTable3.probe(42,0));
		//System.out.println("a: " + testTable3.a);
		//System.out.println("b: " + testTable3.b);

	}
}