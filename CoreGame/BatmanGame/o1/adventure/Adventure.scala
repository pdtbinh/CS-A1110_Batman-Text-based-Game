package o1.adventure
import o1._


class Adventure { 

  val title = "A Bomb Deactivating Mission"
  
  /* Top floor */
  private val top1 = new Area("top1", """You are now at: Room Y (Please turn on sound or use headphones. This game has quite a lot sound effects and pictures.)
    
BRUCE:  Micro check. Alfred, do you copy? The board says I'm at room Y. Over.
ALFRED: Yes, I can hear you well, Master Bruce. What do you see there?
""")
  private val top2 = new Area("top2", """You are now at: Room O

BRUCE: No stair in this room.        
""")
  private val top3 = new Area("top3", """You are now at: Room U
    
BRUCE: An empty room. But there is a locked door on the south. 
""")
  private val top4 = new Area("top4", "")
  private val top5 = new Area("top5", """You are now at: Untitled room

BRUCE: A totally empty room. Nothing's here.    
""")
  private val top6 = new Area("top6", "")
  private val top7 = new Area("top7", """You are now at: Room W
    
BRUCE: A dark room with curses on the walls, and yet no stair to go down.
""")
  private val top8 = new Area("top8", """You are now at: Room I
    
BRUCE: There is a locked door on the south. It's hard to imagine exactly where I am in the house. 
""")
  private val top9 = new Area("top9", "")
  private val top10 = new Area("top10", """You are now at: Untitled room

BRUCE: A totally empty room. Nothing's here.    
""")
  private val top11 = new Area("top11", "")
  private val top12 = new Area("top12", """You are now at: Room L

BRUCE: Another quiet and dark room, no guard, no gun, nothing.
""")
  private val top13 = new Area("top13", """You are now at: Room E
    
BRUCE: There it is, a stair to the bottom floor.   
""")
  private val top14 = new Area("top14", """You are now at: Room I
    
BRUCE:  No stair here, let's find another place. Still, everything is so quiet.
ALFRED: I must say, Master Bruce, an intelligent fight is way more interesting than a fist fight.
""")
  private val top15 = new Area("top15", """You are now at: Room D
    
BRUCE: There is a locked door on the west. It's a silver lock.    
""")
  private val top16 = new Area("top16", """You are now at: Room L

BRUCE:  Another empty room. Riddler seems to be less violent than usual.
ALFRED: Maybe he just wants to challenge your intelligence not your fist this time, Master Bruce. 
BRUCE:  Hmm... Something is wrong with this room...   
""")
  
  /* Bottom floor */
  private val bot1 = new Area("bot1", "You are now at: Room B1")
  private val bot2 = new Area("bot2", "You are now at: Room B2")
  private val bot3 = new Area("bot3", "You are now at: Room B3")
  private val bot4 = new Area("bot4", "You are now at: Room B4")
  private val bot5 = new Area("bot5", "You are now at: Room B5")
  private val bot6 = new Area("bot6", "You are now at: Room B6")
  private val bot7 = new Area("bot7", "You are now at: Bomb room!")
  private val bot8 = new Area("bot8", "You are now at: Room B8")
  private val bot9 = new Area("bot9", "You are now at: Room B9")
  private val bot10 = new Area("bot10", "You are now at: Room B10")
  private val bot11 = new Area("bot11", "You are now at: Room B11")
  private val bot12 = new Area("bot12", "You are now at: Room B12")
  private val bot13 = new Area("bot13", """You are now at: Room B13
    
ALFRED: Have you found Riddler, Master Bruce?
BRUCE:  Not yet, but he won't be able to hide from me much longer. No one will.     
""")
  private val bot14 = new Area("bot14", "You are now at: Room B14")
  private val bot15 = new Area("bot15", "You are now at: Room B15")
  private val bot16 = new Area("bot16", "You are now at: Room B16")
  
  
  /* Neighbors for rooms on top floor */
  top1.setNeighbors(Vector("east" -> top2, "south" -> top5))
  top2.setNeighbors(Vector("east" -> top3, "south" -> top6, "west" -> top1))
  top3.setNeighbors(Vector("east" -> top4, "south" -> top7, "west" -> top2))
  
