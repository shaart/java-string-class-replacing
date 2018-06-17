# java-string-class-replacing
You can't replace `String` (or other `final` classes from package `java.*`) by your implementation.

Your custom class loader will not be able to load class that starts with `java.`.

But for tests it's possible to use PowerMock, for example.
