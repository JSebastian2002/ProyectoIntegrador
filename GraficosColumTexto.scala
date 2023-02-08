
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot._
import com.github.tototoshi.csv._
import java.io.File
import scala.util.{Failure, Success, Try}

object GraficosColumTexto extends App {

  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 0))
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  val homepagev = data
    .map(row => row("homepage"))
    .groupBy{
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
println("Homepage: "+homepagev)


  val genresv = data
    .map(row => row("genres"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Genres: "+genresv)

  val keywordsv = data
    .map(row => row("keywords"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Keywords: "+keywordsv)

  val overviewv = data
    .map(row => row("overview"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Overview: "+overviewv)


  val taglinev = data
    .map(row => row("tagline"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Tagline: "+taglinev)

  val director = data
    .map(row => row("director"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Director: "+director)


}
