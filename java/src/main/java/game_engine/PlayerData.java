package game_engine;

public class PlayerData
{
    public PlayerData(String name, int level)
    {
        mName = name;
        mLevel = level;
    }

    public String GetPlayerName()
    {
        return mName;
    }

    public int GetLevel()
    {
        return mLevel;
    }

    private String mName;
    private int mLevel;
}
