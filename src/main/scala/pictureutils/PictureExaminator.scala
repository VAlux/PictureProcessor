package pictureutils

import java.awt.Color
import java.awt.image.BufferedImage

import scala.util.Try

/**
*  Created by Alvo on 22.10.2015.
*  Access to the needed pre-calculated picture data.
*/
class PictureExaminator(private val picture: BufferedImage) {

  def width: Int = picture.getWidth
  def height: Int = picture.getHeight

  /**
   * List of image's color pixels as Integers.
   */
  val pixels: Vector[Int] = picture.getRGB(0, 0, width, height, null, 0, width).toVector

  /**
   * Image's clustered pixels in a form of Map(Color -> amount of pixels with such color)
   */
  private lazy val clusteredPixels: Map[Int, Int] = pixels.groupBy(pixel => pixel) map {
    case(colorValue, entriesList) => colorValue -> entriesList.size
  }

  /**
   * Extract full image's color palette from clustered pixels data as a set of Color objects.
   */
  lazy val palette: Iterable[Color] = clusteredPixels.values map (colorValue => new Color(colorValue))

  /**
   * Extract picture's most used color as a Byte value.
   */
  private lazy val dominantColorByte: Int = clusteredPixels.maxBy {
    case(colorValue, entriesAmount) => entriesAmount
  }._1

  /**
   * Extract picture's most used color as a Color object.
   * @return Picture's dominant color as a Color object.
   */
  lazy val dominantColor: Color = new Color(dominantColorByte)

  /**
   * Get dominant color as RBB Integer value.
   * @return DC as Int RGB.
   */
  def dominantColorRGB: Int = dominantColor.getRGB

  def pixelsAmount: Int = pixels.size
}