
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

  //Homepage Some/None
  val homepagev = data
    .map(row => row("homepage"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println("Homepage: " + homepagev)

  val homevalues = homepagev
    .map(_._2)
    .map(_.toDouble)

  val homelabel = homepagev
    .map(_._1)
    .map(_.toString)

  BarChart(homevalues)
    .title(" Hompeage Some/None ")
    .xAxis(homelabel)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/homepagev.png"))


  // Genres Some/None
  val genresv = data
    .map(row => row("genres"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println("Genres: " + genresv)

  val valuegenres = genresv
    .map(_._2)
    .map(_.toDouble)

  val labelgenres = genresv
    .map(_._1)
    .map(_.toString)

  BarChart(valuegenres)
    .title(" Genres Some/None ")
    .xAxis(labelgenres)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/Genresv.png"))

  // Keywords Some/None
  val keywordsv = data
    .map(row => row("keywords"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList


  println("Keywords: " + keywordsv)

  val valuekeywords = keywordsv
    .map(_._2)
    .map(_.toDouble)

  val labelkeywords = keywordsv
    .map(_._1)
    .map(_.toString)

  BarChart(valuekeywords)
    .title(" Keywords Some/None ")
    .xAxis(labelkeywords)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/keywordsv.png"))


  //Overview Some/None
  val overviewv = data
    .map(row => row("overview"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println("Overview: " + overviewv)

  val valueover = overviewv
    .map(_._2)
    .map(_.toDouble)

  val labelover = overviewv
    .map(_._1)
    .map(_.toString)

  BarChart(valueover)
    .title(" Overview Some/None ")
    .xAxis(labelover)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/overviewsyn.png"))

  //Tagline Some/None
  val taglinev = data
    .map(row => row("tagline"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList


  println("Tagline: " + taglinev)

  val valuetag = taglinev
    .map(_._2)
    .map(_.toDouble)

  val labeltag = taglinev
    .map(_._1)
    .map(_.toString)

  BarChart(valuetag)
    .title(" Tagline Some/None ")
    .xAxis(labeltag)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/taglinessyn.png"))


  //Directo Some/None

  val directorv = data
    .map(row => row("director"))
    .groupBy {
      case "" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println("Director: " + directorv)


  val valuedirector = directorv
    .map(_._2)
    .map(_.toDouble)

  val labeldirector = directorv
    .map(_._1)
    .map(_.toString)

  BarChart(valuedirector)
    .title(" Keywords Some/None ")
    .xAxis(labeldirector)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/directorsn.png"))


}
