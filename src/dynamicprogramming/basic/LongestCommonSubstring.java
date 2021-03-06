package dynamicprogramming.basic;

import java.util.Arrays;

// https://www.geeksforgeeks.org/longest-common-substring-dp-29/
// https://youtu.be/BysNXJHzCEs

public class LongestCommonSubstring {
    
    // top-down memoization
    /*
     * The idea is to first find the longest common suffix for all the pairs of s1's 
     * and s2's prefix, the result is stored in a 2D array. Then find the largest length 
     * in this array. The tricky part of this question is that we are not looking for 
     * an optimal structure for the original question, instead we first convert the 
     * original question to another question, i.e. finding the longest common suffix 
     * for ALL the pairs of s1's and s2's prefix. Remember we need to calculate for all 
     * the pairs. For example, if we use a top-down approach, for the problem P(m, n), 
     * if s1[m-1] and s2[n-1] are not equal then P(m, n) = 0, this doesn't mean the 
     * recursion stops here, instead it needs to continue until we calculated all P(i, j) 
     * where 0<=i<=m  and 0<=j<=n
     */
    public int lcsMem(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] mem = new int[m+1][n+1];
        for (int i = 0; i <= m; i++)
            Arrays.fill(mem[i], -1);
        
        lcsMem(s1, s2, s1.length(), s2.length(), mem);
        return Arrays.stream(mem).flatMapToInt(Arrays::stream).max().getAsInt();
    }
    
    private void lcsMem(String s1, String s2, int m, int n, int[][] mem) {
        if (m == 0 || n == 0) {
            mem[m][n] = 0;
            return;
        }
        
        if (mem[m][n] != -1)
            return;
        
        // we always needs to calculate subproblem(m-1, n-1), 
        // subproblem(m-1, n) and subproblem(m, n-1)
        lcsMem(s1, s2, m-1, n-1, mem);
        lcsMem(s1, s2, m, n-1, mem);
        lcsMem(s1, s2, m-1, n, mem);
        
        if (s1.charAt(m-1) == s2.charAt(n-1))
           mem[m][n] = mem[m-1][n-1] + 1;
        else
           mem[m][n] = 0;
    }
    
    // bottom-up tabulation
    // T(m,n) = (mn), S(m,n) = (mn)
    public int lcs(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int lcs = 0;
        int[][] dp = new int[m+1][n+1];
        
        // initialize base case: when (m == 0 || n == 0)
        for (int i = 0; i <= m; i++)
            dp[i][0] = 0;
        for (int j = 1; j <= n; j++)
            dp[0][j] = 0;
        
        // fill up rest of the table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = dp[i-1][j-1] + 1;
                else dp[i][j] = 0;
                
                lcs = Math.max(lcs, dp[i][j]);
            }
        }
        
        return lcs;
    }
    
    /*
     * NOTE 1:
     * we can space optimize above solution,
     * as at any time it uses only one previous row
     * 
     * NOTE 2:
     * this problem can also be solved in O(m+n) time using Suffix Tree
     */
    
    public static void main(String[] args) {
        String s1 = "abcdxyz"; 
        String s2 = "xyzabcd";
        LongestCommonSubstring o = new LongestCommonSubstring();
        System.out.println(o.lcsMem(s1, s2)); // 4
        System.out.println(o.lcs(s1, s2)); // 4
    }
}
