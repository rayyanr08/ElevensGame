import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Font;

class DrawPanel extends JPanel implements MouseListener {

    private ArrayList<Card> hand;
    private Rectangle button;

    public DrawPanel() {
        button = new Rectangle(147, 100, 160, 26);
        this.addMouseListener(this);
        hand = Card.buildHand();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        int count = 0;

        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);

            // establish location of rectangle's hitbox
            c.setRectangleLocation(x, y);

            if (c.getHighlight()) {
                // draw the border around the card
                g.drawRect(x - 2, y - 2, c.getImage().getWidth() + 4, c.getImage().getHeight() + 4);
            }

            g.drawImage(c.getImage(), x, y, null);
            count = count + 1;

            // move to the next column
            x = x + c.getImage().getWidth() + 20;

            // after 3 cards, go to next row
            if (count % 3 == 0) {
                count = 0;
                x = 50;
                y = y + c.getImage().getHeight() + 20;
            }
        }

        // drawing bottom button with desired font
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("GET NEW CARDS", 85, 280);
        g.drawRect(80, 260, 180, 30);
    }

    public void mousePressed(MouseEvent e) {

        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (button.contains(clicked)) {
                hand = Card.buildHand();
            }

            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipCard();
                }
            }
        }

        if (e.getButton() == 3) {
            for (int i = 0; i < hand.size(); i++) {
                Rectangle box = hand.get(i).getCardBox();
                if (box.contains(clicked)) {
                    hand.get(i).flipHighlight();
                }
            }
        }


    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}
