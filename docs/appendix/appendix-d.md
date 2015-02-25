## <a name="appendix-d"/>Appendix D: Unsupported operations

Although we try to support each and every Spring Data feature, sometimes native implementation is not possible using Space as a data source. Instead of providing workarounds, which are often slow, we decided to mark some features as unsupported, among them are:
#### Using `IgnoreCase`, `Exists`, `IsNear` and `IsWithin` keywords
```java
${unsupported/KeywordsInQuery.java}
```
#### Setting `Sort` to `ignoreCase`
```java
${unsupported/IgnoreCaseInSorting.java}
```
#### Setting any `NullHandling` in `Sort` other than `NATIVE`
```java
${unsupported/NullHandling.java}
```
