import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class LinearRegresion {

	public static void main(String[] args) throws IOException {
		File learningExamples = new File("C:\\Users\\Sjöberg\\Desktop\\Master\\Education\\MachineLearning\\learn.TXT");

		double[][] matrix = readDataFrom(learningExamples);
	
		double[] theta = {0.0, 0.0};
		double alpha = 0.01;
		
		//matrix = normalize(matrix);
		
		for(int i = 0; i < 1000; i++) {
			theta = countError(theta, alpha, matrix);
		}
		
		System.out.println(h(1, theta));
	
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
	//redundant
	static double[][] findMinMax(double[][] matrix) {
		double[][] columnMinMax = new double[matrix[0].length][2];
		double min, max;
		for(int col = 0; col < matrix[0].length; col++) {
			int row = 0;
			min = matrix[row][col];
			max = matrix[row][col];
			for(; row < matrix.length; row++) {
				if(matrix[row][col] < min) min = matrix[row][col];
				if(matrix[row][col] > max) max = matrix[row][col];
			}
			columnMinMax[col][0] = min;
			columnMinMax[col][1] = max;
		}
		return columnMinMax;
	}
	// Redundant
	static double[][] normalize(double[][] matrix) {
		double[][] minmax = findMinMax(matrix);
		for(int row = 0; row < matrix.length; row++){
			for(int index = 0; index < matrix[row].length; index++) {
				double temp = matrix[row][index];
				matrix[row][index] = ((temp - minmax[index][0]) / (minmax[index][1] - minmax[index][0]));
			}
		}
		
		return matrix;
	}
	
	//Hypothesis method B0 + B1 * x[i]
	static double h(double x, double[] theta) {
		return (theta[0] + theta[1] * x);
	}
	
	static double Error(double[][] matrix, double[] theta) {
		// summate the errors
		double sum = 0;
		double m = matrix.length;
		for(int i = 0; i < matrix.length; i++) {
			sum += (h(matrix[i][0], theta) - matrix[i][1]);
			sum = Math.round(sum);
		}
		return ((1/ m) * sum);
	}
	
	// itterate and adjust the theta-values
	static double[] countError(double[] theta, double alpha, double[][] matrix) {
		double error = 0;
		for(int i = 0; i < matrix.length; i++) {
			error = Error(matrix, theta);
			theta[0] -= alpha * error;
			theta[1] -= alpha * error * matrix[i][0];
		}
		return theta;
	}
}