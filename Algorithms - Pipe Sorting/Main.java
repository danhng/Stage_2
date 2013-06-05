package cw2;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
		//Arraylists to represent pipes in stock
		//Arraylist used as there is no limit to the amount of pipes in stock
		ArrayList<Pipe> pipeStock = new ArrayList<Pipe>();
		ArrayList<Pipe> pipeStock1 = new ArrayList<Pipe>();
		ArrayList<Pipe> pipeStock2 = new ArrayList<Pipe>();
	
		//Arraylist to represent customer orders
		ArrayList<Pipe> customerOrders = new ArrayList<Pipe>();
		ArrayList<Pipe> customerOrders1 = new ArrayList<Pipe>();
		ArrayList<Pipe> customerOrders2 = new ArrayList<Pipe>();
		
		//Arraylist to represent wholesaler stock
		ArrayList<Pipe> wholesaler = new ArrayList<Pipe>();
		ArrayList<Pipe> wholesaler1 = new ArrayList<Pipe>();
		ArrayList<Pipe> wholesaler2 = new ArrayList<Pipe>();
				
		//Populate arraylists
		
		//Number of orders
		for(int i = 0; i<100000; i++){
			
			
			CreateData.custOrder(customerOrders);				
			customerOrders1.add(new Pipe(customerOrders.get(i).getLength(), customerOrders.get(i).getColour()));
			customerOrders2.add(new Pipe(customerOrders.get(i).getLength(), customerOrders.get(i).getColour()));
			
		}	
		
		//Generate a few pipes for initial stock
		for(int i = 0; i<10; i++ ){
			
			CreateData.orderPipe(pipeStock);
			pipeStock1.add(new Pipe(pipeStock.get(i).getLength(), pipeStock.get(i).getColour()));
			pipeStock2.add(new Pipe(pipeStock.get(i).getLength(), pipeStock.get(i).getColour()));
			
		}		
		
		//Ensure wholesaler has enough stock
		for (int i = 0; i<3000000; i++){
			
			CreateData.orderPipe(wholesaler);
			wholesaler1.add(new Pipe(wholesaler.get(i).getLength(), wholesaler.get(i).getColour()));
			wholesaler2.add(new Pipe(wholesaler.get(i).getLength(), wholesaler.get(i).getColour()));
		}

				
		NextFit.runNext(pipeStock, wholesaler, customerOrders);
		
		FirstFit.runFirst(pipeStock2, wholesaler2, customerOrders2);
				
		BestFit.runBest(pipeStock1, wholesaler1, customerOrders1);		
		
				
		//TEST DATA
//		Pipe p1 = new Pipe(100, 1);
//		Pipe p2 = new Pipe(200, 1);
//		Pipe p3 = new Pipe(200, 1);
//		
//		Pipe o1 = new Pipe(95, 1);
//		Pipe o2 = new Pipe(50, 2);
//		Pipe o3 = new Pipe(50, 3);
//		
//		Pipe w1 = new Pipe(200, 1);
//		Pipe w2 = new Pipe(200, 2);
//		Pipe w3 = new Pipe(200, 3);
//		
//		ArrayList<Pipe> pipeStock3 = new ArrayList<Pipe>();
//		ArrayList<Pipe> wholesaler3 = new ArrayList<Pipe>();
//		ArrayList<Pipe> customerOrders3 = new ArrayList<Pipe>();
//		
//		pipeStock3.add(p1);
//		pipeStock3.add(p2);
//		pipeStock3.add(p3);
//
//		customerOrders3.add(o1);
//		customerOrders3.add(o2);
//		customerOrders3.add(o3);
//		
//		wholesaler3.add(w1);
//		wholesaler3.add(w2);
//		wholesaler3.add(w3);
		
		//CreateData.custOrder(customerOrders3);
		
		//System.out.println(customerOrders3.get(0).toString());
		
								
	}

}
