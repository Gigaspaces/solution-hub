## <a name="appendix-c"/>Appendix C: Supported Change API methods

Next Change API methods are available while using Querydsl syntax (`QChangeSet` class):
* field: `set`, `unset`
* numeric: `increment`, `decrement`
* collections and maps: `addToCollection`, `addAllToCollection`, `removeFromCollection`, `putInMap`, `removeFromMap`
* lease: `lease`
* custom change: `custom`