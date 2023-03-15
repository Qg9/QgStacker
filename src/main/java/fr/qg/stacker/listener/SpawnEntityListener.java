package fr.qg.stacker.listener;

import fr.qg.stacker.manager.StackManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;

public class SpawnEntityListener implements Listener {

	private final StackManager manager;

	@Inject
	public SpawnEntityListener(StackManager manager) {
		this.manager = manager;
	}

	/**
	 * stack an entity when she spawn from a spawner
	 *
	 * @param event is the current instance
	 */
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event) {
		if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER)return;
		LivingEntity spawned = event.getEntity();
		if(manager.wantStack(spawned))event.setCancelled(true);
	}
}
