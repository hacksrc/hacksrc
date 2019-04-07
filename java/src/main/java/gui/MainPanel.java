package gui;

import java.awt.CardLayout;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class MainPanel extends JPanel
{
    private CardLayout cardLayout;

    MainPanel()
    {
        super(new MigLayout("", "", ""));
    }
}
