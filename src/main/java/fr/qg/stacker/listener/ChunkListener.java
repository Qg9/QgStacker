package fr.qg.stacker.listener;

import fr.qg.stacker.manager.StackManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChunkListener implements Listener {

	private final StackManager manager;
	private final List<EntityType> types;

	@Inject
	public ChunkListener(JavaPlugin plugin, StackManager manager) {
		this.manager = manager;
		this.types = plugin.getConfig().getStringList("mob.blacklist").stream().map(it -> EntityType.valueOf(it.toUpperCase())).collect(Collectors.toList());
	}

	/**
	 * stack or load entity when a chunk is loaded
	 *
	 * @param event is the current instance
	 */
	@EventHandler
	public void onLoad(ChunkLoadEvent event) {
		Arrays.stream(event.getChunk().getEntities()).forEach(e -> {
			if(!types.contains(e.getType()))return;
			int stack = manager.getStackFromName(e);
			manager.setStack(e, stack);
		});
	}
}
