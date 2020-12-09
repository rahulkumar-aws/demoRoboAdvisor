package services

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

class DummyMLService {

  val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd")

  def getConfidenceLevel(dob: String): String = {
    val jodatime = dateFormat.parseDateTime(dob)
    val localDate = LocalDate.now();
    val currYear = localDate.getYear
    val dobYear = jodatime.getYear
    val age = currYear - dobYear
    println("Year ", age)
    age match {
      case age if 18 to 35 contains age => "high"
      case age if 36 to 45 contains age => "medium"
      case _ => "low"
    }
  }
}
