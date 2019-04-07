package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class EncounterPanel extends JPanel
{
    public EncounterPanel()
    {
        super(new MigLayout());
        this.add(new JLabel("encounter panel"));
    }
}
