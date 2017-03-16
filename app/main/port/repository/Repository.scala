package main.port.repository

import main.address.Contact
import main.address.ContactId

trait Repository {
  def get(id: ContactId): Option[Contact]
  def list: List[Contact]
  def insert(contact: Contact): Int
  def update(contact: Contact): Int
  def logicalDelete(id: ContactId): Int
}