  top5.setNeighbors(Vector("north" -> top1, "east" -> top6, "south" -> top9))
  
  top7.setNeighbors(Vector("north" -> top3, "east" -> top8, "south" -> top11, "west" -> top6))
  top8.setNeighbors(Vector("north" -> top4, "south" -> top12, "west" -> top7))
  top10.setNeighbors(Vector("north" -> top6, "east" -> top11, "south" -> top14, "west" -> top9))
  
  top12.setNeighbors(Vector("north" -> top8, "south" -> top16, "west" -> top11))
  top13.setNeighbors(Vector("north" -> top9, "east" -> top14, "down" -> bot13))
  top14.setNeighbors(Vector("north" -> top10, "east" -> top15, "west" -> top13))
  top15.setNeighbors(Vector("north" -> top11, "east" -> top16, "west" -> top14))
  top16.setNeighbors(Vector("north" -> top12, "west" -> top15))
  
  /* Neighbors for rooms on bottom floor */
  bot13.setNeighbors(Vector("up" -> top13, "east" -> bot14))
   bot1.setNeighbors(Vector(                  "east" -> bot2 , "south" -> bot5                                ))
   bot2.setNeighbors(Vector(                  "east" -> bot3 , "south" -> bot6 , "west" -> bot1               ))
   bot3.setNeighbors(Vector(                  "east" -> bot4 , "south" -> bot7 , "west" -> bot2               ))
   bot4.setNeighbors(Vector(                                   "south" -> bot8 , "west" -> bot3               ))
   bot5.setNeighbors(Vector("north" -> bot1 , "east" -> bot6 , "south" -> bot9                                ))
   bot6.setNeighbors(Vector("north" -> bot2 , "east" -> bot7 , "south" -> bot10, "west" -> bot5               )) 
   bot7.setNeighbors(Vector("north" -> bot3 , "east" -> bot8 , "south" -> bot11, "west" -> bot6               ))
   bot8.setNeighbors(Vector("north" -> bot4 ,                  "south" -> bot12, "west" -> bot7               ))
   bot9.setNeighbors(Vector("north" -> bot5 , "east" -> bot10                                                 ))
  bot10.setNeighbors(Vector("north" -> bot6 , "east" -> bot11, "south" -> bot14, "west" -> bot9               ))
  bot11.setNeighbors(Vector("north" -> bot7 , "east" -> bot12, "south" -> bot15, "west" -> bot10              ))
  bot12.setNeighbors(Vector("north" -> bot8 ,                  "south" -> bot16, "west" -> bot11              ))
  
  bot14.setNeighbors(Vector("north" -> bot10, "east" -> bot15,                   "west" -> bot13              ))
  bot15.setNeighbors(Vector("north" -> bot11, "east" -> bot16,                   "west" -> bot14              ))
  bot16.setNeighbors(Vector("north" -> bot12,                                    "west" -> bot15              ))
  
  /*CREATING MAP AND SCANNER FOR PLAYER TO PICK UP AND USE DURING GAME:
   *Since there are only 1 map and 1 scanner, their GPS are already set in Item.scala.*/
  this.top1.addItem(new digitalMap("map", """BRUCE: Looks like some digital map has GPS tracker of wherever I go
            The only way to know for sure is to use it.
""")) 
  
  this.top1.addItem(new Scanner("scanner", """BRUCE: Some kind of device used to detect bomb according to direction.
            The closer I get to the bomb, the higher the level of danger. 
            Maximum "danger" must mean that the bomb is in the room right next to me.
            The only way to know for sure is to use it.
"""))
  
