import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import net.miginfocom.swing.MigLayout;

import gui.MainPanel;

public class Main
{
    public static void main(String[] args)
    {
        String windowsLookAndFeel = getLookAndFeel("Windows");
        if (windowsLookAndFeel != null)
        {
            try {
                UIManager.setLookAndFeel(windowsLookAndFeel);
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(Main::createAndRunGui);
    }

    private static String getLookAndFeel(String lookAndFeelName)
    {
        LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        for (LookAndFeelInfo info : plafs)
        {
            System.err.println("Main::setLookAndFeel - info.name: " + info.getName());
            if (info.getName().contains(lookAndFeelName)) {
                return info.getClassName();
            }
        }

        return null;
    }

    private static void createAndRunGui()
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("DM Assistant");
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainPanel mainPanel = new MainPanel();
        frame.getContentPane().setLayout(new MigLayout("fill, insets 0", "[]", "[][]"));
        frame.getContentPane().add(mainPanel, "dock north");

        frame.pack();
        frame.setVisible(true);
    }
}
