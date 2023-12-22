Conception MP2-2023
==
## 🔄 **Architecture changes**

### 📜 Messages
The `GamePlayMessage` now resides in a queue within the `GameState`, enabling the processing of multiple messages simultaneously. Previously, only the first message was considered.

### 👾 NPCActor
Introduced a constructor accepting a `RegionOfInterest` to facilitate the use of custom sprites.

### 🎮 Trainer Class
`package ch.epfl.cs107.icmon.actor.npc`<br>

A `Trainer` is now an `NPCActor` implementing `ICMonFightableActor`. This entity possesses a list of Pokémon, enabling battles with the player and introducing complexity to the game. Trainers can decide whether to engage in a fight with the player.

### 🐉 Pokémon
The previously abstract Pokémon class has undergone significant changes:
- Attributes `hp` and `maxHp` now have a `float` type for more precise damage calculations during fights.
- Added attributes `pokedexId`, `types`, and `defense` for damage calculations using the original Pokémon formula (gen III).
- Additional methods in `PokemonProperties` provide more information on the Pokémon's state.

#### 🔄 Dynamic Pokémon loader
`package ch.epfl.cs107.icmon.data`: package containing stuff related to data loading<br>

Implemented the `PokemonDataLoader` class to dynamically load Pokémon on the fly by reading the Pokédex, a collection of XML files. Consequently, the classes for Bulbasaur, Nidoqueen, and Latios have been deleted in favor of dynamically loaded Pokémon.
The `PokemonDataLoader` uses data structures such as `PokemonMove` and `PokemonType` to represent loaded data.

#### ⚔️ ICMonFightAction
Attacks are now dynamically created through the `PokemonDataLoader`.
- `doAction` considers both attacking and defending Pokémon to compute damages based on their stats.
- Added a `sfx` property getter to play a sound when the attack is triggered.
- A `type` property getter distinguishes `PHYSICAL` attacks from `SPECIAL` or `STATUS` attacks (`PokemonMoveType` enum).

#### 🥊 Fights
- `package ch.epfl.cs107.icmon.gamelogic.fights`<br>
    - Modified fights to play sounds and announce each attack before application. Trainers will announce their Pokémon if present.

- `package ch.epfl.cs107.icmon.graphics`<br>
  - Modified `ICMonFightArenaGraphics` to hide the hp bar when the Pokémon is KO. Also inverted the display of the two Pokémon battling to have more cohesion with real Pokémon games.

### 🔴 Pokéball area
`package ch.epfl.cs107.icmon.area.maps`<br>

The class `Pokeball` is a fictive area where new Pokémon are put when initialized. This allows to be free of the constraint of giving an area to the Pokémon to initialize it.

## 🆕 **New features**

### 🌍 ICMonArea
- Added `getAmbiantSound` method to provided audio context.
- Added `onEnter` method to be able to execute some code when the player enters the area.

### 🎵 Audio
`package ch.epfl.cs107.icmon.audio`: package containing stuff related to audio<br>

Implemented audio support through the `AudioPreset` and `ICMonGameState` classes. `AudioPreset` allows the definition of base presets for playing sounds.

### 🏥 Nurse
`package ch.epfl.cs107.icmon.actor.npc`<br>

Introduced the ability for the player to heal their Pokémon in a PokéCenter.
- Added `requestHealFromNurse`, `hasPokemonNeedingHeal`, and `hasHealthyPokemon` methods to `ICMonPlayer`.

### 🚀 Start menu
- `package ch.epfl.cs107.icmon.graphics`<br>
    - Added a start menu graphics class: `ICMonStartMenuGraphics`

- `package ch.epfl.cs107.icmon.gamelogic.menu`: package containing all pause menus except fights<br>
  - Added a start menu displaying the game's name and slogan (`ICMonStartMenu`).

### 🌾 Tall grass cell
`package ch.epfl.cs107.icmon.area.cells.behaviors`: package containing special cell behaviors<br>

This cell has a probability of containing a Pokémon. If occupied, the Pokémon will jump onto the player, initiating a battle.
`TallGrass` has two static methods: one randomly decides tall grass occupation, and the other launches a fight with the player.

### 🏆 League trainers
`package ch.epfl.cs107.icmon.actor.npc.league`: package containing all league trainers<br>

The league consists of trainers with special Pokémon attributes. For example, they have custom attacks.
- Added class `AnnaLachowska`: IC analysis professor (FR)
- Added class `FredericBlanc`: one of the two IC physics professor (FR)
- Added class `NicolasBoumal`: IC linear algebra professor (FR)
- Added class `TanjaKaser`: IC AICC professor
- Added class `JamilaSam` IC code introduction professor

### 🗺️ Rolex Learning Center underground map
`package ch.epfl.cs107.icmon.area.maps`<br>

- Added class `UndergroundLab`. This is the area where you can fight the league trainers.

### 🤔 ICMonInteractionVisitor
`package ch.epfl.cs107.icmon.handler`<br>

Modified the `ICMonInteractionVisitor` class to add default interactions with the npc we added as stated above.

### 📅👉 Actions and events
`package ch.epfl.cs107.icmon.gamelogic.actions`:
- Added action class `GivePokemonToPlayerAction`
- Added action class `ResumeGameAction`
- Added action class `PauseGameAction`
- Added action class `SetTrainerFightsAcceptanceAction`
- Added action class `StartDialogAction`

`package ch.epfl.cs107.icmon.gamelogic.events`:
- Added action class `FightWithEliteFourEvent`: Define the behavior with the council for trainers and unlocks the fight with the league master.
- Added action class `FightWithMasterJamilaSamEvent`: Trigger the `EndOfTheGameEvent` when beaten.
