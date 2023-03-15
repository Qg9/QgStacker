package fr.qg.stacker.api;

import fr.qg.stacker.manager.StackManager;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.inject.Inject;

public class QgStackerPluginLoadedEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private final StackManager manager;

	@Inject
	public QgStackerPluginLoadedEvent(StackManager manager) {
		this.manager = manager;
	}

	public StackManager getManager() {
		return manager;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
