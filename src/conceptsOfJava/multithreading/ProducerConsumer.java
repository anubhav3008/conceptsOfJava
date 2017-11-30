package conceptsOfJava.multithreading;

import java.util.ArrayList;

public class ProducerConsumer {

	public static void main(String args[]){

		ArrayList<Integer> x= new ArrayList<>();
		int size=10;
		Thread t1=new Thread(new Producer(x, size));

		Thread t2= new Thread(new Consumer(x, size));

		t2.start();
		t1.start();
	}



}
class Producer implements Runnable{
	ArrayList<Integer> list;
	int size;
	Producer(ArrayList<Integer> list, int size){
		this.list = list;
		this.size =  size;
	}
	@Override
	public void run() {
		System.out.println("starting producer");
		while(true){
			synchronized (list) {
				if(list.size()<size){
					System.out.println("producing at index =  "+(list.size()));
					list.add((int)Math.random()*10);
					list.notifyAll();
					System.out.println("list status at producer code = "+list);
				}
				else{
					try {
						System.out.println("waiting for the consumer to consumer something as the queue is full");
						list.wait();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					System.out.println("producing at index =  "+(list.size()));
					list.add((int) Math.random()*10);
					System.out.println("list status at producer code = "+list);
					list.notifyAll();
				}


			}
		}

	}
}




class Consumer implements Runnable{
	ArrayList<Integer> list;
	int size;
	Consumer(ArrayList<Integer> list, int size){
		this.list = list;
		this.size =  size;
	}
	@Override
	public void run() {
		System.out.println("starting consumer");
		while(true){
			synchronized (list) {
				if(list.size()>0){
					System.out.println("removing the index = "+(list.size()-1));
					list.remove(list.size()-1);
					System.out.println("list status at consumer code = "+list);
					list.notifyAll();
				}
				else{
					try {
						list.wait();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					System.out.println("removing the index = "+(list.size()-1));
					list.remove(list.size()-1);
					System.out.println("list status at consumer code = "+list);
					list.notifyAll();
				}


			}
		}

	}
}