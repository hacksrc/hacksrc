package gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class AmbiencePanel extends JPanel
{
        public AmbiencePanel()
        {
            super(new MigLayout());
            this.add(new JLabel("ambience panel"));
        }
}
