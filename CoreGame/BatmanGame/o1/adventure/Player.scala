package o1.adventure

import o1._
import scala.collection.mutable.Map
import scala.collection.mutable._


class Player(startingArea: Area) {

  private var currentLocation = startingArea       // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false             // one-way flag
  private var utilityBelt = Map[String, Item]()    // where you store whatever you pick up 
  private val materials = Vector[String]("stone", "steel", "bronze", "silver", "golden", "skull") // all kinds of keys
  private var isDead = false                       // your current status. You will lose if (isDead == true)
  private var missionSuccess = false               // will be set to 'true' once the bomb is deactivated and Riddler is found
  private var bombDeact = false                    // will be set to 'true' once you enter the right number in the final bomb room
  private var openedBox = false                    // whether some box has been used but has not been answered.  
  
  /*Returns the current location*/
  def location = this.currentLocation
  
  /*Tells you whether you are carrying something*/
  def look(itemName: String) = if (this.utilityBelt.contains(itemName)) "BRUCE: There it is!" else "BRUCE: Not in the belt. Where is it?"

  
  override def toString = "Now at: " + this.location.name
  
  /*Basically, you want to rest and Alfred yells at you.*/
  def rest() = "ALFRED: Sir, you're resting right inside the enemy's lair! I don't think it's a good idea!"
 
  /*Check whether you quit the game.*/
  def hasQuit = this.quitCommandGiven
  
  /*Quit the game.*/
  def quit() = {
    this.quitCommandGiven = true
    ""
  }
  
  /*This function checks whether the direction you are going has some locked door.
   *It returns index that helps function "go(direction)" decide whether you have the suitable key 
   *to go through the locked door.*/
  private def checkForLockedDoor(location: String, destination: String) = {
    val lockedDoors = Vector[(String, String)](("top1", "top2"), ("top3", "top7"), ("top12", "top16"), 
        ("top15", "top14"), ("top13", "top14"),("bot13", "bot14"))
    if (lockedDoors.contains((location, destination))) {
      lockedDoors.indexOf((location, destination)) 
    }
    else if (lockedDoors.contains((destination, location))) {
      lockedDoors.indexOf((destination, location)) 
    }
    else 6
  }
  
