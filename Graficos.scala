
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme, defaultTheme}
import com.cibo.evilplot._
import com.cibo.evilplot.plot._
import com.github.tototoshi.csv.CSVReader
import java.io.File
import scala.util.{Failure, Success, Try}

object Graficos extends App {

  //  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45))
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()


  val presupuestografica = data
    .map(row => row("budget"))
    .map(elem => elem.toLong)
    .sortBy(x => -x)


  val prevalorh = presupuestografica
    .map(_.toDouble)
    .take(5)


  Histogram(prevalorh)
    .title("Presupuestos")
    .xAxis()
    .xbounds(2.6E8, 3.8E8)
    .yAxis()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Histograma/histogrampresupuestos.png"))


  val popularidadgrafica = data
    .map(row => row("popularity"))
    .map(elem => Try {
      (elem.replace(".", "").toLong)
    })
    .filter(f => f.isSuccess)
    .map(_.get)
    .sortBy(x => -x)


  val popuvalorh = popularidadgrafica
    .map(_.toDouble)
    .take(5)


  Histogram(popuvalorh)
    .title("Popularidad")
    .xAxis()
    .xbounds()
    .yAxis()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Histograma/popularidadh.png"))


  val graficarevenue = data
    .map(row => row("revenue"))
    .map(elem => Try {
      (elem.toLong)
    })
    .filter(f => f.isSuccess)
    .map(_.get)
    .sortBy(x => -x)

  val valorhrevenue = graficarevenue
    .map(_.toDouble)
    .take(5)


  Histogram(valorhrevenue)
    .title("Ingresos")
    .xAxis()
    .xbounds()
    .yAxis()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Histograma/revenueh.png"))


  val graficaruntime = data
    .map(row => row("runtime"))
    .map(elem => Try {
      (elem.toDouble)
    })
    .filter(f => f.isSuccess)
    .map(_.get)
    .sortBy(x => -x)

  val valorhruntime = graficaruntime
    .take(5)

  Histogram(valorhruntime)
    .title("Duración")
    .xAxis()
    .xbounds()
    .yAxis()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Histograma/hruntime.png"))


  val graficavoteaverage = data
    .map(row => row("vote_average"))
    .map(elem => Try {
      (elem.toDouble)
    })
    .filter(f => f.isSuccess)
    .map(_.get)
    .sortBy(x => -x)

  val valorhvoteavg = graficavoteaverage
    .take(5)

  Histogram(valorhvoteavg)
    .title("Promedio de votos")
    .xAxis()
    .xbounds()
    .yAxis()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Histograma/voteavgh.png"))


  val graficavotecount = data
    .map(row => row("vote_count"))
    .map(elem => Try {
      (elem.toInt)
    })
    .filter(f => f.isSuccess)
    .map(_.get)
    .sortBy(x => -x)

  val valorhvotecou = graficavotecount
    .map(_.toDouble)
    .take(5)


  Histogram(valorhvotecou)
    .title("Conteo de votos")
    .xAxis()
    .xbounds()
    .yAxis()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Histograma/votecounth.png"))







}
