import java.util.Arrays;

public class Matrix {
	
	// Order of matrix
	private int m, n;
	
	private double[][] elements;
	
	/**
	 * Create a rowNum by colNum zero matrix  
	 * @param rowNum
	 * @param colNum
	 */
	public Matrix(int rowNum, int colNum){
		m  = rowNum;
		n = colNum;
		elements = new double[m][n];
	}
	/**
	 * Use a 2D-array of double to initialize the matrix
	 * @param input
	 */
	public Matrix(double[][] input){
		if (input.length==0) return;
		m = input.length;
		n = input[0].length;
		elements = new double[m][n];
		for (int i=0;i<m;i++){
			for (int j=0;j<n;j++)
				setElement(i,j,input[i][j]);
		}
	}
	
	// Overwrite toString()
	public String toString(){
		String res="";
		for(int i=0;i<m;i++){
			res = res + Arrays.toString(elements[i]) + "\n";
		}
		return res;
	}
	
	// Use for in class change of variables (such as elementary row operation)
	private void setElement(int i, int j, double value){
		this.elements[i][j] = value;
	}
	/**
	 * Get the number at [i,j] (starting from 0)
	 * @param i
	 * @param j
	 * @return
	 */
	public double getElementAt(int i,int j){
		return this.elements[i][j];
	}
	/**
	 * Return the number of rows
	 * @return
	 */
	public int getRowNum(){
		return m;
	}
	/**
	 * Return the number of columns
	 * @return
	 */
	public int getColNum(){
		return n;
	}
	private double[] getRow(int n){
		return elements[n];
	}
	
	
	/**
	 * Calculate the determinant of this matrix
	 * Return 0 and print error message in console if it is not a square matrix
	 * @return
	 */
	public double getDeterminant(){
		if (m==n && m>=2) {
			return getDeterminantRecursive(this);
		}else {
			System.out.println("Only work for Square Matrix, outputing 0");
			return 0;
		}
	}
	// Used for getDeterminant
	private double getDeterminantRecursive(Matrix A){
		double sum=0;
		if (A.getRowNum()==2 && A.getColNum()==2){
			return A.getElementAt(0, 0)*A.getElementAt(1, 1) - A.getElementAt(0, 1) * A.getElementAt(1, 0);
		}
		for (int j=0;j<n;j++)
			sum += A.getElementAt(0, j)*getDeterminantRecursive(getMinor(0,j)) * Math.pow(-1, j+0);
		return sum;
	}
	/**
	 * Getting the minor row col
	 * @param row
	 * @param col
	 * @return
	 */
	public Matrix getMinor(int row, int col){
		Matrix A = new Matrix(this.m-1, this.n-1);
		int counter=0;
		for (int i=0;i<m;i++){
			for (int j=0;j<n;j++){
				if (i!=row && j!=col){
					A.setElement(counter/(m-1), counter%(n-1), this.elements[i][j]);
					counter++;
				}
			}
		}
		return A;
	}
	/**
	 * Return the determinant Matrix
	 * @return
	 */
	public Matrix getMatrixOfMinors(){
		Matrix A = new Matrix(m,n);
		if (m!=n){
			return null;
		}
		for (int i=0;i<m;i++){
			for (int j=0;j<n;j++){
				A.setElement(i, j, getMinor(i,j).getDeterminant());
			}
		}
		return A;
	}
	/**
	 * Cofactor Matrix is the matrix of minors with the cofactor considered
	 * @return
	 */
	public Matrix getCofactorMatrix(){
		Matrix A = new Matrix(m,n);
		Matrix minors = this.getMatrixOfMinors();
		for (int i=0;i<m;i++){
			for (int j=0;j<n;j++)
				A.setElement(i, j, minors.getElementAt(i, j)*Math.pow(-1, j+i));
		}
		return A;
	}
	/**
	 * Only works with 
	 * @return
	 */
	public Matrix getTranspose(){
		Matrix A = new Matrix(n,m);
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				A.setElement(j,i, elements[i][j]);
			}
		}
		return A;
	}
	
	/**
	 * Return the adjuate
	 * Adjuate is the transpose of the cofactor matrix
	 * @return
	 */
	public Matrix getAdjuateMatrix(){
		return this.getCofactorMatrix().getTranspose();
	}
	/**
	 * Calculation with another matrix
	 * Return a new matrix for the answer
	 * If the symbol is invalid or the given matrix cannot operate the calculation, return null
	 * @param B
	 * @param symbol +, - or *
	 * @return
	 */
	public Matrix calculationWithMatrix(Matrix B, char symbol){
		Matrix C=null;
		switch (symbol){
		case '+':
			C = addMatrix(B, true);
			break;
		case '-':
			C = addMatrix(B, false);
			break;
		case '*':
			C = multiplyMatrix(B);
			break;
		}
		return C;
	}
	// Matrix multiplication for calculationWithMatrix
	private Matrix multiplyMatrix(Matrix B){
		if (n!=B.getRowNum())
			return null;
		Matrix C = new Matrix(m, B.getColNum());
		double temp=0;
		for (int i=0;i<m;i++){
			for (int j=0;j<B.getColNum();j++){
				temp = 0;
				for (int nCtr=0;nCtr<n;nCtr++){
					temp+=this.getElementAt(i, nCtr) * B.getElementAt(nCtr, j);
				}
				C.setElement(i, j, temp);
			}
		}
		return C;
	}
	// Matrix addition for calculationWithMatrix
	private Matrix addMatrix(Matrix B, boolean addition){
		if (m != B.getRowNum() || n != B.getColNum())
			return null;
		int sign = addition?1:-1;
		Matrix A = new Matrix(m, n);
		for (int i=0;i<m;i++){
			for (int j=0;j<n;j++){
				A.setElement(i, j, this.getElementAt(i, j)+sign*B.getElementAt(i, j));
			}
		}
		return A;
	}
	/**
	 * Do the same calculation to every value in the matrix
	 * If the symbol is invalid, do nothing
	 * @param constant
	 * @param symbol * or /
	 */
	public void calculationWithConstant(double constant, char symbol){
		for (int i=0;i<m;i++){
			for (int j=0;j<n;j++){
				switch (symbol){
				case '*':
					elements[i][j]*=constant;
					break;
				case '/':
					elements[i][j]/=constant;
					break;
				}
			}
		}
	}
	/**
	 * Join matrix B into the right of Matrix A
	 * Must have the same rows, otherwise return null
	 * @param A
	 * @param B
	 * @return
	 */
	public static Matrix join(Matrix A, Matrix B){
		if (A.getColNum()!=B.getColNum()) return null;
		double[][] result = new double[A.getRowNum()][A.getColNum()+B.getColNum()];
		for (int i=0;i<result.length;i++){
			for (int j=0;j<result[0].length;j++){
				if (j<A.getColNum())
					result[i][j]=A.getElementAt(i, j);
				else
					result[i][j]=B.getElementAt(i, j-A.getColNum());
			}
		}
		return new Matrix(result);
	}
	
	public static Matrix removeDuplication(Matrix A){
		// check every column (unless all zero)
		// reduce to a all 0 row if it is duplicate
		// remove all the 0 column
		for(int i=0;i<A.getRowNum();i++){
			for(int j=i+1;j<A.getRowNum();j++){
				
			}
		}
		return new Matrix(1,1);
	}
	// used for removeDuplication
	private static boolean compareRow(double[] a, double[] b){
		if (a.length!=b.length) return false;
		for (int i=0;i<a.length;i++){
			if (a[i]!=b[i])
				return false;
		}
		return true;
	}
	
	/**
	 * Copy the matrix from into the calling matrix
	 * **That means the calling matrix will be cleared
	 * @param from
	 */
	public void copy(Matrix from){
		m = from.getRowNum();
		n = from.getColNum();
		this.elements = new double[from.getRowNum()][from.getColNum()];
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				this.setElement(i, j, from.getElementAt(i, j));
			}
		}
	}
	/**
	 * Return the ref for the matrix (a new matrix is returned)
	 * @return
	 */
	public Matrix getRef(){
		Matrix A = new Matrix(1,1);
		A.copy(this);
		
		A.getElementAt(i, j)
		return this;
	}
	// Elementary Row Operation used for getRef
	/**
	 * Add/subtract the result to another row
	 * addition == true to adding, adding == false to subtraction
	 * add row at from into the row at to after applying the multiplier
	 * Change the calling matrix
	 * @param from
	 * @param fromMultiplier
	 * @param to
	 * @param toMultiplier
	 * @param addition true for +, false for -
	 */
	private void addRow(int from, double fromScalar, int to, double toScalar, boolean addition){
		int sign=addition?1:-1;
		for(int j=0;j<n;j++){
			elements[to][j] = elements[to][j]*(toScalar==0?1:toScalar) + sign*elements[from][j]*(fromScalar==0?1:fromScalar);
		}
	}
	/**
	 * swap two rows
	 * @param r1
	 * @param r2
	 */
	private void swap(int r1, int r2){
		double temp;
		for(int j=0;j<n;j++){
			temp = elements[r1][j];
			elements[r1][j] = elements[r2][j];
			elements[r2][j] = temp;
		}
	}
	/**
	 * Multiply/divide a row by a scalar
	 * @param row
	 * @param scalar
	 * @param multiplication true for *, false for /
	 */
	private void rowMultiplyScalar(int row, double scalar, boolean multiplication){
		for (int j=0;j<n;j++){
			if (multiplication)
				elements[row][j] = elements[row][j] * (scalar==0?1:scalar);
			else
				elements[row][j] = elements[row][j] / (scalar==0?1:scalar);
		}
	}
}
