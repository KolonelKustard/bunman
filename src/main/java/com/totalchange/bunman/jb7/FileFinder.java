package com.totalchange.bunman.jb7;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * I can't for the life of me figure out the precise rules used by the Brennan
 * for how it turns a song title into a filename. There seems to be some weird
 * truncation rules which I can't figure out, so this utility class figures out
 * the closest match instead.
 * </p>
 * 
 * @author kolonel
 */
final class FileFinder {
    private static final Logger logger = LoggerFactory
            .getLogger(FileFinder.class);

    private List<File> files;
    private List<String> fileNames;

    FileFinder(File[] files, File... ignored) {
        logger.trace("Creating new file finder for files {} whilst "
                + "ignoring {}", files, ignored);

        this.files = new ArrayList<File>(files.length);
        this.fileNames = new ArrayList<String>(files.length);

        Arrays.sort(ignored);
        for (File file : files) {
            if (Arrays.binarySearch(ignored, file) < 0) {
                logger.trace("Not ignoring file {}", file);
                this.files.add(file);
                this.fileNames.add(Jb7Utils.removeExtension(file.getName()));
            }
        }
    }

    private String removeIllegalCharacters(String title) {
        logger.trace("Removing illegal characters from song title '{}'", title);
        String stripped = title.replace("\\", " ");
        stripped = stripped.replace("/", " ");
        stripped = stripped.replace("?", " ");
        stripped = stripped.replace(":", " ");
        stripped = stripped.trim();
        logger.trace("Changed '{}' to '{}'", title, stripped);
        return stripped;
    }

    /**
     * <p>
     * Taken from <a
     * href="http://rosettacode.org/wiki/Levenshtein_distance#Java"
     * >http://rosettacode.org/wiki/Levenshtein_distance#Java</a>
     * </p>
     * 
     * @param s1
     *            first String
     * @param s2
     *            second String
     * @return a number saying how similar they are (with 0 being exactly the
     *         same and the length of the longest String being the least
     *         similar)
     */
    public static int computeDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    File findTrackFile(String track) {
        logger.trace("Finding closest file for track name {}", track);
        track = removeIllegalCharacters(track);

        int closestMatch = Integer.MAX_VALUE;
        File closestFile = null;
        for (int num = 0; num < fileNames.size(); num++) {
            int match = computeDistance(track, fileNames.get(num));
            logger.trace("Computed distance of {} between {} and {}", match,
                    track, fileNames.get(num));

            if (match <= 0) {
                logger.trace("Distance of 0 or less is perfect match, "
                        + "returning file {}", files.get(num));
                return files.get(num);
            } else if (match < closestMatch) {
                closestMatch = match;
                closestFile = files.get(num);

                logger.trace("Got a closer match, closest so far is file {}",
                        closestFile);
            }
        }

        logger.trace("Closest file to {} deemed to be {}", track, closestFile);
        return closestFile;
    }
}
