
import com.github.tototoshi.csv._
import java.io.File
import play.api.libs.json._
import requests.Response
import scalikejdbc.{AutoSession, ConnectionPool, DBSession}
import scala.util.{Failure, Success, Try}
import scalikejdbc._





object PoblarBase extends App {
//Modelo Relacional


  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv/movie_dataset.csv"))
  val data = reader.allWithHeaders()
  reader.close()


  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://localhost:3306/taller14", "root", "admin")
  implicit val session: DBSession = AutoSession


  cast.foreach(x =>
    sql"""
   INSERT INTO cast
   VALUES
   (${x})
   """.stripMargin
      .update
      .apply())


}
