package o1.adventure

import o1._
import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

class Area(var name: String, var description: String) {

  private val neighbors = Map[String, Area]()
  private var hasItem = Map[String, Item]()
  
 
  def neighbor(direction: String) = this.neighbors.get(direction)
  
  def checkNeighbors(direction: String) = this.neighbors.contains(direction)

  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += direction -> neighbor
  }

  
  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }


  def fullDescription = {
    val exitList = "\nExits available: " + this.neighbors.keys.mkString(" ") 
    if (this.hasItem.isEmpty) this.description + exitList else this.description + "\nYou see here: " + this.hasItem.keys.mkString(", ") + exitList
  }


  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)

  
  def addItem(item: Item): Unit = {
    this.hasItem += item.name -> item
  }

  
  def contains(itemName: String): Boolean = this.hasItem.contains(itemName)
  
 
  def removeItem(itemName: String): Option[Item] = {
    val a = this.hasItem.get(itemName)
    this.hasItem -= itemName 
    a
  }
  
  def explode = {
    val bombRoom = Vector[String]("top4", "top6", "top9", "top11")
    bombRoom.contains(this.name)
  }
  
  
}
