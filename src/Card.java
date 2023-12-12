public class Card {
    private String rank;
    private String suit;
    private int point;

    public Card(String rank, String suit, int point )
    {
        this.rank = rank;
        this.suit = suit;
        this.point = point;
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        {
            this.rank = rank;
        }
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

    public void setSuit(String suit) {
        if(suit.equals("clubs") || suit.equals("diamonds") || suit.equals("hearts") || suit.equals("spades"))
        {
            this.suit = suit;
        }
    }
}
