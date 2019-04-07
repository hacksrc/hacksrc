package game_engine;

public class XpBudget
{
    enum Difficulty
    {
        EASY,
        MEDIUM,
        HARD,
        DEADLY
    };

    public XpBudget(Difficulty difficulty)
    {
        switch(difficulty)
        {
            case EASY:
                mDifficulty = 0;
                break;
            case MEDIUM:
                mDifficulty = 1;
                break;
            case HARD:
                mDifficulty = 2;
                break;
            case DEADLY:
                mDifficulty = 3;
                break;
        }
    }

    public void AddPlayer(int level)
    {
        mXpBudget += mXpThresholdMap[mDifficulty][level];
    }

    public int GetXpBudget()
    {
        return mXpBudget;
    }

    private int mDifficulty = 0;
    private int mXpBudget = 0;
    private static final int[][] mXpThresholdMap = new int[][]
    {
        {25, 50, 75, 125, 250, 300, 350, 450, 550, 600, 800, 1000, 1100, 1250, 1400, 1600, 2000, 2100, 2400, 2800},
        {50, 100, 150, 250, 500, 600, 750, 900, 1100, 1200, 1600, 2000, 2200, 2500, 2800, 3200, 3900, 4200, 4900, 5700},
        {75, 150, 225, 375, 750, 900, 1100, 1400, 1600, 1900, 2400, 3000, 3400, 3800, 4300, 4800, 5900, 6300, 7300, 8500},
        {100, 200, 400, 500, 1100, 1400, 1700, 2100, 2400, 2800, 3600, 4500, 5100, 5700, 6400, 7200, 8800, 9500, 10900, 12700},
    };
}
