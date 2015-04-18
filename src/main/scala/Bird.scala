import java.io._

import net.liftweb.json._

import scala.util.Random


/**
 * Created by nlw on 18/04/15.
 *
 */
case class Bird(scientific_name: String, common_names: List[String], sights: Int, wing_span: Double, hangs_out: List[Place])

case class Place(name: String, _id: Int, latlon: List[Double], description: Option[String], michelin_rate: Option[Int])

object BirdGen {
  def apply() = {
    val nom = rand_name
    val comm = rand_list(rand_name)
    val si = rand_int
    val sp = rand_double
    val hang = rand_list(gen_place)
    Bird(nom, comm, si, sp, hang)
  }

  def gen_place() = {
    val name = rand_name
    val id = (rand_int /: (1 to 10)) { (a, b) => a * rand_int }
    val lalo = List(rand_double, rand_double)
    val desc = rand_maybe(rand_string)
    val mich = rand_maybe(rand_int)
    Place(name, id, lalo, desc, mich)
  }

  def rand_name = Array(rand_char).mkString.toUpperCase + rand_string + " " + rand_string

  def rand_string = (for (x <- 1 to (5 + Random.nextInt(5))) yield rand_char).mkString

  def rand_char = (Random.nextInt(26) + 'a'.asInstanceOf[Int]).asInstanceOf[Char]

  def rand_double = Random.nextDouble * 360 - 180

  def rand_int = Random.nextInt(5) + 1

  def rand_maybe[A](obj: A) = if (Random.nextInt(8) > 0) Some(obj) else None

  def rand_list[A](what: => A) = (for (x <- 1 to rand_int - 1) yield what).toList
}

object BirdSerializerLift {
  implicit val formats = Serialization.formats(NoTypeHints)

  def apply(): String = Serialization.write(BirdGen())
}

object BirdGenMain {
  val pw = new PrintWriter(new File("src/main/resources/birds.dat"))
  val NUM_ROWS = 25000
  (0 until NUM_ROWS) foreach { _ =>
    pw.write(BirdSerializerLift() + "\n")
  }
  pw.close()

}