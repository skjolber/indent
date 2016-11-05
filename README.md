[![Build Status](https://travis-ci.org/skjolber/indent.svg?branch=master)](https://travis-ci.org/skjolber/indent)

# indent
Simple utility for configurable indent / whitespace.

Configuration options:

 * indent count, character
 * unix/windows or no line-break
 * reset-level - drop indent down to zero if whitespace fills the whole display width
 * prepared-level - the number of read-to-go strings for optimal performance

## License
[Apache 2.0]

# Obtain
The project is based on [Maven] and is pending deployment at central Maven repository.

Example dependency config

```xml
<dependency>
    <groupId>com.skjolberg</groupId>
    <artifactId>indent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

# Usage
The simple utility supports

 * Writer
 * StringBuffer
 * StringBuilder

### Initialize

For two spaces
```java
Indent indent = new IndentBuilder().withUnixLinebreak().build();
```

or four spaces
```java
Indent indent = new IndentBuilder().withUnixLinebreak().withSpace(4).build();
```
or for a single tab

```java
Indent indent = new IndentBuilder().withUnixLinebreak().withTab().build();
```

then just append:

```java
StringBuilder builder = ...; // get target
indent.append(builder, 2); // append whitespace, level 2
```

If you prefer indent resets (for improved readability), go with

```java
Indent indent = new IndentBuilder().withResetLevel(32).build();
```

## Performance

### CPU
If requested level is above the prepared-level, the utility is forced to do multiple writes. These writes are done using the optimal combination of prepared whitespace.

### Memory
The utility does not keep references to increasingly long strings; its size is fixed upon construction. There is however no maximum intent level, so in case of no reset-level, it is up to the caller to make sure the resulting output is not exceedingly large.

# History

 - [1.0.0]: Initial version (pending)

[Apache 2.0]:          	http://www.apache.org/licenses/LICENSE-2.0.html
[issue-tracker]:       	https://github.com/skjolber/indent/issues
[Maven]:                http://maven.apache.org/
[1.0.0]:				https://github.com/skjolber/indent/releases/tag/indent-1.0.0