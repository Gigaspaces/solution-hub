#### <a name="lease"/>4.5 Gigaspaces Lease Time

Spring Data Gigaspaces  comes with a support of defining lease time for new objects in the repository. The basic idea behind it is limiting the time an object is reachable in space. To use this feature, you can specify lease time (in any time units) when saving with `save(...)` methods. These overloaded methods will return a special `LeaseContext` object that allows you to track, renew and cancel the lease.

The essential idea behind a lease is fairly simple.
* When creating a resource, the requestor creates the resource with a limited life span.
* The grantor of the resource will then give access for some period of time that is no longer than that requested.
* The period of time that is actually granted is returned to the requestor as part of the Lease object.
* A holder of a lease can request that a Lease be renewed, or cancel the Lease at any time.
* Successfully renewing a lease extends the time period during which the lease is in effect.
* Cancelling the lease drops the lease immediately.

To read more about this feature, please, refer to [Lease Time](http://docs.gigaspaces.com/latest/dev-java/leases-automatic-expiration.html).
