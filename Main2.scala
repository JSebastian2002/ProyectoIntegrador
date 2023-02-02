import com.github.tototoshi.csv._
import java.io.File
import scala.util.{Failure, Success, Try}


object Main2 extends App {
  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  println(" ")
  val homepage = data.take(4)
    .map(elem => (elem("homepage"), elem(("title"))))
    .map(elem => Try {
      (elem._2, elem._1.toString)
    })
    .filter(f => f.isSuccess)
    .map(_.get)

  println(homepage)

  println("        ")
  val original_language = data
    .flatMap(elem => elem.get("original_language"))
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  println(original_language)
  println(" ")
  val keywoards = data
    .flatMap(elem => elem.get("keywords"))
    .flatMap(elem => elem.split("\\s"))
    .filter(f1 => f1 != "on")
    .filter(f1 => f1 != "of")
    .filter(string => string.trim.nonEmpty)
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse


  println(keywoards)

  println(" ")
  val genres = data
    .flatMap(elem => elem.get("genres"))
    .flatMap(elem => elem.split("\\s"))
    .filter(string => string.trim.nonEmpty)
    .groupBy(identity)
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
    .sortBy(_._2)
    .reverse

  println(genres)


  println(" ")
  val overview = data.take(4)
    .map(elem => (elem("homepage"), elem(("overvierw"))))
    .map(elem => Try {
      (elem._2, elem._1.toString)
    })
    .filter(f => f.isSuccess)
    .map(_.get)

  println(overview)


}
