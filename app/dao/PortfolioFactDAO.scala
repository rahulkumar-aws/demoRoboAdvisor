package dao

import com.google.inject.Inject
import models.{Customer, PortfolioAmount, PortfolioFact}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class PortfolioFactDAO @Inject()(dbConfigProvider: DatabaseConfigProvider)(
  implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._

  private val portfolioFacts = TableQuery[PortfolioFactTable]
   //TODO
  //def allPortfolio(): Future[Seq[PortfolioFact]] = db.run(portfolioFacts.result)

  def getPortfolio(email: String, pType: String ): Future[Option[PortfolioFact]] = {
    db.run(portfolioFacts.filter(d => d.email === email && d.pType === pType ).result.headOption)
  }

  def addPortfolio(custPort: PortfolioFact): Future[PortfolioFact] = {
    db.run(portfolioFacts += custPort).map(_ => custPort)
  }

  def updatePortfolio(portfolioFact: PortfolioFact): Future[PortfolioFact] = {
    db.run(portfolioFacts.filter( p => {p.email === portfolioFact.email && p.pType=== portfolioFact.pType}).update(portfolioFact)).map(_ => portfolioFact)
  }

  private class PortfolioFactTable(tag: Tag) extends Table[PortfolioFact](tag, "portfoliofact") {

    def email = column[String]("email")
    def pId = column[String]("pId", O.Unique)
    def pType = column[String]("pType")
    def amount = column[Double]("amount")

    def * = (email, pId, pType, amount) <> (PortfolioFact.tupled, PortfolioFact.unapply)
    def pk = primaryKey("pk_portfoliofact", (email, pType))
  }
}
