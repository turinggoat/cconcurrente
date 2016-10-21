package sync;

public class Ejercicio2 {

	// supervisor
	protected static Supervisor<Thread> s;

	public static class Process1 extends Thread {
		public void run() {
			// section 1
			s.Y.compareAndSet(s.X.get(), 10);
			if (s.Y.get() == 10)
				s.X.compareAndSet(s.Y.get(), 10);
			// section 2
			if (s.X.getAndAdd(5) != s.Y.getAndAdd(5))
				s.X.set(0);
			// section 3
			int local = s.Y.get();
			s.X.compareAndSet(5, local);
			local = s.X.get();
			s.Y.compareAndSet(10, local);
		}
	}

	private static class Process2 extends Thread {
		public void run() {
			// section 1
			s.Y.compareAndSet(s.X.get(), 5);
			if (s.Y.get() == 10)
				s.X.compareAndSet(s.Y.get(), 5);
			// section 2
			if (s.Y.getAndAdd(10) != s.Y.getAndAdd(10))
				s.Y.set(0);
			// section 3
			int local = s.Y.get();
			s.X.compareAndSet(5, local);
			local = s.X.get();
			s.Y.compareAndSet(10, local);
		}
	}

	public static void main(String[] args) {
		Process1 p1 = new Process1();
		Process2 p2 = new Process2();
		Thread[] procs = new Thread[]{p1, p2};
		s = new Supervisor<Thread>(procs, 0, 0);
		s.run();
	}
}
