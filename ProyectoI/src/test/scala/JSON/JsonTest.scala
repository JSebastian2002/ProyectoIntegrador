package JSON

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

object JsonTest {
  val jsonValue = """
  {
    "JSON":
    {
      "budget":"150000000",
      "genres":"Action Adventure Science Fiction Thriller",
      "id":"135397",
      "keywords":"monster dna tyrannosaurus rex velociraptor island",
      "original_title":"Jurassic World",
      "overview":"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.",
      "production_countries":"iso_3166_1 US name  United States of America",
      "popularity":
      {
        "tagline":"The park is open.",
        "title":"Jurassic World",
        "homepage":"http://www.jurassicworld.com/",
        "vote_average":"6/05/2023",
        "cast":"Chris Pratt Bryce Dallas Howard Irrfan Khan Vincent D'Onofrio Nick Robinson",
        "vote_count":"8662",
        "director":"Colin Trevorrow"
      }
    }
  }
  """
  def printJson ={

    implicit val rltRds = (
      (__ \ "tagline").read[String] ~
        (__ \ "title").read[String] ~
        (__ \ "homepage").read[String] ~
        (__ \ "vote_average").read[String] ~
        (__ \ "cast").read[String] ~
        (__ \ "vote_count").read[String] ~
        (__ \ "director").read[String]
      )//(Retailer)


    val JSON = Json.parse(jsonValue)//.validate[Business](bsnsRds)
    //val json = Json.toJson(bus)

    println(JSON)
  }

  def main(args: Array[String]): Unit = {
    printJson
  }

}