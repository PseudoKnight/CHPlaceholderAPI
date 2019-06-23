package me.pseudoknight.chplaceholderapi;

import com.laytonsmith.core.constructs.CClosure;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.exceptions.FunctionReturnException;
import com.laytonsmith.core.natives.interfaces.Mixed;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class Placeholders extends PlaceholderExpansion {

	private String identifier;
	private CClosure closure;

	Placeholders(String identifier, CClosure closure) {
		this.identifier = identifier;
		this.closure = closure;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String onRequest(OfflinePlayer player, String s) {
		CString string = new CString(s, Target.UNKNOWN);
		try {
			if (player == null) {
				closure.execute(CNull.NULL, string);
			} else if(player.isOnline()) {
				closure.execute(new CString(player.getName(), Target.UNKNOWN), string);
			} else {
				closure.execute(new CString(player.getUniqueId().toString(), Target.UNKNOWN), string);
			}
		} catch (FunctionReturnException e) {
			Mixed m = e.getReturn();
			if(m instanceof CNull){
				return null;
			}
			return m.val();
		} catch (ConfigRuntimeException cre){
			ConfigRuntimeException.HandleUncaughtException(cre, closure.getEnv());
		}
		return null;
	}

	@Override
	public String getAuthor() {
		return "CHPlaceHolderAPI";
	}

	@Override
	public String getVersion() {
		return "0.1.3";
	}
}