  /*CREATING RIDDLER'S BOXES IN PARTICULAR ROOMS AND SET THERE GPS ACCORDINGLY:*/
  val box1 = new Box("riddler's box", """BRUCE: Question box Riddler left for me to answer.
            There must be something important inside. 
            Let see what the question is...
 
? What is always on its way here, but never arrives ?
""")
  box1.GPS = "top1"
  this.top1.addItem(box1)
  
  val box2 = new Box("riddler's box", """BRUCE: Another question box Riddler left for me to answer.
            There must be something important inside. 
            I wonder what this time question is...
       
 ? What is it that no man wants to have but no man wants to lose ?
""")
  box2.GPS = "top3"
  this.top3.addItem(box2)

  val box3 = new Box("riddler's box", """BRUCE: Another question box Riddler left for me to answer.
            There must be something important inside. 
            Let see what the question is...
 
? What belongs to you, but is used by others ?
""")
  box3.GPS = "top12"
  this.top12.addItem(box3)

  val box4 = new Box("riddler's box", """BRUCE: Another box, I guess. But this box looks different...
 
? Of all the places you've gone through
  Each one contains a clue
  About what I'm trying to tell you
  What is it ?

            Hmm... I have no idea Riddler likes poetry.
""")
  box4.GPS = "bot13"
  this.bot13.addItem(box4)
  
  val box5 = new Box("riddler's box", """BRUCE: Predictably, another question box from Riddler.
            He never gets tired of this.

  ? I can be broken without being held. Given and then taken away. Some people use me to deceive, but when delivered, I am the greatest gift of all. What am I ?
""")
  box5.GPS = "top14"
  this.top14.addItem(box5)

  val box6 = new Box("riddler's box", """BRUCE: One more question box Riddler left for me to answer.
            The key to the other room must be inside this box. 
            I wonder what this time question is...
 
? I have billions of eyes, yet I live in darkness. I have millions of ears, yet only four lobes. I have no muscle, yet I rule two hemispheres. What am I ?
""")
  box6.GPS = "top15"
  this.top15.addItem(box6)
  
  val paper2 = new Paper("second paper", "Another piece of paper. Inside must be the hint for the right number to enterin the bomb room.")
  paper2.GPS = "bot7"
  this.bot7.addItem(paper2)

  val player = new Player(top1)
  
  /*Turn count has no significance in this game, but it's an optional challenge for player: 
   *Try to complete the game in as few steps as possible.*/
  var turnCount = 0
  
  val timeLimit = 0

  def isComplete = this.player.success
  
  def isOver = {
    if (this.player.isEnded) {
      Sound("explode.wav").play(0, 200)
      show(Pic("explosion.png").scaleTo(800, 500))
      true
    } 
    else this.player.success 
  }

  def welcomeMessage = {
    Sound("batmanbegin.wav").play(0, 500)
    
    """(Please turn on sound or use headphones. This game has quite a lot sound effects and pictures.)
      
>>> Introduction story:
The Riddler has escaped from Arkham Asylum and threatened the safety of civilians with his evil plan...
He has planned bombs in a special house right in the middle of Gotham. Unless The Batman shows up and deactivates the bombs himself, The Riddler will set off the bombs and many will die.
In this game, you will play as The Batman (a.k.a. Bruce Wayne), with the help of your assistants Alfred and Lucius from the Batcave, you need to complete the challenge given by The Riddler.
"""
  }

  def goodbyeMessage = {
    if (this.isComplete) {
      """ You found the Riddler's secret room!
 You jump right into it, defeat all the guards and catch the Riddler.
 Mission success! 
 
 Thank you for playing! We hope you enjoy the game."""
    }
     
    else  
      "And accidentally trigger the bomb.\n\nTry again next time."
  }
  
  /*A room to add to the game world once bomb has been deactivated.*/
  private val scRoom = new Area("secret room", "Riddler!")
  
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) {
      /*This checks if you have successfully deactivate the bomb, then it will add the secret room where Riddler hides for you to find.*/
      if (this.player.deactBomb && !this.bot1.checkNeighbors("down")) this.bot1.setNeighbor("down", this.scRoom)
      this.turnCount += 1
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


}
