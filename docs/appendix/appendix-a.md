## <a name="appendix-a"/>Appendix A: Supported Query Keywords

The following table lists the keywords is supported by the Spring Data Gigaspaces Repository query derivation mechanism:

Logical keyword | Keyword expressions
--- | ---
AND | And
OR | Or
AFTER | After, IsAfter
BEFORE | Before, IsBefore
CONTAINING | Containing, IsContaining, Contains
BETWEEN | Between, IsBetween
ENDING_WITH | EndingWith, IsEndingWith, EndsWith
FALSE | False, IsFalse
GREATER_THAN | GreaterThan, IsGreaterThan
GREATER_THAN_EQUALS | GreaterThanEqual, IsGreaterThanEqual
IN | In, IsIn
IS | Is, Equals, (or no keyword)
IS_NOT_NULL | NotNull, IsNotNull
IS_NULL | Null, IsNull
LESS_THAN | LessThan, IsLessThan
LESS_THAN_EQUAL | LessThanEqual, IsLessThanEqual
LIKE | Like, IsLike
NOT | Not, IsNot
NOT_IN | NotIn, IsNotIn
NOT_LIKE | NotLike, IsNotLike
REGEX | Regex, MatchesRegex, Matches
STARTING_WITH | StartingWith, IsStartingWith, StartsWith
TRUE | True, IsTrue

Next keywords are not supported in Gigaspaces Repositories:

Logical keyword | Keyword expressions
--- | ---
EXISTS | Exists
NEAR | Near, IsNear
WITHIN | Within, IsWithin
