package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import game_engine.PlayerData;

public class EncounterPanel extends JPanel
{
    private final JLabel encounterNameLabel = new JLabel("Encounter name:");
    private final JTextField encounterNameTextField = new JTextField("");
    private final JButton saveEncounterButton = new JButton("Save");
    private final JButton loadEncounterButton = new JButton("Load");

    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);

    private final EncounterDifficultyPanel encounterDifficultyPanel = new EncounterDifficultyPanel();
    private final EncounterMonstersPanel encounterMonstersPanel = new EncounterMonstersPanel();

    public EncounterPanel()
    {
        super(new MigLayout("fill, debug", "[][][]", "[][grow]"));

        this.add(encounterNameLabel, "cell 0 0");
        this.add(encounterNameTextField, "cell 0 0, wmin 150");
        this.add(saveEncounterButton, "cell 0 0");
        this.add(loadEncounterButton, "cell 0 0");

        this.add(tabbedPane, "cell 0 1 3 1, align right, grow");
        tabbedPane.addTab("Difficulty", encounterDifficultyPanel);
        tabbedPane.addTab("Monsters", encounterMonstersPanel);
    }

    static class PlayersTableModel extends AbstractTableModel
    {
        private final List<PlayerData> rowData = new ArrayList<>();

        public void addPlayerData(PlayerData playerData)
        {
            this.rowData.add(playerData);
        }

        @Override
        public String getColumnName(int column) {
            switch (column)
            {
                case 0:
                    return "Name";
                case 1:
                    return "Level";
                default:
                    return null;
            }
        }

        @Override
        public int getRowCount()
        {
            return rowData.size();
        }

        @Override
        public int getColumnCount()
        {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            switch (columnIndex)
            {
                case 0:
                    return rowData.get(rowIndex).GetPlayerName();
                case 1:
                    return rowData.get(rowIndex).GetLevel();
                default:
                    return null;
            }
        }
    }

    class EncounterDifficultyPanel extends JPanel
    {
        private final PlayersTableModel playersTableModel = new PlayersTableModel();
        private final JTable playersTable = new JTable(playersTableModel);

        public EncounterDifficultyPanel()
        {
            super(new MigLayout("fill", "[grow][150::300]", "[grow][][]"));
            this.setBackground(Color.CYAN);

            this.add(playersTable, "cell 0 0 1 3, growy");

            playersTableModel.addPlayerData(new PlayerData("Kagain", 4));
        }
    }

    class EncounterMonstersPanel extends JPanel
    {
        public EncounterMonstersPanel()
        {
            super(new MigLayout("fill", "", ""));
        }
    }
}
