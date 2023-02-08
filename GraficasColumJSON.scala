import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot._
import com.github.tototoshi.csv._
import java.io.File
import scala.util.{Failure, Success, Try}
import play.api.libs.json._
object GraficasColumJSON extends App{

  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 0))
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  val productionCompaniesg = data
    .flatMap(row => row.get("production_companies"))
    .groupBy{
      case "[]" => None
      case (_) => Some

    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Production Companies: " +productionCompaniesg)


  val productionCountriesg = data
    .flatMap(row => row.get("production_countries"))
    .groupBy {
      case "[]" => None
      case (_) => Some

    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Production Countries :"+productionCountriesg)

  val spokenlanguages = data
    .flatMap(row => row.get("spoken_languages"))
    .groupBy {
      case "[]" => None
      case (_) => Some

    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Spoken Languages :" + spokenlanguages)



}
