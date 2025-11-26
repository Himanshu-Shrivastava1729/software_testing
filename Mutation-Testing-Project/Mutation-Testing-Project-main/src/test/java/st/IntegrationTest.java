package st;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Integration tests for string algorithms.
 * Tests multiple algorithms working together and end-to-end workflows.
 */
public class IntegrationTest {

    private App algorithms;

    @Before
    public void setUp() {
        algorithms = new App();
    }

    /**
     * Integration Test: Multiple Pattern Matching Algorithms
     * Tests that different pattern matching algorithms produce consistent results
     */
    @Test
    public void testMultiplePatternMatchingAlgorithmsConsistency() {
        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFG";
        String pattern = "DEFG";

        // All pattern matching algorithms should find the pattern at the same position
        int rabinKarpResult = algorithms.rabinKarp(pattern, text, 101);
        int kmpResult = algorithms.KMPSearch(pattern, text);
        int zAlgoResult = algorithms.ZAlgorithm(text, pattern);
        int boyerMooreResult = algorithms.BoyerMoore(text.toCharArray(), pattern.toCharArray());

        // All algorithms should agree on the position
        assertEquals(rabinKarpResult, kmpResult);
        assertEquals(kmpResult, zAlgoResult);
        assertEquals(zAlgoResult, boyerMooreResult);
        assertTrue(rabinKarpResult >= 0);
    }

    /**
     * Integration Test: Search then Verify with Different Algorithms
     * Search for a pattern, then verify it's a palindrome using other algorithms
     */
    @Test
    public void testPatternSearchAndPalindromeVerification() {
        String text = "xyzabcdefghijklmnopqrsracecarstuvwxyz";
        String pattern = "racecar";

        // Find the pattern
        int position = algorithms.KMPSearch(pattern, text);
        assertTrue(position >= 0);

        // Verify the found pattern is a palindrome
        String foundPattern = text.substring(position, position + pattern.length());
        assertTrue(algorithms.isPalindrome(foundPattern));

        // Verify longest palindromic substring includes this pattern
        String longestPalindrome = algorithms.findLongestPalindromicSubstring(text);
        assertTrue(longestPalindrome.contains(pattern) || longestPalindrome.length() >= pattern.length());
    }

    /**
     * Integration Test: String Manipulation Pipeline
     * Rotate string, search for pattern, then reverse vowels
     */
    @Test
    public void testStringManipulationPipeline() {
        String original = "helloworld";

        // Step 1: Left rotate by 2
        String rotated = algorithms.leftrotate(original, 2);
        assertEquals("lloworldhe", rotated);

        // Step 2: Search for "world" in rotated string
        int position = algorithms.KMPSearch("world", rotated);
        assertTrue(position >= 0);

        // Step 3: Reverse vowels in rotated string
        String vowelsReversed = algorithms.reverseVowel(rotated);
        // "lloworldhe" -> vowels are 'o','o','e' -> reversed becomes "lleworldho"
        assertEquals("lleworldho", vowelsReversed);

        // Step 4: Right rotate back
        String rightRotated = algorithms.rightrotate(rotated, 2);
        assertEquals(original, rightRotated);
    }

    /**
     * Integration Test: Text Processing Workflow
     * Find common prefix, calculate edit distance, and verify LCS
     */
    @Test
    public void testTextAnalysisWorkflow() {
        String[] strings = { "algorithm", "algorithmic", "algorithms" };

        // Step 1: Find longest common prefix
        String commonPrefix = algorithms.longestCommonPrefix(strings);
        assertEquals("algorithm", commonPrefix);

        // Step 2: Calculate edit distance between first two strings
        int editDist = algorithms.calculateEditDistance(
                strings[0], strings[1],
                strings[0].length(), strings[1].length(),
                new int[strings[0].length() + 1][strings[1].length() + 1]);
        assertTrue(editDist >= 0 && editDist <= Math.max(strings[0].length(), strings[1].length()));

        // Step 3: Verify LCS relationship
        char[] s1 = strings[0].toCharArray();
        char[] s2 = strings[1].toCharArray();
        int lcsLength = algorithms.LCS(s1, s2, s1.length, s2.length);
        assertTrue(lcsLength >= commonPrefix.length());
    }

