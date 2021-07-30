import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.ufc.mdcc.spoonmetrics.SpoonMetricsApi;

public class Run {

	public static void main(String[] args) {
		try {
			String filePath = "./libs/project-list.csv";
			FileReader file = new FileReader(filePath);
			BufferedReader reader = new BufferedReader(file);
			String line = reader.readLine();
			
			while (line != null) {
				String[] values = line.split(";");
				System.out.println("Mining metrics suite of " + values[0] + " at " + values[1]);
				SpoonMetricsApi.mine(values[0], values[1]);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
