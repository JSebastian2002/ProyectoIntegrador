import com.github.tototoshi.csv._
import java.io.File
import play.api.libs.json._
import scala.util.{Failure, Success, Try}
import scala.util.matching.Regex
import com.cibo.evilplot.plot.{BarChart, PieChart}
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, defaultTheme}
import com.cibo.evilplot._
import com.cibo.evilplot.plot._


object Crew extends App {

  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()

            //Limpieza
  def replacePattern(original: String): String = {
    var txtOr = original
    val pattern: Regex = "(\\s\"(.*?)\",)".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("'", "-u0027")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

  def replacePattern2(original: String): String = {
    var txtOr = original
    val pattern: Regex = "([a-z]\\s\"(.*?)\"\\s*[A-Z])".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("\"", "-u0022")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

  def replacePattern3(original: String): String = {
    var txtOr = original
    val pattern: Regex = "(:\\s'\"(.*?)',)".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("\"", "-u0022")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

  val crew = data
    .map(row => row("crew"))
    .map(replacePattern2)
    .map(replacePattern)
    .map(replacePattern3)
    .map(text => text.replace("'", "\""))
    .map(text => text.replace("-u0027", "'"))
    .map(text => text.replace("-u0022", "\\\""))
    .map(text => Try(Json.parse(text)))
    .filter(_.isSuccess)

     //Frecuencia department
  val departmentall = crew
    .map(_.get)
    .flatMap(_.as[JsArray].value)
    .map(_.as[JsObject])
    .map(jsObj => (jsObj("department").as[String]))
    .groupBy(identity)
    .map {
      case (keyword, lista) => (keyword, lista.size)
    }
    .toList
    .sortBy(_._2)
    .reverse

  println(departmentall)

  //Frecuencia Jobs
  val jobsall = crew
    .map(_.get)
    .flatMap(_.as[JsArray].value)
    .map(_.as[JsObject])
    .map(jsObj => (jsObj("job").as[String]))
    .groupBy(identity)
    .map {
      case (keyword, lista) => (keyword, lista.size)
    }
    .toList
    .sortBy(_._2)
    .reverse

  println(jobsall)
  //Diagrama de Barras  department

  val departmentsg = departmentall.take(5)
    .map(_._2)
    .map(_.toDouble)
  val popularidaddepartments = departmentall.take(5)
    .map(_._1)


  BarChart(departmentsg)
    .title("Departamentos")
    .xAxis(popularidaddepartments)
    .yAxis()
    .frame()
    .yLabel("Popularidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/departments.png"))

  //Diagrama de Barras Jobs

  val jobsg = jobsall.take(5)
    .map(_._2)
    .map(_.toDouble)
  val popularidadjobs = jobsall.take(5)
    .map(_._1)


  BarChart(jobsg)
    .title("Trabajos")
    .xAxis(popularidadjobs)
    .yAxis()
    .frame()
    .yLabel("Popularidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/jobs.png"))

  val genderall = crew
    .map(_.get)
    .flatMap(_.as[JsArray].value)
    .map(_.as[JsObject])
    .map(jsObj => (jsObj("gender").as[Int]))
    .groupBy(identity)
    .map {
      case (keyword, lista) => (keyword, lista.size)
    }
    .toList
    .sortBy(_._2)
    .reverse

  println(genderall)

  //Diagrama Pastel

  val genderpastel = genderall
    .map(elem => (elem._2.toString, elem._2.toDouble))

  PieChart(genderpastel)
    .title("Valores totales de Gender")
    .frame()
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Pastel/genderpastel1.png"))

  //Diagrama de Barras

  val gendergvalue = genderall
    .map(_._2)
    .map(_.toDouble)
  val genderglabel = genderall
    .map(_._1)
    .map(_.toString)


  BarChart(gendergvalue)
    .title("Genero")
    .xAxis(genderglabel)
    .yAxis()
    .frame()
    .yLabel("Frecuencia")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/gender.png"))


  // Grafica Some/None

  val crewsn = data
    .flatMap(row => row.get("crew"))
    .groupBy {
      case "[]" => None
      case (_) => Some

    }
    .map { case (keyword, lista) => (keyword, lista.size) }
    .toList
  println("Crew :" + crewsn)


  val crewvalues = crewsn
    .map(_._2)
    .map(_.toDouble)

  val crewlabel = crewsn
    .map(_._1)
    .map(_.toString)

  BarChart(crewvalues)
    .title(" Crew Some/None ")
    .xAxis(crewlabel)
    .yAxis()
    .frame()
    .yLabel("Cantidad")
    .bottomLegend()
    .render()
    .write(new File("C:\\Users\\agrab\\Documents\\Diagrama Barras/crewsn.png"))

}
