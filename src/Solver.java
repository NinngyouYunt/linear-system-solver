
public class Solver {

	public static void main(String[] args) {
		double[][] input1 = 
			{
				{3,2,-1},
				{2,-2,4},
				{-1,0.5,-1}
			};
		double[][] input2 = 
			{
				{9,8},
				{6,5}
			};
		double[][] constantsArr =
			{
				{1},
				{-2},
				{0}
			};
		double[][] arrMA = 
			{
				{5,2,6,1},
				{0,6,2,0},
				{3,8,1,4},
				{1,8,5,6}
			};
		double[][] arrMB = 
			{
				{7,5,8,0},
				{1,8,2,6},
				{9,4,3,8},
				{5,3,7,9}
			};
		Matrix A = new Matrix(input1);
		Matrix C = new Matrix(constantsArr);
		Matrix MA = new Matrix(arrMA);
		Matrix MB = new Matrix(arrMB);
		System.out.println(decimalPlaces(0));
//		inverseSolver(A, C);
	}
	
	public static void solver(Matrix coeffs, Matrix constants){
		// assume reduced already
		if (coeffs.getRowNum()<coeffs.getColNum()) // step 2
		{
			parametricSolver(coeffs, constants);
		} else if (coeffs.getRowNum()>coeffs.getColNum()) // step 3 
		{
			System.out.println("No Solution");
		} else // continue 
		{
			if (!inverseSolver(coeffs, constants)) // step 4
			{
				// step 5
				
			}
		}
	}
	
	
	
	public static boolean inverseSolver(Matrix coeffs, Matrix constants){
		double det = coeffs.getDeterminant();
		if (det == 0) return false;
		Matrix temp = coeffs.getAdjuateMatrix();
		System.out.println("Adjuate is ");
		System.out.println(temp);
		System.out.println("Determinant is "+det+"\n");
		temp.calculationWithConstant(det, '/');
		System.out.println("Inverse is ");
		System.out.println(temp);
		System.out.println("Solution is");
		System.out.println(temp.calculationWithMatrix(constants, '*'));
		return true;
	}
	
	public static int decimalPlaces(double n){
		String str = n+"";
		str = str.substring(str.indexOf('.')+1);
		if (str.equals("0")) return 0;
		return str.length();
	}
	// 1. Reduce duplicated row
	// 2. m<n? (true - parametric solver)
	// 3. m>n? (true - no solution)
	// 4. Determinant (false - inverse solver)
	// 5. Ref (invalid row - no solution)
	// 6. Parametrix solver
	
	public static void parametricSolver(Matrix coeffs, Matrix constants){
		Matrix A = Matrix.join(coeffs, constants);
		
	}
}
