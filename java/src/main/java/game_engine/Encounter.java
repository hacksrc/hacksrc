package game_engine;

public class Encounter
{
    public Encounter(XpBudget budget, MonsterList list)
    {
        mBudget = budget;
        mList = list;
    }

    private XpBudget mBudget;
    private MonsterList mList;
}
