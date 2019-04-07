package game_engine;

import java.util.HashMap;

public class Encounter
{
    public Encounter(String name, int playerBudget, MonsterList list)
    {
        mPlayerBudget = playerBudget;
        mList = list;
        mEncounterName = name;
        mMonsters = new HashMap<>();
    }

    // Suggest monsters that fit within the current xp budget
    public MonsterSuggestionSet SuggestMonsters()
    {
        return mList.FilterByXp(GetAdjustedXp());
    }

    // Adds a monster with a unique name to the encounter
    public void AddMonster(String uniqueName, MonsterSchema monster)
    {
        if(!mMonsters.containsKey(uniqueName))
        {
            mMonsters.put(uniqueName, monster.GetMonster(uniqueName));
            mMonsterBudget += monster.getXpCost();
        }
    }

    // Removes a named monster from the encounter.
    public void RemoveMonster(String uniqueName)
    {
        mMonsterBudget -= mMonsters.get(uniqueName).mXpCost;
        mMonsters.remove(uniqueName);
    }

    // Gets the remaining xp in the budget after all modifications
    private int GetAdjustedXp()
    {
        int num_monsters = mMonsters.size() + 1;
        float modifier = 1;
        if(num_monsters >= 15)
        {
            modifier = 4;
        }
        else if(num_monsters >= 11)
        {
            modifier = 3;
        }
        else if(num_monsters >= 7)
        {
            modifier = 2.5f;
        }
        else if(num_monsters >= 3)
        {
            modifier = 2;
        }
        else if(num_monsters == 2)
        {
            modifier = 1.5f;
        }
        return (int)((mPlayerBudget - (modifier * mMonsterBudget))/modifier);
    }

    private final int mPlayerBudget;
    private int mMonsterBudget;
    private String mEncounterName;
    private HashMap<String, MonsterSchema.Monster> mMonsters;
    private MonsterList mList;
}
