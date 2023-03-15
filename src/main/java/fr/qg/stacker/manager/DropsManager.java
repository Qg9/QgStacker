package fr.qg.stacker.manager;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DropsManager {

	private final Map<EntityType, Material> loots;

	@Inject
	public DropsManager(JavaPlugin plugin) {
		this.loots = new HashMap<>();
		ConfigurationSection section = plugin.getConfig().getConfigurationSection("mob.loots");
		for(String key : section.getKeys(false)) {
			Material type = Material.matchMaterial(section.getString(key).toUpperCase());
			EntityType entity = EntityType.valueOf(key.toUpperCase());
			loots.put(entity, type);
		}
	}

	/**
	 * get the loot drop by a stacked entity
	 *
	 * @param tool is the itemStack that the player have in the hand
	 * @param entity is the stacked entity
	 * @return return a material*amount
	 */
	public ItemStack getLoot(ItemStack tool, Entity entity) {
		Material material = loots.get(entity.getType());
		if(material == null)return null;
		return new ItemStack(material, tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS)+1);
	}
}
