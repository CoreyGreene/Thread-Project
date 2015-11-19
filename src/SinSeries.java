import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SinSeries implements Runnable {

	double angle;
	int terms, counter = 0, printCount;// = terms/10;

	SinSeries(double angle, int terms) {
		this.angle = angle;
		this.terms = terms;
		this.printCount = terms / 100;
	}

	public void run() {

		Boolean negValue = true;
		BigDecimal ans = new BigDecimal("0");
		long startTime = System.currentTimeMillis();
		System.out.print("<=");

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("sinValues.txt")))) {

			for (int i = 3; i < terms * 2; i += 2) {

				if (counter == printCount) {
					counter = 0;
					System.out.print("=");
				}
				counter++;

				if (negValue) {
					ans = ans.add((ThreadProject.myPow(angle, i).divide(ThreadProject.factorial(i), 1000,
							RoundingMode.HALF_UP)).multiply(new BigDecimal(-1)));
					negValue = false;
					bw.newLine();
					bw.write(ans.add(new BigDecimal(angle)).setScale(40, RoundingMode.CEILING).toPlainString());
				} else {
					ans = ans.add((ThreadProject.myPow(angle, i).divide(ThreadProject.factorial(i), 1000,
							RoundingMode.HALF_UP)));
					bw.newLine();
					bw.write(ans.add(new BigDecimal(angle)).setScale(40, RoundingMode.CEILING).toPlainString());
					negValue = true;
				}
			}

		} catch (FileNotFoundException ex) {
			System.out.println(ex.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(">");
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		ThreadProject.sinValue = ans.add(new BigDecimal(angle));
		ThreadProject.sinTime = duration;
	}

}