
# LoC (Lines of Code)

### Description

LoC provides cli to count lines of code in files that match a set of filters.

The filters are set up in a config file, where they're listed one per line.

**Filter types**:

1. Simple
2. Composite - can contain Simple filters and other Composites.

### Usage

1. Prepare a config file: a file that lists various filters - one per line
2. Choose a directory for LoC to look through
3. Run `LoC config.txt root_directory`
4. Get a list of filter-files matches with lines of code counted

### Filter syntax

**Simple** filter conforms the following rule: 
> [space]\*\<prefix>[space]\*\<body>[space]*

A *prefix* is a unique one character id that defines a filter. *Body* part can be used to configure semantics of the filter.

**Composite** filter conforms the following rule: 
> [space]\*\<prefix>[space]\*\([space]\* \(Simple \| Composite\)+ [space]\*)[space]\*



### Examples

What a config file can look like:
```
.java
    .    txt  
>140505040
&(  .css <15000000  )
|( & (.h .c)  &     ( .  hpp .  cpp) .jar )
```
The resulting output might be:
```
Total - 8637 lines in 110 files
---------------
>140505040 - 8637 lines in 110 files
|( &( .h .c) &( .hpp .cpp) .jar ) - 4344 lines in 4 files
.java - 1646 lines in 36 files
.txt - 72 lines in 16 files
```