    /**
     * Integration Test: Search and Alignment
     * Binary search for position, then use sequence alignment
     */
    @Test
    public void testSearchAndAlignment() {
        int[] sortedArray = { 1, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50 };

        // Step 1: Binary search for value
        int binaryResult = algorithms.binarySearch(sortedArray, 25);
        assertEquals(5, binaryResult);

        // Step 2: Linear search verification
        int linearResult = algorithms.linearSearch(sortedArray, 25);
        assertEquals(binaryResult, linearResult);

        // Step 3: Use sequence alignment on string representations
        String seq1 = "AGGTC";
        String seq2 = "AGGCA";
        int alignment = algorithms.SequenceAlignment(seq1, seq2, 3, 2);
        assertTrue(alignment >= 0);
    }

    /**
     * Integration Test: Pattern Matching with Wildcards
     * Test pattern matching followed by wildcard pattern matching
     */
    @Test
    public void testComplexPatternMatching() {
        String text = "abcdefghijk";
        String exactPattern = "def";

        // Step 1: Exact pattern match
        int exactMatch = algorithms.KMPSearch(exactPattern, text);
        assertTrue(exactMatch >= 0);

        // Step 2: Wildcard pattern match
        boolean wildcardMatch = algorithms.WildcardPattern(text, "*def*", text.length(), 5);
        assertTrue(wildcardMatch);

        // Step 3: More complex wildcard
        wildcardMatch = algorithms.WildcardPattern(text, "abc?ef*", text.length(), 7);
        assertTrue(wildcardMatch);
    }

    /**
     * Integration Test: Word Break and Concatenation
     * Test word breaking and word concatenation detection
     */
    @Test
    public void testWordBreakAndConcatenation() {
        // Test word break
        String sentence = "catsanddog";
        List<String> dictionary = Arrays.asList("cat", "cats", "and", "sand", "dog");

        // Step 1: Verify word can be broken
        boolean canBreak = algorithms.wordBreak1(sentence, dictionary);
        assertTrue(canBreak);

        // Step 2: Get all possible breaks
        List<String> breaks = algorithms.wordBreak2(sentence, dictionary);
        assertTrue(breaks.size() > 0);

        // Step 3: Test concatenated words detection
        String[] words = { "cat", "dog", "catdog" };
        List<String> concatenated = algorithms.findAllConcatenatedWordsInADict(words);
        assertEquals(1, concatenated.size());
        assertTrue(concatenated.contains("catdog"));
    }

    /**
     * Integration Test: Palindrome Analysis Pipeline
     * Multiple palindrome operations working together
     */
    @Test
    public void testPalindromeAnalysisPipeline() {
        String text = "abcdefedcbaxyzzyx";

        // Step 1: Find longest palindromic substring
        String longestPalindrome = algorithms.findLongestPalindromicSubstring(text);
        assertTrue(longestPalindrome.length() >= 5);

        // Step 2: Verify it's actually a palindrome
        assertTrue(algorithms.isPalindrome(longestPalindrome));

        // Step 3: Calculate minimum palindrome partitions for original
        int partitions = algorithms.minPalPartition("abba");
        assertEquals(0, partitions); // "abba" is already a palindrome

        // Step 4: Find palindrome pairs
        String[] words = { "bat", "tab", "cat" };
        List<List<Integer>> pairs = algorithms.palindromePairs(words);
        assertEquals(2, pairs.size());
    }

    /**
     * Integration Test: String Transformation Chain
     * Multiple string transformations in sequence
     */
    @Test
    public void testStringTransformationChain() {
        String input = "programming";

        // Step 1: Find longest repeating subsequence
        int repeating = algorithms.LongestRepeatingSubSeq(input);
        assertTrue(repeating >= 0);

        // Step 2: Find longest prefix suffix
        int prefixSuffix = algorithms.longestPrefixSuffix(input);
        assertTrue(prefixSuffix >= 0);

        // Step 3: Reverse vowels
        String reversed = algorithms.reverseVowel(input);
        assertTrue(reversed.length() == input.length());

        // Step 4: Left rotate
        String rotated = algorithms.leftrotate(input, 3);
        assertEquals(input.length(), rotated.length());

        // Step 5: Verify rotation works both ways
        String backToOriginal = algorithms.rightrotate(rotated, 3);
        assertEquals(input, backToOriginal);
    }

