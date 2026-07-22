import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Speller
{
    // Max word length
    public static final int LENGTH = 45;

    public static void main(String[] args)
    {
        // Check for correct number of args
        if (args.length != 1 && args.length != 2)
        {
            System.out.println("Usage: java Speller [DICTIONARY] text");
            return;
        }

        // Benchmarks (in seconds)
        double timeLoad = 0.0, timeCheck = 0.0, timeSize = 0.0;

        // Determine dictionary and text to use
        String dictionaryPath = (args.length == 2) ? args[0] : "src/dictionaries/large";
        String textPath = (args.length == 2) ? args[1] : args[0];

        // Instantiate your Hash Table (Main class)
        Dictionary dict = new Dictionary();

        // 1. LOAD DICTIONARY
        long beforeLoad = System.nanoTime();
        dict.load(dictionaryPath);
        long afterLoad = System.nanoTime();
        timeLoad = (afterLoad - beforeLoad) / 1_000_000_000.0;

        // Prepare to spell-check
        int misspellings = 0, words = 0;
        System.out.println("\nMISSPELLED WORDS\n");

        // 2. CHECK WORDS IN TEXT
        try (BufferedReader reader = new BufferedReader(new FileReader(textPath)))
        {
            int c;
            StringBuilder word = new StringBuilder();

            // Read character by character
            while ((c = reader.read()) != -1)
            {
                char ch = (char) c;

                // Allow only alphabetical characters and apostrophes
                if (Character.isLetter(ch) || (ch == '\'' && !word.isEmpty()))
                {
                    word.append(ch);

                    // Ignore alphabetical strings too long to be words
                    if (word.length() > LENGTH)
                    {
                        while ((c = reader.read()) != -1 && Character.isLetter((char) c));
                        word.setLength(0); // Prepare for new word
                    }
                }
                // Ignore words with numbers
                else if (Character.isDigit(ch))
                {
                    while ((c = reader.read()) != -1 && Character.isLetterOrDigit((char) c));
                    word.setLength(0); // Prepare for new word
                }
                // We must have found a whole word
                else if (word.length() > 0)
                {
                    words++;

                    // Check word's spelling
                    long beforeCheck = System.nanoTime();
                    boolean misspelled = !dict.check(word.toString());
                    long afterCheck = System.nanoTime();

                    timeCheck += (afterCheck - beforeCheck) / 1_000_000_000.0;

                    // Print word if misspelled
                    if (misspelled)
                    {
                        System.out.println(word.toString());
                        misspellings++;
                    }

                    // Prepare for next word
                    word.setLength(0);
                }
            }

            // Check for remaining word at end of file
            if (word.length() > 0)
            {
                words++;
                long beforeCheck = System.nanoTime();
                boolean misspelled = !dict.check(word.toString());
                long afterCheck = System.nanoTime();
                timeCheck += (afterCheck - beforeCheck) / 1_000_000_000.0;
                if (misspelled)
                {
                    System.out.println(word.toString());
                    misspellings++;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Could not open " + textPath + ".");
            return;
        }

        // 3. GET DICTIONARY SIZE
        long beforeSize = System.nanoTime();
        int n = dict.getSize();
        long afterSize = System.nanoTime();
        timeSize = (afterSize - beforeSize) / 1_000_000_000.0;

        // Report benchmarks
        System.out.println("\nWORDS MISSPELLED:     " + misspellings);
        System.out.println("WORDS IN DICTIONARY:  " + n);
        System.out.println("WORDS IN TEXT:        " + words);
        System.out.printf("TIME IN load:         %.2f\n", timeLoad);
        System.out.printf("TIME IN check:        %.2f\n", timeCheck);
        System.out.printf("TIME IN size:         %.2f\n", timeSize);
        System.out.printf("TIME IN TOTAL:        %.2f\n\n", (timeLoad + timeCheck + timeSize));
    }
}
