package com.creepercountry.ccspawners.database;

public class Miner extends MasterObject
{
	private String lastBreakName, lastBreakDate;
	private int spider, blaze, cavespider, creeper, pig, pigzombie, skeleton, zombie, ocelot,
	magmacube, cow, enderman, villager, enderdragon, chicken, slime, sheep, irongolem, mushroomcow,
	squid, ghast, wolf, snowman, silverfish, witch, bat, wither, giant;
	private double moneyGain;

    public Miner(String name)
    {
    	setName(name);
    }
    
    public void addMoneyGain(double money)
    {
    	this.moneyGain = moneyGain + money;
    }
    
    public void setMoneyGain(double money)
    {
    	this.moneyGain = money;
    }
    
    public double getMoneyGain()
    {
    	return this.moneyGain;
    }
    
    public void setLastBreak(String name, String date)
    {
    	this.lastBreakDate = date;
    	this.lastBreakName = name;
    }
    
    public String getLastBreakName()
    {
    	return lastBreakName;
    }
    
    public String getLastBreakDate()
    {
    	return lastBreakDate;
    }
    
    public String getLastBreak()
    {
    	return lastBreakName + "@" + lastBreakDate;
    }
    
    public void setCount(String type, int amount)
    {
    	if (type.equalsIgnoreCase("spider"))
    		spider = amount;
    	else if (type.equalsIgnoreCase("blaze"))
    		blaze = amount;
    	else if (type.equalsIgnoreCase("cavespider"))
    		cavespider = amount;
    	else if (type.equalsIgnoreCase("creeper"))
    		creeper = amount;
    	else if (type.equalsIgnoreCase("pig"))
    		pig = amount;
    	else if (type.equalsIgnoreCase("pigzombie"))
    		pigzombie = amount;
    	else if (type.equalsIgnoreCase("skeleton"))
    		skeleton = amount;
    	else if (type.equalsIgnoreCase("zombie"))
    		zombie = amount;
    	else if (type.equalsIgnoreCase("ocelot"))
    		ocelot = amount;
    	else if (type.equalsIgnoreCase("magmacube"))
    		magmacube = amount;
    	else if (type.equalsIgnoreCase("cow"))
    		cow = amount;
    	else if (type.equalsIgnoreCase("enderman"))
    		enderman = amount;
    	else if (type.equalsIgnoreCase("villager"))
    		villager = amount;
    	else if (type.equalsIgnoreCase("enderdragon"))
    		enderdragon = amount;
    	else if (type.equalsIgnoreCase("chicken"))
    		chicken = amount;
    	else if (type.equalsIgnoreCase("slime"))
    		slime = amount;
    	else if (type.equalsIgnoreCase("sheep"))
    		sheep = amount;
    	else if (type.equalsIgnoreCase("irongolem"))
    		irongolem = amount;
    	else if (type.equalsIgnoreCase("mushroomcow"))
    		mushroomcow = amount;
    	else if (type.equalsIgnoreCase("squid"))
    		squid = amount;
    	else if (type.equalsIgnoreCase("ghast"))
    		ghast = amount;
    	else if (type.equalsIgnoreCase("wolf"))
    		wolf = amount;
    	else if (type.equalsIgnoreCase("snowman"))
    		snowman = amount;
    	else if (type.equalsIgnoreCase("silverfish"))
    		silverfish = amount;
    	else if (type.equalsIgnoreCase("witch"))
    		witch = amount;
    	else if (type.equalsIgnoreCase("bat"))
    		bat = amount;
    	else if (type.equalsIgnoreCase("wither"))
    		wither = amount;
    	else if (type.equalsIgnoreCase("giant"))
    		giant = amount;
    }
    
