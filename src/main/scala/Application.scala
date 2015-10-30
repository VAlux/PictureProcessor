import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation.HORIZONTAL
import picture.ImageContainer
import pictureutils.PictureLoader
import slicing.PictureScanner

/**
*  Created by Alvo on 22.10.2015.
*  Main app entry point.
*/
object Application extends App {
  val image = ImageContainer(PictureLoader("/home/alexander/kirby.png").imageFile)
  val scanner = new PictureScanner(image)
  val horizontalIntersections = scanner.getTileRowIntervals(HORIZONTAL, image.height - 1)
  println(horizontalIntersections)
}
