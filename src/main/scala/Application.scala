import javafx.geometry.Orientation.HORIZONTAL

import picture.ImageContainer
import pictureutils.PictureLoader
import slicing.PictureScanner

/**
*  Created by Alvo on 22.10.2015.
*  Main app entry point.
*/
object Application extends App {
  val image = ImageContainer(PictureLoader("F:/kirby2.png").imageFile)
  val scanner = new PictureScanner(image)
  val horizontalIntersections = scanner.scan(HORIZONTAL, image.height - 1)
  println(horizontalIntersections)
}
