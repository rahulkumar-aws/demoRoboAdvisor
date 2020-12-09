package dao

import com.google.inject.Inject
import models.RoboCustomer
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class CustomerDAO @Inject()(dbConfigProvider: DatabaseConfigProvider)(
  implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private val customers = TableQuery[CustomerTable]

  def all(): Future[Seq[RoboCustomer]] = db.run(customers.result)

  def get(email: String): Future[Option[RoboCustomer]] = {
    db.run(customers.filter(_.email === email).result.headOption)
  }

  def insert(cust: RoboCustomer): Future[RoboCustomer] = {
    db.run(customers += cust).map(_ => cust)
  }

  def update(cust: RoboCustomer): Future[RoboCustomer] = {
    db.run(customers.filter(_.email === cust.email).update(cust)).map(_ => cust)
  }

  def delete(email: String): Future[Unit] = {
    db.run(customers.filter(_.email === email).delete).map(_ => ())
  }

  private class CustomerTable(tag: Tag) extends Table[RoboCustomer](tag, "customer") {

    def email = column[String]("email", O.PrimaryKey)
    def name = column[String]("name")
    def dob = column[String]("dob")
    def country = column[String]("country")
    def isVerified = column[Boolean]("isVerified")
    def isKYC = column[Boolean]("isKYC")
    def confidence_level = column[String]("confidence_level")

    def * = (email, name, dob, country, isVerified, isKYC, confidence_level) <> ((RoboCustomer.apply _).tupled, RoboCustomer.unapply)
  }
}
