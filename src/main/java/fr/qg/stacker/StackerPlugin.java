package fr.qg.stacker;

import fr.qg.stacker.api.QgStackerPluginLoadedEvent;
import fr.qg.stacker.listener.ChunkListener;
import fr.qg.stacker.listener.MobListener;
import fr.qg.stacker.listener.PropertyListener;
import fr.qg.stacker.listener.SpawnEntityListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.codejargon.feather.Feather;
import org.codejargon.feather.Provides;

public class StackerPlugin extends JavaPlugin {

	@Provides
	public JavaPlugin get() {
		return this;
	}

	@Override
	public void onEnable() {
		saveDefaultConfig();
		Feather feather = Feather.with(this);
		if(getConfig().getBoolean("load-stacked-in-chunk"))listen(feather.instance(ChunkListener.class));
		listen(feather.instance(PropertyListener.class));
		listen(feather.instance(SpawnEntityListener.class));
		listen(feather.instance(MobListener.class));

		Bukkit.getPluginManager().callEvent(feather.instance(QgStackerPluginLoadedEvent.class));
	}

	public void listen(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
}
