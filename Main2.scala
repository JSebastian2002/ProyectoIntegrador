import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot._
import com.github.tototoshi.csv._
import java.io.File
import scala.util.{Failure, Success, Try}
import play.api.libs.json._

object Main2 extends App {
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()
  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45))


  println("        ")


  //Frecuencia
  val original_language = data
    .flatMap(elem => elem.get("original_language"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size.toDouble) }
    .toList
    .sortBy(_._2)
    .reverse



  println(original_language)
//Diagrama Pastel

  val olpastel = original_language
    .take(10)

  PieChart(olpastel)
    .title("idiomas Originales")
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Pastel/pastelorilan.png"))



//Frecuencia
  val keywords = data
    .flatMap(elem => elem.get("keywords"))
    .flatMap(elem => elem.split("\\s"))
    .filter(f1 => f1 != "on")
    .filter(f1 => f1 != "of")
    .filter(vacios => vacios.trim.nonEmpty)
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse


  println(keywords)





  println(" ")
  val genres = data
    .flatMap(elem => elem.get("genres"))
    .flatMap(elem => elem.split("\\s"))
    .map(genre => {
      if (genre == "Science" || genre == "Fiction") {
        "Science Fiction"

      }.distinct else {
        genre
      }
    })
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse


println(genres)


  println(" ")
  val overview = data.take(2)
    .map(elem => (elem("homepage"), elem(("overview"))))
    .map(elem => Try {
      (elem._2, elem._1.toString)
    })
    .filter(f => f.isSuccess)
    .map(_.get)

  println(overview)


  val productionCompanies = data
    .flatMap(row => row.get("production_companies"))
    .map(row => Json.parse(row))
    .flatMap(jsonData => jsonData \\ "name")
    .map(jsValue => jsValue.as[String])
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  val productioncompaniesvalue = productionCompanies
    .map(_._2)
    .map(_.toDouble)
    .take(10)

  val productionconmpanieslabel = productionCompanies
    .map(_._1)
    .take(10)


  BarChart(productioncompaniesvalue)
    .title("Compañias productoras ")
    .xAxis(productionconmpanieslabel)
    .yAxis()
    .frame()
    .yLabel("Producciones")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/proudctioncompanies.png"))


  //println(productionCompanies)


  val productionCountries = data
    .flatMap(row => row.get("production_countries"))
    .map(row => Json.parse(row))
    .flatMap(jsonData => jsonData \\ "name")
    .map(jsValue => jsValue.as[String])
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  val productioncontriesvalue = productionCountries
    .map(_._2)
    .map(_.toDouble)
    .take(10)


  val productioncontrieslabel = productionCountries
    .map(_._1)
    .take(10)

  BarChart(productioncontriesvalue)
    .title("Paises produccion ")
    .xAxis(productioncontrieslabel)
    .yAxis()
    .frame()
    .yLabel("Producciones")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/proudctioncontries.png"))


  // println(productionCountries)

  val spokenlanguages = data
    .flatMap(row => row.get("spoken_languages"))
    .map(row => Json.parse(row))
    .flatMap(jsonData => jsonData \\ "name")
    //.map(StringContext.processEscapes)
    .map(jsValue => jsValue.as[String])
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  val spokenlanguagesvalue = spokenlanguages
    .take(10)
    .map(_._2)
    .map(_.toDouble)


  val spokenlanguageslabel = spokenlanguages
    .take(10)
    .map(_._1)

  BarChart(spokenlanguagesvalue)
    .title("Idiomas Hablados ")
    .xAxis(spokenlanguageslabel)
    .yAxis()
    .frame()
    .yLabel("Idiomas")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/spokenlanguages.png"))

  //println(spokenlanguages)


}
