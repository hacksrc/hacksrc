package gui;

import game_engine.Encounter;
import game_engine.MonsterSuggestionSet;

import javax.swing.*;

public class MonsterTables
{
    public void ReopulateTables(Encounter encounter)
    {
        MonsterSuggestionSet monsters = encounter.SuggestMonsters();
        mSuggestedTable = new JTable(new MonsterTableModel(monsters.GetSuggestedMonsters()));
        mOverflowTable = new JTable(new MonsterTableModel(monsters.GetOtherMonsters()));
    }

    public JTable GetSuggestedTable()
    {
        return mSuggestedTable;
    }

    public JTable GetOverflowTable()
    {
        return mOverflowTable;
    }

    private JTable mSuggestedTable;
    private JTable mOverflowTable;
}
