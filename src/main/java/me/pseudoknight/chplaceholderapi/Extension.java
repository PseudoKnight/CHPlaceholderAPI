package me.pseudoknight.chplaceholderapi;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

@MSExtension("CHPlaceholderAPI")
public class Extension extends AbstractExtension {

	public Version getVersion() {
		return new SimpleVersion(0,1,6);
	}

	@Override
	public void onStartup() {
		try {
			Class.forName("me.clip.placeholderapi.expansion.PlaceholderExpansion");
		} catch(ClassNotFoundException ex) {
			Static.getLogger().severe( "[CHPlaceholderAPI] Dependency is not loaded: PlaceholderAPI plugin.");
			return;
		}
		Static.getLogger().info("CHPlaceholderAPI " + getVersion() + " loaded.");
	}

	@Override
	public void onShutdown() {
		Static.getLogger().info("CHPlaceholderAPI " + getVersion() + " unloaded.");
	}

}
