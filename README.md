# CHPlaceholderAPI

## Function Documentation

**set_placeholders(player, string)**

Replaces all placeholders in the given string. Player can be null. This functionality in PlaceholderAPI automatically
"colorizes" the returned string.

**register_placeholder_hook(identifier, closure)

Registers a PlaceholderAPI identifier. When the identifier is used in a placeholder, it executes the given closure.
The closure will be passed the player name (or null) and the particular placeholder name that follows the identifier
(eg. \"%id_placeholder_name%\") as variables. Use return() in the closure to specify the output for each placeholder
name you're checking for.";

**unregister_placeholder_hook(identifier)

Unregisters a PlaceholderAPI identifier.