  /*In this method, you can go in a total of 6 directions ("north", "east", "south", "west", "up", "down").
   *All rooms have some or all of the basic directions: "north", "east", "south", "west"
   *For some particular rooms, you can also go "up" and "down" 
   *This function is quite complicated as it has to handle quite many situations:
   * - When you try to go in the direction that has a locked door, this will allow you to go if you have the suitable key.
   *Otherwise, it will tell you what kind of lock you see here and what kind of compatible key you should have to through that door.
   *When you unlock doors and go to new area, there will be sound effect and picture pops up.
   * - When you try to go to some direction that has no door (direction that is not printed on the line Exits available:...), 
   *it will tell you there's just wall or that there is no stair.
   * - When you try to enter some words that is not direction with this function (for example: "go home"), Alfred will complain.
   * - When the direction you are going in has no locked door, you simply just move there.
   * - When you try to enter the final bomb room from the wrong direction, the whole place explodes and you die.
   * - When you enter from the right direction, sound and picture will pop up.
   *Especially, whenever you move to somewhere new and carry with you the map or the scanner ( or both ), 
   *their GPS statuses will change according to the new location. 
   *Meaning that whenever you go to a new place, try "use map" or "use scanner", they will show you different things,
   *but with the same sound effects*/
  def go(direction: String) = {
    val trueDirection = direction.toLowerCase()
    val destination = this.location.neighbor(trueDirection)
    val directions = Vector[String]("north", "east", "south", "west")
    
    /* GPS tracking changes location */
    def adjustGPS = {
      if (this.utilityBelt.contains("map")) this.utilityBelt("map").GPS = destination.get.name
      if (this.utilityBelt.contains("scanner")) this.utilityBelt("scanner").GPS = destination.get.name
    }
      /*CHECK FOR AVAILABLE DIRECTION*/
      if (destination.isDefined) {
        /*CHECK SPECIAL ROOMS*/
        /*This part checks whether you enter the final bomb room from the wrong direction when the bomb has not been deactivated.*/
        if (destination.get.name == "bot7" && !this.bombDeact) {
          if (trueDirection != "west") {
            this.isDead = true
            "Boooom! You go to bomb room from the wrong direction..."
          } else {
            adjustGPS
            Sound("danger.wav").play(0, 500)
            show(Pic("bombroom.png").scaleTo(800, 800))
            this.currentLocation = destination.get
            "You go " + trueDirection
          }
        } 
        /*This part checks whether you have enter the right room to catch Riddler after you deactivated the bomb.*/
        else if (destination.get.name == "secret room") {
            show(Pic("batmanFinal.png").scaleTo(800, 700))
            Sound("glass.wav").play(0, 200)
            Sound("endingsound.wav").play(0, 200)
            this.missionSuccess = true
            ""
        }
        /*CHECK NORMAL ROOMS*/
        else {
          val keyNumber = this.checkForLockedDoor(this.currentLocation.name, destination.get.name)
          /*(keyNumber < 6) means that the direction you go in has a locked door.*/
          if (keyNumber < 6) {
            val keyName = this.materials(keyNumber) + " key"
            val lockName = this.materials(keyNumber) + " lock"
            /*Checks if you have the suitable keys.*/
            if (this.utilityBelt.contains(keyName)) {
              adjustGPS
              this.currentLocation = destination.get
              show(Pic("doorunlock.png"))
              Sound("door-2.wav").play(0, 200)
              "Door unlocked...\n\nYou go " + trueDirection + ".\n\nDoor has closed behind you..." 
            } else s"""BRUCE: Can't go $trueDirection yet. Hmm... Looks like door is locked by a $lockName.
I guess we need a $keyName first."""
            } else {
              adjustGPS
              this.currentLocation = destination.get
              "You go " + trueDirection + "..."
            }
         }
      }
      /*HANDLES OTHER DIRECTION AND NON-DIRECTION INPUT*/
      else if (!destination.isDefined && (trueDirection == "up" || trueDirection == "down")) 
        "BRUCE: No stair here to go " + trueDirection + "."
      else if (!destination.isDefined && directions.contains(trueDirection)) 
        "BRUCE: No door at " + trueDirection + ". " + "There is just wall."
      else "ALFRED: That's not even a direction, Master Bruce."
  }
  
  /*The most interesting function and has the most sound effects of the game (I suppose).
   *It allows you to use the items you are carrying. 
   * Each item's function will be presented in the walk-through.
   *Though I believe simply reading the text printed by the "examine(itemName)" function is enough to understand the items.*/
  def use(itemName: String) = {
    val realName = itemName.toLowerCase()
    if (this.utilityBelt.contains(realName)) {
      /*Just a mark that you have opened the riddler's box and know the question (by this "use" function)*/
      if (realName == "riddler's box") this.openedBox = true
        this.utilityBelt(realName).activate 
    } else "No such thing found in belt."
  }
  
  /*Pick up the item and store it in the belt.*/
  def get(itemName: String): String = {
    val itemname = itemName.toLowerCase()
    if (this.currentLocation.contains(itemname)) {
        this.utilityBelt += itemname -> this.currentLocation.removeItem(itemname).get
        "You pick up the " + itemname + "."
    } else "BRUCE: There is no " + itemname + " here to pick up."
  }
  
  /*Remove an item from your belt and leave it where you are.*/
  def drop(itemName: String): String = {
    if (this.utilityBelt.contains(itemName)) {
      this.currentLocation.addItem(this.utilityBelt.get(itemName).get)
      this.utilityBelt.remove(itemName)
      "You drop the " + itemName + "."
    } else {
      "You don't have that!"
    }
  }
  
  /*Reveal Bruce (a.k.a Batman)'s first thought when looking at an item.
   * This method also has sound effect for some items.*/
  def examine(itemName: String): String = {
    val realName = itemName.toLowerCase()
    if (!this.utilityBelt.contains(itemName))
      "If you want to examine something, you need to pick it up first." 
    else {
      if (realName == "riddler's box") {
        Sound("question.wav").play(0, 200)
        "You look closely at the box\n\nBRUCE: A green box with question marks on it, must be some of Riddler's games."
      } else "You look closely at the " + itemName + "\n\n" + this.utilityBelt.get(itemName).get.description
    }
  }
  
