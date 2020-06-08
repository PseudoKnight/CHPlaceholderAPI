package me.pseudoknight.chplaceholderapi;

import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.MSVersion;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.*;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREFormatException;
import com.laytonsmith.core.exceptions.CRE.CREPlayerOfflineException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.natives.interfaces.Mixed;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

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

		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			OfflinePlayer p = null;
			if(args.length == 2 && !(args[0] instanceof CNull) && !args[0].val().equals(Static.getConsoleName())) {
				p = (OfflinePlayer) Static.GetUser(args[0], t).getHandle();
			}
			return new CString(PlaceholderAPI.setPlaceholders(p, args[args.length - 1].val()), t);
		}

		public String getName() {
			return "set_placeholders";
		}

		public Integer[] numArgs() {
			return new Integer[]{1, 2};
		}

		public String docs() {
			return "string {[player], string} Replaces all placeholders in the given string."
					+ " Player can be null or absent if player context is not necessary for the given placeholders."
					+ " Offline players are supported by some placeholders."
					+ " PlaceholderAPI automatically \"colorizes\" the returned string.";
		}

		public Version since() {
			return MSVersion.V3_3_2;
		}

	}

	@api
	public static class register_placeholder_hook extends AbstractFunction {

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{CREFormatException.class};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			String id = args[0].val();
			CClosure closure;
			if(args[1] instanceof CClosure) {
				closure = (CClosure) args[1];
			} else {
				throw new CREFormatException("This must be a closure.", t);
			}
			if(PlaceholderAPI.isRegistered(id)) {
				PlaceholderAPI.unregisterPlaceholderHook(id);
			}
			try {
				new Placeholders(id, closure).register();
			} catch(IllegalArgumentException ex){
				throw new CREFormatException("Invalid identifier.", t);
			}
			return CVoid.VOID;
		}

		public String getName() {
			return "register_placeholder_hook";
		}

		public Integer[] numArgs() {
			return new Integer[]{2};
		}

		public String docs() {
			return "void {identifier, closure} Registers a PlaceholderAPI identifier. When the identifier is used in a"
					+ " placeholder, it executes the given closure. The closure will be passed the player name"
					+ " (or null) and the particular placeholder name that follows the identifier"
					+ " (eg. \"%id_placeholder_name%\") as variables. Use return() in the"
					+ " closure to specify the output for each placeholder name you're checking for.";
		}

		public Version since() {
			return MSVersion.V3_3_2;
		}

	}

	@api
	public static class unregister_placeholder_hook extends AbstractFunction {

		public Class<? extends CREThrowable>[] thrown() {
			return new Class[]{};
		}

		public boolean isRestricted() {
			return true;
		}

		public Boolean runAsync() {
			return false;
		}

		public Mixed exec(Target t, Environment environment, Mixed... args) throws ConfigRuntimeException {
			return CBoolean.get(PlaceholderAPI.unregisterPlaceholderHook(args[0].val()));
		}

		public String getName() {
			return "unregister_placeholder_hook";
		}

		public Integer[] numArgs() {
			return new Integer[]{1};
		}

		public String docs() {
			return "boolean {identifier} Unregisters a PlaceholderAPI identifier."
					+ " Returns true if a placeholder by that id existed.";
		}

		public Version since() {
			return MSVersion.V3_3_2;
		}

	}

}
