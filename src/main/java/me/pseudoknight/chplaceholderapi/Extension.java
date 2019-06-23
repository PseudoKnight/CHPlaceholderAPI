package me.pseudoknight.chplaceholderapi;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.core.extensions.AbstractExtension;
import com.laytonsmith.core.extensions.MSExtension;

@MSExtension("CHPlaceholderAPI")
public class Extension extends AbstractExtension {

	public Version getVersion() {
		return new SimpleVersion(0,1,3);
	}

	@Override
	public void onStartup() {
		System.out.println("CHPlaceholderAPI " + getVersion() + " loaded.");
	}

	@Override
	public void onShutdown() {
		System.out.println("CHPlaceholderAPI " + getVersion() + " unloaded.");
	}

}
