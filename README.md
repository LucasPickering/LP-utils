# LP-utils
General utility classes I use across my projects.

## Installation
### From Release
1. Download the latest artifact from [releases](https://github.com/LucasPickering/LP-utils/releases)
2. `mvn install:install-file -Dfile=LP-utils-<version>.jar -DgroupId=me.lucaspickering
-DartifactId=LP-utils -Dversion=<version> -Dpackaging=jar -DgeneratePom=true`
3. The library should now be installed in your .m2, ready to be used!

### From Source
1. Clone this repo with `git clone https://github.com/LucasPickering/LP-utils`
2. `cd LP-utils`
3. `mvn install`
4. The library should now be installed in your .m2, ready to be used!

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