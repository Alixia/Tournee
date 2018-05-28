package algoGrandVoisinage;

import java.util.PriorityQueue;

public class Test2 {
	
	public static void main(String[] args){
		PriorityQueue<Integer> p = new PriorityQueue<>();
		p.add(10);
		p.add(15);
		p.add(7);
		while(!p.isEmpty()) {
			System.out.println(p.poll());
		}
	}

}
