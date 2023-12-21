Conception MP2-2023
==
## 🔄 **Architecture changes**

### 📜 Messages
The `GamePlayMessage` now resides in a queue within the `GameState`, enabling the processing of multiple messages simultaneously. Previously, only the first message was considered.

### 👾 NPCActor
Introduced a constructor accepting a `RegionOfInterest` to facilitate the use of custom sprites.

### 🎮 Trainer Class
A `Trainer` is now an `NPCActor` implementing `ICMonFightableActor`. This entity possesses a list of Pokémon, enabling battles with the player and introducing complexity to the game. Trainers can decide whether to engage in a fight with the player.

### 🐉 Pokémon
The previously abstract Pokémon class has undergone significant changes:
- Attributes `hp` and `maxHp` now have a `float` type for more precise damage calculations during fights.
- Added attributes `pokedexId`, `types`, and `defense` for damage calculations using the original Pokémon formula (gen III).
- Additional methods in `PokemonProperties` provide more information on the Pokémon's state.

#### 🔄 Dynamic Pokémon loader
Implemented the `PokemonDataLoader` class to dynamically load Pokémon on the fly by reading the Pokédex, a collection of XML files. Consequently, the classes for Bulbasaur, Nidoqueen, and Latios have been deleted in favor of dynamically loaded Pokémon.

The `PokemonDataLoader` uses data structures such as `PokemonMove` and `PokemonType` to represent loaded data.

#### ⚔️ ICMonFightAction
Attacks are now dynamically created through the `PokemonDataLoader`.
- `doAction` considers both attacking and defending Pokémon to compute damages based on their stats.
- Added a `sfx` property getter to play a sound when the attack is triggered.
- A `type` property getter distinguishes `PHYSICAL` attacks from `SPECIAL` or `STATUS` attacks (`PokemonMoveType` enum).

#### 🥊 Fights
Modified fights to play sounds and announce each attack before application. Trainers will announce their Pokémon if present.

## 🆕 **New features**

### 🎵 Audio
Implemented audio support through the `AudioPreset` and `ICMonGameState` classes. `AudioPreset` allows the definition of base presets for playing sounds.

### 🏥 Nurse
Introduced the ability for the player to heal their Pokémon in a Pokécenter.
- Added `requestHealFromNurse`, `hasPokemonNeedingHeal`, and `hasHealthyPokemon` methods to `ICMonPlayer`.

### 🚀 Start menu
Added a start menu displaying the game's name and slogan (`ICMonStartMenu`).

### 🌾 Tall grass cell
This cell has a probability of containing a Pokémon. If occupied, the Pokémon will jump onto the player, initiating a battle.
`TallGrass` has two static methods: one randomly decides tall grass occupation, and the other launches a fight with the player.