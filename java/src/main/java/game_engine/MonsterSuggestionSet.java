package game_engine;

import java.util.LinkedList;

public class MonsterSuggestionSet
{
    public MonsterSuggestionSet(LinkedList<MonsterSchema> onBudget, LinkedList<MonsterSchema> offBudget)
    {
        mSuggestedMonsters = onBudget;
        mOtherMonsters = offBudget;
    }

    public LinkedList<MonsterSchema> GetSuggestedMonsters()
    {
        return mSuggestedMonsters;
    }

    public LinkedList<MonsterSchema> GetOtherMonsters()
    {
        return mOtherMonsters;
    }

    private LinkedList<MonsterSchema> mSuggestedMonsters;
    private LinkedList<MonsterSchema> mOtherMonsters;
}
