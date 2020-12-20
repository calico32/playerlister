# PlayerLister plugin

List players connected to a Minecraft server.

Default endpoints:

- `/` - Returns 200 OK
- `/players` - Returns the list of players in all worlds 
  
Example response:

```json
{
  "players": ["player1", "player2", "player3"]
}
```