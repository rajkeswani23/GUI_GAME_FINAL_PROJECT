//Raj Keswani
//December 14 2023

import java.util.Scanner;

public class Game
{
    private Deck deck;
    private Player player;
    private Player dealer;
    private int currentBet;


    public Game()
    {
        //These are the arrays for the rank, suit, and point value of the card.
        // The point and rank indexes line up with each other.
        //This constructor also creates a deck as well as a dealer and a player.
        String[] suits = {"Clubs", "Hearts", "Spades", "Diamonds"};
        String[] ranks = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
        int[] points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
        deck = new Deck(ranks, suits, points);
        Scanner user = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = user.nextLine();
        player = new Player(playerName);
        dealer = new Player("Dealer");
        currentBet = 0;
    }

    //This prints the instructions at the beginning of the game.
    public void printInstructions()
    {
        System.out.println("Welcome to Blackjack!!!");
        System.out.println("You will start with 2000 dollars.");
        System.out.println("For every hand, you can hit, stand, split, or double down.");
        System.out.println("Double down doubles your bet while split gives you two hands with half your bet for each.");
        System.out.println("The dealer will always hit until they are above 16.");
        System.out.println("Your session will end whenever you decide to cash out or lose all of your money.");
        System.out.println("Have fun and gamble responsibly!");
    }

