import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot._
import com.github.tototoshi.csv._
import java.io.File
import play.api.libs.json._

object AnalisisNoNum extends App {
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()
  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45))

  //Frecuencia original_language

  val original_language = data
    .flatMap(elem => elem.get("original_language"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  println(original_language)


  //Diagrama de Barras original_language

  val originallanvalue = original_language
    .take(10)
    .map(_._2)
    .map(_.toDouble)


  val originallanlabel = original_language
    .take(10)
    .map(_._1)


  BarChart(originallanvalue)
    .title("Top 10 Idiomas Originales  ")
    .xAxis(originallanlabel)
    .yAxis()
    .frame()
    .yLabel("Frecuencia")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/originallan.png"))


  //Frecuencia keywords

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

  //Diagrama de Barras keywords

  val keywordsvalue = keywords
    .take(10)
    .map(_._2)
    .map(_.toDouble)


  val keywordslabel = keywords
    .take(10)
    .map(_._1)


  BarChart(keywordsvalue)
    .title("Top 10 Palabras Clave ")
    .xAxis(keywordslabel)
    .yAxis()
    .frame()
    .yLabel("Frecuencia")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/keywords.png"))


  println(" ")

  //Frecuencia genres

  val genres = data
    .flatMap(elem => elem.get("genres"))
    .flatMap(elem => elem.split("\\s"))
    .map {
      case "Science" => "Science Fiction"
      case "Fiction" => null
      case x => x
    }
    .filter(f => f != null)
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  println(genres)

  //Diagrama de Barras genres

  val genresvalue = genres
    .take(10)
    .map(_._2)
    .map(_.toDouble)


  val genreslabel = genres
    .take(10)
    .map(_._1)


  BarChart(genresvalue)
    .title("Top 10 Generos en las peliculas ")
    .xAxis(genreslabel)
    .yAxis()
    .frame()
    .yLabel("Frecuencia")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/genres.png"))

  println(" ")

  //Frecuencia productioncompanies

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
    .take(10)

  println(productionCompanies)

  //Diagrama de Barras productioncompanies

  val productioncompaniesvalue = productionCompanies
    .map(_._2)
    .map(_.toDouble)
    .take(10)

  val productionconmpanieslabel = productionCompanies
    .map(_._1)
    .take(10)


  BarChart(productioncompaniesvalue)
    .title("Top 10 Compañias productoras ")
    .xAxis(productionconmpanieslabel)
    .yAxis()
    .frame()
    .yLabel("Producciones")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/proudctioncompanies.png"))

  //Frecuencia productioncountries

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
    .take(10)
  println(productionCountries)

  //Diagrama de Barras productioncountries

  val productioncontriesvalue = productionCountries
    .map(_._2)
    .map(_.toDouble)
    .take(10)


  val productioncontrieslabel = productionCountries
    .map(_._1)
    .take(10)

  BarChart(productioncontriesvalue)
    .title("Top 10 paises productores ")
    .xAxis(productioncontrieslabel)
    .yAxis()
    .frame()
    .yLabel("Producciones")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/proudctioncontries.png"))

  //Frecuencia spoken_languages
  val spokenlanguages = data
    .flatMap(row => row.get("spoken_languages"))
    .map(row => Json.parse(row))
    .flatMap(jsonData => jsonData \\ "name")
    .map(jsValue => jsValue.as[String])
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse
    .take(10)
  println(spokenlanguages)

  //Diagrama de Barras spokenlanguages

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
    .yLabel("Frecuencia")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/spokenlanguages.png"))


  //Frecuencia director

  val directors = data
    .flatMap(elem => elem.get("director"))
    .filter(f => f.trim.nonEmpty)
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse
    .take(10)

  println(directors)

  //Diagrama de Barras director

  val directorsvalue = directors
    .take(10)
    .map(_._2)
    .map(_.toDouble)


  val directorslabel = directors
    .take(10)
    .map(_._1)

  BarChart(directorsvalue)
    .title("Directores con mas peliculas")
    .xAxis(directorslabel)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/directores.png"))

}
