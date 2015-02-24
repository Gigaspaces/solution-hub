## <a name="appendix-b"/>Appendix B: Supported Querydsl Methods

Next `Predicate` methods are supported by Spring Data XAP to build up Querydsl queries:
* number comparison: `eq`, `ne`, `lt`, `loe`, `goe`, `between`, `notBetween`
* string comparison: `like`, `matches`, `isEmpty`, `isNotEmpty`, `contains`, `containsIgnoreCase`, `endsWith`, `endsWithIgnoreCase`, `startsWith`, `startsWithIgnoreCase`
* other comparison: `isNull`, `isNotNull`, `in`, `notIn`
* complex queries: `and`, `or`
* embedded fields

> Note that `contains`, `startsWith`, `endWith` and their `...IgnoreCase` equivalents use the `Regular Expression` matches
