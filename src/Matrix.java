import java.util.Arrays;

public class Matrix {
	
	// Order of matrix
	private int m, n;
	
	private double[][] elements;
	
	
	/**
	 * 
	 * @param rowNum
	 * @param colNum
	 */
	public Matrix(int rowNum, int colNum){
		m  = rowNum;
		n = colNum;
		elements = new double[m][n];
	}
	
	// Overwrite toString()
	public String toString(){
		String res="";
		for(int i=0;i<m;i++){
			res = res + Arrays.toString(elements[i]) + "\n";
		}
		return res;
	}
	
	public static void main(String[] args) {
		Matrix A = new Matrix(2, 2);
		System.out.println(A);
	}

}
