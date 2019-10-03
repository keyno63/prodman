package libs

import skinny.http.{HTTP, Request, Response}

object HttpClient {

  def Get(query: Map[String, String])(implicit url: String): Response = {
      val request = Request(url)
      query.foreach{ kv
      => val (k,v) = kv
        request.queryParams(k -> v)
      }
      HTTP.get(request)
    }

}
