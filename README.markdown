

Hex - A simple hex viewer written in Java.

Copyright (C) 2009  Trejkaz, Hex Project

Now accepting contributions for a Mac icon file for this project! ;-)


WHAT IS IT AND WHY
------------------

My day job often involves looking at the internals of various files.
As such, I use a hex viewer quite a lot, but all the free ones seem to
be too limited and don't do the type of annotation I want, and all the
full featured ones seem to cost money.

I don't need a full blown hex editor, I need a hex viewer with full blown
annotation capabilities.  And that is what I am trying to do here.


BUILDING
--------

You'll need a Java build environment.  I'm developing this on Java 5 at
the moment, but I foresee a shift to Java 6 in the not too distant future.
At runtime I will have to support Java 5 still because a certain stylish
computer company has decided we will not get Java 6 on 32-bit hardware.

You'll also need Ant.  All the other dependencies should be bundled.
If something is missing, prod me to fix it.

To build, execute 'ant' in the top directory.

A file you can run will appear in the launcher/build directory.  If you
are a Mac user you will get a proper .app bundle, everyone else will
currently get a .jar file until I have time to do something about that
(pro-tip: contribute!)


WHAT WORKS
----------

You can open files and look around (see CAVEATS below.)

Cursor and mouse input is all done as far as cursor/selection movement
is concerned.  Copy also works, currently it copies the selection as
hexadecimal.  Annotation works but the number of types of annotation
is still a little limiting.


WHAT I AM TRYING TO GET WORKING NOW
-----------------------------------

Next I will be building the catalogue of available annotations while
I test the program under real usage conditions.


CAVEATS
-------

Files over 2G will not work.  Actually the real limit is lower than this.
If you are on Windows and using a 32-bit JRE, the limit will be the
longest block of contiguous memory remaining from the initial 2G limit,
after all DLLs and the JVM heap have already taken their share.

Even if you are on 64-bit, Swing and Java2D dimensions are in 32-bit, so
you will get a negative size exception at some large amount of data.
So beware.

To fix that, I need to write JScrollPane from scratch but instead of
moving a viewport, it has to move a long number of lines.  And I will
have to make the scrollbars appear to be the correct size for small files,
to maintain the illusion.


BEYOND
------

* More data types (the list will grow as I need them.)
* Structs - a hard one but I have some ideas
* *Scriptable* structs (because I want to put a Ruby DSL in here.)

