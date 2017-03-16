package main.address

import org.scalatestplus.play.PlaySpec

class ContactRepositorySpec extends PlaySpec {
  
  "ContactRepository" should {
    "Contactを1件取得する" in {
      val result = ContactRepository.get(new ContactId("874B2A37B9C345E8819116003A81B9AE"))
      result.map(_.id.value mustBe("874B2A37B9C345E8819116003A81B9AE"))
      result.map(_.name.firstName mustBe("Akira"))
      result.map(_.name.lastName mustBe("Hashimoto"))
      result.map(_.email.value mustBe("hashimoto.akira@gmail.com"))
      result.map(_.phone.value mustBe("092-926-5568"))
      result.map(_.deleted mustBe(false))
    }
    
    "Contactを全件取得する" in {
      val result = ContactRepository.list
      result.length mustBe > (0)
    }
  }
}