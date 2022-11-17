# CHPlaceholderAPI

Latest version requires CommandHelper 3.3.4 - 3.3.5.
Use version 0.1.1 for earlier versions of CommandHelper.

## Function Documentation

**set_placeholders([player], string)**

Replaces all placeholders in the given string. Player can be null or absent if player context is not necessary for the
given placeholders. PlaceholderAPI automatically "colorizes" the returned string.

**register_placeholder_hook(identifier, closure)**

Registers a PlaceholderAPI identifier. When the identifier is used in a placeholder, it executes the given closure.
The closure will be passed the player name (or null) and the particular placeholder name that follows the identifier
(eg. \"%id_placeholder_name%\") as variables. Use return() in the closure to specify the output for each placeholder
name you're checking for. Returns true if the placeholder was successfully registered.

Example:
````
// handles %player_health% and %player_hunger%
register_placeholder_hook('player', closure(@player, @placeholder){
    if(is_null(@player)) {
        return("");
    }
    if(@placeholder == 'health') {
        return(phealth(@player));
    }
    if(@placeholder == 'hunger') {
        return(phunger(@player));
    }
});
````

**unregister_placeholder_hook(identifier)**

Unregisters a PlaceholderAPI identifier.
Returns true if a placeholder by that id existed.