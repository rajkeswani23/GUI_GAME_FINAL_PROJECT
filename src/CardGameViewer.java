// Raj Keswani
// February 23, 2024

import javax.swing.*;
import java.awt.*;
public class CardGameViewer extends JFrame
{
    private Image backgroundImage;
    private Image cardBack;
    private final int WINDOW_WIDTH = 1010;
    private final int WINDOW_HEIGHT = 810;
    private final int CARD_SPACING = 60;
    private final int OUTCOME_X = 470;
    private final int OUTCOME_Y = 450;
    private Game game;

    private ImageIcon[] cardImages;

    public CardGameViewer(Game game)
    {
        this.game = game;

        // Setup the window and the buffer strategy.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Blackjack");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        backgroundImage = new ImageIcon("Resources/Background.png").getImage();
        cardBack = new ImageIcon("Resources/back.png").getImage();

        // Helps initialize the images and adds them to an array of ImageIcons
        cardImages = new ImageIcon[53];
        for (int i = 1; i < 53; i++)
        {
            cardImages[i] = new ImageIcon("Resources/" + i + ".png");
        }
    }


    // Paint method for everytime the board gets painted
    public void paint(Graphics g)
    {
        g.drawImage(backgroundImage,0,0,1000,800,this);
        g.drawImage(cardBack,800,270,150,250,this);
        g.setFont(new Font("Serif", Font.PLAIN, 30));
        g.drawString("Dealer", 480, 80);
        g.drawString("User", 480, 750);
        g.drawString("Money: " + game.getPlayer().getPoints(), 770, 250);

        // Gets every card to draw themselves in the dealer's hand and tells the card where to be drawn
        // If there are no cards in the hand, nothing will be drawn
        for (int i = 0; i < game.getDealer().getHand().size(); i++)
        {
            Card drawnCard = game.getDealer().getHand().get(i);
            drawnCard.draw(g,cardImages,drawnCard,(i + 1) * CARD_SPACING,120);
        }

        // Gets every card to draw themselves in the player's first hand and tells the card where to be drawn
        // If there are no cards in the hand, nothing will be drawn
        for (int i = 0; i < game.getPlayer().getHand().size(); i++)
        {
            Card drawnCard = game.getPlayer().getHand().get(i);
            drawnCard.draw(g,cardImages,drawnCard,(i + 1) * CARD_SPACING,500);
        }

        // Gets every card to draw themselves in the player's second hand and tells the card where to be drawn
        // If there are no cards in the hand, nothing will be drawn
        for (int i = 0; i < game.getPlayer().getSecondHand().size(); i++)
        {
            Card drawnCard = game.getPlayer().getSecondHand().get(i);
            drawnCard.draw(g,cardImages,drawnCard,(i + 1) * CARD_SPACING,630);
        }

        // If else method that takes in a marker and writes the outcome on the window
        if (game.getMarker() == 1)
        {
            g.drawString("You won money!", OUTCOME_X, OUTCOME_Y);
        }

        else if (game.getMarker() == 2)
        {
            g.drawString("You lost money!", OUTCOME_X, OUTCOME_Y);
        }

        else if (game.getMarker() == 3)
        {
            g.drawString("You broke even!", OUTCOME_X, OUTCOME_Y);
        }

    }

}

