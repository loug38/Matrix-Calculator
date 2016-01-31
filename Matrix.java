//ligeorge Lou George PA3

public class Matrix {
	
	private class Entry{
		private int column;
		private double data;
		
		Entry(){
			column = 0;
			data = 0;			
		}
		
		public void setColumn(int c){ column = c; }
		public void setData(double d){ data = d; }
		
		public int getColumn(){ return column; }
		public double getData(){ return data; }

		
	}
	
	
	List[] row;
	int matrixSize = 0;
	
	// Constructor=====================================================
	// Makes a new n x n zero Matrix. pre: n>=1
	Matrix(int n){							 
		row = new List[n];
		
		for (int i = 0; i < n; i++)
			row[i] = new List();
		
		matrixSize = n;
	}
	
	
	// Access functions================================================
	// Returns n, the number of rows and columns of this Matrix
	int getSize(){ return matrixSize; }		 
	
	// Returns the number of non-zero entries in this Matrix
	int getNNZ(){ 							
		int ret = 0, hold = 0;
		
		for (int i = 1; i < matrixSize; i++){
			hold = row[i].getIndex();
			row[i].moveTo(0);
			
			while (row[i].getElement() != row[i].back()){
				if (row[i].getIndex() == -1)
					break;
				ret++;
				row[i].moveNext();
			}
			if (row[i].getElement() == row[i].back())
				ret++;
			row[i].moveTo(hold);
			
		}		
		return ret;
	}//getNNZ

	// overrides Object's equals() method
	public boolean equals(Object x){ 
		if (matrixSize != ((Matrix)x).getSize())
			return false;
		
		for (int i = 1; i < matrixSize; i++){
			if (!(row[i].equals(((Matrix)x).row[i])))
				return false;
		}
		return true;
	}//equals
	
	// Mutators procedures=============================================
	// sets this Matrix to the zero state
	void makeZero(){ 							
		for (int i = 0; i < matrixSize; i++){
			row[i] = null;
		}
		matrixSize = 0;
	}//makeZero

	// returns a new Matrix having the same entries as this Matrix
	Matrix copy(){								
		Matrix ret = new Matrix(matrixSize);
		Object insert = null;

		for(int i = 0; i < matrixSize; i++){
			List pass = new List();
			for (int j = 0; j <row[i].length(); j++){
				row[i].moveTo(j);
				insert = (Entry)row[i].getElement();
				pass.append(insert);
			}
			ret.row[i] = pass;
		}
		return ret;
	}//copy
	
	void changeEntry(int i, int j, double x){
		Entry insertEntry = new Entry();
		insertEntry.setColumn(j);
		insertEntry.setData(x);
		int column = 0;
		boolean finished = false;
		
		if (x == 0)
			return;
				
		if (row[i].getElement() == null){
			column = 0;
		}else{
			column = ((Entry)row[i].getElement()).getColumn();
		}

		if(row[i].length() > 0){
			row[i].moveTo(0);
			//Handles base case
			if (row[i].length() == 1){														
				if ((column == j) && !finished){
					((Entry)row[i].getElement()).setData(insertEntry.getData());
					finished = true;
				}
				
				if ((column < j) && !finished){
					row[i].insertAfter(insertEntry);
					finished = true;
				}else{
					row[i].insertBefore(insertEntry);
				}
				return;
			}
			
			//Handles cases beyond the first column
			for (int k = 0; k < row[i].length(); k++){										
				row[i].moveTo(k);													

				if (column > j && !finished){
					row[i].insertBefore(insertEntry);
					finished = true;
				}
				if (column == j && !finished){
					row[i].insertAfter(insertEntry);
					finished = true;		
				}
				if ((k == row[i].length() - 1) && !finished){
					row[i].insertAfter(insertEntry);
					finished = true;
				}
			}
		}else{
			//If its the last in a row
			row[i].append(insertEntry);														
		}		
	}//changeEntry

	
	// returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x){
		Matrix ret = new Matrix(matrixSize);
		int hold = 0, jHold = 0;
		double dataHold = 0;
		
