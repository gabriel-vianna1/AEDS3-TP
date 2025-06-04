package algoritmos.padroes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class KMP {
    
    public static List<Long> search(RandomAccessFile file, byte[] pattern) throws IOException {
        long fileSize = file.length();
        int patternLength = pattern.length;

        ArrayList<Long> results = new ArrayList<>();
        int[] lps = buildLongestPrefixSuffix(pattern);

        int j = 0;
        long i = 0;

        file.seek(0);
        while (file.getFilePointer() < fileSize) {
            int fileByte = file.read();
            if (fileByte == -1) break;

            if (fileByte == (pattern[j] & 0xFF)) {
                i++;
                j++;
                if (j == patternLength) {
                    results.add(i - j);
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return results;
    }

    private static int[] buildLongestPrefixSuffix(byte[] pattern) {
        int length = 0;
        int[] lps = new int[pattern.length];
        lps[0] = 0;

        int i = 1;
        while (i < pattern.length) {
            if (pattern[i] == pattern[length]) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }
}   