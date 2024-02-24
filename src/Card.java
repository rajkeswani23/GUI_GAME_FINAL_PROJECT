// Raj Keswani
// February 23, 2024

import javax.swing.*;
import java.awt.*;

public class Card
{
    private CardGameViewer view;
    private String rank;
    private String suit;
    private int point;
    private final int CARD_WIDTH = 50;
    private final int CARD_HEIGHT = 100;

    // Constructor that takes in a rank, suit, and point
    public Card(String rank, String suit, int point, CardGameViewer view)
    {
        this.view = view;
        this.rank = rank;
        this.suit = suit;
        this.point = point;
    }

    // Setters and getters for rank, point and suit
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
    // A toString that tells you the card's rank and suit
    public String toString() {
        return (rank + " of " + suit);
    }

    // Method that takes in the card's point to help calculate what image to use
    // It takes in rank as well to help differentiate a ten and face cards
    public int findRank(int point, String rank)
    {
        if (rank.equals("Ace"))
        {
           point = 1;
        }
        if (rank.equals("Jack"))
        {
            point = 11;
        }
        if (rank.equals("Queen"))
        {
            point = 12;
        }
        if (rank.equals("King"))
        {
            point = 13;
        }

       return point * 4 - 3;
    }

    // Method that takes in a suit and returns a number to help calculate what image a card is
    public int findSuit(String a)
    {
        if (a.equals("Spades"))
        {
            return 0;
        }
        if (a.equals("Hearts"))
        {
            return 1;
        }
        if (a.equals("Diamonds"))
        {
            return 2;
        }
            return 3;
    }

    // Draw method for the card, so it can draw itself on the window
    public void draw(Graphics g,ImageIcon[] cardImages,Card card, int x, int y)
    {
        g.drawImage(cardImages[findRank(card.getPoint(),card.getRank()) + findSuit(card.getSuit())].getImage(), x, y, CARD_WIDTH,CARD_HEIGHT, view);
    }
}
