package fr.qg.stacker.listener;

import fr.qg.stacker.manager.StackManager;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class PropertyListener implements Listener {

	private final StackManager manager;

	@Inject
	public PropertyListener(StackManager manager) {
		this.manager = manager;
	}

	/**
	 * block the fact that player can rename stacked entities
	 *
	 * @param event is the current event instance
	 */
	@EventHandler
	public void onRename(PlayerInteractEntityEvent event) {
		if (event.getPlayer().getItemInHand().getType() == Material.NAME_TAG &&
				event.getRightClicked() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity)event.getRightClicked();
			Player player = event.getPlayer();
			if(manager.getStack(entity) == 1)return;
			event.setCancelled(true);
			player.sendMessage("§cTu ne peux pas faire ça !");
		}
	}
}
