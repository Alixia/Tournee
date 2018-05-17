
public class Test {

	public static void main(String[] args){
		double[] tab = {1.2,3.,4.5};
		double[] copie = tab.clone();
		System.out.println(tab[0]+" "+tab[1]+" "+tab[2]+ "\n" + copie[0]+" "+copie[1]+" "+copie[2]);
	}
	
}
