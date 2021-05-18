val scala3Version = "3.0.1-RC2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Scala3-EventSourcing-Demo",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    scalacOptions ++= Seq("-source", "future")
  )
