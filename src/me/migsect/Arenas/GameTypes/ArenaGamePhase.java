package me.migsect.Arenas.GameTypes;

public abstract class ArenaGamePhase
{
	// An ArenaGamePhase changes how the game is run.  When a game is first loaded, it will create
	//   each of it's gamePhases as they will only be used by certain games.  As such gamePhases will
	//   probably not be reused and will have to constructed per game basis.
	// One use of gamePhases would be to bolster a last-alive player's damaged if they are on their
	//   team. GamePhases will be one of the only adjusters of states and teams as they may actively
	//   change the effects a state may bestow or change if a team can capture something.
	// Gamephases will be implemented later once basic game functions have been established.
	//   Otherwise the complexities of making each game will not allow for natural design expansion
}