  /*Check what you are carrying with you.*/
  def belt(): String = {
    var ItemNames = ""
    if (this.utilityBelt.isEmpty) "You are empty-handed." else {
      for (i <- this.utilityBelt) {
        ItemNames += i._1 + "\n"
      }
      "You are carrying:\n" + ItemNames
    } 
  }
  
  /*Check whether the answer is correct. Correct answers for different Riddler's boxes are different.
   *All the answers contain only one word, except the last one.
   *Because in the last one, you are asked to 'answer' the whole message.
   *If you want to enjoy the game, you might not want to read this because it has all the answers.*/
  private def checkAnswer(ans: String) = {
    val realAns = ans.toLowerCase()
    if (this.utilityBelt.contains("riddler's box")) {
     if (this.utilityBelt("riddler's box").GPS == "top1" && realAns == "tomorrow") 0
     else if (this.utilityBelt("riddler's box").GPS == "top3"  && realAns == "lawsuit") 1
     else if (this.utilityBelt("riddler's box").GPS == "top12" && realAns == "name") 2
     else if (this.utilityBelt("riddler's box").GPS == "top15" && realAns == "brain") 3
     else if (this.utilityBelt("riddler's box").GPS == "top14" && realAns == "promise") 4
     else if (this.utilityBelt("riddler's box").GPS == "bot13" && realAns == "you will die") 5
     else 6
     }
    else 7
  }
  
  /*Answer the Riddler's box to get the key. Once you enter the correct answer, the key will appear in the room, 
   *pick it up if you want to go to another room.
   *Also, the box will "vanish" from your belt and you can't pick it up anymore, because it's meaningless once you got the key.
   *You can, however, pick up another box in another room.
   *The final box is somewhat different from the others, it also add a piece of paper that you can use to know what to do next.
   *Of course, sound effects when obtain key and pictures are different too.
   *This method also handles other cases. For example, when you type "answer" but carry no box.
   *But you can still answer even when you haven't read the question. Let's see how lucky you are:)*/ 
  def answer(ans: String) = {
    val numbers = Vector[Int](0,1,2,3,4)
    val realAns = ans.toLowerCase()
    val number = this.checkAnswer(realAns)
    if (numbers.contains(number)) {
      val keynames = this.materials(number) + " key"
      this.utilityBelt.remove("riddler's box")
      this.openedBox = false
      this.currentLocation.addItem(new Key(keynames, "BRUCE: I guess this can be used to unlock the " + this.materials(number) + " lock."))
      show(Pic(keynames + ".png").scaleTo(500, 500))
      Sound("gotkey.wav").play(0, 200)
      "BRUCE: Got the key now. I guess we don't need this box anymore."
    } 
    else if (number == 5) {
      val keynames = this.materials(number) + " key"
      this.utilityBelt.remove("riddler's box")
      this.openedBox = false
      Sound("joker.wav").play(0, 200)
      this.currentLocation.addItem(new Key(keynames, "BRUCE: I guess this can be used to unlock the " + this.materials(number) + " lock."))
      
      val paper1 = new Paper("first paper", "BRUCE: A piece of paper, must be from Riddler.")
      paper1.GPS = "bot13"
      this.currentLocation.addItem(paper1)
      
      show(Pic(keynames + ".png").scaleTo(500, 500))
      Sound("gotkey.wav").play(0, 200)
      "BRUCE: Got the key now. I guess we don't need this box anymore."
    }
    else if (number == 6) {
      Sound("wrong.wav").play(0, 200)
      "Sound from nowhere: Hehehe...Wrong answer!"
    }
    else "ALFRED: Who are you answering to, Master Bruce?"
  }
  
