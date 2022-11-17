package me.pseudoknight.chplaceholderapi;

import com.laytonsmith.core.constructs.CClosure;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.CancelCommandException;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.exceptions.ProgramFlowManipulationException;
import com.laytonsmith.core.natives.interfaces.Mixed;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

	private final String identifier;
	private final CClosure closure;

	Placeholders(String identifier, CClosure closure) {
		this.identifier = identifier;
		this.closure = closure;
	}

	public static boolean unregister(String id) {
		PlaceholderExpansion phe = PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().getExpansion(id);
		if(phe == null) {
			return false;
		}
		return phe.unregister();
	}

	@Override
	public @NotNull String getIdentifier() {
		return identifier;
	}

	@Override
	public String onRequest(OfflinePlayer player, String s) {
		CString string = new CString(s, Target.UNKNOWN);
		Mixed ret = null;
		try {
			if (player == null) {
				ret = closure.executeCallable(CNull.NULL, string);
			} else if(player.isOnline()) {
				ret = closure.executeCallable(new CString(player.getName(), Target.UNKNOWN), string);
			} else {
				ret = closure.executeCallable(new CString(player.getUniqueId().toString(), Target.UNKNOWN), string);
			}
		} catch (ConfigRuntimeException cre) {
			ConfigRuntimeException.HandleUncaughtException(cre, closure.getEnv());
		} catch (CancelCommandException e) {
			// fair enough
		} catch (ProgramFlowManipulationException e) {
			ConfigRuntimeException.DoWarning("Using program flow manipulation improperly!");
		}
		if(ret == null || ret instanceof CNull || ret instanceof CVoid) {
			return null;
		}
		return ret.val();
	}

	@Override
	public @NotNull String getAuthor() {
		return "CHPlaceHolderAPI";
	}

	@Override
	public @NotNull String getVersion() {
		return "0.1.6";
	}

	@Override
	public boolean persist() {
		return true;
	}
}
