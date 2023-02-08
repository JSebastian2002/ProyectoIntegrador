import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot._
import com.github.tototoshi.csv._
import java.io.File
import scala.util.{Failure, Success, Try}

object GraficosColumNum extends App {

  implicit val theme = DefaultTheme.copy(elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45))
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  //Budget
   //None y Some

  val budgetnys = data
    .map(elem => elem("budget"))
    .map(_.toInt)
    .groupBy {
      case 0 => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println(budgetnys)
  val budgetvalue = budgetnys
    .map(_._2)
    .map(_.toDouble)


  val budgetlabel = budgetnys
    .map(_._1)
    .map(_.toString)


  BarChart(budgetvalue)
    .title(" Budget Some y None ")
    .xAxis(budgetlabel)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/budgetnys.png"))


  //Top 10 peliculas con mas presupuesto

  val budgetg = data
    .map(elem => (elem("budget"), elem("title")))
    .map(elem => (elem._1.toInt, elem._2))
    .sortBy(elem => (-elem._1, elem._2))


  val presupuestovalor = budgetg
    .map(_._1)
    .map(_.toDouble)
    .take(5)

  val presupuestotitulo = budgetg
    .map(_._2)
    .take(5)




  BarChart(presupuestovalor)
    .title("Top 5 peliculas con mas presupuesto ")
    .xAxis(presupuestotitulo)
    .yAxis()
    .frame()
    .yLabel("Presupuesto")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/budgetop5.png"))



//Popularity

  val popularity = data
    .map(elem => (elem("popularity"), elem("title")))
    .map(elem => Try {
      (elem._1.replace(".", "").toLong, elem._2)
    })
    .filter(f => f.isSuccess)
    .map(_.get)
    .sortBy(elem => (-elem._1, elem._2))


  val popuvalor = popularity
    .map(_._1)
    .map(_.toDouble)
    .take(10)

  val poputitulo = popularity
    .map(_._2)
    .take(10)


  BarChart(popuvalor)
    .title("Top 10 peliculas con mas popularidad ")
    .xAxis(poputitulo)
    .yAxis()
    .frame()
    .yLabel("Popularidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/poputop10.png"))



//Revenue
  //None y Some
  val revenuenys = data
    .map(elem => elem("revenue"))
    .map(_.toLong)
    .groupBy {
      case 0 => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println(revenuenys)
  val revenuenysvalue = revenuenys
    .map(_._2)
    .map(_.toDouble)


  val revenuelabelsyns = revenuenys
    .map(_._1)
    .map(_.toString)


  BarChart(revenuenysvalue)
    .title("Revenue Some y None  ")
    .xAxis(revenuelabelsyns)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/revenuetnys.png"))


// Top 10 peliculas con mas ingresos
  val revenue = data
    .map(elem => (elem("revenue"), elem("title")))
    .map(elem => (elem._1.toLong, elem._2))
    .sortBy(elem => (-elem._1, elem._2))


  val revevalue = revenue
    .map(_._1)
    .map(_.toDouble)
    .take(10)

  val revetitulo = revenue
    .map(_._2)
    .take(10)


  BarChart(revevalue)
    .title("Top 10 peliculas con mas ingresos ")
    .xAxis(revetitulo)
    .yAxis()
    .frame()
    .yLabel("Ingresos")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/revetop10.png"))


  //Runtime
  //None,Some

  val runtimesyns: List[Option[Double]] = data
    .flatMap(elem => elem.get("runtime")
    .map(_.toDoubleOption))
    .sortBy(x => x)



//Top 10 peliculas con mas promedio de votos
  val voteaverageg = data
    .map(elem => (elem("vote_average"), elem("title")))
    .map(elem => (elem._1.toDouble, elem._2))
    .sortBy(elem => (-elem._1, elem._2))


  val voteavgvalue = voteaverageg
    .map(_._1)
    .take(10)

  val voteavgtitulo = voteaverageg
    .map(_._2)
    .take(10)


  BarChart(voteavgvalue)
    .title("Top 10 peliculas con mas promedio de voto ")
    .xAxis(voteavgtitulo)
    .yAxis()
    .frame()
    .yLabel("Promedio de votos")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/voteavgtop10.png"))



  val votecountnys = data
    .map(elem => elem("vote_count"))
    .map(_.toInt)
    .groupBy {
      case 0 => None
      case (_) => Some
    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList

  println(votecountnys)
  val votecountnysvalue = votecountnys
    .map(_._2)
    .map(_.toDouble)


  val votecountnyslabel = votecountnys
    .map(_._1)
    .map(_.toString)


  BarChart(votecountnysvalue)
    .title("Votecount Some y None ")
    .xAxis(votecountnyslabel)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/votecountnys.png"))


  //Top 10 peliculas con mas conteo de votos

  val votecountg = data
    .map(elem => (elem("vote_count"), elem("title")))
    .map(elem => (elem._1.toInt, elem._2))
    .sortBy(elem => (-elem._1, elem._2))


  val votecountvalor = votecountg
    .map(_._1)
    .map(_.toDouble)
    .take(10)

  val votecounttitulo = votecountg
    .map(_._2)
    .take(10)




  BarChart(votecountvalor)
    .title("Top 5 peliculas con mas conteo de votos ")
    .xAxis(votecounttitulo)
    .yAxis()
    .frame()
    .yLabel("Conteo de votos")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/votecountop10.png"))


}
