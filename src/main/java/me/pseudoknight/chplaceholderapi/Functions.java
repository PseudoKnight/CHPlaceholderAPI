package me.pseudoknight.chplaceholderapi;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class Functions {
	public static String docs() {
		return "Functions for the PlaceholderAPI plugin.";
	}

	@api
	public static class set_placeholders extends AbstractFunction {

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREPlayerOfflineException.class};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
			Player p = null;
			if(!(args[0] instanceof CNull)) {
				p = (Player) Static.GetPlayer(args[0].val(), t).getHandle();
			}
			return new CString(PlaceholderAPI.setPlaceholders(p, args[1].val()), t);
		}

		public String getName() {
			return "set_placeholders";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public String docs() {
			return "void {player, string} Replaces all placeholders in the given string. Player can be null."
					+ " This functionality in PlaceholderAPI automatically \"colorizes\" the returned string.";
		}

		public Version since() {
			return CHVersion.V3_3_2;
		}

	}
}
