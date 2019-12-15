Spring Data Gigaspaces - Reference Documentation
============================
## Table of Contents
_[Preface](#preface)_

_[Requirements](#requirements)_

_[Reference Documentation](#reference)_

1. [Document Structure](#structure)

2. [Configuration](#configuration)

  2.1 [Gigaspaces Support](#support)
  
  * [Connecting to space using XML based metadata](#support-xml)

  * [Connecting to space using Java based metadata](#support-java)

  * [Other commonly used space configurations](#support-space)

  * [Using native write and read operation](#support-usage)

  * [Modeling your data](#support-pojo)
  
  2.2 [Gigaspaces Repositories](#repositories)

  * [Registering Gigaspaces Repositories using XML-based metadata](#repositories-xml)

  * [Registering Gigaspaces Repositories using Java-based metadata](#repositories-java)

  * [Excluding custom interfaces from the search](#repositories-exclude)

  * [Multi-space configuration](#repositories-multi)

3. [Basic Usage](#basic)

  3.1 [Query Methods](#query)
  
  3.2 [Custom Methods](#custom)

4. [Advanced Usage](#advanced)

  4.1 [Gigaspaces Projection API](#projection)
  
  4.2 [Querydsl Support](#querydsl)
  
  4.3 [Gigaspaces Change API](#change)
  
  4.4 [Gigaspaces Take Operations](#take)
  
  4.5 [Gigaspaces Lease Time](#lease)
  
  4.6 [Gigaspaces Transactions](#transaction)

  4.7 [Gigaspaces Document Storage Support](#document)

_[Other Resources](#resources)_

_[Appendix](#appendix)_

  - _[Appendix A: Supported Query Keywords](#appendix-a)_

  - _[Appendix B: Supported Querydsl Methods](#appendix-b)_

  - _[Appendix C: Supported Change API methods](#appendix-c)_

  - _[Appendix D: Unsupported operations](#appendix-d)_

-------------
${preface.md}
-------------
${requirements.md}
-------------

## <a name="reference"/>Reference Documentation
${structure.md}

-------------------
${configuration.md}
-------------------
${support/support.md}
---------------------
${repositories/repositories.md}

-----------
${basic.md}
-----------------
{% include_relative query/query.md %}
-------------------
${custom/custom.md}

--------------
${advanced.md}
---------------------------
${projection/projection.md}
-----------------------
${querydsl/querydsl.md}
-------------------
${change/change.md}
---------------
${take/take.md}
-----------------
${lease/lease.md}
-----------------------------
${transaction/transaction.md}
-----------------------
${document/document.md}

---------------------------------------
## <a name="resources"/>Other Resources
${resources.md}

-------------------------------
## <a name="appendix"/>Appendix
{% include_relative appendix/appendix-a.md %}
{% include_relative appendix/appendix-b.md %}
{% include_relative appendix/appendix-c.md %}
{% include_relative appendix/appendix-d.md %}