    public void incrementCount(String type)
    {
    	if (type.equalsIgnoreCase("spider"))
    		spider++;
    	else if (type.equalsIgnoreCase("blaze"))
    		blaze++;
    	else if (type.equalsIgnoreCase("cavespider"))
    		cavespider++;
    	else if (type.equalsIgnoreCase("creeper"))
    		creeper++;
    	else if (type.equalsIgnoreCase("pig"))
    		pig++;
    	else if (type.equalsIgnoreCase("pigzombie"))
    		pigzombie++;
    	else if (type.equalsIgnoreCase("skeleton"))
    		skeleton++;
    	else if (type.equalsIgnoreCase("zombie"))
    		zombie++;
    	else if (type.equalsIgnoreCase("ocelot"))
    		ocelot++;
    	else if (type.equalsIgnoreCase("magmacube"))
    		magmacube++;
    	else if (type.equalsIgnoreCase("cow"))
    		cow++;
    	else if (type.equalsIgnoreCase("enderman"))
    		enderman++;
    	else if (type.equalsIgnoreCase("villager"))
    		villager++;
    	else if (type.equalsIgnoreCase("enderdragon"))
    		enderdragon++;
    	else if (type.equalsIgnoreCase("chicken"))
    		chicken++;
    	else if (type.equalsIgnoreCase("slime"))
    		slime++;
    	else if (type.equalsIgnoreCase("sheep"))
    		sheep++;
    	else if (type.equalsIgnoreCase("irongolem"))
    		irongolem++;
    	else if (type.equalsIgnoreCase("mushroomcow"))
    		mushroomcow++;
    	else if (type.equalsIgnoreCase("squid"))
    		squid++;
    	else if (type.equalsIgnoreCase("ghast"))
    		ghast++;
    	else if (type.equalsIgnoreCase("wolf"))
    		wolf++;
    	else if (type.equalsIgnoreCase("snowman"))
    		snowman++;
    	else if (type.equalsIgnoreCase("silverfish"))
    		silverfish++;
    	else if (type.equalsIgnoreCase("witch"))
    		witch++;
    	else if (type.equalsIgnoreCase("bat"))
    		bat++;
    	else if (type.equalsIgnoreCase("wither"))
    		wither++;
    	else if (type.equalsIgnoreCase("giant"))
    		giant++;
    }
    
    public int getCount(String type)
    {
    	if (type.equalsIgnoreCase("spider"))
    		return spider;
    	else if (type.equalsIgnoreCase("blaze"))
    		return blaze;
    	else if (type.equalsIgnoreCase("cavespider"))
    		return cavespider;
    	else if (type.equalsIgnoreCase("creeper"))
    		return creeper;
    	else if (type.equalsIgnoreCase("pig"))
    		return pig;
    	else if (type.equalsIgnoreCase("pigzombie"))
    		return pigzombie;
    	else if (type.equalsIgnoreCase("skeleton"))
    		return skeleton;
    	else if (type.equalsIgnoreCase("zombie"))
    		return zombie;
    	else if (type.equalsIgnoreCase("ocelot"))
    		return ocelot;
    	else if (type.equalsIgnoreCase("magmacube"))
    		return magmacube;
    	else if (type.equalsIgnoreCase("cow"))
    		return cow;
    	else if (type.equalsIgnoreCase("enderman"))
    		return enderman;
    	else if (type.equalsIgnoreCase("villager"))
    		return villager;
    	else if (type.equalsIgnoreCase("enderdragon"))
    		return enderdragon;
    	else if (type.equalsIgnoreCase("chicken"))
    		return chicken;
    	else if (type.equalsIgnoreCase("slime"))
    		return slime;
    	else if (type.equalsIgnoreCase("sheep"))
    		return sheep;
    	else if (type.equalsIgnoreCase("irongolem"))
    		return irongolem;
    	else if (type.equalsIgnoreCase("mushroomcow"))
    		return mushroomcow;
    	else if (type.equalsIgnoreCase("squid"))
    		return squid;
    	else if (type.equalsIgnoreCase("ghast"))
    		return ghast;
    	else if (type.equalsIgnoreCase("wolf"))
    		return wolf;
    	else if (type.equalsIgnoreCase("snowman"))
    		return snowman;
    	else if (type.equalsIgnoreCase("silverfish"))
    		return silverfish;
    	else if (type.equalsIgnoreCase("witch"))
    		return witch;
    	else if (type.equalsIgnoreCase("bat"))
    		return bat;
    	else if (type.equalsIgnoreCase("wither"))
    		return wither;
    	else if (type.equalsIgnoreCase("giant"))
    		return giant;
    	return 0;
    }

	public void clear()
	{
		// nothing atm
	}
	
	@Override
	public boolean equals(Object obj)
	{
		Miner min = (Miner) obj;
		return min.getName().equals(this.getName()) ? true : false;
	}
}
