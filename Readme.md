# Client/ Frontend
commands
- npm install (in order to install the dependencies)
- npm start (in order to start the frontend application)
- npm test (in order to test the frontend application)

### React Router Routes (React App)

| Path                 | Component              | Behavior                                                                               |
| -------------------- | ---------------------- | -------------------------------------------------------------------------------------- |
| `/`                  | HomePage               | Form to type the player's name, and navigate to the game's page                        |
| `/players/:playerId` | GamePage               | Display of the rock, paper and scissors, the player and the enemy could throw.         |
|                      |                        | Once the player clicks one of the buttons with the symbols, the game starts, either    |
|                      |                        | there is a draw or a win, and there are displayed the symbol selected by the player,   |
|                      |                        | and the randomly selected symbol for the enemy.                                        |
|                      |                        | If there is a draw, if there is not a winner, there is the option to try again.        |
|                      |                        | And if there is a winner, the winner's name is displayed, and there is the option to   |
|                      |                        | play again.                                                                            |
|                      |                        | Either, when there is a draw, and the player tries again, or when there is a winner,   |
|                      |                        | and the player plays again, the rock, paper and scissors, the player and the enemy     |
|                      |                        | could throw, are displayed again.                                                      |

The draw is handled in the client/frontend, until there is a winner.
The win is handled in the server/backend:
- It instanciates and saves a game object, 
- It returns the result of the game to the client/frontend. 
    - If the result is true, the player won the game
    - If the result is false, the player lost the game, and therefor, the enemy won the game

<br>

## Components

- HomePage
- AddPlayer
- GamePage
- PlayerCard

## Services

- PlayerService
    - addPlayer(newPlayer)
    - getPlayerById(playerId)
- GameService
    - addGame(playerId, newGame)
- SymbolService
    - getSymbols()

# Server/ Backend

## Objects

#### Player Object

```javascript
{
​	id: long id,
​	nickName: String,
​	games: List<Game>
}
```

<br>

#### Game Object

```javascript
{
​	id:  long,
​	playerSmybol:  String,
​	​enemySmybol:  String,
​	result: boolean,
​	player: Player
}
```

<br>

## API Endpoints

| HTTP Method | URL                        | Request Body         | Success status | Error Status | Description                          |
| ----------- | -------------------------- | -------------------- | -------------- | ------------ | ------------------------------------ |
| POST        | `/players`                 | Player player        | 200            | 400/404      | Saves a player                       |
| GET         | `/players/{playerId}`      |                      | 200            | 404          | Gets a player by id                  |
| POST        | `players/{playerId}/games` | Game game            | 200            | 400/404      | Saves a game                         |

<br>

## Services

- PlayerService
    - Player create(Player player)
    - Optional<Player> getBy(long playerId)
- GameService
    - boolean create(Game game, Long playerId)
