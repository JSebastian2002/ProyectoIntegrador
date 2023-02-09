import com.github.tototoshi.csv._
import java.io.File
import play.api.libs.json._
import requests.Response
import scalikejdbc.{AutoSession, ConnectionPool, DBSession}
import scala.util.{Failure, Success, Try}
import scalikejdbc._
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.util.matching.Regex

object PoblarBase extends App {

  def actorsNames(dataRaw: String): Option[String] = {
    val response: Response = requests
      .post("http://api.meaningcloud.com/topics-2.0",
        data = Map("key" -> "a1e3dd531085b28b1ecccff737ad5b6c",
          "lang" -> "en",
          "txt" -> dataRaw,
          "tt" -> "e"),
        headers = Map("content-type" -> "application/x-www-form-urlencoded"))
    Thread.sleep(1000)
    if (response.statusCode == 200) {
      Option(response.text)
    } else
      Option.empty
  }


  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()


  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://localhost:3306/pfr", "root", "admin")
  implicit val session: DBSession = AutoSession

  //Poblar mediante SCRIPT
  //Tabla director
  case class Directorm(director: String)

  val directorData = data
    .map(row => Directorm(
      row("director")))


  val SQL_INSERT_PATTERN =
    """INSERT INTO director ('NOMBRE´)
      |VALUES
      |('%s') ;
      |""".stripMargin


  val scripData = directorData
    .map(movie => SQL_INSERT_PATTERN.formatLocal(java.util.Locale.US,
      escapeMysql(movie.director)
    ))


  val scriptFile = new File("C:\\Users\\agrab\\OneDrive\\Documentos\\Repositorio\\ProyectoIntegrador\\m/scrip.sql")
  if (scriptFile.exists()) scriptFile.delete()
  scripData.foreach(insert => Files.write(Paths.get("C:\\Users\\agrab\\OneDrive\\Documentos\\Repositorio\\ProyectoIntegrador\\m/scrip.sql"), insert.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND)
  )

  //Tabla Pelicula

  case class Pelicula(id: String,
                      originalTitle: String,
                      title: String,
                      originallanguage: String,
                      genres: String,
                      tagline: String,
                      runtime: Double,
                      popularity: Long,
                      budget: Long,
                      revenue: Long,
                      status: String,
                      voteaverage: Double,
                      votecocount: Int,
                      homepage: String,
                      keywords: String,
                      overview: String,
                      release_date: String)


  val peliculaData = data
    .map(row => Pelicula(
      row("id"),
      row("original_title"),
      row("title"),
      row("original_language"),
      row("genres"),
      row("tagline"),
      row("runtime") match {
        case valueofRT if valueofRT.trim.isEmpty => 0.0
        case valueofRT => valueofRT.toDouble
      },
      row("popularity").replace(".", "").toLong,
      row("budget").toLong,
      row("revenue").toLong,
      row("status"),
      row("vote_average").toDouble,
      row("vote_count").toInt,
      row("homepage"),
      row("keywords"),
      row("overview"),
      row("release_date")))


  val SQL_INSERT_PATTERN2 =
    """INSERT INTO pelicula (`ID_P`, `ORIGINAL_TITLE`, `TITLE`, `ORIGINAL_LANGUAGE`,
      |`GENRES`, `TAGLINE`, `RUNTIME`,`POPULARITY`, `BUDGET`, `REVENUE`,`STATUS`,
      |`VOTEAVERAGE`, `VOTECOUNT`,`HOMEPAGE`, `KEYWORDS`, `OVERVIEW`,`RELEASE_DATE`)
      |VALUES
      |('%s', '%s', '%s', '%s', '%s', '%s', %f, %d, %d, %d, '%s', %f, %d, '%s', '%s', '%s', '%s' ) ;
      |""".stripMargin

  val scripData1 = peliculaData
    .map(peliculadata => SQL_INSERT_PATTERN2.formatLocal(java.util.Locale.US,
      peliculadata.id,
      escapeMysql(peliculadata.originalTitle),
      escapeMysql(peliculadata.title),
      escapeMysql(peliculadata.originallanguage),
      escapeMysql(peliculadata.genres),
      escapeMysql(peliculadata.tagline),
      peliculadata.runtime,
      peliculadata.popularity,
      peliculadata.budget,
      peliculadata.revenue,
      escapeMysql(peliculadata.status),
      peliculadata.voteaverage,
      peliculadata.votecocount,
      escapeMysql(peliculadata.homepage),
      escapeMysql(peliculadata.keywords),
      escapeMysql(peliculadata.overview),
      escapeMysql(peliculadata.release_date)))


  val scriptFile1 = new File("C:\\Users\\agrab\\OneDrive\\Documentos\\Repositorio\\ProyectoIntegrador\\m/scripelicula.sql")
  if (scriptFile1.exists()) scriptFile1.delete()
  scripData1.foreach(insert => Files.write(Paths.get("C:\\Users\\agrab\\OneDrive\\Documentos\\Repositorio\\ProyectoIntegrador\\m/scripelicula.sql"), insert.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND)
  )


  // POBLAR MEDIANTE LIBRERIA
  // Tabla productioncompanies
  val productionCompdata = data
    .flatMap(elem => elem.get("production_companies"))
    .map(Json.parse)
    .flatMap(m => m.as[List[JsValue]])
    .map(m => (m("id").as[Int], m("name").as[String]))
    .distinct

  productionCompdata.foreach(x =>
    sql"""
         INSERT INTO productioncompanies(ID_PCP,NOMBRES)
         VALUES
         (${x._1},${x._2})
         """
      .stripMargin
      .update
      .apply())


  //Tabla Crew
  val crew = data
    .flatMap(row => row.get("crew"))
    .map(replacePattern2)
    .map(replacePattern)
    .map(replacePattern3)
    .map(text => text.replace("'", "\""))
    .map(text => text.replace("-u0027", "'"))
    .map(text => text.replace("-u0022", "\\\""))
    .map(text => Try(Json.parse(text)))
    .filter(_.isSuccess)


  val crewData = crew
    .map(_.get)
    .flatMap(_.as[JsArray].value)
    .map(_.as[JsObject])
    .map(jsObj => (jsObj("id").as[Int], jsObj("name").as[String], jsObj("credit_id").as[String]
      , jsObj("gender").as[Int], jsObj("job").as[String], jsObj("department").as[String]))
    .map(m => (m._1, m._2, m._3, m._4, m._5, m._6))
    .distinct
    .toSet


  crewData.foreach(x =>
    sql"""
   INSERT INTO crew(CREW_ID,NOMBRE,CREDIT_ID,GENDER,JOB,DEPARTMENT)
   VALUES
   (${x._1},${x._2},${x._3},${x._4},${x._5},${x._6})
   """
      .stripMargin
      .update
      .apply())

  def escapeMysql(text: String): String = text
    .replaceAll("\\\\", "\\\\\\\\")
    .replaceAll("\b", "\\\\b")
    .replaceAll("\n", "\\\\n")
    .replaceAll("\r", "\\\\r")
    .replaceAll("\t", "\\\\t")
    .replaceAll("\\x1A", "\\\\Z")
    .replaceAll("\\x00", "\\\\0")
    .replaceAll("'", "\\\\\"")

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
}




