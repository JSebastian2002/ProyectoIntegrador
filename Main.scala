import com.github.tototoshi.csv._
import java.io.File
import scala.util.{Failure, Success, Try}
import play.api.libs.json._
import com.cibo.evilplot.plot.{BarChart, PieChart}
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, defaultTheme}
import com.cibo.evilplot._
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import com.cibo.evilplot.numeric.Point
object Main extends App {

  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()


  val presupuesto = data
    .map(elem => (elem("budget"), elem(("title")), elem(("original_language")),
      elem(("tagline")), elem(("director")), elem(("status"))))
    .map(elem => Try {
      (elem._1.toInt, elem._2, elem._3, elem._4, elem._5, elem._6)
    })
    .filter(f => f.isSuccess)
    .map(_.get)

  val maxpre = presupuesto.map(_._1).max
  val minpre = presupuesto.map(_._1).filter(_ != 0).min
  val sumpre = presupuesto.map(_._1).sum
  val lenpre = presupuesto.map(_._1).length
  val avgpre = sumpre / lenpre



  val titlepremax = presupuesto
    .filter(elem => elem._1 == maxpre)
    .map(_._2)
    .head
  val titlepremin = presupuesto
    .filter(elem => elem._1 == minpre)
    .map(_._2)
    .head


  val olangpremax = presupuesto
    .filter(elem => elem._1 == maxpre)
    .map(_._3)
    .head
  val olangpremin = presupuesto
    .filter(elem => elem._1 == minpre)
    .map(_._3)
    .head


  val tagpremax = presupuesto
    .filter(elem => elem._1 == maxpre)
    .map(_._4)
    .head
  val tagpremin = presupuesto
    .filter(elem => elem._1 == minpre)
    .map(_._4)
    .head

  val direpremax = presupuesto
    .filter(elem => elem._1 == maxpre)
    .map(_._5)
    .head

  val direpremin = presupuesto
    .filter(elem => elem._1 == minpre)
    .map(_._5)
    .head


  val statpremax = presupuesto
    .filter(elem => elem._1 == maxpre)
    .map(_._6)
    .head
  val statpremin = presupuesto
    .filter(elem => elem._1 == minpre)
    .map(_._6)
    .head




  println("Budget")
  println("El presupuesto mas alto es: "+maxpre+" \nPelicula: "+titlepremax+"" +
    "\nIdioma: "+olangpremax+"\nEslogan: "+tagpremax+"\nDirector: "+direpremax+"\nEstado: "+statpremax)
  println("")

  println("El presupuesto mas bajo es: "+minpre+" \nPelicula: "+titlepremin + "" +
    "\nIdioma: " + olangpremin  + "\nEslogan: " + tagpremin  + "\nDirector: "
    + direpremin  + "\nEstado: " + statpremin )
  println("")

  println("El promedio del presupuesto de las peliculas es: "+avgpre)

val presupuesto2 = data
  .map(elem => (elem("budget"),elem("title")))
  .map(elem => Try {
    (elem._1.toInt, elem._2)
  })
  .filter(f => f.isSuccess)
  .map(_.get)
  .groupBy(identity)
  .map { case (keyword, title) => (keyword, title.size ) }
  .toList
  .sortBy(_._2)
  .reverse
  val a = presupuesto2.map(_._2).map(_.toDouble).take(5)
  val b = presupuesto2.map(_._1).map(_.toString).take((5))

  BarChart(a)
    .title("Presupuesto de Peliculas")
    .xAxis(b)
    .yAxis()
    .frame()
    .yLabel("Peliculas")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\presupuesto1.png"))


  val popularidad = data
    .map(elem => (elem("popularity"), elem(("title")) ,elem(("original_language")),
      elem(("tagline")), elem(("director")), elem(("status"))))
    .map(elem => Try{
      (elem._1.replace(".", "").toLong , elem._2, elem._3, elem._4, elem._5, elem._6)
    })
    .filter(f => f.isSuccess)
    .map(_.get)


  val maxpopu = popularidad.map(_._1).max
  val minpopu = popularidad.map(_._1).filter(_ != 0).min
  val sumpopu = popularidad.map(_._1).sum
  val lenpopu = popularidad.map(_._1).length
  val avgpopu = sumpopu / lenpopu

  val titlepopumax = popularidad
    .filter(elem => elem._1 == maxpopu)
    .map(_._2)
    .head

  val titlepopumin = popularidad
    .filter(elem => elem._1 == minpopu)
    .map(_._2)
    .head

