
import java.util.Scanner;
import java.math.*;

/*
 * 
 * 7 terms will give accuracy equivalent to a Ti84
 * 
 */
public class ThreadProject {

	public static BigDecimal sinValue = new BigDecimal("1");
	public static BigDecimal cosValue = new BigDecimal("1");
	public static long sinTime = 0;
	public static long cosTime = 0;
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		int terms;

		double angle;
		System.out.print("Enter the angle in degrees: ");
		angle = input.nextDouble();

		angle = (convert(angle));

		System.out.print("Enter the number of terms to calculate in the sin series: ");
		terms = input.nextInt();
		long startTime = System.currentTimeMillis();
		Runnable sin = new SinSeries(angle, terms);
		Runnable cos = new CosSeries(angle, terms);

		Thread thread1 = new Thread(sin);
		Thread thread2 = new Thread(cos);

		thread1.start();
		thread2.start();

		while (true) {
			try {
				try {
					thread1.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					thread2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			} finally {
				System.out.println("cos value: " + cosValue);
				System.out.println("sin value: " + sinValue);
				// this is called when both threads are finished, this way we
				// KNOW we have values for sin/cos
				System.out
						.println("tan value: " + sinValue.divide(cosValue, 1000, RoundingMode.HALF_UP).toPlainString());
				long endTime = System.currentTimeMillis();

				System.out.println("sin time: " + sinTime);
				System.out.println("cos time: " + cosTime);
				System.out.println("Total Time: " + (endTime - startTime));
			}
		}
	}

	public static double convert(double angle) {

		BigDecimal multiple = new BigDecimal("0");
		BigDecimal div = new BigDecimal("180");
		multiple = getPi().divide(div, 1000, RoundingMode.HALF_UP);

		BigDecimal convAngle = new BigDecimal(angle);
		return convAngle.multiply(multiple).doubleValue();
	}

	public static BigDecimal getPi() {
		BigDecimal pi = new BigDecimal("3.14159265358979323846264338327950288419716939937510"
				+ "58209749445923078164062862089986280348253421170679"
				+ "82148086513282306647093844609550582231725359408128");
		return pi;
	}

	public static BigDecimal myPow(double num, int p) {
		BigDecimal answer = new BigDecimal(num);
		for (int i = 0; i < p - 1; i++) {
			answer = answer.multiply(new BigDecimal(num));
		}
		return answer;
	}

	public static BigDecimal factorial(int num) {
		BigDecimal answer = new BigDecimal(num);
		for (int i = num; i > 1; i--) {
			answer = answer.multiply(new BigDecimal(i - 1));
		}
		return answer;
	}

}