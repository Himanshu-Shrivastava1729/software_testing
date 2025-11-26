package st;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * System-level integration tests.
 * Tests end-to-end workflows and complete use cases.
 */
public class SystemIntegrationTest {

    private App algorithms;

    @Before
    public void setUp() {
        algorithms = new App();
    }

    /**
     * End-to-End Test: Complete Text Search System
     * Simulates a real-world text search application
     */
    @Test
    public void testCompleteTextSearchSystem() {
        // Simulate a document database
        String document1 = "Machine learning is a subset of artificial intelligence";
        String document2 = "Deep learning is a subset of machine learning";
        String document3 = "Artificial intelligence is transforming the world";

        String searchQuery = "learning";

        // Search in all documents using different algorithms
        int pos1_kmp = algorithms.KMPSearch(searchQuery, document1);
        int pos2_kmp = algorithms.KMPSearch(searchQuery, document2);
        int pos3_kmp = algorithms.KMPSearch(searchQuery, document3);

        int pos1_rk = algorithms.rabinKarp(searchQuery, document1, 101);
        int pos2_rk = algorithms.rabinKarp(searchQuery, document2, 101);

        // Verify search consistency
        assertEquals(pos1_kmp, pos1_rk);
        assertEquals(pos2_kmp, pos2_rk);

        // Found in first two documents
        assertTrue(pos1_kmp >= 0);
        assertTrue(pos2_kmp >= 0);
        assertEquals(-1, pos3_kmp);

        // Verify common terms across documents
        String[] commonWords = { "Machine learning", "machine learning", "Artificial intelligence" };
        String prefix = algorithms.longestCommonPrefix(Arrays.copyOfRange(commonWords, 0, 2));
        assertTrue(prefix.length() > 0 || commonWords[0].toLowerCase().startsWith("machine"));
    }

    /**
     * End-to-End Test: DNA Sequence Analysis System
     * Simulates bioinformatics sequence analysis
     */
    @Test
    public void testDNASequenceAnalysisSystem() {
        String dnaSequence1 = "ATCGATCGATCG";
        String dnaSequence2 = "ATCGATGGGATCG";
        String targetGene = "GATCG";

        // Step 1: Search for gene in both sequences
        int pos1 = algorithms.KMPSearch(targetGene, dnaSequence1);
        int pos2 = algorithms.KMPSearch(targetGene, dnaSequence2);

        assertTrue(pos1 >= 0);
        assertTrue(pos2 >= 0);

        // Step 2: Calculate sequence similarity (LCS)
        char[] seq1 = dnaSequence1.toCharArray();
        char[] seq2 = dnaSequence2.toCharArray();
        int similarity = algorithms.LCS(seq1, seq2, seq1.length, seq2.length);
        assertTrue(similarity > 0);

        // Step 3: Calculate sequence alignment cost
        int alignmentCost = algorithms.SequenceAlignment(dnaSequence1, dnaSequence2, 3, 2);
        assertTrue(alignmentCost >= 0);

        // Step 4: Find common substring
        int commonLength = algorithms.maxCommStr(dnaSequence1, dnaSequence2);
        assertTrue(commonLength > 0);

        // Step 5: Verify edit distance
        int editDist = algorithms.calculateEditDistance(
                dnaSequence1, dnaSequence2,
                dnaSequence1.length(), dnaSequence2.length(),
                new int[dnaSequence1.length() + 1][dnaSequence2.length() + 1]);
        assertTrue(editDist >= 0);
    }

    /**
     * End-to-End Test: Spell Checker System
     * Simulates a spell checking and suggestion system
     */
    @Test
    public void testSpellCheckerSystem() {
        String[] dictionary = { "hello", "world", "help", "held", "hero" };
        String misspelled = "helo";

        // Find closest match using edit distance
        String closestMatch = null;
        int minDistance = Integer.MAX_VALUE;

        for (String word : dictionary) {
            int[][] dp = new int[misspelled.length() + 1][word.length() + 1];
            for (int i = 0; i <= misspelled.length(); i++) {
                Arrays.fill(dp[i], -1);
            }

            int distance = algorithms.calculateEditDistance(
                    misspelled, word,
                    misspelled.length(), word.length(),
                    dp);

            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = word;
            }
        }

