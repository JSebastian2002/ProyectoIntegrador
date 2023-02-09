import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot._
import com.github.tototoshi.csv._
import java.io.File

object GraficasColumJSON extends App {

  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 0))
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  //Product Companies Grafica Some y None

  val productionCompaniesg = data
    .flatMap(row => row.get("production_companies"))
    .groupBy {
      case "[]" => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println("Production Companies: " + productionCompaniesg)

  val productcompvalues = productionCompaniesg
    .map(_._2)
    .map(_.toDouble)

  val productcomlabel = productionCompaniesg
    .map(_._1)
    .map(_.toString)

  //Product Countries Grafica Some y None

  BarChart(productcompvalues)
    .title(" Product Companies Some/None ")
    .xAxis(productcomlabel)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/productcom.png"))


  val productionCountriesg = data
    .flatMap(row => row.get("production_countries"))
    .groupBy {
      case "[]" => None
      case (_) => Some

    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Production Countries :" + productionCountriesg)


  val productcontvalues = productionCompaniesg
    .map(_._2)
    .map(_.toDouble)

  val productcontlabel = productionCountriesg
    .map(_._1)
    .map(_.toString)

  BarChart(productcontvalues)
    .title(" Product Countries Some/None ")
    .xAxis(productcontlabel)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/productcont.png"))


  //Spoken Languages Grafica Some y None

  val spokenlanguages = data
    .flatMap(row => row.get("spoken_languages"))
    .groupBy {
      case "[]" => None
      case (_) => Some

    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println("Spoken Languages :" + spokenlanguages)


  val spokenlangvalues = spokenlanguages
    .map(_._2)
    .map(_.toDouble)

  val spokenlanglabel = spokenlanguages
    .map(_._1)
    .map(_.toString)

  BarChart(spokenlangvalues)
    .title(" Spoken Languages Some/None ")
    .xAxis(spokenlanglabel)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/spokenlang.png"))


}
