package factories

import java.net.HttpURLConnection

import java.net.URL

object ConnectionFactory {
  def connection(url: String): HttpURLConnection =
    new URL(url).openConnection().asInstanceOf[HttpURLConnection]

}
