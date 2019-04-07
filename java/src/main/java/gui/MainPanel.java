package gui;

import javax.swing.JTabbedPane;

public class MainPanel extends JTabbedPane
{
    public MainPanel()
    {
        EncounterPanel encounterPanel = new EncounterPanel();
        AmbiencePanel ambiencePanel = new AmbiencePanel();

        this.addTab("Encounter", encounterPanel);
        this.addTab("Ambience", ambiencePanel);
    }
}
