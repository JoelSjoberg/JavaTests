import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MultiVariateLinearReg {

	public static void main(String[] args) throws IOException {
		File learningExamples = new File("C:\\Users\\Sjöberg\\Desktop\\Master\\Education\\MachineLearning\\multilearn.TXT");
		
		double[][] matrix = readDataFrom(learningExamples);
		double[] y = new double[matrix.length];
		double[] theta = new double[matrix[0].length - 1];
		
		for(int i = 0; i < theta.length; i++) {
			theta[i] = 0;
		}
		for(int i = 0; i < y.length; i++) {
			y[i] = matrix[i][matrix.length - 1];
		}
		System.out.println(hypothesis(theta, matrix[0]));
		
	}
	
	static double hypothesis(double[] theta, double[] x)
	{
		// start sum with the first element in theta to add the bias value
		double sum = theta[0];
		for(int i = 1; i < x.length; i++){
			sum += theta[i] * x[i - 1];
		}
		return sum;
	}
	
	// read data from a file and convert that data into a M*N matrix
		static double[][] readDataFrom(File file) throws IOException {
			ArrayList <String[]> Data = new ArrayList <String[]>(); 
			double[][] matrix = null;
			BufferedReader buffer = null;
			
			// Open reader
			try {
				buffer = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {e.printStackTrace();}
			
			// read each line
			String line;
			while((line = buffer.readLine()) != null) {
				// Split by , after trimming unnecessary blank spaces
				Data.add(line.trim().split(","));	
			}
			matrix = new double[Data.size()][Data.get(0).length];
			
			for(int x = 0; x < matrix.length; x++) {
				for(int y = 0; y < matrix[0].length; y++) {
					// parse to double
					matrix[x][y] = Double.parseDouble(Data.get(x)[y]);
				}
			}
			return matrix;
		}
}
