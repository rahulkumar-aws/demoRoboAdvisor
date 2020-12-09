package services

import dao.DepositFactDAO
import javax.inject.Inject
import models.DepositFact

import scala.concurrent.{ExecutionContext, Future}

class DepositFactService @Inject()(dao: DepositFactDAO)(implicit ex: ExecutionContext) {

  def getDeposit(email: String, pType: String): Future[Option[DepositFact]] = {
    dao.getDeposit(email, pType)
  }
  def addDeposit(depositFact: DepositFact): Future[DepositFact] = {
    dao.addDeposit(depositFact)
  }
}
