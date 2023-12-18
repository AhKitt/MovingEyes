import javax.swing.*;
import java.awt.*;

public class EyesPanel extends JPanel implements Runnable{

    final int WINDOW_WIDTH = 750;
    final int WINDOW_HEIGHT = 750;
    final int screenCenterX = WINDOW_WIDTH / 2;
    final int screenCenterY = WINDOW_HEIGHT / 2;
    final int constrainedDiameter = 200;
    final int constrainedRadius = constrainedDiameter/2;
    final int eyesAreaWidth = 320;
    final int eyesAreaHeight = 150;
    final int eyesDiameter = 150;
    final int eyesRadius = eyesDiameter / 2;
    final int FPS = 60;
    private MouseHandler mh;
    public Point validPoint = new Point(screenCenterX,screenCenterY); //default valid point
    boolean debug = false;


    public EyesPanel(MouseHandler mh) {
        //initialize
        setDoubleBuffered(true);
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setVisible(true);

        this.mh = mh;
        Thread panelThread = new Thread(this);
        panelThread.start();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            repaint(); // Trigger continuous repainting
            try {
                Thread.sleep(1000/FPS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculateAndSetValidPoint(); //get point within boundary

        if(debug) { drawEyesArea(g); }
        drawEyes(g);
    }

    public void drawEyes(Graphics g) {
        int validX = validPoint.x;
        int validY = validPoint.y;

        Point leftEyes = new Point(validX-(eyesAreaWidth/2) , validY-eyesAreaHeight/2);
        Point rightEyes = new Point(validX+(eyesAreaWidth/2)- eyesDiameter, validY-eyesAreaHeight/2);

        g.setColor(Color.WHITE);
        g.fillOval(leftEyes.x, leftEyes.y, eyesDiameter, eyesDiameter);
        g.fillOval(rightEyes.x, rightEyes.y, eyesDiameter, eyesDiameter);
        g.setColor(Color.BLACK);
        g.fillOval(leftEyes.x+(eyesRadius-25), leftEyes.y+(eyesRadius-25), 50, 50);
        g.fillOval(rightEyes.x+(eyesRadius-25), rightEyes.y+(eyesRadius-25), 50, 50);
    }

    public Point getArcPointEyesArea(int width, int height) {
        //arc point = upper-left point
        int arcX = validPoint.x - (width / 2);
        int arcY = validPoint.y - (height / 2);

        return new Point(arcX, arcY);
    }

    /**
     * calculate distance between mouse and screen center
     */
    public int calculateDistanceFromCenter() {
        // Formula for Euclidean distance: sqrt((x2 - x1)^2 + (y2 - y1)^2)
        return (int) Math.sqrt(Math.pow(screenCenterX - mh.mouseX, 2) + Math.pow(screenCenterY - mh.mouseY, 2));
    }

    /**
     * Calculates and sets the valid position within the constrained boundary.
     * The result is stored in the 'validPoint' field.
     */
    public void calculateAndSetValidPoint() {
        int distance = calculateDistanceFromCenter();
        int validX;
        int validY;
        if (distance > constrainedRadius) {
            double angle = Math.atan2(mh.mouseY - screenCenterY, mh.mouseX - screenCenterX);

            // calculate the intersect point of the constrained area
            validX = (int) (screenCenterX + constrainedRadius * Math.cos(angle));
            validY = (int) (screenCenterY + constrainedRadius * Math.sin(angle));
        } else {
            validX = mh.mouseX;
            validY = mh.mouseY;
        }
        validPoint = new Point(validX, validY);
    }
    public void drawEyesArea(Graphics g) {
        Point arcPoint = getArcPointEyesArea(eyesAreaWidth, eyesAreaHeight);
        g.setColor(Color.darkGray);
        g.fillRect(arcPoint.x, arcPoint.y, eyesAreaWidth, eyesAreaHeight);
    }
}
