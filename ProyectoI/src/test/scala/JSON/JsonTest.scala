package JSON

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

object JsonTest {
  val jsonValue = """
  {
    "JSON":
    {
      "name":"Some Business Name",
      "preferredUrl":"someurl",
      "businessPhone":"somenumber",
      "businessPhone":"somenumber",
      "retailer":
      {
        "firstName":"Some",
        "lastName":"One",
        "homepage":"http://www.jurassicworld.com/",
        "mobileNo":"someothernumber",
        "password":"$^^HFKH*"
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