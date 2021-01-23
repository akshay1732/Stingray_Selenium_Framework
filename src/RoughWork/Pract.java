package RoughWork;

public class Pract {
	private static void fill(int[][] arr)
    {
        int n = arr[0].length;
        int x=n/2;
        int y=n-1;
        for(int i=1;i<=n*n;i++)
        {
            arr[x][y]=i;
            x = (x+1)%n;
            y = (y+1)%n;
            if(arr[x][y]!=0)
            {
                y = (y+n-2)%n;
                x = (x+n-1)%n;
            }
        }
    }
	public static void main(String[] args) {

		int n = 3;
		int [][] arr = new int[n][n];
		fill(arr);
		for(int i=n-1;i>=0;i--)
		{
		    for(int j=0;j<n;j++)
		    {
		        System.out.print(arr[i][j]+",");
		    }
		    System.out.println();
		}

	}

}
