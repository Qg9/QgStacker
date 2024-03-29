package fr.qg.stacker.listener;

import fr.qg.stacker.api.EntityStackReduceEvent;
import fr.qg.stacker.manager.DropsManager;
import fr.qg.stacker.manager.StackManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MobListener implements Listener {

	// Default item used internally
	private static final ItemStack DEFAULT_ITEM = new ItemStack(Material.APPLE);

	private final StackManager manager;
	private final DropsManager drops;

	private final int exp;

	@Inject
	public MobListener(JavaPlugin plugin, DropsManager drops, StackManager manager) {
		this.manager = manager;
		this.drops = drops;
		this.exp = plugin.getConfig().getInt("mob.experience");
	}

	/**
	 * change stack of an entity when he died and give itemstack
	 *
	 * @param event is the event instance
	 */
	@EventHandler(ignoreCancelled = true)
	public void onDied(EntityDamageByEntityEvent event) {

		if (!(event.getEntity() instanceof LivingEntity))return;
		if (!(event.getDamager() instanceof Player))return;

		LivingEntity entity = (LivingEntity) event.getEntity();
		Player player = (Player) event.getDamager();
		ItemStack item = player.getItemInHand();
		if(item == null)item = DEFAULT_ITEM;
		if ((entity.getHealth() - event.getFinalDamage()) >= 0) return;

		int stack = manager.getStack(entity);
		List<ItemStack> loots = new ArrayList<>();
		loots.add(drops.getLoot(item, entity));

		EntityStackReduceEvent reduceEvent = new EntityStackReduceEvent(manager, player, entity, stack,
				loots, 1, exp);

		if (reduceEvent.getReduce() >= stack)return;

		entity.setHealth(entity.getMaxHealth());
		event.setDamage(0);
		manager.setStack(entity, stack-reduceEvent.getReduce());

		for(ItemStack loot : loots)player.getInventory().addItem(loot);
		player.giveExp(reduceEvent.getFinalExp());
	}
}
