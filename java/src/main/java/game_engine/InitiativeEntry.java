package game_engine;

public class InitiativeEntry
{
    public InitiativeEntry(int initiative, String name)
    {
        mInitiative = initiative;
        mName = name;
    }

    public InitiativeEntry(int initiative, MonsterSchema.Monster monster)
    {
        mInitiative = initiative;
        mName = monster.mName;
        mMonster = monster;
    }

    // Get the name
    public String getName()
    {
        return mName;
    }

    // Get the initiative
    public int getInitiative()
    {
        return mInitiative;
    }

    // Gets monster info, can be null.
    public MonsterSchema.Monster GetMonsterInfo()
    {
        return mMonster;
    }

    private int mInitiative;
    private String mName;
    private MonsterSchema.Monster mMonster = null;
}
