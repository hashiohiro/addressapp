package main.address

import org.scalatestplus.play.PlaySpec
import main.common.PersonName
import main.common.EmailAddress
import main.common.PhoneNumber
import main.framework.Identifier

class ContactSpec extends PlaySpec {
  "Contact" should {
    "ファーストネームが空文字の場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName("", "Hashimoto"), new EmailAddress("test@co.jp"), new PhoneNumber("092-926-5586"), false)
      }
    }
    
    "ファーストネームがnullの場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName(null, "Hashimoto"), new EmailAddress("test@co.jp"), new PhoneNumber("092-926-5586"), false)
      }
    }
    
    "ラストネームが空文字の場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName("Hiroshi", ""), new EmailAddress("test@co.jp"), new PhoneNumber("092-926-5586"), false)
      }
    }
    
    "ラストネームがnullの場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName("Hiroshi", null), new EmailAddress("test@co.jp"), new PhoneNumber("092-926-5586"), false)
      }
    }
    
    "メールアドレスが空文字の場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName("Hiroshi", "Hashimoto"), new EmailAddress(""), new PhoneNumber("092-926-5586"), false)
      }
    }
    
    "メールアドレスがnullの場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName("Hiroshi", "Hashimoto"), new EmailAddress(null), new PhoneNumber("092-926-5586"), false)
      }
    }
    
    "メールアドレスのパターンがマッチしていない場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName("Hiroshi", "Hashimoto"), new EmailAddress("basic"), new PhoneNumber("092-926-5586"), false)
      }
    }
    
    "電話番号が空文字の場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName("Hiroshi", "Hashimoto"), new EmailAddress("test@co.jp"), new PhoneNumber(""), false)
      }
    }
    
    "電話番号がnullの場合は例外を発生させる" in {
      a[IllegalArgumentException] must be thrownBy {
        new Contact(new ContactId(Identifier.create), new PersonName("Hiroshi", "Hashimoto"), new EmailAddress("test@co.jp"), new PhoneNumber(null), false)
      }
    }
  }
}