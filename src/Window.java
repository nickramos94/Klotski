import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Window extends JFrame  {

    final static String winName = "Klotski";
    final static int width = 800;
    final static int height = 600;

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

    }
}
