package com.ppa8ball.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class OrderedPlayer
{
   @Id
   @GeneratedValue
   private long id;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn
   private Player player;

   private int sequenceNumber;

   public long getId()
   {
      return id;
   }

   public void setId(long id)
   {
      this.id = id;
   }

   public Player getPlayer()
   {
      return player;
   }

   public void setPlayer(Player player)
   {
      this.player = player;
   }

   public int getOrder()
   {
      return sequenceNumber;
   }

   public void setOrder(int order)
   {
      this.sequenceNumber = order;
   }
}
