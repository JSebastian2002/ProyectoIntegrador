import com.github.tototoshi.csv._
import java.io.File
import play.api.libs.json._
import scala.util.{Failure, Success, Try}


object Main extends App {

  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()


  val presupuesto = data
    .map(elem => (elem("budget"), elem(("title"))))
    .map(elem => Try {
      (elem._1.toInt, elem._2)
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

  println("Budget")
  println("El presupuesto mas alto es: "+maxpre+" \nPelicula: "+titlepremax)
  println("")
  println("El presupuesto mas bajo es: "+minpre+" \nPelicula: "+titlepremin)
  println("")
  println("El promedio del presupuesto de las peliculas es: "+avgpre)


  val popularidad = data
    .map(elem => (elem("popularity"), elem(("title"))))
    .map(elem => Try{
      (elem._1.replaceFirst("(\\d+\\.\\d+).*", "$1").toDouble , elem._2)
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

  println("----------------------------------------------")
  println("Popularity")
  println("La popularidad mas alta es: " + maxpopu + " \nPelicula: " + titlepopumax)
  println("")
  println("La popularidad mas baja es: " + minpopu + " \nPelicula: " + titlepopumin)
  println("")
  println("El promedio de la popularidad de las peliculas es: " + avgpopu)


  val revenue = data
    .map(elem => (elem("revenue"), elem(("title"))))
    .map(elem => Try {
      (elem._1.toLong, elem._2)
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

  println("---------------------------------------------------")
  println("Revenue")
  println("Los ingresos  mas altos es: " + maxrevenue + " \nPelicula: " + titlerevemax)
  println("")
  println("Los ingresos  mas bajos es: " + minrevenue + " \nPelicula: " + titlerevemin)
  println("")
  println("El promedio de los ingresos  de las peliculas es: " + avgpopu)


}

