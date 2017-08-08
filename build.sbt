lazy val commonSettings = Seq(
  name := "SomeProjectName",
  version := "0.1",
  organization := "com.irayd.data",
  scalaVersion := "2.11.8",
  test in assembly := {}
)

lazy val app = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("com.irayd.data.XXX"),
    assemblyJarName in assembly := s"${name.value}-${version.value}_assembly.jar",
    assemblyOption in packageDependency ~= { _.copy(appendContentHash = true) }
  )

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.1" % "provided",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.172" % "provided"
)

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
