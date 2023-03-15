package fr.qg.stacker.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class StackManager {

	private static final String STACK_KEY = "stack",
			ENTITY_NAME_PLACEHOLDER = "%name%",
			ENTITY_STACK_PLACEHOLDER = "%stack%";

	private final JavaPlugin plugin;
	private final String name;

	@Inject
	public StackManager(JavaPlugin plugin) {
		this.plugin = plugin;
		this.name = plugin.getConfig().getString("mob.name", "&c%name% * %stack%").replace("&", "§");
	}

	/**
	 * try to stack an entity
	 * it will not remove the entity given in parameter
	 *
	 * @param entity is the entity who spawned recently
	 * @return if the entity is stacked or not
	 */
	public boolean wantStack(Entity entity) {

		ConfigurationSection section = plugin.getConfig().getConfigurationSection("radius");
		int x = section.getInt("x");
		int y = section.getInt("y");
		int z = section.getInt("z");

		List<Entity> entities = entity.getNearbyEntities(x, y, z);

		for(Entity current : entities) {
			if(current.getType() != entity.getType())continue;
			stack(current, entity);
			return true;
		}
		return false;
	}

	/**
	 * Stack two entities
	 *
	 * @param source is the new entity
	 * @param current is the last entity
	 */
	public void stack(Entity source, Entity current) {
		int stack1 = getStack(source);
		int stack2 = getStack(current);
		setStack(source, stack1+stack2);
	}

	/**
	 * @param entity is the instance
	 * @return the entity stack size, 1 if not stacked
	 */
	public int getStack(Entity entity) {
		List<MetadataValue> values = entity.getMetadata(STACK_KEY);
		if(values.size() == 0)return 1;
		return values.get(0).asInt();
	}

	/**
	 * @param entity is the source entity
	 * @param stack is the new stack size
	 */
	public void setStack(Entity entity, int stack) {
		entity.setMetadata(STACK_KEY, new FixedMetadataValue(plugin, stack));
		entity.setCustomName(name.replace(ENTITY_NAME_PLACEHOLDER, entity.getType().name()).replace(ENTITY_STACK_PLACEHOLDER, stack + ""));
		entity.setCustomNameVisible(true);
	}

	/**
	 * originally used for entity loading
	 *
	 * @param entity is the current entity
	 * @return the entity stack size
	 */
	public int getStackFromName(Entity entity) {
		if(entity.getCustomName() == null)return 1;
		String entityName = entity.getCustomName();
		if(entityName.isEmpty())return 1;
		String[] split = name.replace(ENTITY_NAME_PLACEHOLDER, entity.getType().name()).split(ENTITY_STACK_PLACEHOLDER);
		try {
			return Integer.parseInt(entityName.replace(split[0], "").replace(split[1], ""));
		} catch(Exception exception) {
			return 1;
		}
	}
}
