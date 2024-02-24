// Raj Keswani
// February 23, 2024

import java.util.Scanner;

public class Game
{
    private Deck deck;
    private Player player;
    private Player dealer;
    private int currentBet;
    private CardGameViewer window;
    private int marker;
    private int pointsBefore;
    private final int BLACKJACK = 21;


    public Game()
    {
        // These are the arrays for the rank, suit, and point value of the card.
        // The point and rank indexes line up with each other.
        // This constructor also creates a deck as well as a dealer and a player.
        String[] suits = {"Clubs", "Hearts", "Spades", "Diamonds"};
        String[] ranks = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
        int[] points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
        deck = new Deck(ranks, suits, points, window);
        Scanner user = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = user.nextLine();
        player = new Player(playerName);
        dealer = new Player("Dealer");
        currentBet = 0;
        this.window = new CardGameViewer(this);
        marker = 0;
        pointsBefore = 0;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Player getDealer()
    {
        return dealer;
    }

    public int getMarker()
    {
        return marker;
    }

    // This prints the instructions at the beginning of the game.
    public void printInstructions()
    {
        System.out.println("Welcome to Blackjack!!!");
        System.out.println("You will start with 2000 dollars.");
        System.out.println("For every hand, you can hit, stand, split, or double down.");
        System.out.println("Split and double down can only be done on your first turn.");
        System.out.println("You can only split if you have two of a kind.");
        System.out.println("Double down doubles your bet while split gives you two hands with half your bet for each.");
        System.out.println("The dealer will always hit until they are above 16.");
        System.out.println("Your session will end whenever you decide to cash out or lose all of your money.");
        System.out.println("Have fun and gamble responsibly!");
    }

    // This gets the user's initial bet for the round.
    public void initialBet()
    {
        Scanner betFinder = new Scanner(System.in);
        System.out.println("You have " + player.getPoints() + " dollar's, what is your initial bet (no decimals)? ");
        currentBet = betFinder.nextInt();
        //Asks again if they do not have enough money,
        while (currentBet > player.getPoints())
        {
            System.out.println("Bet exceeds current balance. Please enter a new bet amount: ");
            currentBet = betFinder.nextInt();
        }
        player.subtractPoints(currentBet);
        window.repaint();
    }

    // This deals the initial card to the players. Two for the user and one for the dealer
    // This also prints the dealer and user hands.
    public void dealInitial()
    {
        player.addCard(deck.deal());
        dealer.addCard(deck.deal());
        player.addCard(deck.deal());
        window.repaint();
        dealer.dealerPrint();
        System.out.println(player);
    }

    // This is the hit method. It confirms the deck is refilled and shuffled, then adds a card and updates the hand sum.
    public void hit(Player player)
    {
        refillAndShuffleDeck();
        player.addCard(deck.deal());
        player.updateHandOne();
        window.repaint();
    }

    // This is the hit method for the second hand. Only the user uses it and it does the same thing as the hit method.
    public void hitHandTwo()
    {
        refillAndShuffleDeck();
        player.addSecondHandCard(deck.deal());
        player.updateHandTwo();
        System.out.println(player);
        window.repaint();
    }

   // This is the method for when the user stands.
    public void stand()
    {
        System.out.println("You chose to stand");
        window.repaint();
    }

    // This is the method for when the user splits.
    public void split()
    {
        // Their second card is added to the second hand, and they now have one in each hand
        player.addSecondHandCard(player.getHand().remove(1));
        window.repaint();
    }

   // This is the method for double down.
    public void doubleDown()
    {
        // It doubles the player's bet if they have enough money
        if (currentBet < player.getPoints())
        {
            player.subtractPoints(currentBet);
        }
        else
        {
            System.out.println("You don't have enough money");
        }
        window.repaint();
    }

    // This checks if the player's hand is above 21
    public boolean checkTwentyOne()
    {
        return player.getHandOneSum() > BLACKJACK;
    }

    // This checks if a player's second hand is above 21.
    public boolean checkTwentyOneSecondHand()
    {
        return player.getHandTwoSum() > BLACKJACK;
    }

    // This checks if a player has blackjack.
    public boolean checkBlackJack()
    {
        return player.getHandOneSum() == BLACKJACK;
    }

    // This checks if a player's second hand has blackjack.
    public boolean checkBlackJackSecondHand()
    {
        return player.getHandTwoSum() == BLACKJACK;
    }

    // This is what the user does after hit or double down.
    public void userAfterInitialTurn()
    {
        Scanner user = new Scanner(System.in);
        String choiceTwo = " ";
        // While they don't choose stand.
        while (!choiceTwo.equals("stand"))
        {
            // Check black jack
            if (checkBlackJack())
            {
                System.out.println("Black Jack!");
                return;
            }
            // Check if they are above 21.
            if (checkTwentyOne())
            {
                System.out.println("Bust!");
                return;
            }
            System.out.println("Choose an action: hit or stand");
            choiceTwo = user.nextLine();
            //if they choose hit, they want another card.
            if (choiceTwo.equals("hit"))
            {
                // If they go above 21 they bust.
                if (checkTwentyOne())
                {
                    System.out.println("Bust!");
                    return;
                }
                // If they have 21 they have black jack.
                if (checkBlackJack())
                {
                    System.out.println("Black Jack!");
                    return;
                }
                // Otherwise get another card
                hit(player);
                System.out.println(player);
            }
        }
    }

    // This is what they do if they split and have two hands. It runs through their second hand
    public void userAfterSplit()
    {
        Scanner user = new Scanner(System.in);
        String choiceTwo = " ";
        // While they do not stand
        while (!choiceTwo.equals("stand"))
        {
            // If above 21 they bust
            if (checkTwentyOneSecondHand())
            {
                System.out.println("Bust!");
                return;
            }
            // If they have 21 it is black jack
            if (checkBlackJackSecondHand())
            {
                System.out.println("Black Jack!");
                return;
            }
            System.out.println(player);
            System.out.println("Choose an action: hit or stand");
            choiceTwo = user.nextLine();
            // If they want to hit
            if (choiceTwo.equals("hit"))
            {
                // If above 21 they bust
                if (checkTwentyOneSecondHand())
                {
                    System.out.println("Bust!");
                    return;
                }
                // If they have 21 it is a blackjack
                if (checkBlackJackSecondHand())
                {
                    System.out.println("Black Jack!");
                    return;
                }
                // Otherwise, they can get another card
                hitHandTwo();
                System.out.println(player);
            }
        }
    }

    // User's first turn where they can hit, stand, double down, or split
    public void userTurn()
    {
        player.updateHandOne();
        if (checkBlackJack())
        {
            System.out.println("Blackjack!");
            return;
        }
        Scanner action = new Scanner(System.in);
        String choice = " ";
        while (!(choice.equals("hit") || choice.equals("stand") || choice.equals("split") || choice.equals("double down")))
        {
            System.out.println("Choose an action: hit, stand, split, or double down");
            choice = action.nextLine();

            if (choice.equals("hit"))
            {
                hit(player);
                System.out.println(player);
                userAfterInitialTurn();
                System.out.println("Now it is the Dealer's Turn");
            }

            else if (choice.equals("stand"))
            {
                stand();
                System.out.println("Now it is the Dealer's Turn");
            }

            else if (choice.equals("split"))
            {
                // If the user can split
                if (player.canSplit())
                {
                    split();
                    // Go through the second hand first
                    userAfterSplit();
                    System.out.println("Now the next hand");
                    // Go through the next hand
                    userAfterInitialTurn();
                    System.out.println("Now it is the Dealer's Turn");
                }

                // Otherwise, they cannot split and will be prompted to choose again.
                else
                {
                    System.out.println("Sorry you can't split. You must have two of the same card");
                    userTurn();
                }
            }

            else if (choice.equals("double down"))
            {
                doubleDown();
                // If they double down, they hit and their turn is over.
                hit(player);
                System.out.println(player);
                System.out.println("It is now the dealer's turn because you only get one card after you double down.");
            }
        }
    }

    // Method for the dealer's turn
    public void dealerTurn()
    {
        // Now you can see the dealer's second card which they have to take
        System.out.println("Dealer shows second card");
        dealer.updateHandOne();
        hit(dealer);
        dealer.dealerPrint();

        // If the dealer has 21, they have blackjack.
        if (dealer.getHandOneSum() == 21)
        {
            System.out.println("Black Jack!");
            return;
        }

        // If user busts or gets blackjack, the dealer's turn is over after their second card is revealed
        if (player.getSecondHand().isEmpty() && (checkTwentyOne() || checkBlackJack()))
        {
                System.out.println("Dealer stands.");
                return;
        }

        // Otherwise, the dealer will hit until they get 17 or higher no matter what
        while(dealer.getHandOneSum() < 17)
        {
            System.out.println("Dealer hits!");
            hit(dealer);
            dealer.dealerPrint();
        }

        // If they get above 21, they bust.
        if (dealer.getHandOneSum() > BLACKJACK)
        {
            System.out.println("Dealer busts.");
            return;
        }

        // If they have 21 it is blackjack.
        else if(dealer.getHandOneSum() == BLACKJACK)
        {
            System.out.println("Black Jack!");
            return;
        }

        // If not blackjack or bust, they stand
        System.out.println("Dealer stands.");
        dealer.dealerPrint();
    }

    // Method is the result of a winning hand.
    public void win()
    {
        System.out.println("This hand wins!");
        //if they have one hand, they get twice their original bet
        if (player.getSecondHand().isEmpty())
        {
            player.addPoints(currentBet * 2);
        }

        // Otherwise they get their original bet for each winning hand
        else
        {
            player.addPoints(currentBet);
        }
        window.repaint();
    }

    // This is the method for what happens when a hand loses.
    public void lose()
    {
        System.out.println("This hand loses!");
    }

    // This is the method for what happens when there is a tie.
    public void tie()
    {
        System.out.println("This hand ties!");
        // If they have one hand, they get all of their money back
        if (player.getSecondHand().isEmpty())
        {
            player.addPoints(currentBet);
        }

        // If they have two hands, a tied hand results in half of their money back (rounded up) for the tied hand.
        else
        {
            System.out.println("We don't give 50 cents so we round up a dollar.");
            player.addPoints(currentBet / 2 + 1);
        }
        window.repaint();
    }

    // This method checks for all possibilities and determines win tie or loss for hand one.
    public void compareHandOne()
    {
        if (dealer.getHandOneSum() > BLACKJACK && player.getHandOneSum() < 22)
        {
            win();
        }
        else if (player.getHandOneSum() > dealer.getHandOneSum() && player.getHandOneSum() < 22)
        {
            win();
        }
        else if (player.getHandOneSum() > BLACKJACK && dealer.getHandOneSum() < 22)
        {
            lose();
        }
        else if (player.getHandOneSum() < dealer.getHandOneSum() && dealer.getHandOneSum() < 22)
        {
            lose();
        }
        else if (player.getHandOneSum() == dealer.getHandOneSum())
        {
            tie();
        }
    }

    // This method checks for all possibilities and determines win tie or loss for hand two.
    public void compareHandTwo()
    {
        if (dealer.getHandOneSum() > BLACKJACK && player.getHandTwoSum() < 22)
        {
            win();
        }
        else if (player.getHandTwoSum() > dealer.getHandOneSum() && player.getHandTwoSum() < 22)
        {
            win();
        }
        else if (player.getHandTwoSum() > BLACKJACK && dealer.getHandTwoSum() < 22)
        {
            lose();
        }
        else if (player.getHandTwoSum() < dealer.getHandOneSum() && dealer.getHandOneSum() < 22)
        {
            lose();
        }
        else if (player.getHandTwoSum() == dealer.getHandOneSum())
        {
            tie();
        }
    }

    // This method checks the result of the hands.
    public void checkWin()
    {
        // If the user only has one hand, it gets compared to the dealer.
        if (player.getSecondHand().isEmpty())
        {
            compareHandOne();
        }

        // Otherwise, both of their hands are compared to the dealer's hand
        else
        {
            System.out.println("Hand One first.");
            compareHandTwo();
            System.out.println("Hand Two now.");
            compareHandOne();
        }
    }

    // This method refills and shuffles the deck
    public void refillAndShuffleDeck()
    {
        // Only refills and shuffles if the deck is out of cards
        if(deck.isEmpty())
        {
            // These are the arrays that refillDeck filters through to make cards
            String[] suits = {"Clubs", "Hearts", "Spades", "Diamonds"};
            String[] ranks = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
            int[] points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
            System.out.println("Deck is empty. Refilling and reshuffling...");
            // The refillDeck method gets called on these arrays
            deck.refillDeck(suits, ranks, points);
        }
    }

    // This method returns a number depending on an outcome, so the paint method in viewer knows what text to draw
    // Checks if money was lost, was gained, or stayed the same in the round
    public void pointsOutcome()
    {
        if (pointsBefore < player.getPoints())
        {
            marker = 1;
        }

        else if (pointsBefore > player.getPoints())
        {
            marker = 2;
        }

        else
        {
            marker = 3;
        }
        window.repaint();
    }

    //Resets the game (player and dealer hands along with current bet)
    public void resetGame()
    {
        player.resetHand();
        dealer.resetHand();
        currentBet = 0;
        marker = 0;
        window.repaint();
    }

    // This is the method that goes through the game logic
    public void playGame()
    {
        Scanner scanner = new Scanner (System.in);
        printInstructions();
        // While the player has money
        while (player.getPoints() > 0 )
        {
            pointsBefore = player.getPoints();
            resetGame();
            initialBet();
            dealInitial();
            userTurn();
            dealerTurn();
            checkWin();
            pointsOutcome();
            System.out.println("You have $" + player.getPoints() + " remaining.");
            System.out.println("Would you like to play again? (yes/no)");
            String playAgain = scanner.nextLine();
            // If they do not say yes
            if(!playAgain.equals("yes"))
            {
                System.out.println("Thank you for playing!");
                System.out.println("You will walk away with " + player.getPoints() + " dollars!");
                return;
            }
        }
        // If the while loop is broken, they have 0 dollars
        System.out.println("Sorry, you are out of money.");
    }

    public static void main(String[] args)
    {
        // New game object that play game gets called on
        Game game = new Game();
        game.playGame();
    }
}