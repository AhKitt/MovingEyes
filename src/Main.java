import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();

        window.setTitle("MovingEyes O.o");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        MouseHandler mh = new MouseHandler();
        EyesPanel panel = new EyesPanel(mh);
        panel.addMouseMotionListener(mh);

        window.add(panel);
        window.pack();

        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }
}