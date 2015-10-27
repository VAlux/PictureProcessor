package pictureutils

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import utils.Logger

import scala.util.{Failure, Success, Try}

/**
*  Created by Alvo on 22.10.2015.
*  Contains picture loading/saving functionality.
*/
case class PictureLoader(private val path: String) {

  /**
   * Loaded image file as BufferedImage object.
   */
  lazy val imageFile: BufferedImage = Try(ImageIO.read(new File(path))) match {
    case Success(image) => Logger.logMessageAndReturnResult[BufferedImage]("Image Successfully Loaded", image)
    case Failure(exception) => Logger.logMessageAndReturnResult[BufferedImage](exception.getMessage)
  }

  /**
   * Save the [[imageFile]] with the specified filename.
   * @param fileName new file name.
   */
  def save(fileName: String) = {
    Try(ImageIO.write(imageFile, fileName, new File(path))) match {
      case Success(image) => Logger.logMessageAndReturnResult[Unit]("Image Successfully Saved")
      case Failure(exception) => Logger.logMessageAndReturnResult[Unit](exception.getMessage)
    }
  }
}
