package com.creepercountry.ccspawners.util;

import java.util.Set;

import org.bukkit.entity.EntityType;

public enum EntitySpawners
{
	SPIDER (EntityType.SPIDER),
	BLAZE (EntityType.BLAZE),
	CAVESPIDER (EntityType.CAVE_SPIDER),
	CREEPER (EntityType.CREEPER),
	PIG (EntityType.PIG),
	PIGZOMBIE (EntityType.PIG_ZOMBIE),
	SKELETON (EntityType.SKELETON),
	ZOMBIE (EntityType.ZOMBIE),
	OCELOT (EntityType.OCELOT),
	MAGMACUBE (EntityType.MAGMA_CUBE),
	COW (EntityType.COW),
	ENDERMAN (EntityType.ENDERMAN),
	VILLAGER (EntityType.VILLAGER),
	ENDERDRAGON (EntityType.ENDER_DRAGON),
	CHICKEN (EntityType.CHICKEN),
	SLIME (EntityType.SLIME),
	SHEEP (EntityType.SHEEP),
	IRONGOLEM (EntityType.IRON_GOLEM),
	MUSHROOMCOW (EntityType.MUSHROOM_COW),
	SQUID (EntityType.SQUID),
	GHAST (EntityType.GHAST),
	WOLF (EntityType.WOLF),
	SNOWMAN (EntityType.SNOWMAN),
	SILVERFISH (EntityType.SILVERFISH),
	WITCH (EntityType.WITCH),
	BAT (EntityType.BAT),
	WITHER (EntityType.WITHER),
	GIANT (EntityType.GIANT);
	
	/**
	 * the objects EntityType
	 */
	private final EntityType et;
	
	/**
	 * Constructor, specify an entity to set the object to better use.
	 * 
	 * @param entity <code>org.bukkit.entity.EntityType</code>
	 */
	EntitySpawners(EntityType entity)
	{
		this.et = entity;
	}
	
	/**
	 * @return the entity type <code>org.bukkit.entity.EntityType</code>
	 */
	public EntityType getEntityType()
	{
		return et;
	}
	
	/**
	 * @return the name of the entitytype <code>as String</code>
	 */
	public String getName()
	{
		return et.getName();
	}
	
	/**
	 * @return a Set of values from this enum
	 */
	public Set<EntitySpawners> toSet()
	{
		return this.toSet();
	}
}
