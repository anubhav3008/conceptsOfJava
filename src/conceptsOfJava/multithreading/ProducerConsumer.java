package conceptsOfJava.multithreading;

import java.util.Vector;

public class ProducerConsumer {

	public static void main(String args[]){

		Vector x= new Vector();
		int size=10;
		Thread t1=new Thread(new Producer(x, size));

		Thread t2= new Thread(new Consumer(x, size));

		t2.start();
		t1.start();
	}



}
class Producer implements Runnable{
	volatile Vector list;
	int size;
	Producer(Vector list, int size){
		this.list = list;
		this.size =  size;
	}
	@Override
	public void run() {
		System.out.println("starting producer");
		while(true){
			while(list.size()==size){

				synchronized (list) {
					try {
						System.out.println("waiting for the consumer to consumer something as the queue is full");
						list.wait();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}

			}


			synchronized (list) {

				System.out.println("producing at index =  "+(list.size()));
				list.add((int)(Math.random()*10));
				list.notifyAll();
				System.out.println("list status at producer code = "+list);
			}

		}

	}
}




class Consumer implements Runnable{
	Vector list;
	int size;
	Consumer(Vector list, int size){
		this.list = list;
		this.size =  size;
	}
	@Override
	public void run() {
		System.out.println("starting consumer");
		while(true){
			while(list.isEmpty()){

				synchronized (list) {
					try {
						System.out.println("waiting for the producer to produce something as the queue is empty");
						list.wait();
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}
			synchronized (list) {
				System.out.println("removing");
				list.remove(0);
				list.notifyAll();
			}
			System.out.println("list status at consumer code = "+list);
			
		}
	}

}
