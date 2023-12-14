//Raj Keswani
//December 14 2023

import java.util.ArrayList;
public class Player
{
    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> secondHand;
    private int points;
    private int handOneSum;
    private int handTwoSum;

    public Player(String name)
    {
        this.name = name;
        this.secondHand = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.points = 2000;
        this.handOneSum = 0;
        this.handTwoSum = 0;
    }

    public Player(String name, ArrayList<Card> hand)
    {
        this.name = name;
        this.secondHand = new ArrayList<>();
        this.hand = hand;
        this.points = 2000;
        this.handOneSum = 0;
        this.handTwoSum = 0;
    }

    //Getter for player name
    public String getName()
    {
        return name;
    }

    //getter for player hand
    public ArrayList<Card> getHand()
    {
        return hand;
    }

    //getter for second hand
    public ArrayList<Card> getSecondHand() {
        return secondHand;
    }

    //getter for player's money (points)
    public int getPoints()
    {
        return points;
    }

    //getter for sum of player's hand
    public int getHandOneSum() {
        return handOneSum;
    }

    //getter for sum of player's second hand
    public int getHandTwoSum() {
        return handTwoSum;
    }

    //setter for points (player's money)
    public void setPoints(int points) {
        this.points = points;
    }

    //changes points to add points passed through
    public void addPoints(int points)
    {
        this.points += points;
    }

    //changes points to subtract points passed through
    public void subtractPoints(int points)
    {
        this.points -= points;
    }

    //add's a card to first hand
    public void addCard(Card card)
    {
        hand.add(card);
    }

    //adds card to second hand
    public void addSecondHandCard(Card card)
    {
        secondHand.add(card);
    }

    //Checks if a player can split in the game (they must have two of a kind and only two cards).
    public boolean canSplit()
    {
        return hand.size() == 2 && hand.get(0).getRank().equals(hand.get(1).getRank());
    }

    //calculates the sum of cards in a hand
    public int calculateHand(ArrayList<Card> hand)
    {
        int sum = 0;
        int numAces = 0;
        //This for-each will add a card's point value to the sum, if it is an ace, it will also add to number of aces.
        for (Card card: hand)
        {
            sum += card.getPoint();
            if (card.getRank().equals("Ace"))
            {
                numAces++;
            }
        }

        //This while loop will change subtract 10 from the sum of hand if the ace with 11 makes the hand go above 21.
        //This helps mimic an ace turning into a 1 as ace can be 11 or 1 in blackjack.
        while(numAces > 0 && sum > 21)
        {
            sum -= 10;
            numAces --;
        }

        return sum;
    }

    //This update's the first hand's value by calling calculate.
    public void updateHandOne()
    {
        handOneSum = calculateHand(hand);
    }

    //This updates the second hand's value by calling calculate
    public void updateHandTwo()
    {
        handTwoSum = calculateHand(secondHand);
    }

    //This resets the hand taking away the cards from the array list.
    public void resetHand()
    {
        hand.clear();
        secondHand.clear();
    }

    //This is what is printed to show the dealer's hand.
    public void dealerPrint()
    {
        System.out.println(this.name + "'s cards: "+ this.hand);
    }

    public String toString()
    {
        //This if prints the user's hand and money if they only have one hand.
        if(secondHand.isEmpty())
        {
            return this.name + " has " + this.points + " dollars\n" + this.name + "'s cards: " + this.hand + "\n";
        }

        //This if prints both of the user's hands and their money if they have two hands.
        else
        {
            return this.name + " has " + this.points + " dollars\n" + this.name + "'s cards: \n" + "Deck 1: " +
                    this.secondHand + "\n" + "Deck 2: " + this.hand + "\n";
        }
    }
}