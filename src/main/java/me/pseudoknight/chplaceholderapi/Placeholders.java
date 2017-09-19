package me.pseudoknight.chplaceholderapi;

import com.laytonsmith.commandhelper.CommandHelperPlugin;
import com.laytonsmith.core.constructs.CClosure;
import com.laytonsmith.core.constructs.CNull;
import com.laytonsmith.core.constructs.CString;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.exceptions.FunctionReturnException;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class Placeholders extends EZPlaceholderHook {

    private CClosure closure;

    Placeholders(String identifier, CClosure closure) {
        super(CommandHelperPlugin.self, identifier);
        this.closure = closure;
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        CString string = new CString(s, Target.UNKNOWN);
        try {
            if (player == null) {
                closure.execute(CNull.NULL, string);
            } else {
                closure.execute(new CString(player.getName(), Target.UNKNOWN), string);
            }
        } catch (FunctionReturnException e) {
            Construct c = e.getReturn();
            if(c instanceof CNull){
                return null;
            }
            return c.val();
        } catch (ConfigRuntimeException cre){
            ConfigRuntimeException.HandleUncaughtException(cre, closure.getEnv());
        }
        return null;
    }
}
