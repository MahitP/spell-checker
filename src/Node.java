public class Node
{
    private String word;
    private Node next;

    public Node(String word)
    {
        this.word = word;
        this.next = null;
    }

    public String getWord()
    {
        return word;
    }

    public Node getNext()
    {
        return next;
    }

    public void setNext(Node next)
    {
        this.next = next;
    }

    @Override
    public String toString()
    {
        return word;
    }
}