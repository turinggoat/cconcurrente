package sync;

public class Ejercicio1 {

	// supervisor
	protected static Supervisor<Thread> s;

	private static class Process1 extends Thread {
		public void run() {
			// section 1
			s.Y.set(5);
			for (s.X.set(0); s.X.get() < 10; s.X.getAndIncrement())
				s.Y.set(s.Y.get() * 2);
			// section 2
			if (s.X.compareAndSet(0, 5))
				s.Y.compareAndSet(s.Y.get(), 5);
			// section 3
			if (s.X.getAndAdd(5) == 5)
				s.X.getAndAdd(5);
		}
	}

	private static class Process2 extends Thread {
		public void run() {
			// section 1
			s.Y.set(5);
			for (s.X.set(0); s.X.get() < 10; s.X.getAndIncrement())
				s.Y.set(s.Y.get() * 2);
			// section 2
			if (s.X.compareAndSet(0, 10))
				s.Y.compareAndSet(s.Y.get(), 10);
			// section 3
			if (s.Y.getAndAdd(10) == 15)
				s.Y.getAndAdd(10);
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