  val olangpopumax = popularidad
    .filter(elem => elem._1 == maxpopu)
    .map(_._3)
    .head
  val olangpopumin = popularidad
    .filter(elem => elem._1 == minpopu)
    .map(_._3)
    .head


  val tagpopumax = popularidad
    .filter(elem => elem._1 == maxpopu)
    .map(_._4)
    .head
  val tagpopumin = popularidad
    .filter(elem => elem._1 == minpopu)
    .map(_._4)
    .head

  val direpopumax = popularidad
    .filter(elem => elem._1 == maxpopu)
    .map(_._5)
    .head

  val direpopumin = popularidad
    .filter(elem => elem._1 == minpopu)
    .map(_._5)
    .head


  val statpopumax = popularidad
    .filter(elem => elem._1 == maxpopu)
    .map(_._6)
    .head
  val statpopumin = popularidad
    .filter(elem => elem._1 == minpopu)
    .map(_._6)
    .head

  println("----------------------------------------------")
  println("Popularity")
  println("La popularidad mas alta es: " + maxpopu + " \nPelicula: " + titlepopumax + "" +
    "\nIdioma: " + olangpopumax + "\nEslogan: " + tagpopumax + "\nDirector: " + direpopumax +
    "\nEstado: " + statpopumax)
  println("")

  println("La popularidad mas baja es: " + minpopu + " \nPelicula: " + titlepopumin + "" +
    "\nIdioma: " + olangpopumin + "\nEslogan: " + tagpopumin + "\nDirector: " +
    direpopumin + "\nEstado: " + statpopumin)
  println("")

  println("El promedio de la popularidad de las peliculas es: " + avgpopu)


  val revenue = data
    .map(elem => (elem("revenue"), elem(("title")), elem(("original_language")),
      elem(("tagline")), elem(("director")), elem(("status"))))
    .map(elem => Try {
      (elem._1.toLong, elem._2, elem._3, elem._4, elem._5, elem._6)
    })
    .filter(f => f.isSuccess)
    .map(_.get)


  val maxrevenue =  revenue.map(_._1).max
  val minrevenue =  revenue.map(_._1).filter(_ != 0).min
  val sumrevenue =  revenue.map(_._1).sum
  val lenrevenue =  revenue.map(_._1).length
  val avgrevenue = sumrevenue / lenrevenue

  val titlerevemax =  revenue
    .filter(elem => elem._1 == maxrevenue)
    .map(_._2)
    .head

  val titlerevemin =  revenue
    .filter(elem => elem._1 == minrevenue)
    .map(_._2)
    .head

  val olangrevemax = revenue
    .filter(elem => elem._1 == maxrevenue)
    .map(_._3)
    .head
  val olangrevemin = revenue
    .filter(elem => elem._1 == minrevenue)
    .map(_._3)
    .head


  val tagrevemax = revenue
    .filter(elem => elem._1 == maxrevenue)
    .map(_._4)
    .head
  val tagrevemin = revenue
    .filter(elem => elem._1 == minrevenue)
    .map(_._4)
    .head

  val direrevemax = revenue
    .filter(elem => elem._1 == maxrevenue)
    .map(_._5)
    .head

  val direrevemin = revenue
    .filter(elem => elem._1 == minrevenue)
    .map(_._5)
    .head


  val statrevemax = revenue
    .filter(elem => elem._1 == maxrevenue)
    .map(_._6)
    .head
  val statrevemin = revenue
    .filter(elem => elem._1 == minrevenue)
    .map(_._6)
    .head


  println("---------------------------------------------------")
  println("Revenue")
  println("Los ingresos  mas altos es: " + maxrevenue + " \nPelicula: " + titlerevemax + "" +
    "\nIdioma: " + olangrevemax  + "\nEslogan: " + tagrevemax  + "\nDirector: " + direrevemax  +
    "\nEstado: " + statrevemax )
  println("")
  println("Los ingresos  mas bajos es: " + minrevenue + " \nPelicula: " + titlerevemin + "" +
    "\nIdioma: " + olangrevemin + "\nEslogan: " + tagrevemin + "\nDirector: " + direrevemin +
    "\nEstado: " + statrevemin)
  println("")
  println("El promedio de los ingresos  de las peliculas es: " + avgrevenue)


  val runtime = data
    .map(elem => (elem("runtime"), elem(("title")), elem(("original_language")),
      elem(("tagline")), elem(("director")), elem(("status"))))
    .map(elem => Try {
      (elem._1.toDouble, elem._2 , elem._3, elem._4, elem._5, elem._6)
    })
    .filter(f => f.isSuccess)
    .map(_.get)


