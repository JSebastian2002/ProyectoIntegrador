package JSON

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

object JsonTest {
  val jsonValue = """
  {
    "JSON":
    {
<<<<<<< HEAD
      "budget":"150000000",
      "genres":"Action Adventure Science Fiction Thriller",
      "keywords":"monster dna tyrannosaurus rex velociraptor island",
      "original_title":"Jurassic World",
      "overview":"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.",
      "production_countries":"iso_3166_1 US name  United States of America",
      "popularity":
=======
      "name":"Some Business Name",
      "preferredUrl":"someurl",
      "businessPhone":"somenumber",
      "businessPhone":"somenumber",
      "retailer":
>>>>>>> a7776c8202bbafbb33911931a1e79d3c9ff4fd69
      {
        "firstName":"Some",
        "lastName":"One",
        "homepage":"http://www.jurassicworld.com/",
<<<<<<< HEAD
        "cast":"Chris Pratt Bryce Dallas Howard Irrfan Khan Vincent D'Onofrio Nick Robinson",
        "director":"Colin Trevorrow"
=======
        "mobileNo":"someothernumber",
        "password":"$^^HFKH*"
>>>>>>> a7776c8202bbafbb33911931a1e79d3c9ff4fd69
      }
    }
  }
  """
  def printJson ={

    implicit val rltRds = (
      (__ \ "firstName").read[String] ~
        (__ \ "lastName").read[String] ~
        (__ \ "homepage").read[String] ~
        (__ \ "mobileNo").read[String] ~
        (__ \ "password").read[String]
      )//(Retailer)



    implicit val bsnsRds = ({
      val JSON = (__ \ "business")
      (JSON \ "name").read[String] ~
        (JSON \ "preferredUrl").read[String] ~
        (JSON \ "businessPhone").read[String]
      //  (business \ "retailer").read[Retailer](rltRds)
    })//(Business)



    val JSON = Json.parse(jsonValue)//.validate[Business](bsnsRds)
    //val json = Json.toJson(bus)

    println(JSON)
  }




  def main(args: Array[String]): Unit = {
    printJson
  }

}