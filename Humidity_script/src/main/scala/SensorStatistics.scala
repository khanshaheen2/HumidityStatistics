
import scala.io.Source
import scala.util.Try
import java.io.File
import java.nio.file.Paths


case class Measurement(sensorId : String, humidity: Option[Double])

object SensorStatistics {

  def main(args: Array[String]): Unit = {
    val path = Paths.get(args(0))
    val files: List[File] = path.toFile.listFiles().filter(_.getName.endsWith(".csv")).toList

    val measurements = files.flatMap { file =>
      val lines = Source.fromFile(file).getLines().drop(1)
      lines.map { line =>
        val Array(sensorId, humidity) = line.split(",")
        Measurement(sensorId, Try(humidity.toDouble).toOption)
      }
    }
    val sensorValues = measurements
      .filter(_.sensorId.nonEmpty)
      .groupBy(_.sensorId)
      .mapValues { measurements =>
//        println(s"BEFORE CALCULATION ${measurements}")
        val humidities = measurements.map(_.humidity.getOrElse(Double.NaN))
        if (humidities.nonEmpty) {
          (humidities.min, humidities.sum / humidities.size, humidities.max)
        }
        else {
          (Double.NaN,Double.NaN,Double.NaN)
        }
      }

    println(f"Num of processed files: ${files.length}")
    println(f"Num of processed measurements: ${measurements.length}")
    println(f"Num of failed measurements: ${measurements.count(m=>m.humidity.isEmpty || m.humidity.get.isNaN )}")
    sensorValues
      .toList
      .sortBy(-_._2._2)
      .foreach {
        case (sensorId, (min, avg, max)) =>
          val min_val = if(!min.isNaN) min.toInt.toString else "NaN"
          val avg_val = if(!avg.isNaN) avg.toInt.toString else "NaN"
          val max_val = if(!max.isNaN) max.toInt.toString else "NaN"

          println(f"$sensorId,$min_val,$avg_val,$max_val")


      }
  }


}
