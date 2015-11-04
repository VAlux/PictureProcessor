import picture.ImageContainer
import pictureutils.PictureLoader
import slicing.PictureScanner
import utils.Logger
import utils.Orientation.HORIZONTAL

import scala.util.Try

/**
*  Created by Alvo on 22.10.2015.
*  Main app entry point.
*/
object Application extends App {
  val image = Try(PictureLoader(args(0)).imageFile).toOption
  if (image.isDefined) {
    val container = ImageContainer(image.get)
    val scanner = new PictureScanner(container)
    val horizontalIntersections = scanner.getTileRowIntervals(HORIZONTAL, container.height - 1)
    println(horizontalIntersections)
  } else {
    Logger.logMessage("Can't start app without image loaded")
  }
}
