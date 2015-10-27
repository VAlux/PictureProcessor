package slicing

import java.awt.Color
import javafx.geometry.Orientation
import javafx.geometry.Orientation.HORIZONTAL

import picture.ImageContainer

import scala.annotation.tailrec

/**
*  Created by Alvo on 22.10.2015.
*  Main picture scanning functionality.
*/
class PictureScanner(private val image: ImageContainer) {
  
  def scan(orientation: Orientation, scanSize: Int): Iterable[Int] = {
    for(level: Int <- 0 to scanSize) yield {
      if(intersectionsAmount(level, image.examinator.dominantColor, orientation) > 0) level
      else 0
    }
  }

  def processScannedData(data: Iterable[Int]): Iterable[Int] = {
    def detectEdge(level: Int): Boolean = {
      val dataList = data.toList
      (dataList(level) != 0 && dataList(level + 1) == 0) || (dataList(level) == 0 && dataList(level + 1) != 0)
    }

    data map (level => if(detectEdge(level)) level)
  }
  
  private def intersectionsAmount(from: Int, mainColor: Color, orientation: Orientation): Int = {

    @inline
    def equalPredicate(pixel: Int, equal: Boolean): Boolean = {
      if(equal) pixel == image.examinator.dominantColorRGB
      else pixel != image.examinator.dominantColorRGB
    }

    @tailrec
    def count(scanline: List[Int], equal: Boolean, amount: Int = -1): Int = {
      if (scanline.nonEmpty) {
        count(scanline.dropWhile(pixel => equalPredicate(pixel, equal)), !equal, amount + 1)
      } else {
        amount
      }
    }

    orientation match {
      case HORIZONTAL =>
        val start = from * image.width
        val scanline = image.examinator.pixels.slice(start, start + image.width)
        count(scanline, scanline.head == image.examinator.dominantColorRGB)
      case _ => 0
    }
  }  
}