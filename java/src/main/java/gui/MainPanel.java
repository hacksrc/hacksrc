package gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

//public class MainPanel extends JPanel
//{
//private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
//
//    public MainPanel()
//    {
//        super(new MigLayout("fill", "[]", "[grow]"));
//        this.add(tabbedPane, "cell 0 0");
//
//        EncounterPanel encounterPanel = new EncounterPanel();
//        AmbiencePanel ambiencePanel = new AmbiencePanel();
//
//        tabbedPane.addTab("Encounter", encounterPanel);
//        tabbedPane.addTab("Ambience", ambiencePanel);
//    }
//}


public class MainPanel extends JTabbedPane
{
    public MainPanel()
    {
        super();

        EncounterPanel encounterPanel = new EncounterPanel();
        AmbiencePanel ambiencePanel = new AmbiencePanel();

        this.addTab("Encounter", encounterPanel);
        this.addTab("Ambience", ambiencePanel);
    }
}
