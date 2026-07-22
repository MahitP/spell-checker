import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Dictionary
{
    private int size = 262139;
    private Node[] nodes = new Node[size];
    private int totalWords = 0;

    public void load(String filename)
    {
        try (Scanner scan = new Scanner(new File(filename)))
        {
            while(scan.hasNext())
            {
                String word = scan.next();
                int index = hash(word);
                totalWords++;

                if(nodes[index] == null)
                {
                    nodes[index] = new Node(word);
                }
                else
                {
                    Node node = new Node(word);
                    node.setNext(nodes[index]);
                    nodes[index] = node;
                }
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Not Found");
        }
    }

    public int getSize()
    {
        return totalWords;
    }

    public int hash(String string)
    {
        int sum = 0;
        char[] word = string.toCharArray();

        for (int i = 0; i < word.length; i++)
        {
            sum = (sum * 31 + Character.toUpperCase(word[i])) % size;
        }

        return sum;
    }

    public void print()
    {
        for(int i = 0; i < nodes.length; i++)
        {
            if(nodes[i] != null)
            {
                System.out.print("Bucket " + i + ": ");

                Node current = nodes[i];

                if (current == null) {
                    System.out.println("empty");
                    continue;
                }

                while(current != null)
                {
                    System.out.print(current.getWord() + " -> ");
                    current = current.getNext();
                }

                System.out.println("null");
            }
        }
    }

    public boolean check(String word)
    {
        int index = hash(word);

        Node current = nodes[index];

        while(current != null)
        {
            if(current.getWord().equalsIgnoreCase(word))
            {
                return true;
            }
            current = current.getNext();
        }

        return false;
    }

    public void stats()
    {
        int usedBuckets = 0;
        int longestChain = 0;
        int totalItems = 0;

        for(Node node : nodes)
        {
            if(node != null)
            {
                usedBuckets++;

                int chainLength = 0;
                Node current = node;

                while(current != null)
                {
                    chainLength++;
                    totalItems++;
                    current = current.getNext();
                }

                longestChain = Math.max(longestChain, chainLength);
            }
        }

        System.out.println("Items: " + totalItems);
        System.out.println("Used buckets: " + usedBuckets);
        System.out.println("Load factor: " + (double)totalItems / size);
        System.out.println("Longest chain: " + longestChain);
    }

    public static void main(String[] args)
    {
        Dictionary dict = new Dictionary();
        dict.load("src/dictionaries/large");
        dict.stats();
    }
}