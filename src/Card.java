import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Card {
    private String suit;
    private String value;
    private String imageFileName;
    private String backImageFileName;
    private boolean show;
    private BufferedImage image;
    private Rectangle cardBox;
    private boolean highlight;
    private static ArrayList<Card> deck = Card.buildDeck();

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.imageFileName = "images/card_"+suit+"_"+value+".png";
        this.backImageFileName = "images/card_back.png";
        this.show = true;
        this.image = readImage();
        this.cardBox = new Rectangle(-100, -100, image.getWidth(), image.getHeight());
        this.highlight = false;
    }

    public Rectangle getCardBox() {
        return cardBox;
    }

    public void setRectangleLocation(int x, int y) {
        cardBox.setLocation(x, y);
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public boolean isShown() {
        return show;
    }

    public void flipCard() {
        show = !show;
        image = readImage();
    }

    public void flipHighlight() {
        highlight = !highlight;
    }

    public boolean getHighlight() {
        return highlight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String toString() {
        return suit + " " + value;
    }

    private BufferedImage readImage() {
        try {
            if (show) return ImageIO.read(new File(imageFileName));
            return ImageIO.read(new File(backImageFileName));
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static ArrayList<Card> buildDeck() {
        ArrayList<Card> newDeck = new ArrayList<Card>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "A", "J", "K", "Q"};
        for (String suit : suits) {
            for (String value : values) {
                newDeck.add(new Card(suit, value));
            }
        }
        return newDeck;
    }

    public static int getDeckSize() {
        return deck.size();
    }

    public static void resetDeck() {
        deck = buildDeck();
    }

    public static ArrayList<Card> buildHand() {
        ArrayList<Card> hand = new ArrayList<Card>();
        if (deck.isEmpty()) deck = buildDeck();
        for (int i = 0; i < 9; i++) {
            int r = (int)(Math.random() * deck.size());
            hand.add(deck.remove(r));
        }
        return hand;
    }

    public static Card buildCard() {
        int r = (int)(Math.random() * deck.size());
        return deck.remove(r);
    }
}
