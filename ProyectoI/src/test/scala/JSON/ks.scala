package JSON

import com.github.tototoshi.csv.CSVReader
import java.io.File
import play.api.libs.json._
import scala.util.{Failure, Success, Try}
import scala.util.matching.Regex


object ks  extends App {
  val reader = CSVReader.open(new File("//home/frank/Documentos/tercero/Programacion/ProyectoIntegrador/ProyectoI/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

  def replacePattern(original: String): String = {
    var txtOr = original
    val pattern1: Regex = "(\\s\"(.*?)\",)".r
    val pattern2: Regex = "([a-z]\\s\"(.?)\"\\s[A-Z])".r
    val pattern3: Regex = "(:\\s'\"(.*?)',)".r
    for (m <- pattern1.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("'", "-u0027")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    for (m <- pattern2.findAllIn(txtOr)) {
      val textOriginal = m
      val replacementText = m.replace("\"", "-u0022")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    for (m <- pattern3.findAllIn(txtOr)) {
      val textOriginal = m
      val replacementText = m.replace("\"", "-u0022")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr = txtOr.replace("'", "\"")
    txtOr = txtOr.replace("-u0027", "'")
    txtOr = txtOr.replace("-u0022", "\\\"")
    Try(Json.parse(txtOr)) match {
      case Success(_) => txtOr
      case Failure(_) => ""
    }
  }

  val crew = data
    .map(row => row("crew"))
    .map(replacePattern)
    .filter(_ != "")
    .size

  println(crew+2)
}
