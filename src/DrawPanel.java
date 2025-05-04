import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    private Rectangle button;
    private Rectangle replaceButton;
    private static int value = 0;

    public DrawPanel() {
        button = new Rectangle(77, 230, 160, 26);
        replaceButton = new Rectangle(77, 270, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        int counter = 0;
        int[] num = new int[hand.size()];

        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            try {
                num[i] = Integer.parseInt(c.getValue());
            } catch (Exception e) {
                num[i] = -1000;
            }

            if (c.getHighlight()) {
                g.drawRect(x, y, c.getImage().getWidth(), c.getImage().getHeight());
            }
            c.setRectangleLocation(x, y);
            g.drawImage(c.getImage(), x, y, null);
            x += c.getImage().getWidth() + 10;
            counter++;
            if (counter == 3) {
                y += c.getImage().getHeight() + 10;
                x = 50;
                counter = 0;
            }
        }

        g.setFont(new Font("Courier New", Font.BOLD, 20));

        boolean validMove = hasTwoSum(num, 11) || hasSuit();
        int cardsLeft = Card.getDeckSize();

        g.drawString("Cards left: " + cardsLeft, 80, 280);
        g.drawString("GET NEW CARDS", 80, 250);
        g.drawRect((int) button.getX(), (int) button.getY(), (int) button.getWidth(), (int) button.getHeight());
        
        g.drawRect((int) replaceButton.getX()+15, (int) replaceButton.getY()+25, (int) replaceButton.getWidth(), (int) replaceButton.getHeight());
        g.setColor(Color.BLACK);
        g.drawString("REPLACE CARDS", 95, 310);  // Button Text

        if (!validMove) {
            if (cardsLeft == 0) {
                g.drawString("You Win!", 80, 310);
            } else {
                g.drawString("No possible moves!", 80, 345);
            }
        }
    }

    private boolean hasTwoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasSuit() {
        boolean hasJack = false; 
        boolean hasQueen = false; 
        boolean hasKing = false;
        for (Card c : hand) {
            if (!c.isShown()) {
                if (c.getValue().equals("J")) hasJack = true;
                if (c.getValue().equals("Q")) hasQueen = true;
                if (c.getValue().equals("K")) hasKing = true;
            }
        }
        return hasJack && hasQueen && hasKing;
    }

    public void mousePressed(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            value = 0;
            String s = "";


            if (button.contains(clicked)) {
                Card.resetDeck();
                hand = Card.buildHand();
            }

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
                if (!hand.get(i).isShown()) {
                    try {
                        if (hand.get(i).getValue().equals("A")) {
                            value += 1;
                        } else {
                            value += Integer.parseInt(hand.get(i).getValue());
                        }
                    } catch (Exception exception) {
                        s += hand.get(i).getValue();
                    }
                }
            }

// two sum 
            if (value == 11 || (s.contains("J") && s.contains("Q") && s.contains("K"))) {
                for (int i = 0; i < hand.size(); i++) {
                    if (!hand.get(i).isShown()) {
                        hand.get(i).flipCard();
                        if (Card.getDeckSize() > 0) {
                            hand.set(i, Card.buildCard());
                        }
                    }
                }
            }
        }

        if (e.getButton() == 3) {

            ArrayList<Card> highlightedCards = new ArrayList<>();

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    if (hand.get(i).getHighlight()) {
                        hand.get(i).flipHighlight();
                        highlightedCards.remove(hand.get(i));
                    } else {
                        hand.get(i).flipHighlight();
                        highlightedCards.add(hand.get(i));
                    }
                }
            }
        }


        if (replaceButton.contains(clicked)) {
            ArrayList<Card> highlightedCards = new ArrayList<>();

            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).getHighlight()) {
                    highlightedCards.add(hand.get(i));
                }
            }


            if (highlightedCards.size() == 2) {
                int cardValue1 = Integer.parseInt(highlightedCards.get(0).getValue());
                int cardValue2 = Integer.parseInt(highlightedCards.get(1).getValue());

                if (cardValue1 + cardValue2 == 11) {
                    int index = hand.indexOf(highlightedCards.get(0));
                    int index1 = hand.indexOf(highlightedCards.get(1));

                    if (Card.getDeckSize() > 0) {
                        hand.set(index, Card.buildCard());
                        hand.set(index1, Card.buildCard());
                    }
                }
            }
        }

        repaint();
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}
