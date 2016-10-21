package sync;

public class Ejercicio3 {

	// supervisor
	protected static Supervisor<Thread> s;

	private static class Process1 extends Thread {
		public void run() {
			// section 1
			int local = s.Y.get();
			s.X.compareAndSet(5, local);
			// section 2
			s.X.set(s.Y.get());
			// section 3
			synchronized(s) {
				int temp = s.X.get();
				s.X.set(s.Y.get());
				s.Y.set(temp);
			}
		}
	}

	private static class Process2 extends Thread {
		public void run() {
			// section 1
			int local = s.X.get();
			s.Y.compareAndSet(10, local);
			// section 2
			s.Y.set(s.X.get());
			// section 3
			synchronized(s) {
				int temp = s.Y.get();
				s.Y.set(s.X.get());
				s.X.set(temp);
			}
		}
	}

	public static void main(String[] args) {
		Process1 p1 = new Process1();
		Process2 p2 = new Process2();
		Thread[] procs = new Thread[]{p1, p2};
		s = new Supervisor<Thread>(procs, 5, 10);
		s.run();
	}
}
