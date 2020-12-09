package dao

import com.google.inject.Inject
import models.DepositFact
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class DepositFactDAO @Inject()(dbConfigProvider: DatabaseConfigProvider)(
  implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private val depositFacts = TableQuery[DepositFactTable]
  //TODO
  //def allPortfolio(): Future[Seq[PortfolioFact]] = db.run(portfolioFacts.result)

  def getDeposit(email: String, dType: String ): Future[Option[DepositFact]] = {
    db.run(depositFacts.filter(d => d.email === email && d.depositType === dType ).result.headOption)
  }

  def addDeposit(deposit: DepositFact): Future[DepositFact] = {
    db.run(depositFacts += deposit).map(_ => deposit)
  }

  private class DepositFactTable(tag: Tag) extends Table[DepositFact](tag, "depositfact") {

    def email = column[String]("email")
    def depositId = column[String]("depositId", O.PrimaryKey)
    def depositType = column[String]("depositType")
    def portfolioType = column[String]("portfolioType")
    def depositAmount = column[Double]("depositAmount")

    def * = (email, depositId, depositType, portfolioType, depositAmount) <> (DepositFact.tupled, DepositFact.unapply)
  }
}
