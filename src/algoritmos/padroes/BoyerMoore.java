package algoritmos.padroes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BoyerMoore {

	public static Optional<List<Long>> search(RandomAccessFile file, byte[] pattern) throws IOException {
		Map<Byte, Integer> badCharacterHeuristic = buildBadCharacter(pattern);
		int[] goodSuffixHeuristic = buildGoodSuffix(pattern);

		List<Long> patternMatches = new ArrayList<>();
		long fileLength = file.length();
		long bytesRead = 0;

		while (bytesRead <= fileLength - pattern.length) {
			byte[] buffer = new byte[pattern.length];
			file.seek(bytesRead);
			file.read(buffer);

			int i = pattern.length - 1;
			while (i >= 0 && buffer[i] == pattern[i]) {
				i--;
			}

			if (i < 0) {
				patternMatches.add(bytesRead);
				bytesRead += pattern.length;
			} else {
				int badCharacterShift = badCharacterHeuristic.getOrDefault(buffer[i], -1);
				int goodSuffixShift = goodSuffixHeuristic[i];
				bytesRead += Math.max(goodSuffixShift, i - badCharacterShift);
			}
		}

		return patternMatches.isEmpty() ? Optional.empty() : Optional.of(patternMatches);
	}

	private static Map<Byte, Integer> buildBadCharacter(byte[] pattern) {
		Map<Byte, Integer> badCharacter = new HashMap<>();
		for (int i = 0; i < pattern.length - 1; i++) {
			badCharacter.put(pattern[i], i);
		}
		return badCharacter;
	}

	private static int[] buildGoodSuffix(byte[] pattern) {
		int m = pattern.length;
		int[] goodSuffix = new int[m];
		int[] suffixes = new int[m];

		suffixes[m - 1] = m;
		int g = m - 1;
		int f = 0;
		for (int i = m - 2; i >= 0; i--) {
			if (i > g && suffixes[i + m - 1 - f] < i - g) {
				suffixes[i] = suffixes[i + m - 1 - f];
			} else {
				if (i < g) g = i;
				f = i;
				while (g >= 0 && pattern[g] == pattern[g + m - 1 - f]) {
					g--;
				}
				suffixes[i] = f - g;
			}
		}

		for (int i = 0; i < m; i++) {
			goodSuffix[i] = m;
		}
		for (int i = m - 1; i >= 0; i--) {
			if (suffixes[i] == i + 1) {
				for (int j = 0; j < m - 1 - i; j++) {
					if (goodSuffix[j] == m) {
						goodSuffix[j] = m - 1 - i;
					}
				}
			}
		}
		for (int i = 0; i < m - 1; i++) {
			goodSuffix[m - 1 - suffixes[i]] = m - 1 - i;
		}

		return goodSuffix;
	}
}

