package services

import org.joda.time.format.DateTimeFormat

class DummyVerificationService {

  val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd")

  def getCustomerVerification(email: String): Boolean = {
      true
    }
  def getKYCVerification(email: String): Boolean = {
    true
  }
  }