    //This gets the user's initial bet for the round.
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
    }

    //This deals the initial card to the players. Two for the user and one for the dealer
    //This also prints the dealer and user hands.
    public void dealInitial()
    {
        player.addCard(deck.deal());
        dealer.addCard(deck.deal());
        player.addCard(deck.deal());
        dealer.dealerPrint();
        System.out.println(player);
    }

    //This is the hit method. It confirms the deck is refilled and shuffled, then adds a card and updates the hand sum.
    public void hit(Player player)
    {
        refillAndShuffleDeck();
        player.addCard(deck.deal());
        player.updateHandOne();
    }

    //This is the hit method for the second hand. Only the user uses it and it does the same thing as the hit method.
    public void hitHandTwo()
    {
        refillAndShuffleDeck();
        player.addSecondHandCard(deck.deal());
        player.updateHandTwo();
        System.out.println(player);
    }

   //This is the method for when the user stands.
    public void stand()
    {
        System.out.println("You chose to stand");
    }

    //this is the method for when the user splits.
    public void split()
    {
        //their second card is added to the second hand, and they now have one in each hand
        player.addSecondHandCard(player.getHand().remove(1));
    }

   //This is the method for double down.
    public void doubleDown()
    {
        //it doubles the player's bet if they have enough money
        if (currentBet < player.getPoints())
        {
            player.subtractPoints(currentBet);
        }
        else
        {
            System.out.println("You don't have enough money");
        }
    }

    //This checks if the player's hand is above 21
    public boolean checkTwentyOne()
    {
        return player.getHandOneSum() > 21;
    }

    //This checks if a player's second hand is above 21.
    public boolean checkTwentyOneSecondHand()
    {
        return player.getHandTwoSum() > 21;
    }

    //This checks if a player has blackjack.
    public boolean checkBlackJack()
    {
        return player.getHandOneSum() == 21;
    }

    //this checks if a player's second hand has blackjack.
    public boolean checkBlackJackSecondHand()
    {
        return player.getHandTwoSum() == 21;
    }

    //This is what the user does after hit or double down.
    public void userAfterInitialTurn()
    {
        Scanner user = new Scanner(System.in);
        String choiceTwo = " ";
        //while they don't choose stand.
        while (!choiceTwo.equals("stand"))
        {
            //check black jack
            if (checkBlackJack())
            {
                System.out.println("Black Jack!");
                return;
            }
            //check if they are above 21.
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
                //if they go above 21 they bust.
                if (checkTwentyOne())
                {
                    System.out.println("Bust!");
                    return;
                }
                //if they have 21 they have black jack.
                if (checkBlackJack())
                {
                    System.out.println("Black Jack!");
                    return;
                }
                //otherwise get another card
                hit(player);
                System.out.println(player);
            }
        }
    }

    //This is what they do if they split and have two hands. It runs through their second hand
    public void userAfterSplit()
    {
        Scanner user = new Scanner(System.in);
        String choiceTwo = " ";
        //while they do not stand
        while (!choiceTwo.equals("stand"))
        {
            //if above 21 they bust
            if (checkTwentyOneSecondHand())
            {
                System.out.println("Bust!");
                return;
            }
            //if they have 21 it is black jack
            if (checkBlackJackSecondHand())
            {
                System.out.println("Black Jack!");
                return;
            }
            System.out.println(player);
            System.out.println("Choose an action: hit or stand");
            choiceTwo = user.nextLine();
            //if they want to hit
            if (choiceTwo.equals("hit"))
            {
                //if above 21 they bust
                if (checkTwentyOneSecondHand())
                {
                    System.out.println("Bust!");
                    return;
                }
                //if they have 21 it is a blackjack
                if (checkBlackJackSecondHand())
                {
                    System.out.println("Black Jack!");
                    return;
                }
                //otherwise, they can get another card
                hitHandTwo();
                System.out.println(player);
            }
        }
    }

    //User's first turn where they can hit, stand, double down, or split
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
                //If the user can split
                if (player.canSplit())
                {
                    split();
                    //go through the second hand first
                    userAfterSplit();
                    System.out.println("Now the next hand");
                    //go through the next hand
                    userAfterInitialTurn();
                    System.out.println("Now it is the Dealer's Turn");
                }

                //otherwise, they cannot split and will be prompted to choose again.
                else
                {
                    System.out.println("Sorry you can't split. You must have two of the same card");
                    userTurn();
                }
            }

            else if (choice.equals("double down"))
            {
                doubleDown();
                //if they double down, they hit and their turn is over.
                hit(player);
                System.out.println(player);
                System.out.println("It is now the dealer's turn because you only get one card after you double down.");
            }
        }
    }

    //method for the dealer's turn
    public void dealerTurn()
    {
        //Now you can see the dealer's second card which they have to take
        System.out.println("Dealer shows second card");
        dealer.updateHandOne();
        hit(dealer);
        dealer.dealerPrint();

        //if the dealer has 21, they have blackjack.
        if (dealer.getHandOneSum() == 21)
        {
            System.out.println("Black Jack!");
            return;
        }

        //if user busts or gets blackjack, the dealer's turn is over after their second card is revealed
        if (player.getSecondHand().isEmpty() && (checkTwentyOne() || checkBlackJack()))
        {
                System.out.println("Dealer stands.");
                return;
        }

        //otherwise, the dealer will hit until they get 17 or higher no matter what
        while(dealer.getHandOneSum() < 17)
        {
            System.out.println("Dealer hits!");
            hit(dealer);
            dealer.dealerPrint();
        }

        //if they get above 21, they bust.
        if (dealer.getHandOneSum() > 21)
        {
            System.out.println("Dealer busts.");
            return;
        }

        //if they have 21 it is blackjack.
        else if(dealer.getHandOneSum() == 21)
        {
            System.out.println("Black Jack!");
            return;
        }

        //if not blackjack or bust, they stand
        System.out.println("Dealer stands.");
        dealer.dealerPrint();
    }

    //method is the result of a winning hand.
    public void win()
    {
        System.out.println("This hand wins!");
        //if they have one hand, they get twice their original bet
        if (player.getSecondHand().isEmpty())
        {
            player.addPoints(currentBet * 2);
        }

        //otherwise they get their original bet for each winning hand
        else
        {
            player.addPoints(currentBet);
        }
    }

    //This is the method for what happens when a hand loses.
    public void lose()
    {
        System.out.println("This hand loses!");
    }

    //this is the method for what happens when there is a tie.
    public void tie()
    {
        System.out.println("This hand ties!");
        //if they have one hand, they get all of their money back
        if (player.getSecondHand().isEmpty())
        {
            player.addPoints(currentBet);
        }

        //if they have two hands, a tied hand results in half of their money back (rounded up) for the tied hand.
        else
        {
            System.out.println("We don't give 50 cents so we round up a dollar.");
            player.addPoints(currentBet / 2 + 1);
        }
    }

    //This method checks for all possibilities and determines win tie or loss for hand one.
    public void compareHandOne()
    {
        if (dealer.getHandOneSum() > 21 && player.getHandOneSum() < 22)
        {
            win();
        }
        else if (player.getHandOneSum() > dealer.getHandOneSum() && player.getHandOneSum() < 22)
        {
            win();
        }
        else if (player.getHandOneSum() > 21 && dealer.getHandOneSum() < 22)
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

    //This method checks for all possibilities and determines win tie or loss for hand two.
    public void compareHandTwo()
    {
        if (dealer.getHandOneSum() > 21 && player.getHandTwoSum() < 22)
        {
            win();
        }
        else if (player.getHandTwoSum() > dealer.getHandOneSum() && player.getHandTwoSum() < 22)
        {
            win();
        }
        else if (player.getHandTwoSum() > 21 && dealer.getHandTwoSum() < 22)
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

    //This method checks the result of the hands.
    public void checkWin()
    {
        //if the user only has one hand, it gets compared to the dealer.
        if (player.getSecondHand().isEmpty())
        {
            compareHandOne();
        }

        //otherwise, both of their hands are compared to the dealer's hand
        else
        {
            System.out.println("Hand One first.");
            compareHandTwo();
            System.out.println("Hand Two now.");
            compareHandOne();
        }
    }

    //this method refills and shuffles the deck
    public void refillAndShuffleDeck()
    {
        //only refills and shuffles if the deck is out of cards
        if(deck.isEmpty())
        {
            //these are the arrays that refillDeck filters through to make cards
            String[] suits = {"Clubs", "Hearts", "Spades", "Diamonds"};
            String[] ranks = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
            int[] points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
            System.out.println("Deck is empty. Refilling and reshuffling...");
            //the refillDeck method gets called on these arrays
            deck.refillDeck(suits, ranks, points);
        }
    }

    //resets the game (player and dealer hands along with current bet)
    public void resetGame()
    {
        player.resetHand();
        dealer.resetHand();
        currentBet = 0;
    }

    //this is the method that goes through the game logic
    public void playGame()
    {
        Scanner scanner = new Scanner (System.in);
        printInstructions();
        //while the player has money
        while (player.getPoints() > 0 )
        {
            resetGame();
            initialBet();
            dealInitial();
            userTurn();
            dealerTurn();
            checkWin();
            System.out.println("You have $" + player.getPoints() + " remaining.");
            System.out.println("Would you like to play again? (yes/no)");
            String playAgain = scanner.nextLine();
            //if they do not say yes
            if(!playAgain.equals("yes"))
            {
                System.out.println("Thank you for playing!");
                System.out.println("You will walk away with " + player.getPoints() + " dollars!");
                return;
            }
        }
        //if the while loop is broken, they have 0 dollars
        System.out.println("Sorry, you are out of money.");
    }
}