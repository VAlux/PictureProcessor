package slicing

import java.awt.Color
import picture.ImageContainer
import utils.Orientation
import utils.Orientation.HORIZONTAL

import scala.annotation.tailrec

/**
*  Created by Alvo on 22.10.2015.
*  Main picture scanning functionality.
*/

class PictureScanner(private val image: ImageContainer) {
  
  private def scan(orientation: Orientation, scanSize: Int): Iterable[Int] = {
    for(level: Int <- 0 to scanSize) yield {
      if(intersectionsAmount(level, image.examinator.dominantColor, orientation) > 0) level
      else 0
    }
  }

  private def processScannedData(data: Iterable[Int]): Map[Int, Int] = {

    val dataVector = data.toVector

    @tailrec
    def getEdge(level: Int): Int = {
      if(level + 1 > dataVector.size) 0
      else if(dataVector(level) != 0 && dataVector(level + 1) == 0) level
      else if(dataVector(level) == 0 && dataVector(level + 1) != 0) level
      else getEdge(level + 1)
    }

    def getInterval(level: Int): (Int, Int) = {
      val top = getEdge(level)
      val bottom = getEdge(top + 1)
      top -> bottom
    }

    def endReached(interval: (Int, Int)): Boolean = {
      interval match {
        case (0, 0) | (_, 0) => true
        case _ => false
      }
    }

    @tailrec
    def collectIntervals(level: Int = 0, intervals: Map[Int, Int] = Map()): Map[Int, Int] = {
     val interval = getInterval(level)
      if (!endReached(interval)) collectIntervals(intervals.last._2, intervals + interval)
      else intervals
    }

    collectIntervals()
  }

  def getTileRowIntervals(orientation: Orientation, scanSize: Int) = {
    processScannedData(scan(orientation, scanSize))
  }
  
  private def intersectionsAmount(from: Int, mainColor: Color, orientation: Orientation): Int = {

    @inline
    def equalPredicate(pixel: Int, equal: Boolean): Boolean = {
      if(equal) pixel == image.examinator.dominantColorRGB
      else pixel != image.examinator.dominantColorRGB
    }

    @tailrec
    def count(scanline: Vector[Int], equal: Boolean, amount: Int = -1): Int = {
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