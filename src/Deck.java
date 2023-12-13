import java.util.ArrayList;
public class Deck
{
    private ArrayList<Card> cards;
    private int cardsLeft;
    public Deck(String[] ranks, String[] suits, int[] points)
    {
        cards = new ArrayList<>();
        int index = 0;
        int point = points[index];
        for (String rank : ranks)
        {
            for (String suit: suits)
            {
                cards.add(new Card(rank, suit, point));
            }
            index++;
        }
        cardsLeft = cards.size();
        shuffle();
    }

    public boolean isEmpty()
    {
        return cardsLeft == 0;
    }

    public int getCardsLeft()
    {
        return cardsLeft;
    }

    public Card deal()
    {
        if (!isEmpty())
        {
            cardsLeft--;
            return cards.get(cardsLeft);
        }
        return null;
    }

    public void shuffle()
    {
        for (int i = cards.size() - 1; i > 0; i--)
        {
            int r = (int)(Math.random() * (i+1));
            Card temp = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r, temp);
        }
    }
}