  val maxruntime = runtime.map(_._1).max
  val minruntime = runtime.map(_._1).filter(_ != 0).min
  val sumruntime = runtime.map(_._1).sum
  val lenruntime = runtime.map(_._1).length
  val avgruntime = sumruntime / lenruntime

  val titlemaxrun = runtime
    .filter(elem => elem._1 == maxruntime)
    .map(_._2)
    .head

  val titleminrun = runtime
    .filter(elem => elem._1 == minruntime)
    .map(_._2)
    .head

  val olangmaxrun = runtime
    .filter(elem => elem._1 == maxruntime)
    .map(_._3)
    .head
  val olangminrun = runtime
    .filter(elem => elem._1 == minruntime)
    .map(_._3)
    .head

  val tagmaxrun = runtime
    .filter(elem => elem._1 == maxruntime)
    .map(_._4)
    .head
  val tagminrun = runtime
    .filter(elem => elem._1 == minruntime)
    .map(_._4)
    .head

  val diremaxrun = runtime
    .filter(elem => elem._1 == maxruntime)
    .map(_._5)
    .head

  val direminrun = runtime
    .filter(elem => elem._1 == minruntime)
    .map(_._5)
    .head


  val statmaxrun = runtime
    .filter(elem => elem._1 == maxruntime)
    .map(_._6)
    .head
  val statminrun = runtime
    .filter(elem => elem._1 == minruntime)
    .map(_._6)
    .head


  println("---------------------------------------------------")
  println("Runtime")
  println("El tiempo de ejecución mas alto es: " + maxruntime + " \nPelicula: " + titlemaxrun + "" +
    "\nIdioma: " + olangmaxrun + "\nEslogan: " + tagmaxrun + "\nDirector: " + diremaxrun +
    "\nEstado: " + statmaxrun)
  println("")
  println("El tiempo de ejecución mas bajo es: " + minruntime + " \nPelicula: " + titleminrun + "" +
    "\nIdioma: " + olangminrun + "\nEslogan: " + tagminrun + "\nDirector: " + direminrun +
    "\nEstado: " + statminrun)
  println("")
  println("El promedio del tiempo de ejecución  de las peliculas es: " + avgruntime)


  val voteaverage = data
    .map(elem => (elem("vote_average"), elem(("title")), elem(("original_language")),
      elem(("tagline")), elem(("director")), elem(("status"))))
    .map(elem => Try {
      (elem._1.toDouble, elem._2, elem._3, elem._4, elem._5, elem._6)
    })
    .filter(f => f.isSuccess)
    .map(_.get)


  val maxvoteaverage = voteaverage.map(_._1).max
  val minvoteaverage = voteaverage.map(_._1).filter(_ != 0).min
  val sumvoteaverage = voteaverage.map(_._1).sum
  val lenvoteaverage = voteaverage.map(_._1).length
  val avgvoteaverage = sumvoteaverage / lenvoteaverage

  val titlemaxvoteavg = voteaverage
    .filter(elem => elem._1 == maxvoteaverage)
    .map(_._2)
    .head

  val titleminvoteavg = voteaverage
    .filter(elem => elem._1 == minvoteaverage)
    .map(_._2)
    .head

  val olangmaxvoteavg = voteaverage
    .filter(elem => elem._1 == maxvoteaverage)
    .map(_._3)
    .head
  val olangminvoteavg = voteaverage
    .filter(elem => elem._1 == minvoteaverage)
    .map(_._3)
    .head

  val tagmaxvoteavg = voteaverage
    .filter(elem => elem._1 == maxvoteaverage)
    .map(_._4)
    .head
  val tagminvoteavg = voteaverage
    .filter(elem => elem._1 == minvoteaverage)
    .map(_._4)
    .head

  val diremaxvoteavg = voteaverage
    .filter(elem => elem._1 == maxvoteaverage)
    .map(_._5)
    .head

  val direminvoteavg = voteaverage
    .filter(elem => elem._1 == minvoteaverage)
    .map(_._5)
    .head


  val statmaxvoteavg = voteaverage
    .filter(elem => elem._1 == maxvoteaverage)
    .map(_._6)
    .head
  val statminvoteavg = voteaverage
    .filter(elem => elem._1 == minvoteaverage)
    .map(_._6)
    .head


