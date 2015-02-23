## <a name="appendix-b"/>Appendix B: Supported Querydsl Methods

Next `Predicate` methods are supported by Spring Data XAP to build up Querydsl queries:
* number comparison: `eq`, `ne`, `lt`, `loe`, `goe`, `between`, `notBetween`
* string comparison: `like`, `matches`, `isEmpty`, `isNotEmpty`
* other comparison: `isNull`, `isNotNull`, `in`, `notIn`
* complex queries: `and`, `or`
* embedded fields

Next `Predicate` methods are not supported, use `like` method instead of them:
* `contains`, `containsIgnoreCase`
* `endsWith`, `endsWithIgnoreCase`
* `startsWith`, `startsWithIgnoreCase`
