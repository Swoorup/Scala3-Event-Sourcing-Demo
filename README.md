# Scala3 EventSourcing Demo

A demo app demonstrating Scala 3 features.

This demonstrates the following Scala 3 features:

* [Union Types](https://docs.scala-lang.org/scala3/book/types-union.html)
* [Higher Kinded Types](https://www.baeldung.com/scala/higher-kinded-types)
* [Traits, Given/Usings](https://dotty.epfl.ch/docs/reference/contextual/motivation.html)
* [Subtyping](https://docs.scala-lang.org/tour/variances.html)

## Building

### Requirements

* Scala 3 compiler
* sbt build tool
* Java 11 SDK
* VsCode (Metals extension) to browse through the source code

If you are on MacOSX and have brew installed, you can install the tools via the following:

```shell
brew install scala sbt openjdk@11
```

### Compiling

This is a normal sbt project. You can compile code with `sbt compile`. For more information on the sbt-dotty plugin, see the
[scala3-example-project](https://github.com/scala/scala3-example-project/blob/main/README.md).

### Running

You can run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

## Scala 3 Reading Resources

* [Rock the JVM](https://blog.rockthejvm.com)
* [Cats - Awesome functional library](https://typelevel.org/cats/)
