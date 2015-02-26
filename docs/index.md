Spring Data XAP - Reference Documentation
============================
## Table of Contents
_[Preface](#preface)_

_[Requirements](#requirements)_

_[Reference Documentation](#reference)_

1. [Document Structure](#structure)

2. [Configuration](#configuration)

  2.1 [XAP Support](#support)
  
  * [Connecting to space using XML based metadata](#support-xml)

  * [Connecting to space using Java based metadata](#support-java)

  * [Other commonly used space configurations](#support-space)

  * [Using native write and read operation](#support-usage)

  * [Modeling your data](#support-pojo)
  
  2.2 [XAP Repositories](#repositories)
  
3. [Basic Usage](#basic)

  3.1 [Query methods](#query)
  
  3.2 [Custom methods](#custom)

4. [Advanced Usage](#advanced)

  4.1 [XAP Projection API](#projection)
  
  4.2 [Querydsl Support](#querydsl)
  
  4.3 [XAP Change API](#change)
  
  4.4 [XAP Take Operations](#take)
  
  4.5 [XAP Lease Time](#lease)
  
  4.6 [XAP Transactions](#transaction)

_[Other Resources](#resources)_

_[Appendix](#appendix)_

  - _[Appendix A: Supported Query Keywords](#appendix-a)_

  - _[Appendix B: Supported Querydsl Methods](#appendix-b)_

  - _[Appendix C: Supported Change API methods](#appendix-c)_

  - _[Appendix D: Unsupported operations](#appendix-d)_

${preface.md}
${requirements.md}

## <a name="reference"/>Reference Documentation
${structure.md}

${configuration.md}
${support/support.md}
${repositories/repositories.md}

${basic.md}
${query/query.md}
${custom/custom.md}

${advanced.md}
${projection/projection.md}
${querydsl/querydsl.md}
${change/change.md}
${take/take.md}
${lease/lease.md}
${transaction/transaction.md}

## <a name="resources"/>Other Resources
${resources.md}

## <a name="appendix"/>Appendix
${appendix/appendix-a.md}
${appendix/appendix-b.md}
${appendix/appendix-c.md}
${appendix/appendix-d.md}