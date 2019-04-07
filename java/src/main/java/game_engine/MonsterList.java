package game_engine;

import java.util.*;

public class MonsterList
{
    // Add a monster to the collection
    public void AddMonster(MonsterSchema monster)
    {
        int xp_cost = monster.getXpCost();
        LinkedList<MonsterSchema> list;
        if(!mMonsterMap.containsKey(xp_cost))
        {
            mMonsterMap.put(xp_cost, new LinkedList<>());
        }
        list = mMonsterMap.get(xp_cost);
        list.add(monster);
    }

    // Return a sorted list of all monsters at or below maxXp
    public LinkedList<MonsterSchema> GetMonsters(int maxXp)
    {
        LinkedList<MonsterSchema> valid_list = new LinkedList<>();
        mMonsterMap.forEach((k,v)->
        {
            if(k >= maxXp)
            {
                valid_list.addAll(v);
            }
        });
        valid_list.sort(Comparator.comparing(MonsterSchema::getName));
        return valid_list;
    }

    private HashMap<Integer, LinkedList<MonsterSchema>> mMonsterMap = new HashMap<>();
}
