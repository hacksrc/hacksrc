package gui;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

public class MainPanel extends JPanel
{
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    public MainPanel()
    {
        super(new MigLayout("", "", ""));
        this.add(tabbedPane, "wrap");

        EncounterPanel encounterPanel = new EncounterPanel();
        AmbiencePanel ambiencePanel = new AmbiencePanel();

        tabbedPane.addTab("Encounter", encounterPanel);
        tabbedPane.addTab("Ambience", ambiencePanel);
    }
}