  println("---------------------------------------------------")
  println("Voteaverage")
  println("El promedio de votos mas alto es: " + maxvoteaverage + " \nPelicula: " + titlemaxvoteavg + "" +
    "\nIdioma: " + olangmaxvoteavg + "\nEslogan: " + tagmaxvoteavg + "\nDirector: " + diremaxvoteavg +
    "\nEstado: " + statmaxvoteavg)
  println("")
  println("El promedio de votos mas bajo es: " + minvoteaverage + " \nPelicula: " + titleminvoteavg + "" +
    "\nIdioma: " + olangminvoteavg + "\nEslogan: " + tagminvoteavg + "\nDirector: " + direminvoteavg +
    "\nEstado: " + statminvoteavg)
  println("")
  println("El promedio de votos   de las peliculas es: " + avgvoteaverage)


  val votecount = data
    .map(elem => (elem("vote_count"), elem(("title")), elem(("original_language")),
      elem(("tagline")), elem(("director")), elem(("status"))))
    .map(elem => Try {
      (elem._1.toInt, elem._2, elem._3, elem._4, elem._5, elem._6)
    })
    .filter(f => f.isSuccess)
    .map(_.get)


  val maxvotecount = votecount.map(_._1).max
  val minvotecount = votecount.map(_._1).filter(_ != 0).min
  val sumvotecount = votecount.map(_._1).sum
  val lenvotecount = votecount.map(_._1).length
  val avgvotecount = sumvotecount / lenvotecount

  val titlemaxvotecount = votecount
    .filter(elem => elem._1 == maxvotecount)
    .map(_._2)
    .head

  val titleminvotecount = votecount
    .filter(elem => elem._1 == minvotecount)
    .map(_._2)
    .head

  val olangmaxvotecoun = votecount
    .filter(elem => elem._1 == maxvotecount)
    .map(_._3)
    .head
  val olangminvotecoun = votecount
    .filter(elem => elem._1 == minvotecount)
    .map(_._3)
    .head

  val tagmaxvotecoun = votecount
    .filter(elem => elem._1 == maxvotecount)
    .map(_._4)
    .head
  val tagminvotecoun = votecount
    .filter(elem => elem._1 == minvotecount)
    .map(_._4)
    .head

  val diremaxvotecoun = votecount
    .filter(elem => elem._1 == maxvotecount)
    .map(_._5)
    .head

  val direminvotecoun = votecount
    .filter(elem => elem._1 == minvotecount)
    .map(_._5)
    .head


  val statmaxvotecoun = votecount
    .filter(elem => elem._1 == maxvotecount)
    .map(_._6)
    .head
  val statminvotecoun = votecount
    .filter(elem => elem._1 == minvotecount)
    .map(_._6)
    .head



  println("---------------------------------------------------")
  println("Votecount")
  println("El conteo de votos mas alto es: " + maxvotecount + " \nPelicula: " + titlemaxvotecount + "" +
    "\nIdioma: " + olangmaxvotecoun + "\nEslogan: " + tagmaxvotecoun + "\nDirector: " + diremaxvotecoun +
    "\nEstado: " + statmaxvotecoun)
  println("")
  println("El conteo de votos mas bajo es: " + minvoteaverage + " \nPelicula: " + titleminvotecount + "" +
    "\nIdioma: " + olangminvotecoun + "\nEslogan: " + tagminvotecoun + "\nDirector: " + direminvotecoun +
    "\nEstado: " + statminvotecoun)
  println("")
  println("El promedio de conteo votos   de las peliculas es: " + avgvotecount)
println("-----------------------------------------------------------------")

  val original_language = data
    .flatMap(elem => elem.get("original_language"))
    .groupBy(identity)
    .map {
      case (keyword,lista) => (keyword, lista.size)
    }
    .toList
    .sortBy(_._2)
    .reverse



  println(original_language)
/*
  val original_title = data
    .flatMap(elem => elem.get("original_title"))
    .groupBy(identity)
    .map {
      case (keyword, lista) => (keyword, lista.size)
    }
    .toList
    .sortBy(_._2)
    .reverse


  val overview = data
    .flatMap(elem => elem.get("overview"))
    .groupBy(identity)
    .map {
      case (keyword, lista) => (keyword, lista.size)
    }
    .toList
    .sortBy(_._2)
    .reverse



  val status = data
    .flatMap(elem => elem.get("status"))
    .groupBy(identity)
    .map {
      case (keyword, lista) => (keyword, lista.size)
    }
    .toList
    .sortBy(_._2)
    .reverse

  println(status)


  val director = data
    .flatMap(elem => elem.get("director"))
    .groupBy(identity)
    .map {
      case (keyword, lista) => (keyword, lista.size)
    }
    .toList
    .sortBy(_._2)
    .reverse

  println(director)


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

  println(productionCountries)


*/

































}


