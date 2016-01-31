
//ligeorge Lou George PA3

import java.util.*;
import java.io.*;

public class Sparse {
	public static void main(String[] args){
		   
		if (args.length < 2){
		   	die("inFile outFile");
		}

		String inputFileName = args[0];
		String outputFileName = args[1];

		BufferedReader br = null;

		try{
		   	br = new BufferedReader (new FileReader(inputFileName));
		}catch(FileNotFoundException e){
		   	die ("File " + inputFileName + " not found.");
		}

		Scanner fileIn = new Scanner(br);

		int size = Integer.parseInt(fileIn.next()) + 1;
		int matrixSizeA = Integer.parseInt(fileIn.next());
		int matrixSizeB = Integer.parseInt(fileIn.next());
		Matrix A = new Matrix(size);
		Matrix B = new Matrix(size);

		for (int i = 0; i < matrixSizeA; i++){
		   	int row = Integer.parseInt(fileIn.next());
		   	int column = Integer.parseInt(fileIn.next());
		   	double data = Double.parseDouble(fileIn.next());
		   
		   	A.changeEntry(row, column, data);
		}

		for (int i = 0; i < matrixSizeB; i++){
		   	int row = Integer.parseInt(fileIn.next());
		   	int column = Integer.parseInt(fileIn.next());
		   	double data = Double.parseDouble(fileIn.next());
		   
		   	B.changeEntry(row, column, data);
		}



		try{
		   	PrintStream fileOut = new PrintStream(new FileOutputStream(outputFileName));
		   	fileOut.println("A has " + A.getNNZ() + " non-zero entries:");
			
		   	fileOut.println(A);
		   	fileOut.println("B has " + B.getNNZ() + " non-zero entries:");
		   	fileOut.println(B);
		   
		   	Matrix C = A.scalarMult(1.5);
		   	fileOut.println("(1.5) * A = ");
		   	fileOut.println(C);
		   
		   	C = A.add(B);
		   	fileOut.println("A + B = ");
		   	fileOut.println(C);
		   
		   	C = A.add(A);
		   	fileOut.println("A + A =");
		   	fileOut.println(C);
		   
		   	C = B.sub(A);
		   	fileOut.println("B - A = ");
		   	fileOut.println(C);
		   
		   	C = A.sub(A);
		   	fileOut.println("A - A = ");
		   	fileOut.println(C);
		   
			C = A.transpose();
			fileOut.println("Transpose of A = ");
			fileOut.println(C);

			C = A.mult(B);
			fileOut.println("A * B = ");
			fileOut.println(C);

			C = B.mult(B);
			fileOut.println("B * B = ");
			fileOut.println(C);
		   
		   	fileOut.close();
		}catch(FileNotFoundException e){
		   	die("File " + outputFileName + " could not be written to.");
		}
				     
		fileIn.close();
		   
	}//main

   	public static void die(String msg){
		System.err.println("Fatal Error: " + msg);
		System.exit(1);
   	}//die
}
