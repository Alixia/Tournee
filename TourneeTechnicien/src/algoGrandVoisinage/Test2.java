package algoGrandVoisinage;

import java.util.PriorityQueue;
import java.util.Random;

public class Test2 {
	
	public static void main(String[] args){
		PriorityQueue<Integer> p = new PriorityQueue<>();
		p.add(10);
		p.add(15);
		p.add(7);
		while(!p.isEmpty()) {
			System.out.println(p.poll());
		}
		System.out.println("***************************");
		int[] ints = new Random().ints(0, 100).distinct().limit(10).toArray();
		for (int i : ints) {
			System.out.println(i);
		}
		System.out.println("***************************");
		PriorityQueue<Integer> p2 = new PriorityQueue<>();
		for (int i : ints) {
			p2.add(i);
		}
		while(!p2.isEmpty()) {
			System.out.println(p2.poll());
		}
	}
}
