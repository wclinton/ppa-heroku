package com.ppa8ball.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class TeamRoster
{
	@Id
    @GeneratedValue
    private Long id;
	private Long teamId;
	private Boolean isHomeRoster;
	private Long player1Id;
	private Long player2Id;
	private Long player3Id;
	private Long player4Id;
	private Long player5Id;
	
	
	public TeamRoster()
	{
		super();
	}
	
	
	public TeamRoster(Long teamId, Boolean isHomeRoster, List<Long> playerIds)
	{
		this.teamId = teamId;
		this.isHomeRoster = isHomeRoster;
		
	//	int i=0;
		
//		player1Id = playerIds.get(i++);
//		player2Id = playerIds.get(i++);
//		player3Id = playerIds.get(i++);
//		player4Id = playerIds.get(i++);
//		player5Id = playerIds.get(i++);
//		//	setPlayerIds(playerIds);
		//	setPlayerIds(playerIds);
		//	setPlayerIds(playerIds);
		
		setPlayerIds(playerIds);
	}
	
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getTeamId()
	{
		return teamId;
	}

	public void setTeamId(Long teamId)
	{
		this.teamId = teamId;
	}

	public Boolean getIsHomeRoster()
	{
		return isHomeRoster;
	}

	public void setIsHomeRoster(Boolean isHomeRoster)
	{
		this.isHomeRoster = isHomeRoster;
	}

	public Long getPlayer1Id()
	{
		return player1Id;
	}

	public void setPlayer1Id(Long player1Id)
	{
		this.player1Id = player1Id;
	}

	public Long getPlayer2Id()
	{
		return player2Id;
	}

	public void setPlayer2Id(Long player2Id)
	{
		this.player2Id = player2Id;
	}

	public Long getPlayer3Id()
	{
		return player3Id;
	}

	public void setPlayer3Id(Long player3Id)
	{
		this.player3Id = player3Id;
	}

	public Long getPlayer4Id()
	{
		return player4Id;
	}

	public void setPlayer4Id(Long player4Id)
	{
		this.player4Id = player4Id;
	}

	public Long getPlayer5Id()
	{
		return player5Id;
	}

	public void setPlayer5Id(Long player5Id)
	{
		this.player5Id = player5Id;
	}
//	
//	
	private void setPlayerIds(List<Long> playerIds)
	{
		int i=0;
		player1Id = playerIds.get(i++);
		player2Id = playerIds.get(i++);
		player3Id = playerIds.get(i++);
		player4Id = playerIds.get(i++);
		player5Id = playerIds.get(i++);
	}
	
	
	
	
	
}
