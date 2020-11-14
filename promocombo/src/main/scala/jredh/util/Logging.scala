package jredh.promocombo.util

import scribe.format._
import scribe._
import scribe.Level

trait Logging {
  val logLevel: Level = Level.Info
  val className = this.getClass().getSimpleName().replace("$", "")
  val logger = Logger(className)
    .orphan()
    .withHandler(
      formatter =
        formatter"$date ${string("[")}$levelColored${string("]")} ${green(positionAbbreviated)} - $message$mdc",
      minimumLevel = Some(logLevel)
    )
}
