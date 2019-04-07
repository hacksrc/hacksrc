// MonsterSchema.java
package game_engine;

import java.util.Comparator;

public class MonsterSchema
{
    public class Monster
    {
        Monster(Monster other)
        {
            mName = other.mName;
            mMaxHp = mCurrentHp = other.mMaxHp;
            mArmor = other.mArmor;
            mXpCost = other.mXpCost;
            mPage = other.mPage;
        }

        Monster(String name, int hp, int ac, int xp, int page)
        {
            mName = name;
            mMaxHp = mCurrentHp = hp;
            mArmor = ac;
            mXpCost = xp;
            mPage = page;
        }

        // Heal or harm monster
        public void DoDamage(int damage)
        {
            mCurrentHp -= damage;
            if(mCurrentHp > mMaxHp)
            {
                mCurrentHp = mMaxHp;
            }
        }

        // Check if monster is dead
        public boolean isDead()
        {
            return mCurrentHp <= 0;
        }

        public final String mName;
        public final int mMaxHp;
        public final int mArmor;
        public final int mXpCost;
        public final int mPage;
        private int mCurrentHp;
    }

    // Creates a monster schema
    public MonsterSchema(String name, int hp, int ac, int xp, int page)
    {
        mMonster = new Monster(name, hp, ac, xp, page);
    }

    // Gets a monster object created from this schema.
    public Monster GetMonster()
    {
        return new Monster(mMonster);
    }

    // Get the name
    public String getName()
    {
        return mMonster.mName;
    }

    // Get the max hp
    public int getMaxHp()
    {
        return mMonster.mMaxHp;
    }

    // Get AC
    public int getArmor()
    {
        return mMonster.mArmor;
    }

    // Get XP cost
    public int getXpCost()
    {
        return mMonster.mXpCost;
    }

    // Get page number
    public int getPageNumber()
    {
        return mMonster.mPage;
    }

    public static Comparator<MonsterSchema> mComparator =
        (MonsterSchema m1, MonsterSchema m2)->m1.getName().compareTo(m2.getName());
    private Monster mMonster;
}
