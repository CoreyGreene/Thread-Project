import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CosSeries implements Runnable {

	int counter = 0;
	double angle;
	int terms;

	CosSeries(double angle, int terms) {
		this.angle = angle;
		this.terms = terms;
	}

	public void run() {

		Boolean negValue = true;
		BigDecimal ans = new BigDecimal("0");
		long startTime = System.currentTimeMillis();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("cosValues.txt")))) {
			for (int i = 2; i < terms * 2 - 1; i += 2) {

				if (negValue) {
					ans = ans.add((ThreadProject.myPow(angle, i).divide(ThreadProject.factorial(i), 1000,
							RoundingMode.HALF_UP)).multiply(new BigDecimal(-1)));
					negValue = false;
					bw.newLine();
					bw.write(ans.add(new BigDecimal("1")).setScale(40, RoundingMode.CEILING).toPlainString());

				} else {
					ans = ans.add((ThreadProject.myPow(angle, i).divide(ThreadProject.factorial(i), 1000,
							RoundingMode.HALF_UP)));
					negValue = true;
					bw.newLine();
					bw.write(ans.add(new BigDecimal("1")).setScale(40, RoundingMode.CEILING).toPlainString());
				}
			}
		} catch (FileNotFoundException ex) {
			System.out.println(ex.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		ThreadProject.cosValue = ans.add(new BigDecimal("1"));
		ThreadProject.cosTime = duration;
	}

}
