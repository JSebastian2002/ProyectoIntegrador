import com.github.tototoshi.csv._
import java.io.File
import play.api.libs.json._
import requests.Response
import scala.util.{Failure, Success, Try}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths, StandardOpenOption}
import scalikejdbc._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot._

object Cast extends App{
  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45))
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://localhost:3306/pfr", "root", "admin")
  implicit val session: DBSession = AutoSession

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


  val castg = data
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


  val castvalue = castg
    .take(10)
    .map(_._2)
    .map(_.toDouble)


  val castlabel = castg
    .take(10)
    .map(_._1)

  BarChart(castvalue)
    .title("Actores")
    .xAxis(castlabel)
    .yAxis()
    .frame()
    .yLabel("Frecuencia")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/cast.png"))


  val castdata = data
    .map(row => row("cast"))
    .filter(_.nonEmpty)
    .map(StringContext.processEscapes)
    .take(10)
    .map(actorsNames)
    .map(json => Try(Json.parse(json.get)))
    .filter(_.isSuccess)
    .map(_.get)
    .flatMap(json => json("entity_list").as[JsArray].value)
    .map(_ ("form"))
    .map(data => data.as[String])

  // POBLAR MEDIANTE LIBRERIA
  // Tabla Cast

  castdata.foreach(x =>
    sql"""
       INSERT INTO cast(NOMBRES)
       VALUES
        (${x})
       """
      .stripMargin
      .update
      .apply())



}
