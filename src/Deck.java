// Raj Keswani
// February 23, 2024

import java.util.ArrayList;
public class Deck
{
    private ArrayList<Card> cards;
    private int cardsLeft;

    private CardGameViewer view;

    // Constructor that makes the cards in the deck.
    public Deck(String[] ranks, String[] suits, int[] points, CardGameViewer viewer)
    {
        this.view = view;
        cards = new ArrayList<>();
        int index = 0;
        int point;
        // The Nested for each loop  goes through every rank and then makes a card of each suit for every rank.
        // For every rank, a point value is assigned based on the array of points. Index tells us where in the array.
        for (String rank : ranks)
        {
            for (String suit: suits)
            {
                point = points[index];
                cards.add(new Card(rank, suit, point,viewer));
            }
            index++;

        }
        cardsLeft = cards.size();
        //Finally, the deck is shuffled at the end of creation.
        shuffle();
    }

    // This checks if the deck is empty by looking at how many cards are left
    public boolean isEmpty()
    {
        return cardsLeft == 0;
    }

    public int getCardsLeft()
    {
        return cardsLeft;
    }

    // This method will deal a card as long as there are cards left. Then, it will remove that card from the total.
    public Card deal()
    {
        if (!isEmpty())
        {
            cardsLeft--;
            return cards.get(cardsLeft);
        }
        return null;
    }

    // This method shuffles the deck by swapping the last card with a random card and going backwards through the deck.
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

    // This method refills the deck. It does what the constructor does, while using the same deck (cards).
    public void refillDeck(String[] ranks, String[] suits, int[] points)
    {
        int index = 0;
        int point = points[index];
        for (String rank : ranks)
        {
            for (String suit: suits)
            {
                cards.add(new Card(rank, suit, point,view));
                point = points[index];
            }
            index++;
        }
        cardsLeft = cards.size();
        shuffle();
    }
}