  /*The map contains hints form Alfred to the particular box question.*/
  private val alfredHint:Map[String, String] = Map("top1" -> "ALFRED: Hmm... I think it can't be something tangible.", 
      "top3"  -> "ALFRED: Could it be... children?:)", 
      "top15" -> "ALFRED: 'Eyes', 'ears' and 'muscle' are all human parts.", 
      "top14" -> "ALFRED: 'Broken' here might actually have intangible meaning.", 
      "top12" -> "ALFRED: Hmm... I've seen this question somewhere, it's very simple but I can't remember...", 
      "bot13" -> "ALFRED: 'Clues'? But most rooms on the top floor are just empty, what does he means by 'clues'?")
  
  /*The map contains hints form Lucius to the particular box question. He is smarter than Alfred, I admit.*/
  private val luciusHint:Map[String, String] = Map("top1" -> "LUCIUS: It might have something to do with time. And the answer is only one word...", 
      "top3"  -> "LUCIUS: 'Have' and 'lose' here might not have the completely opposite meanings.",
      "top15" -> "LUCIUS: Does he mean that this thing controls all other parts including 'eyes', 'ears' and 'muscle'?", 
      "top14" -> "LUCIUS: 'Use me to decieve', does this mean some kind of words?", 
      "top12" -> "LUCIUS: I think it must be some kind of information.",
      "bot13" -> "LUCIUS: It's weird that some rooms have names, while the others are just 'untitled', some rooms even have the same name.")
  
  /*You can use this function when you need hints for some question.
   *This function basically tells you what your assistants Alfred and Lucius think about the question.
   *You can ask when you already opened the riddler's box, see the question and still carry the box.
   *This function also handles other situations where you have the box but ask some other people than Lucius and Alfred,
   *or when you carry no box, or when you carry some box but haven't opened it yet, for example.*/
  def ask(name: String) = {
    if (this.utilityBelt.contains("riddler's box")) {
      val possesion = this.utilityBelt("riddler's box")
      if (this.openedBox == true) {
        if (name.toLowerCase() == "alfred") alfredHint(possesion.GPS)
        else if (name.toLowerCase() == "lucius") luciusHint(possesion.GPS)
        else "ALFRED: Who is " + name + ", Master Bruce? I've never heard of him." 
      } else "ALFRED: You need to use the box to see the question first, Master Bruce." 
    } else "BRUCE: But there is nothing to ask about."
  }
  
  /*This function is used to answer the final question to deactivate the bomb,
   *meaning that it can only be used when you are in the final bomb room.
   *If you try to enter something when you are not in that room, Alfred will complain.
   *Unlike other questions from riddler's boxes, you can only answer the final question once.
   *If you enter the wrong number, the whole place will explode and you fail the mission.*/
  def enter(number: String) = {
    if (this.currentLocation.name == "bot7") {
      if (number != "4") {
        this.isDead = true
        "You enter the wrong number..."
      } else {
        this.bombDeact = true
        Sound("deact.wav").play(0, 500)
        """BRUCE: Bomb successfully deactivated, now just one last thing: Riddler.
            As far as I can see, he might be hiding in the basement under one of the corner rooms on this floor.
            Let see what corner room from which we can go down."""
      }
    } else "ALFRED: Nothing here to enter, Master Bruce."
  }
  
  def help() = """>>> Instruction: You will start on the top floor, your job is to find your way to the bottom floor without entering any bomb room on the top floor.
                           Once you reach to the bottom floor, there will be more hint about what to do next given in the game.
***New added commands:  
 - use itemName (you will have to use this a lot)
 - answer something
 - ask someone (it can be Alfred or Lucius)
 - enter something
 - look itemName
For more details, please see the moreInstruction.txt file. The given space here to too small to express everything you might need to know. 
    """
  
  /*Check whether you have succeeded the mission.*/
  def success = this.missionSuccess
  
  /*Check whether you have deactivate the bomb. 
   *The bomb being deactivated does not mean you win, you have to complete one last task:
   *Find the Riddler and catch him.*/
  def deactBomb = this.bombDeact
  
  /*Check whether you are dead. This can happen when:
   *-You enter the bomb room on the top floor.
   *-You enter the bomb room on the ground floor from the wrong direction.
   *-You enter the wrong number in the final bomb room.*/
  def isEnded = this.isDead || this.currentLocation.explode

}
