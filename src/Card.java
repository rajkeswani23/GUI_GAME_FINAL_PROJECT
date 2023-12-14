//Raj Keswani
//December 14

public class Card
{
    private String rank;
    private String suit;
    private int point;

    //constructor that takes in a rank, suit, and point
    public Card(String rank, String suit, int point )
    {
        this.rank = rank;
        this.suit = suit;
        this.point = point;
    }

    //setters and getters for rank, point and suit
    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
            this.rank = rank;
    }

    public String getSuit()
    {
        return suit;
    }

    public int getPoint()
    {
        return point;
    }

    public void setPoint(int point)
    {
        this.point = point;
    }

    public void setSuit(String suit)
    {
            this.suit = suit;
    }

    @Override
    //toString that tells you the card's rank and suit
    public String toString() {
        return (rank + " of " + suit);
    }
}
