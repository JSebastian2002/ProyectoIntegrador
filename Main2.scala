import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot._
import com.github.tototoshi.csv._
import java.io.File
import play.api.libs.json._

object Main2 extends App {
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()
  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45))

  //Frecuencia

  val original_language = data
    .flatMap(elem => elem.get("original_language"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  println(original_language)

  //Diagrama Pastel

  val olpastel = original_language
    .map(m => (m._1, m._2.toDouble))
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

  //Diagrama de Barras

  val keywordsvalue = keywords
    .take(10)
    .map(_._2)
    .map(_.toDouble)


  val keywordslabel = keywords
    .take(10)
    .map(_._1)


  BarChart(keywordsvalue)
    .title("Palabras Clave ")
    .xAxis(keywordslabel)
    .yAxis()
    .frame()
    .yLabel("Frecuencia")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/keywords.png"))


  println(" ")

  //Frecuencia

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

  //Diagrama de Barras

  val genresvalue = genres
    .take(10)
    .map(_._2)
    .map(_.toDouble)


  val genreslabel = genres
    .take(10)
    .map(_._1)



  BarChart(genresvalue)
    .title("Palabras Clave ")
    .xAxis(genreslabel)
    .yAxis()
    .frame()
    .yLabel("Frecuencia")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/genres.png"))

  println(" ")

  //Frecuencia
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
  //Diagrama de Barras
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

  //Frecuencia
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
  //Diagrama de Barras
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


  //Frecuencia
  val directors = data
    .flatMap(elem => elem.get("director"))
    .flatMap(elem => elem.split("\\s"))
    .filter(f => f.trim.nonEmpty)
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse
    .take(10)

  println(directors)



  //Diagrama Pastel

  val directorspastel = directors
    .map(m => (m._1, m._2.toDouble))
    .take(10)

  PieChart(directorspastel)
    .title("Directores")
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Pastel/directorespastel.png"))


}
