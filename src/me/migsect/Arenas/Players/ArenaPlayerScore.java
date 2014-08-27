package me.migsect.Arenas.Players;

public class ArenaPlayerScore
{
	// ArenaPlayerScore is just a means of tracking a player's score.  It also contains information
	//   such as score tags and otherwise.  ArenaPlayerScores are stored per player and should be emptied at the end of each game.
	//   Scores will actually be stored by their tag such as retrieving is easier in the end.
	
	String scoreName = "Score";
	String scoreTag = "";
	String scoreDescription = "";
	int score = 0;
	
	public ArenaPlayerScore(String scoreTag)
	{
		scoreTag = "";
	}
	
	public void setScoreName(String string){this.scoreName = string;}
	public void setScoreDesc(String string){this.scoreDescription = string;}
	public boolean isType(String tag){return scoreTag.equalsIgnoreCase(tag);}
	
	public String getScoreName(){return this.scoreName;}
	public String getScoreDesc(){return this.scoreDescription;}
	
	public void addScore(){score++;}
	public void addScore(int num){score += num;}
	public void resetScore(){score = 0;}
	public void setScore(int num){score = num;}
	
	public int getScore(){return score;}
}
