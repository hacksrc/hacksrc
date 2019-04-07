// MonsterList.java
package game_engine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Comparator;

public class MonsterList
{
    public MonsterList() {}
    public MonsterList(MonsterList other)
    {
        other.mMonsterMap.forEach((k,v)->this.mMonsterMap.put(k, v));
    }

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
        list.sort(Comparator.comparing(MonsterSchema::getName));
    }

    // Return a sorted list of all monsters at or below maxXp
    public MonsterSuggestionSet FilterByXp(int maxXp)
    {
        LinkedList<MonsterSchema> on_budget = new LinkedList<>();
        LinkedList<MonsterSchema> off_budget = new LinkedList<>();
        mMonsterMap.forEach((k,v)->
        {
            if(k > maxXp)
            {
                off_budget.addAll(v);
            }
            else
            {
                on_budget.addAll(v);
            }
        });
        return new MonsterSuggestionSet(on_budget, off_budget);
    }

    // Saves all stored monsters to a file
    public void SaveMonsters(String filename)
    {
        try
        {
            FileWriter file = new FileWriter(filename);
            mMonsterMap.forEach((k,v)->
            {
                for (MonsterSchema monster : v)
                {
                    try
                    {
                        file.write(monster.getCSV() + "\n");
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            file.close();
        }
        catch(IOException except)
        {
            except.printStackTrace();
        }
    }

    private HashMap<Integer, LinkedList<MonsterSchema>> mMonsterMap = new HashMap<>();
}
