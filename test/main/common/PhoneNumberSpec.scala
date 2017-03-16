package main.common

import org.scalatestplus.play.PlaySpec

class PhoneNumberSpec extends PlaySpec {
  "電話番号のパターンがマッチしていない場合は例外を発生させる" in {
    a[IllegalArgumentException] must be thrownBy {
      new PhoneNumber("0929265568")
    }
  } 
  
  "市外局番2桁パターン" in {
    new PhoneNumber("03-1234-1234")
  }
  
  "市外局番3桁パターン" in {
    new PhoneNumber("092-123-1234")
  }
  
  "市外局番4桁パターン" in {
    new PhoneNumber("0924-23-1234")
  }
}