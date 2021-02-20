package o1.adventure
import o1._


class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  def execute(actor: Player) = this.verb match {
    case "go"    => Some(actor.go(this.modifiers))
    case "rest"  => Some(actor.rest())
    case "quit"  => Some(actor.quit())
    case "get"   => Some(actor.get(this.modifiers))
    case "drop"   => Some(actor.drop(this.modifiers))
    case "examine"   => Some(actor.examine(this.modifiers))
    case "belt"   => Some(actor.belt())
    case "use" => Some(actor.use(this.modifiers))
    case "answer" => Some(actor.answer(this.modifiers))
    case "enter" => Some(actor.enter(this.modifiers))
    case "ask" => Some(actor.ask(this.modifiers))
    case "look" => Some(actor.look(this.modifiers))
    case "help" => Some(actor.help())
    case other   => None
  }

  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

