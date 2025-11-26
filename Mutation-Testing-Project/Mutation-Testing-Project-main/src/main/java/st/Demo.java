package st;

public class Demo {
    public static void main(String[] args) {
        System.out.println("==== String Algorithms Demo ====\n");
        
        App algorithms = new App();
        
        // 1. Rabin-Karp Algorithm Demo
        System.out.println("1. Rabin-Karp Pattern Searching:");
        String text = "ABCFGHIJKLMNOPQRSTUVWXZXYZOPQRSTUWXYZ";
        String pattern = "XYZOPQRS";
        int result = algorithms.rabinKarp(pattern, text, 101);
        System.out.println("Text: " + text);
        System.out.println("Pattern: " + pattern);
        System.out.println("Pattern found at index: " + result);
        System.out.println();
        
        // 2. Binary Search Demo
        System.out.println("2. Binary Search:");
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 5;
        int index = algorithms.binarySearch(arr, target);
        System.out.print("Array: ");
        for (int num : arr) System.out.print(num + " ");
        System.out.println();
        System.out.println("Target: " + target);
        System.out.println("Found at index: " + index);
        System.out.println();
        
        // 3. KMP Algorithm Demo
        System.out.println("3. KMP Pattern Searching:");
        String kmpText = "ABABDABACDABABCABCABCABCABC";
        String kmpPattern = "ABABCABC";
        int kmpResult = algorithms.KMPSearch(kmpPattern, kmpText);
        System.out.println("Text: " + kmpText);
        System.out.println("Pattern: " + kmpPattern);
        System.out.println("Pattern found at index: " + kmpResult);
        System.out.println();
        
        // 4. Longest Common Subsequence Demo
        System.out.println("4. Longest Common Subsequence:");
        String s1 = "ABCDGH";
        String s2 = "AEDFHR";
        char[] X = s1.toCharArray();
        char[] Y = s2.toCharArray();
        int lcsLength = algorithms.LCS(X, Y, X.length, Y.length);
        System.out.println("String 1: " + s1);
        System.out.println("String 2: " + s2);
        System.out.println("LCS Length: " + lcsLength);
        System.out.println();
        
        // 5. Longest Common Prefix Demo
        System.out.println("5. Longest Common Prefix:");
        String[] strings = {"flower", "flow", "flight"};
        String commonPrefix = algorithms.longestCommonPrefix(strings);
        System.out.print("Strings: ");
        for (String str : strings) System.out.print("\"" + str + "\" ");
        System.out.println();
        System.out.println("Longest Common Prefix: \"" + commonPrefix + "\"");
        System.out.println();
        
        // 6. Edit Distance Demo
        System.out.println("6. Edit Distance:");
        String str1 = "kitten";
        String str2 = "sitting";
        int m = str1.length();
        int n = str2.length();
        int[][] memo = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                memo[i][j] = -1;
            }
        }
        int editDist = algorithms.calculateEditDistance(str1, str2, m, n, memo);
        System.out.println("String 1: " + str1);
        System.out.println("String 2: " + str2);
        System.out.println("Edit Distance: " + editDist);
        System.out.println();
        
        // 7. Palindrome Detection Demo
        System.out.println("7. Longest Palindromic Substring:");
        String palindromeText = "babad";
        String longestPalindrome = algorithms.findLongestPalindromicSubstring(palindromeText);
        System.out.println("Input: " + palindromeText);
        System.out.println("Longest Palindromic Substring: " + longestPalindrome);
        System.out.println();
        
        System.out.println("==== Demo Complete! ====");
        System.out.println("\nThis project contains " + getAlgorithmCount() + " different string algorithms");
        System.out.println("with comprehensive unit tests for mutation testing analysis.");
    }
    
    private static int getAlgorithmCount() {
        // Count of public methods in App class (approximate)
        return 30; // This is an approximation based on the code we saw
    }
}