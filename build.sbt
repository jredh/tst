lazy val commonSettings = Seq(
  scalaVersion := "2.12.7",
  organization := "jredh",
  version := "0.0.1",
  libraryDependencies ++= dependencies
)

lazy val dependencies = Seq[ModuleID](
  "com.outr" %% "scribe" % "3.0.3",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

lazy val global =
  Project("global", file("."))
    .settings(commonSettings: _*)
    .settings(
      name := "tst-coding-assessment"
    )
    .aggregate(
      grouprate,
      promocombo
    )

val gr = "grouprate"
lazy val grouprate = Project(gr, file(gr))
  .settings(commonSettings: _*)
  .settings(
    name := gr,
    mainClass in (Compile, run) := Some(s"jredh.$gr.BestGroupRateApp")
  )

val pc = "promocombo"
lazy val promocombo = Project(pc, file(pc))
  .settings(commonSettings: _*)
  .settings(
    name := pc,
    mainClass in (Compile, run) := Some(
      s"jredh.$pc.PromoComboApp"
    )
  )
