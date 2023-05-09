import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Window extends JFrame  {

    final static String winName = "Klotski";
    final static int width = 800;
    final static int height = 600;
    final static int BOARD_LENGTH = 400;
    final static int BOARD_HEIGHT = 500;

    protected JPanel menu;
    protected JPanel board_view;

    public Window() {

        // Create Window
        super(winName);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        // Create MENU
        menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Create BOARD
        board_view = new JPanel();
        board_view.setLayout(new BorderLayout());
        board_view.setBackground(Color.GRAY);
        board_view.setBounds(50, 50, BOARD_LENGTH, BOARD_HEIGHT);


        JLabel title = new JLabel("Klotski");
        title.setFont(new Font("Arial", Font.BOLD, 88));
        title.setBorder(new EmptyBorder(150, 10, 10, 10));
        title.setHorizontalAlignment(JLabel.CENTER);

        String[] levels = { "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6" };
        JComboBox<String>  selectLevel = new JComboBox<String>(levels);
        selectLevel.setPreferredSize(new Dimension(100, 50));

        JButton playBtn = new JButton("Play");
        menu.putClientProperty("play_button", playBtn);
        playBtn.setPreferredSize(new Dimension(100, 50));
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel levelMenu = new JPanel();
        levelMenu.add(selectLevel);
        levelMenu.add(playBtn);

        menu.add(title, BorderLayout.NORTH);
        menu.add(levelMenu, BorderLayout.CENTER);

        // Create BOARD

        setVisible(true);
    }

    public void showMenu() {
        add(menu, BorderLayout.CENTER);
        setVisible(true);
    }

    public void showBoard() {
        add(board_view);
    }

    public void remove(JPanel panel) {
        super.remove(panel);
        revalidate();
        repaint();
    }
}
