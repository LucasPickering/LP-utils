# lp-jutils
General Java utility classes I use across my projects.

## Installation
### From Release
1. Download the latest artifact from [releases](https://github.com/LucasPickering/lp-jutils/releases)
2. `mvn install:install-file -Dfile=lp-jutils-<version>.jar -DgroupId=me.lucaspickering
-DartifactId=lp-jutils -Dversion=<version> -Dpackaging=jar -DgeneratePom=true`
3. The library should now be installed in your .m2, ready to be used!

### From Source
```
git clone https://github.com/LucasPickering/lp-jutils
cd lp-jutils
mvn install
```
The library should now be installed in your .m2, ready to be used!

## Maven Dependency
Include this in your `pom.xml` to using this library as a dependency. You must install it first
using the above instructions.
```
<dependency>
    <groupId>me.lucaspickering</groupId>
    <artifactId>LP-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```
