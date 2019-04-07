package gui;

import game_engine.MonsterSchema;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;

public class MonsterTableModel extends AbstractTableModel
{
    public MonsterTableModel(LinkedList<MonsterSchema> monsters)
    {
        mMonsters = monsters;
    }

    @Override
    public int getRowCount()
    {
        return mMonsters.size();
    }

    @Override
    public int getColumnCount()
    {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if(columnIndex == 0)
        {
            return mMonsters.get(rowIndex).getName();
        }
        else
        {
            return mMonsters.get(rowIndex).getXpCost();
        }
    }

    LinkedList<MonsterSchema> mMonsters;
}
