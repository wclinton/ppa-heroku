package com.ppa8ball.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "ishome", "team_id", "season_id" }) )

public class Roster
{
   @Id
   @GeneratedValue
   private long id;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn
   private Season season;

   @OneToOne(fetch = FetchType.LAZY)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn
   private Team team;

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
   @JoinColumn(name = "ROSTER_ID")
   private List<OrderedPlayer> orderedPlayers;

   @Column
   private boolean isHome;

   public long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Season getSeason()
   {
      return season;
   }

   public void setSeason(Season season)
   {
      this.season = season;
   }

   public Team getTeam()
   {
      return team;
   }

   public void setTeam(Team team)
   {
      this.team = team;
   }

   public void setPlayers(List<OrderedPlayer> players)
   {
      this.orderedPlayers = players;
   }

   public List<OrderedPlayer> getPlayers()
   {
      return orderedPlayers;
   }

   public boolean isHome()
   {
      return isHome;
   }

   public void setHome(boolean isHome)
   {
      this.isHome = isHome;
   }
}
