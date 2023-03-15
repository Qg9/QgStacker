package fr.qg.stacker.api;

import fr.qg.stacker.manager.StackManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public class EntityStackReduceEvent extends EntityEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private boolean cancelled;

	private final StackManager manager;
	private final int current;
	private int reduce;

	public EntityStackReduceEvent(StackManager manager, Entity entity, int current, int reduce) {
		super(entity);
		this.current = current;
		this.reduce = reduce;
		this.manager = manager;
		this.cancelled = false;
	}

	public int getCurrent() {
		return current;
	}

	public int getReduce() {
		return reduce;
	}

	public void setReduce(int reduce) {
		this.reduce = reduce;
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

	/**
	 * Gets the cancellation state of this event. A cancelled event will not
	 * be executed in the server, but will still pass to other plugins
	 *
	 * @return true if this event is cancelled
	 */
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets the cancellation state of this event. A cancelled event will not
	 * be executed in the server, but will still pass to other plugins.
	 *
	 * @param cancel true if you wish to cancel this event
	 */
	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}
