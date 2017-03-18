package main.infra

/**
 * アサーション
 */
object Assert {
  /** 引数が空文字もしくはnullでないことを保証します。 */
  def argumentNotEmpty(str: String) {
    if (str == null || str.trim.isEmpty) {
      throw new IllegalArgumentException("入力値がNullもしくは空文字です")
    }
  }
  
  /** 引数の長さがmin以上、max以下であることを保証します。 */
  def argumentLength(str: String, max: Int, min: Int) {
    val length = str.trim.length
    if (length < min || length > max) {
      throw new IllegalArgumentException("値が規定の入力桁数の範囲外です")
    }
  }
  
  /** 引数の値が正規表現パターンにマッチしていることを保証します。 */
  def argumentMatch(str: String, pattern: String) {
    if (str == null || !str.matches(pattern)) {
      throw new IllegalArgumentException("値が正規表現パターンとマッチしてません")
    }
  }
  
  /** 引数がtrueであることを保証する。 */
  def argumentTrue(bool: Boolean) {
    if (!bool) {
      throw new IllegalArgumentException("値がtrueではありません")
    }
  }
  
  /** 引数がfalseであることを保証する。 */
  def argumentFalse(bool: Boolean) {
    if (bool) {
      throw new IllegalArgumentException("値がfalseではありません")
    }
  }
}