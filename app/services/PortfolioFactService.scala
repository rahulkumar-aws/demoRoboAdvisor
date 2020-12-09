package services

import dao.PortfolioFactDAO
import javax.inject.Inject
import models.PortfolioFact

import scala.concurrent.{ExecutionContext, Future}

class PortfolioFactService @Inject()(dao: PortfolioFactDAO)(implicit ex: ExecutionContext) {

  def getPortfolio(email: String, pType: String): Future[Option[PortfolioFact]] = {
    dao.getPortfolio(email, pType)
  }
  def addPortfolio(portfolioFact: PortfolioFact): Future[PortfolioFact] = {
    dao.addPortfolio(portfolioFact)
  }
  def updatePortfolio(portfolioFact: PortfolioFact): Future[PortfolioFact] = {
    dao.updatePortfolio(portfolioFact)
  }
}
