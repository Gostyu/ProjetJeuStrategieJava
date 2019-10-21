package civilisation;

import javax.swing.JFrame;

public class TestTwoFrames implements Runnable{
	private Civilisation theFrame;
public TestTwoFrames(Civilisation theFrame) {
		this.theFrame = theFrame;
	}

public static void main(String[] args) {
	final Civilisation f1=new Civilisation();
	final Civilisation f2=new Civilisation();
	f1.setTitle("Fenetre 1");
	f2.setTitle("Fenetre 2");

	
	Thread t1=new Thread(new TestTwoFrames(f1));
	Thread t2=new Thread(new TestTwoFrames(f2));
	
	t1.start();
	t2.start();
}
	@Override
	public void run() {

		

	}
	
}