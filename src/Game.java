import java.util.Scanner;

public class Game
{
    private Deck deck;
    private Player player;
    private Player dealer;
    private int playerMoney;
    private int currentBet;

    public Game()
    {
        String[] suits = {"Clubs", "Hearts", "Spades", "Diamonds"};
        String[] ranks = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
        int[] points = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
        deck = new Deck(ranks, suits, points);
        Scanner user = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = user.nextLine();
        player = new Player(playerName);
        dealer = new Player("Dealer");
        playerMoney = 2000;
        currentBet = 0;
    }

    public void printInstructions()
    {
        System.out.println("Welcome to Blackjack!!!");
        System.out.println("You will start with 2000 dollars.");
        System.out.println("For every hand, you can hit, stand, split, or double down.");
        System.out.println("Your session will end whenever you decide to cash out or lose all of your money.");
        System.out.println("Have fun and gamble responsibly!");

    }
}



