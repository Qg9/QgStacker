package fr.qg.stacker.api;

import fr.qg.stacker.manager.StackManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EntityStackReduceEvent extends EntityEvent implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;

	private boolean cancelled;

	private final StackManager manager;
	private final int current;
	private final List<ItemStack> loots;
	private int reduce, finalReduce, exp, finalExp;

	public EntityStackReduceEvent(StackManager manager, Player damager,
	                              Entity entity, int current, List<ItemStack> loots, int reduce, int exp) {
		super(entity);
		this.player = damager;
		this.current = current;
		this.loots = loots;
		this.manager = manager;

		this.cancelled = false;

		this.reduce = reduce;
		this.exp = exp;
		this.finalReduce = reduce;
		this.finalExp = exp;
	}

	public List<ItemStack> getLoots() {
		return loots;
	}

	public int getFinalReduce() {
		return finalReduce;
	}

	public void setFinalReduce(int finalReduce) {
		this.finalReduce = finalReduce;
	}

	public int getFinalExp() {
		return finalExp;
	}

	public void setFinalExp(int finalExp) {
		this.finalExp = finalExp;
	}

	public Player getDamager() {
		return player;
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
