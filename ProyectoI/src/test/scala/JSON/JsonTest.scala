package JSON

import com.github.tototoshi.csv.CSVReader

import java.io.File
import play.api.libs.json._


object JsonTest {
  val jsonValue = """
  {
    "business":
    {
      "name":"Some Business Name",
      "preferredUrl":"someurl",
      "businessPhone":"somenumber",
      "retailer":
      {
        "firstName":"Some",
        "lastName":"One",
        "email":"someone@somewhere.com",
        "mobileNo":"someothernumber",
        "password":"$^^HFKH*"
      }
    }

  }
  """
  def printJson ={

    val buisness = Json.parse(jsonValue)//.validate[Business](bsnsRds)
    //val json = Json.toJson(bus)

    println(buisness)
  }

  def main(args: Array[String]): Unit = {
    printJson
  }
  val reader = CSVReader.open(new File("//home/frank/Documentos/tercero/Programacion/ProyectoIntegrador/ProyectoI/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()
  val Tittle = data.flatMap(elem => (elem.get("title")))
  val orginalTittle2 = Tittle.groupBy(x => x).map(x => (x._1, x._2.length))
  println(orginalTittle2)



}