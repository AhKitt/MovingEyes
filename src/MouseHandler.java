import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseMotionListener {
    public int mouseX;
    public int mouseY;

    @Override
    public void mouseDragged(MouseEvent e) {
        //do nothing
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
