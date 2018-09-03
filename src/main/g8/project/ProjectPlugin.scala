import sbt._
import sbt.Keys._
import org.scalafmt.sbt.ScalafmtPlugin.autoImport._
import sbt.{AutoPlugin, PluginTrigger}

object ProjectPlugin extends AutoPlugin {

  override def trigger: PluginTrigger = allRequirements

  object autoImport {

    lazy val V = new {
      val fs2            = "0.10.4"
      val log4cats       = "0.1.1"
      val logbackClassic = "1.2.3"
      val freestyleRPC   = "$freesRPCVersion$"
    }
  }

  import autoImport._

  private lazy val logSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      "ch.qos.logback"    % "logback-classic" % V.logbackClassic,
      "io.chrisdavenport" %% "log4cats-core"  % V.log4cats,
      "io.chrisdavenport" %% "log4cats-slf4j" % V.log4cats
    ))


  lazy val rpcProtocolSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq("io.frees" %% "frees-rpc-client-core" % V.freestyleRPC)
  )

  lazy val clientRPCSettings: Seq[Def.Setting[_]] = logSettings ++ Seq(
    libraryDependencies ++= Seq(
      "io.frees" %% "frees-rpc-client-netty" % V.freestyleRPC,
      "io.frees" %% "frees-rpc-client-cache" % V.freestyleRPC
    )
  )

  lazy val serverSettings: Seq[Def.Setting[_]] = logSettings ++ Seq(
    libraryDependencies ++= Seq("io.frees" %% "frees-rpc-server" % V.freestyleRPC))

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      scalaVersion := "2.12.6",
      scalacOptions := Seq(
        "-deprecation",
        "-encoding",
        "UTF-8",
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-unchecked",
        "-Xlint",
        "-Yno-adapted-args",
        "-Ywarn-dead-code",
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard",
        "-Xfuture",
        "-Ywarn-unused-import"
      ),
      scalafmtCheck := true,
      scalafmtOnCompile := true,
      addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
    )
}
