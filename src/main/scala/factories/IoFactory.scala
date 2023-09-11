package factories

import java.io.{BufferedReader, InputStreamReader}
import java.net.HttpURLConnection
import scala.io.Source

object IoFactory {
  def getSource(connection: HttpURLConnection): Source =
    Source.fromInputStream(connection.getInputStream)
}
