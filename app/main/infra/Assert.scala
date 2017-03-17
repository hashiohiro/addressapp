package main.infra

object Assert {
  def argumentNotEmpty(str: String) {
    if (str == null || str.trim.isEmpty) {
      throw new IllegalArgumentException("入力値がNullもしくは空文字です")
    }
  }
  
  def argumentLength(str: String, max: Int, min: Int) {
    val length = str.trim.length
    if (length < min || length > max) {
      throw new IllegalArgumentException("値が規定の入力桁数の範囲外です")
    }
  }
  
  def argumentMatch(str: String, pattern: String) {
    if (str == null || !str.matches(pattern)) {
      throw new IllegalArgumentException("値が正規表現パターンとマッチしてません")
    }
  }
  
  def argumentTrue(bool: Boolean) {
    if (!bool) {
      throw new IllegalArgumentException("値がtrueではありません")
    }
  }
  
  def argumentFalse(bool: Boolean) {
    if (bool) {
      throw new IllegalArgumentException("値がfalseではありません")
    }
  }
}