    /**
     * Integration Test: Multi-Algorithm Document Processing
     * Simulates processing a document with multiple string operations
     */
    @Test
    public void testDocumentProcessingWorkflow() {
        String document = "The quick brown fox jumps over the lazy dog";

        // Step 1: Find common prefix among similar words
        String[] similarWords = { "the", "they", "them" };
        String prefix = algorithms.longestCommonPrefix(similarWords);
        assertEquals("the", prefix);

        // Step 2: Search for specific patterns
        int position = algorithms.KMPSearch("quick", document);
        assertTrue(position >= 0);

        // Step 3: Verify palindrome detection
        assertFalse(algorithms.isPalindrome(document));

        // Step 4: Calculate edit distance between words
        int[][] memo = new int[6][6];
        for (int i = 0; i < 6; i++) {
            Arrays.fill(memo[i], -1);
        }
        int dist = algorithms.calculateEditDistance("quick", "quack", 5, 5, memo);
        assertTrue(dist >= 0);
    }

    /**
     * Integration Test: Data Validation Pipeline
     * Uses multiple algorithms to validate and process data
     */
    @Test
    public void testDataValidationPipeline() {
        String data = "abcxyzabc";

        // Step 1: Find maximum common substring with itself shifted
        int maxCommon = algorithms.maxCommStr(data, data.substring(3));
        assertTrue(maxCommon >= 3);

        // Step 2: Check for longest palindromic subsequence
        int lps = algorithms.lps(data.toCharArray(), 0, data.length() - 1);
        assertTrue(lps >= 1);

        // Step 3: Verify pattern exists multiple times
        int firstOccurrence = algorithms.KMPSearch("abc", data);
        assertEquals(0, firstOccurrence);

        // Step 4: Test repeated string matching
        int repeatCount = algorithms.repeatedStringMatch("abc", "abcabcabc");
        assertTrue(repeatCount > 0);
    }

    /**
     * Integration Test: Algorithm Comparison
     * Verify different algorithms produce compatible results
     */
    @Test
    public void testAlgorithmCompatibilityAndConsistency() {
        String text1 = "AABAACAADAABAABA";
        String text2 = "AABAACAADAABAABA";

        // LCS should equal string length when strings are identical
        char[] arr1 = text1.toCharArray();
        char[] arr2 = text2.toCharArray();
        int lcs = algorithms.LCS(arr1, arr2, arr1.length, arr2.length);
        assertEquals(text1.length(), lcs);

        // Edit distance should be 0 for identical strings
        int editDist = algorithms.calculateEditDistance(text1, text2, text1.length(), text2.length(),
                new int[text1.length() + 1][text2.length() + 1]);
        assertEquals(0, editDist);

        // Palindrome check consistency
        String palindrome = "racecar";
        assertTrue(algorithms.isPalindrome(palindrome));
        assertEquals(palindrome, algorithms.findLongestPalindromicSubstring(palindrome));
    }

    /**
     * Integration Test: Complex Search Scenario
     * Multiple search operations with result validation
     */
    @Test
    public void testComplexSearchScenario() {
        String corpus = "abracadabra";

        // Search using multiple algorithms
        String pattern = "abra";
        int pos1 = algorithms.rabinKarp(pattern, corpus, 101);
        int pos2 = algorithms.KMPSearch(pattern, corpus);
        int pos3 = algorithms.ZAlgorithm(corpus, pattern);

        // All should find the first occurrence
        assertEquals(0, pos1);
        assertEquals(0, pos2);
        assertEquals(0, pos3);

        // Verify the pattern is actually there
        assertTrue(corpus.startsWith(pattern));

        // Test with pattern at the end
        pattern = "dabra";
        int endPos = algorithms.KMPSearch(pattern, corpus);
        assertTrue(endPos > 0);
        assertEquals(corpus.indexOf(pattern), endPos);
    }
}