		for (int i = 1; i < matrixSize; i++){
			hold = row[i].getIndex();		
			row[i].moveTo(0);
			
			while (row[i].getElement() != row[i].back()){
				if (row[i].getIndex() == -1 )
					break;
				dataHold = x * (((Entry)row[i].getElement()).getData());
				jHold = ((Entry)row[i].getElement()).getColumn();
				ret.changeEntry(i, jHold, dataHold);
				row[i].moveNext();
			}
			
			if (row[i].getElement() == row[i].back()){
				dataHold = x * (((Entry)row[i].getElement()).getData());
				jHold = ((Entry)row[i].getElement()).getColumn();
				ret.changeEntry(i, jHold, dataHold);
			}
			
			row[i].moveTo(hold);
		}
		return ret;
	}//scalarMult

	// returns a new Matrix that is the sum of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix add(Matrix M){
		
		if (M.getSize() != getSize())
			die ("Invalid matrix size for add.");
		
		if (M.matrixSize != matrixSize)
			return null;
		
		Matrix ret = new Matrix(matrixSize);
		int hold = 0;
		
		double currentData = 0;
		double mData = 0;
		int currentColumn = 0;
		int mColumn = 0;
		
		for (int i = 1; i < matrixSize; i++){
			hold = row[i].getIndex();
			row[i].moveTo(0);
			M.row[i].moveTo(0);
			
			for(int j = 0; j < row[i].length(); j++){
				
				if (row[i].getElement() == null && M.row[i].getElement() == null )
					break;
				
				if (M.row[i].getElement() == null){
					mColumn = 0;
					mData = 0;
				}else{
					mColumn = ((Entry)M.row[i].getElement()).getColumn();
					mData = ((Entry)M.row[i].getElement()).getData();
				}
				
				if (row[i].getElement() == null){
					currentColumn = 0;
					currentData = 0;
				}else{
					currentColumn = ((Entry)row[i].getElement()).getColumn();
					currentData = ((Entry)row[i].getElement()).getData();
				}
				
				if (currentColumn == 0){
					ret.changeEntry(i, mColumn, (currentData + mData));
					break;
				}
				
				if (mColumn == 0){
					ret.changeEntry(i, currentColumn, (currentData + mData));
					break;
				}
				
				if (currentColumn > mColumn){
					ret.changeEntry(i, mColumn, (mData));
					M.row[i].moveNext();
				}
				
				if (currentColumn < mColumn){
					ret.changeEntry(i, currentColumn, (currentData));
					row[i].moveNext();
				}
				
				if (currentColumn == mColumn){
					ret.changeEntry(i, currentColumn, (currentData + mData));
					if (row[i] == M.row[i]){
						row[i].moveNext();
					}else{
						M.row[i].moveNext();
						row[i].moveNext();
					}
				}
				
			}
			row[i].moveTo(hold);

		}
		return ret;
	}//add


	// returns a new Matrix that is the difference of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix sub(Matrix M){
		if (M.getSize() != getSize())
			die ("Invalid matrix size for subtract.");
		
		Matrix ret = new Matrix(matrixSize);
		int hold = 0;
		
		double currentData = 0;
		double mData = 0;
		int currentColumn = 0;
		int mColumn = 0;
		
		for (int i = 1; i < matrixSize; i++){
			hold = row[i].getIndex();
			row[i].moveTo(0);
			M.row[i].moveTo(0);
			
			for(int j = 0; j < row[i].length(); j++){
				
				if (row[i].getElement() == null && 
					M.row[i].getElement() == null){
					break;
				}
				
				if (M.row[i].getElement() == null){
					mColumn = 0;
					mData = 0;
				}else{
					mColumn = ((Entry)M.row[i].getElement()).getColumn();
					mData = ((Entry)M.row[i].getElement()).getData();
				}
				
				if (row[i].getElement() == null){
					currentColumn = 0;
					currentData = 0;
				}else{
					currentColumn = ((Entry)row[i].getElement()).getColumn();
					currentData = ((Entry)row[i].getElement()).getData();

				}
				
				if (currentColumn == 0){
					ret.changeEntry(i, mColumn, (currentData -mData));
					break;
				}
				
				if (mColumn == 0){
					ret.changeEntry(i, currentColumn, (currentData - mData));
					break;
				}
				
				if (currentColumn > mColumn){
					ret.changeEntry(i, mColumn, (0-mData));
					M.row[i].moveNext();
				}
				
				if (currentColumn < mColumn){
					ret.changeEntry(i, currentColumn, (currentData));
					row[i].moveNext();
				}
				
				if (currentColumn == mColumn){
					ret.changeEntry(i, currentColumn, (currentData - mData));
					row[i].moveNext();
					M.row[i].moveNext();
				}
				
			}
			row[i].moveTo(hold);

		}
		return ret;
	}//sub
	

	// returns a new Matrix that is the transpose of this Matrix
	Matrix transpose(){
		Matrix ret = new Matrix(matrixSize);
		
		for (int i = 1; i < matrixSize; i++){
			row[i].moveTo(0);
			while (row[i].getElement() != null){
				int column = ((Entry)row[i].getElement()).getColumn();
				double data = ((Entry)row[i].getElement()).getData();
				ret.changeEntry(column, i, data);
				row[i].moveNext();
			}
		}
		return ret;
	}//transpose

	//Returns the multiplication of this matrix and the matrix M
	Matrix mult(Matrix M){		
		if (M.getSize() != getSize())
			die ("Invalid matrix size for multiply.");
		
		Matrix ret = new Matrix(matrixSize);
		Matrix hold = new Matrix(matrixSize);
		hold = M.transpose();
		double product = 0;
		Entry pass = new Entry();
		
		for (int i = 1; i < matrixSize; i++){
			if (row[i].length() != 0){
				for (int j = 1; j <matrixSize; j++){
					if (hold.row[j].length() == 0){
						continue;
					}else{
						product = dotProduct(row[i], hold.row[j]);
						if (product != 0){
							pass = new Entry();
							pass.setData(product);
							pass.setColumn(j);
							
							ret.row[i].append(pass);
						}
					}
				}
			}
		}
		return ret;
	}//mult
	
	//Returns the dot product of this matrix and matrix M
	double dotProduct(List A, List B){
		double ret = 0;
		
		A.moveTo(0);
		B.moveTo(0);
		
		int mColumn = 0;
		int currentColumn = 0;
		double mData = 0;
		double currentData = 0;
		
		while ((A.getIndex() != -1) && (B.getIndex() != -1)){
			
			if (A.getElement() == null && B.getElement() == null )
				break;
			
			if (B.getElement() == null){
				mColumn = 0;
				mData = 0;
			}else{
				mColumn = ((Entry)B.getElement()).getColumn();
				mData = ((Entry)B.getElement()).getData();
			}
			
			if (A.getElement() == null){
				currentColumn = 0;
				currentData = 0;
			}else{
				currentColumn = ((Entry)A.getElement()).getColumn();
				currentData = ((Entry)A.getElement()).getData();
			}
			
			if (currentColumn == mColumn){
				ret = ret + (currentData * mData);
				A.moveNext();
				B.moveNext();
			}else if (currentColumn < mColumn){
				A.moveNext();
			}else if (currentColumn > mColumn){
				B.moveNext();
			}
		}
		return ret;
	}//dotProduct
		
	
	// Other functions=================================================
	public String toString(){
		String ret = "";
		boolean nextLine = false;
		
		for (int i = 1; i < getSize(); i++){
			int temp = row[i].getIndex();
			row[i].moveTo(0);
			nextLine = false;
			for (int j = 0; j < row[i].length(); j++){
				if (j == 0)
					ret += i + ": ";
				ret += "(";
				ret += ((Entry)row[i].getElement()).getColumn();
				ret += ", ";
				ret += ((Entry)row[i].getElement()).getData();
				ret += ") ";
				row[i].moveNext();
				nextLine = true;
			}
			if (nextLine)
				ret += "\r\n";
			row[i].moveTo(temp);
		}
		return ret;
	}//toString
	
	public static void die(String msg){
		System.err.println("Fatal Error: " + msg);
		System.exit(1);
	}//die
}
