package slicing

import java.awt.Color
import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation
import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation.HORIZONTAL
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

  def processScannedData(data: Iterable[Int]): Map[Int, Int] = {

    def detectEdge(level: Int): Int = {
      val dataList = data.toList
      if(dataList(level) != 0 && dataList(level + 1) == 0) -1
      else if(dataList(level) == 0 && dataList(level + 1) != 0) 1
      else 0
    }

    val edges = data map { level =>
      detectEdge(level) match {
        case offset if offset != 0 => level -> (level + offset)
      }
    }

    edges.toMap
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