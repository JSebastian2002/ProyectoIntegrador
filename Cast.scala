import com.github.tototoshi.csv._
import java.io.File
import play.api.libs.json._
import requests.Response
import scala.util.{Failure, Success, Try}

object Cast extends App{
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  def actorsNames(dataRaw: String): Option[String] = {
    val response: Response = requests
      .post("http://api.meaningcloud.com/topics-2.0",
        data = Map("key" -> "a1e3dd531085b28b1ecccff737ad5b6c",
          "lang" -> "en",
          "txt" -> dataRaw,
          "tt" -> "e"),
        headers = Map("content-type" -> "application/x-www-form-urlencoded"))
    Thread.sleep(1000)
    if (response.statusCode == 200) {
      Option(response.text)
    } else
      Option.empty
  }


  val cast = data
    .map(row => row("cast"))
    .filter(_.nonEmpty)
    .map(StringContext.processEscapes)
    .map(actorsNames)
    .map(json => Try(Json.parse(json.get)))
    .filter(_.isSuccess)
    .map(_.get)
    .flatMap(json => json("entity_list").as[JsArray].value)
    .map(_ ("form"))
    .map(data => data.as[String])
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse


println("Nombres que mas se repiten"+cast)





}
