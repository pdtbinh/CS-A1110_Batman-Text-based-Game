package o1.adventure
import o1._


abstract class Item(val name: String, val description: String) {
  var GPS: String                     // track the location of only some of the items (map and scanner). Other items have fixed GPS.
  override def toString = this.name
  def activate: String                // allow player to use the items
}

/*Represents map that shows exactly where you are in the whole game world.*/
class digitalMap(name: String, description: String) extends Item(name, description) {
  var GPS = "top1"
  def activate = {
    val picToShow = this.GPS + "Map.png"
    Sound("map.wav").play(0, 200)
    show(Pic(picToShow).scaleTo(800, 500))
    "Map loading..."
  }
}

/*Represents the Riddler's box that you have to answer to get the key.*/
class Box(name: String, description: String) extends Item(name, description) {
  var GPS = ""
  def activate = {
    if (this.GPS == "top1") Sound("rid1.wav").play(0, 200) else Sound("rid2.wav").play(0, 200)
    if (this.description.contains("clue")) {
      show(Pic("special riddler's box.png").scaleTo(800, 500))
      this.description
    } else {
      show(Pic("box_of_room1Center.png").scaleTo(600, 500))
      this.description
    }
  }
}

/*Represents the keys that you need to have in order to go through some particular rooms.*/
class Key(name: String, description: String) extends Item(name, description) {
   var GPS = ""
   def activate = "ALFRED: Use " + this.name + " to go where, Master Bruce?"
 }

/*Represent bomb scanner that you can use to know how close you are to the surrounding bomb rooms for each room you go to.*/
class Scanner(name: String, description: String) extends Item(name, description) {
   var GPS = "top1"
   def activate = {
    val picToShow = "scanner" + this.GPS + ".png"
    Sound("scanner.wav").play(0, 200)
    show(Pic(picToShow).scaleTo(800, 500))
    "Scanner loading..."
  }
}

/*Represents a piece of paper that contains clue for what to do next after you answer the final Riddler's box question.*/
class Paper(name: String, description: String) extends Item(name, description) {
   var GPS = ""
   def activate = {
     val paperName = this.GPS + "paper.png"
     show(Pic(paperName).scaleTo(800, 500))
     "Reading paper..."
   }
}
