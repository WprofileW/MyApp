import sttp.client4.quick._

object testHttp {

  def main(args: Array[String]): Unit = {
    val json = ujson.Obj(
      "username" -> "admin",
      "password" -> "admin"
    )

    val response = quickRequest
      // .post(uri"http://localhost:9988/dashboard/user/login")
      .get(uri"http://localhost:9988/dashboard/user/getUserInfo")
      .header("Content-Type", "application/json")
      .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsidXNlcklkIjoxLCJ1c2VybmFtZSI6ImFkbWluIn0sImV4cCI6MTcwNzI3Nzc3N30.cclbRJN07PfOFiGIj7if4hBQ6VxwhWW5Ylvbpnm6g68")
      // .body(ujson.write(json))
      .send()

    println(response.code)
    println(response.body)
  }
}
