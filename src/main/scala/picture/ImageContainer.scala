package picture

import java.awt.image.BufferedImage
import pictureutils.PictureExaminator

/**
 * Created by Alvo on 22.10.2015.
 *
 */
case class ImageContainer(image: BufferedImage) {
  val examinator = new PictureExaminator(image)
  val width = examinator.width
  val height = examinator.height

  override def toString: String = s"Image :: width[$width] height[$height]"
}
