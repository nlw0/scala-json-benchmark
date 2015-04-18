import java.lang.System.currentTimeMillis

import org.json4s._
import org.json4s.jackson.JsonMethods._

import rapture.json._
import rapture.json.jsonBackends.jackson._

import scala.io._

import spray.json._

import DefaultJsonProtocol._

import net.liftweb.json._

import play.api.libs.json._

/**
 * Created by nlw on 18/04/15.
 *
 */
object ScalaJsonBenchmark extends App {

  val file = Source.fromFile("src/main/resources/mydata.dat").getLines().toArray

  var yy = 0
  for (x <- 1 to 10000) {
    yy += x * x
  }
  println(yy)

  def sumyears(dd: Array[EnemRow]) = (dd map { ex => ex.year }).sum

  def sumirt(dd: Array[EnemRow]) = (
    for {row <- dd
         ex <- row.exams
         ii <- ex.irt} yield ii).sum

  for (iteration <- 1 to 10) {
    var xx = System.currentTimeMillis()
    val dataJson4s = file map Json4sParser.apply
    println(s"Json4s $iteration " + (System.currentTimeMillis() - xx))

    xx = System.currentTimeMillis()
    val dataRapture = file map RaptureParser.apply
    println(s"Rapture $iteration " + (System.currentTimeMillis() - xx))

    xx = System.currentTimeMillis()
    val dataLift = file map LiftParser.apply
    println(s"Lift $iteration " + (System.currentTimeMillis() - xx))

    xx = System.currentTimeMillis()
    val dataPlay = file map PlayParser.apply
    println(s"Play $iteration " + (System.currentTimeMillis() - xx))

    xx = System.currentTimeMillis()
    val dataSpray = file map SprayParser.apply
    println(s"Spray $iteration " + (System.currentTimeMillis() - xx))

    println(s"Json4s  $iteration ${sumyears(dataJson4s)} ${sumirt(dataJson4s)}")
    println(s"Rapture $iteration ${sumyears(dataRapture)} ${sumirt(dataRapture)}")
    println(s"Lift    $iteration ${sumyears(dataLift)} ${sumirt(dataLift)}")
    println(s"Play    $iteration ${sumyears(dataPlay)} ${sumirt(dataPlay)}")
    println(s"Spray   $iteration ${sumyears(dataSpray)} ${sumirt(dataSpray)}")
  }
}

object Json4sParser {
  implicit val formats = org.json4s.DefaultFormats

  def apply(s: String) = {
    org.json4s.jackson.JsonMethods.parse(s).extract[EnemRow]
  }
}

object RaptureParser {
  def apply(s: String) = {
    rapture.json.Json.parse(s).as[EnemRow]
  }
}

object LiftParser {
  implicit val formats = net.liftweb.json.DefaultFormats

  def apply(s: String) = {
    net.liftweb.json.parse(s).extract[EnemRow]
  }
}

object PlayParser {
  implicit val examReads = play.api.libs.json.Json.reads[EnemExam]
  implicit val rowReads = play.api.libs.json.Json.reads[EnemRow]

  def apply(s: String) = {
    play.api.libs.json.Json.parse(s).as[EnemRow]
  }
}

object SprayParser {
  implicit val rowFormat = jsonFormat6(EnemExam)
  implicit val examFormat = jsonFormat9(EnemRow)

  def apply(s: String) = {
    s.parseJson.convertTo[EnemRow]
  }
}