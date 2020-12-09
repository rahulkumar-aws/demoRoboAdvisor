package services

import java.util.UUID

import dao.CustomerDAO
import javax.inject.Inject
import models.{Customer, RoboCustomer}

import scala.concurrent.{ExecutionContext, Future}

class CustomerService @Inject()(dao: CustomerDAO)(implicit ex: ExecutionContext) {

  def get(email: String): Future[Option[RoboCustomer]] = {
    dao.get(email)
  }

  def getAll(): Future[Seq[RoboCustomer]] = {
    dao.all()
  }
  def create(customer: RoboCustomer): Future[RoboCustomer] = {
    dao.insert(customer)
  }

  def update(customer: RoboCustomer): Future[RoboCustomer] = {
    dao.update(customer)
  }

  def delete(email: String): Future[Unit] = {
    dao.delete(email)
  }
}
