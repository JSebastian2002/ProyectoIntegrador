package Main


import com.github.tototoshi.csv.CSVReader
import java.io.File

object Main extends App {

  val reader = CSVReader.open(new File("C:\\Users\\agrab\\Downloads\\archivo csv\\movie_dataset.csv"))
  val rows: List[(String, String, Int, Double, String)] = reader.allWithHeaders()

  val intColumn = rows.map(_._3) // selecciona la columna con datos enteros
  val floatColumn = rows.map(_._4) // selecciona la columna con datos de punto flotante

  // Calcula la media
  val intMean = intColumn.sum / intColumn.length
  val floatMean = floatColumn.sum / floatColumn.length

  // Calcula la mediana
  val intMedian = if (intColumn.length % 2 == 0) {
    // Si el número de elementos es par, la mediana es la media de los dos elementos del medio
    (intColumn(intColumn.length / 2) + intColumn(intColumn.length / 2 - 1)) / 2.0
  } else {
    // Si el número de elementos es impar, la mediana es el elemento del medio
    intColumn(intColumn.length / 2)
  }
  val floatMedian = if (floatColumn.length % 2 == 0) {
    (floatColumn(floatColumn.length / 2) + floatColumn(floatColumn.length / 2 - 1)) / 2.0
  } else {
    floatColumn(floatColumn.length / 2)
  }

  // Calcula la moda
  val intMode = intColumn.groupBy(identity).mapValues(_.size).maxBy(_._2)._1
  val floatMode = floatColumn.groupBy(identity).mapValues(_.size).maxBy(_._2)._1
}