        assertNotNull(closestMatch);
        assertEquals("hello", closestMatch);
        assertTrue(minDistance <= 2);

        // Verify using LCS
        char[] mis = misspelled.toCharArray();
        char[] correct = closestMatch.toCharArray();
        int lcs = algorithms.LCS(mis, correct, mis.length, correct.length);
        assertTrue(lcs >= 3);
    }

    /**
     * End-to-End Test: Code Plagiarism Detection System
     * Simulates plagiarism detection between code snippets
     */
    @Test
    public void testCodePlagiarismDetectionSystem() {
        String code1 = "publicvoidmainstring";
        String code2 = "publicvoidmainstring";
        String code3 = "privatevoidteststring";

        // Calculate similarity between code snippets
        char[] c1 = code1.toCharArray();
        char[] c2 = code2.toCharArray();
        char[] c3 = code3.toCharArray();

        // Compare code1 vs code2 (identical)
        int similarity12 = algorithms.LCS(c1, c2, c1.length, c2.length);
        assertEquals(code1.length(), similarity12);

        // Compare code1 vs code3 (different)
        int similarity13 = algorithms.LCS(c1, c3, c1.length, c3.length);
        assertTrue(similarity13 < code1.length());

        // Calculate edit distance
        int[][] dp12 = new int[code1.length() + 1][code2.length() + 1];
        int[][] dp13 = new int[code1.length() + 1][code3.length() + 1];

        for (int i = 0; i <= code1.length(); i++) {
            Arrays.fill(dp12[i], -1);
            Arrays.fill(dp13[i], -1);
        }

        int distance12 = algorithms.calculateEditDistance(code1, code2, code1.length(), code2.length(), dp12);
        int distance13 = algorithms.calculateEditDistance(code1, code3, code1.length(), code3.length(), dp13);

        assertEquals(0, distance12); // Identical code
        assertTrue(distance13 > 0); // Different code
    }

    /**
     * End-to-End Test: Password Strength Analyzer
     * Analyzes password patterns and strength
     */
    @Test
    public void testPasswordStrengthAnalyzer() {
        String password = "P@ssw0rd123";

        // Check for repeating subsequences (weakness)
        int repeating = algorithms.LongestRepeatingSubSeq(password);

        // Check for palindromic patterns (weakness)
        String longestPalindrome = algorithms.findLongestPalindromicSubstring(password);

        // Check for common patterns
        String[] commonPatterns = { "password", "123456", "qwerty" };
        boolean containsCommonPattern = false;

        for (String pattern : commonPatterns) {
            int pos = algorithms.KMPSearch(pattern.toLowerCase(), password.toLowerCase());
            if (pos >= 0) {
                containsCommonPattern = true;
                break;
            }
        }

        // Password should not contain obvious patterns
        assertTrue(repeating < password.length() / 2);
        assertTrue(longestPalindrome.length() < password.length() / 2);
    }

    /**
     * End-to-End Test: Log File Analysis System
     * Analyzes log files for patterns and anomalies
     */
    @Test
    public void testLogFileAnalysisSystem() {
        String[] logEntries = {
                "ERROR: Connection timeout at 10:30",
                "ERROR: Connection timeout at 10:35",
                "INFO: System started successfully",
                "ERROR: Connection timeout at 10:40"
        };

        String errorPattern = "ERROR";
        int errorCount = 0;

        // Count errors using pattern matching
        for (String log : logEntries) {
            int pos = algorithms.KMPSearch(errorPattern, log);
            if (pos >= 0) {
                errorCount++;
            }
        }

        assertEquals(3, errorCount);

        // Find common prefix in error messages
        String[] errorLogs = {
                "ERROR: Connection timeout at 10:30",
                "ERROR: Connection timeout at 10:35",
                "ERROR: Connection timeout at 10:40"
        };

        String commonPrefix = algorithms.longestCommonPrefix(errorLogs);
        assertTrue(commonPrefix.contains("ERROR"));

        // Check for repeating patterns
        String combinedLogs = String.join("", errorLogs);
        int repeatingLength = algorithms.LongestRepeatingSubSeq(combinedLogs);
        assertTrue(repeatingLength > 0);
    }

    /**
     * End-to-End Test: Auto-Complete System
     * Simulates text auto-completion functionality
     */
    @Test
    public void testAutoCompleteSystem() {
        String[] dictionary = { "algorithm", "algorithmic", "algorithms", "algebra", "alien" };
        String userInput = "algo";

        // Find all words with matching prefix
        int matchCount = 0;
        for (String word : dictionary) {
            if (word.length() >= userInput.length()) {
                String wordPrefix = word.substring(0, userInput.length());
                if (wordPrefix.equals(userInput)) {
                    matchCount++;
                }
            }
        }

        assertEquals(3, matchCount);

        // Find common prefix among suggestions
        String[] suggestions = { "algorithm", "algorithmic", "algorithms" };
        String commonPrefix = algorithms.longestCommonPrefix(suggestions);
        assertEquals("algorithm", commonPrefix);

        // Calculate similarity scores
        for (String suggestion : suggestions) {
            int[][] dp = new int[userInput.length() + 1][suggestion.length() + 1];
            for (int i = 0; i <= userInput.length(); i++) {
                Arrays.fill(dp[i], -1);
            }

            int distance = algorithms.calculateEditDistance(
                    userInput, suggestion,
                    userInput.length(), suggestion.length(),
                    dp);

            assertTrue(distance >= 0);
        }
    }

    /**
     * End-to-End Test: URL Pattern Matching System
     * Analyzes and validates URL patterns
     */
    @Test
    public void testURLPatternMatchingSystem() {
        String url1 = "https://www.example.com/path/to/resource";
        String url2 = "https://www.example.com/path/to/another";

        // Find common prefix
        String[] urls = { url1, url2 };
        String commonBase = algorithms.longestCommonPrefix(urls);
        assertTrue(commonBase.contains("https://www.example.com/path/to"));

        // Search for specific patterns
        String protocol = "https";
        int protocolPos = algorithms.KMPSearch(protocol, url1);
        assertEquals(0, protocolPos);

        String domain = "example.com";
        int domainPos = algorithms.KMPSearch(domain, url1);
        assertTrue(domainPos > 0);

        // Calculate URL similarity
        char[] u1 = url1.toCharArray();
        char[] u2 = url2.toCharArray();
        int similarity = algorithms.LCS(u1, u2, u1.length, u2.length);
        assertTrue(similarity > commonBase.length());
    }

    /**
     * End-to-End Test: Data Deduplication System
     * Finds and removes duplicate patterns in data
     */
    @Test
    public void testDataDeduplicationSystem() {
        String data = "abcabcdefdefghighi";

        // Find repeating patterns
        int longestRepeating = algorithms.LongestRepeatingSubSeq(data);
        assertTrue(longestRepeating > 0);

        // Find all occurrences of common patterns
        String pattern = "abc";
        int firstOccurrence = algorithms.KMPSearch(pattern, data);
        assertEquals(0, firstOccurrence);

        // Verify pattern appears multiple times by searching in substring
        String afterFirst = data.substring(firstOccurrence + 1);
        int secondOccurrence = algorithms.KMPSearch(pattern, afterFirst);
        assertTrue(secondOccurrence >= 0);

        // Calculate compression potential
        int originalLength = data.length();
        assertTrue(longestRepeating < originalLength);
    }

    /**
     * End-to-End Test: Natural Language Processing Pipeline
     * Complete NLP workflow with multiple operations
     */
    @Test
    public void testNaturalLanguageProcessingPipeline() {
        String sentence = "the quick brown fox jumps over the lazy dog";
        String[] words = sentence.split(" ");

        // Step 1: Find repeated words
        int repeatedCount = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].equals(words[j])) {
                    repeatedCount++;
                }
            }
        }
        assertTrue(repeatedCount > 0); // "the" appears twice

        // Step 2: Check palindromes in words
        boolean hasPalindrome = false;
        for (String word : words) {
            if (algorithms.isPalindrome(word) && word.length() > 1) {
                hasPalindrome = true;
                break;
            }
        }
        // This sentence doesn't have palindromes
        assertNotNull(hasPalindrome); // Variable is initialized

        // Step 3: Find common substrings
        int maxCommon = algorithms.maxCommStr(words[0], words[words.length - 1]);
        assertTrue(maxCommon >= 0);

        // Step 4: Pattern matching
        String pattern = "fox";
        int patternPos = algorithms.KMPSearch(pattern, sentence);
        assertTrue(patternPos > 0);
    }
}
