package utils

/**
 * Created by Alvo on 22.10.2015.
 * Some useful methods for logging.
 *
 */
object Logger {
  /**
   * Log the message to the console and return the desired result(null by default)
   * @param message The log message.
   * @param result Result to return.
   * @return Result.
   * @author Alvo.
   */
  def logMessageAndReturnResult[T](message: String, result: T = null): T = {
    println(message)
    result
  }
}
