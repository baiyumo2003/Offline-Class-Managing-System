/*
Dec 26th 2019
This class is to calculate the precentage integrety of the class
 */
//package comsci;

public class CalculateIntegrety
{

    // set
    private int rows, column, suspectors;
    private int vnum;
    private int ans;
    private long percentage[][][] = new long[100][100][100];
    private long vis[] = new long[1000];
    private long caseValue[] = new long[1000];
    private String result;

    public CalculateIntegrety(int rows, int column, int suspectors)
    {
        if (rows > column)
        {
            int tepo = rows;
            rows = column;
            column = tepo;
        }
        // calculate the result
        result = setUp(column, rows, suspectors);
    }

    private int checkStatus(int x)
    {
        // check the status of the classroom
        int temp = 0;
        for (int i = 0; i < column; i++)
        {
            if ((x & (1 << i)) != 0)
            {
                temp++;
            }
        }
        return temp;
    }

    // return the result
    public String getResult()
    {
        return result;
    }

    private int Getvalidstate()
    {
        // get the start info
        int n=0;
        for (int i = 0; i < (1 << column); i++)
        {
            if ((i & (i << 1)) == 0)
            {
                vis[++n] = i;
                caseValue[n] = checkStatus(i);
            }
        }
        return n;
    }

    private long Gcd(long x, long y)
    {
        // find the GCD 
        if (y == 0)
        {
            return x;
        }
        else
        {
            return Gcd(y, x % y);
        }
    }

    private double findSolution(long x, long y)
    {
        // Calculate the ratio if the arrangement is possible
        long x1, x2, p;
        x1 = 1;
        x2 = 1;

        for (int i = 1; i <= y; i++)
        {
            x2 *= i;
        }
        for (int i = 1; i <= y; i++)
        {
            x1 *= x;
            p = Gcd(x1, x2);
            x1 /= p;
            x2 /= p;
            p = Gcd(x1, ans);
            x1 /= p;
            ans /= p;
            x--;
        }
        x2 *= ans;
        p = Gcd(x1, x2);
        x1 /= p;
        x2 /= p;
        double a = x1;
        double b = x2;
        //System.out.println(a + "/" + b + " " + b / a);
        return b / a;
    }

    private String setUp(int rows, int column, int suspectors)
    {
        // set the data
        this.rows = rows;
        this.column = column;
        this.suspectors = suspectors;
        vnum = Getvalidstate();
        percentage[0][1][0] = 1;
        // calculate whether it is possible to arrange
        for (int i = 1; i <= rows; i++)
        {
            for (int j = 1; j <= vnum; j++)
            {
                for (int l = (int) caseValue[j]; l <= suspectors; l++)
                {
                    for (int h = 1; h <= vnum; h++)
                    {
                        if ((vis[j] & vis[h]) == 0)
                        {
                            percentage[i][j][l] += percentage[i - 1][h][l - (int) caseValue[j]];
                        }
                    }
                }
            }
        }

        ans = 0;
        for (int i = 1; i <= vnum; i++)
        {
            //check whether the arrangement is possible
            ans += percentage[(int) rows][i][(int) suspectors];
        }
        // if arrangement is impossible
        if (ans == 0)
        {
            return "0";
        }
        // if it is possible
        else
        {
            return Double.toString(findSolution(rows * column, suspectors));
        }
    }

    public static void main(String args[])
    {
        //System.out.println("0");
        CalculateIntegrety object = new CalculateIntegrety(5, 3, 2);
        System.out.println(object.getResult());
    }

}
