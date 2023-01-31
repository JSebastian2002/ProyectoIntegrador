import com.github.tototoshi.csv._
import play.api.libs.json
import scala.io.Source
import java.io.File
import play.api.libs.json._
import requests.Response
import scalikejdbc.{AutoSession, ConnectionPool, DBSession}
import scala.util.{Failure, Success, Try}
import scala.util.matching.Regex
import scalikejdbc._
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
      .flatMap(elem => elem.get("budget"))
      .map(_.toInt)


    val alto = presupuesto.max

    val bajo = presupuesto.filter(_ != 0).min

    val promediopre = presupuesto.sum / presupuesto.length
    val promediopresin0 = presupuesto.sum / presupuesto.filter(_ != 0).length

    val peliculaalto = data.find(elem => elem("budget").toInt == alto)
    val nombrepeliculalto = peliculaalto.get("title")


    println("El presupuesto mas alto es: " + alto)
    println("El presupuesto mas bajo es: " + bajo)
    println("El promedio contando los 0 es: " + promediopre)
    println("El promedio sin contar el 0 es: " + promediopresin0)
    println("El nombre de la pelicula con el presupuesto mas alto es: " + nombrepeliculalto)
    println("")
    println("-------------------------------------------------------------")

  val id = data
    .flatMap(elem => elem.get("id"))
    .map(_.toInt)


  val altoid = id.max

  val bajoid = id.filter(_ != 0).min

  val promedioid = id.sum / id.length
  val promedioidsin0 = id.sum / id.filter(_ != 0).length

  val idaltopelicula = data.find(elem => elem("id").toInt == altoid)
  val nombrepeliculaltoid = idaltopelicula.get("title")


  println("La id mas alta es: " + altoid)
  println("La id mas baja es: " + bajoid)
  println("El promedio contando los 0 es: " + promedioid)
  println("El promedio sin contar el 0 es: " + promedioidsin0)
  println("El nombre de la pelicula con el id mas alto es: " + nombrepeliculaltoid)
  println("")
  println("-------------------------------------------------------------")


  val popularidad = data
    .flatMap(elem => elem.get("popularity"))
    .map(_.replaceFirst("\\..*", ""))
    .map(_.toDouble)


  val altopopu = popularidad.max

  val bajopopu = popularidad.filter(_ != 0).min

  val promediopopu = popularidad.sum / popularidad.length
  val promediopopuin0 = popularidad.sum / popularidad.filter(_ != 0).length

  val peliculapopular = data.find(elem => elem("popularity").toDouble == altopopu)
  val nombrepeliculapopular = peliculapopular.get("title")


  println("La pelocula mas popular es: " + altopopu)
  println("El presupuesto mas bajo es: " + bajopopu)
  println("El promedio contando los 0 es: " + promediopopu)
  println("El promedio sin contar el 0 es: " + promediopopuin0)
  println("El nombre de la pelicula con el presupuesto mas alto es: " +nombrepeliculapopular)
  println("")
  println("-------------------------------------------------------------")



  val revenue = data
    .flatMap(elem => elem.get("revenue"))
    .map(elemm => Try {
      elemm.toDouble
    })
    .filter(_.isSuccess)
    .map(_.get)


  val altorenevue = revenue.max

  val bajorenevue = revenue.filter(_ != 0).min

  val promediorene = revenue.sum / revenue.length
  val promediorenesin0 = revenue.sum / revenue.filter(_ != 0).length

  val peliculaaltorene = data.find(elem => elem("revenue").toLong == altorenevue)
  val nombrepeliculaltorene = peliculaaltorene.get("title")


  println("El renevue mas alto es: " + altorenevue)
  println("El renevue mas bajo es: " + bajorenevue)
  println("El promedio contando los 0 es: " + promediorene)
  println("El promedio sin contar el 0 es: " + promediorenesin0)
  println("El nombre de la pelicula con el renevue mas alto es: " +promediorenesin0 )
  println("")
  println("-------------------------------------------------------------")



  val runtime = data
    .flatMap(elem => elem.get("runtime"))
    .map(elemm => Try {
      elemm.toDouble
    })
    .filter(_.isSuccess)
    .map(_.get)



  val altorun = runtime.max

  val bajorun = runtime.filter(_ != 0).min

  val promediorun = runtime.sum / runtime.length
  val promediorunsin0 = runtime.sum / runtime.filter(_ != 0).length

  val peliculaltorun = data.find(elem => elem("runtime").toDouble == altorun)
  val nombrepeliculaltorun = peliculaltorun.get("title")


  println("El runtime mas alto es: " + altorun)
  println("El presupuesto mas bajo es: " + bajorun)
  println("El promedio contando los 0 es: " + promediorun)
  println("El promedio sin contar el 0 es: " + promediorunsin0)
  println("El nombre de la pelicula con el presupuesto mas alto es: " + nombrepeliculaltorun)
  println("")
  println("-------------------------------------------------------------")


  val voteaverage = data
    .flatMap(elem => elem.get("vote_average"))
    .map(elemm => Try {
      elemm.toDouble
    })
    .filter(_.isSuccess)
    .map(_.get)


  val altoavg = voteaverage.max

  val bajoavg = voteaverage.filter(_ != 0).min

  val promedioavg = voteaverage.sum / voteaverage.length
  val promedioavgsin0 = voteaverage.sum / voteaverage.filter(_ != 0).length

  val peliculaltoavg = data.find(elem => elem("vote_average").toDouble == altoavg)
  val nombrepeliculaltoavg = peliculaltoavg.get("title")


  println("El voteaverage mas alto es: " + altoavg)
  println("El voteaverage mas bajo es: " + bajoavg)
  println("El promedio contando los 0 es: " + promedioavg)
  println("El promedio sin contar el 0 es: " + promedioavgsin0)
  println("El nombre de la pelicula con el voteaverage mas alto es: " + nombrepeliculaltoavg)
  println("")
  println("-------------------------------------------------------------")


}


