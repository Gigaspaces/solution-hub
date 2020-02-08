# Cassandra Data Source
 
The cassandra dataSource define the java connection configuration.
all connection properties are the same as in the [cassandra driver](https://docs.datastax.com/en/developer/java-driver/4.4/manual/core/)
The spring xml configuration is :

```xml
	<bean id="cassandraDataSource" class="org.openspaces.persistency.cassandra.pool.CassandraDataSourceFactoryBean">
	</bean>
```
* configLoader :
Sets the configuration loader to use. <p>If you don't call this method, the builder will use the default implementation, based on the
Typesafe config library. More precisely, configuration properties are loaded and merged from  the following
(first-listed are higher priority): <ul>
  <li>system properties
  <li>application.conf (all resources on classpath with this name)
  <li>application.json (all resources on classpath with this name)
  <li>application.properties(all resources on classpath with this name)
  <li>reference.conf (all resources on classpath with this name). In particular, this
      will load the reference.conf included in the core driver JAR, that defines 
      default options for all mandatory options.</ul> The resulting configuration is expected to contain a  datastax-java-driver section. This default loader will honor the reload interval defined by the option  basic.config-reload-interval. [Typesafe config's standard loading behavior](https://github.com/typesafehub/config#standard-behavior)
      
* profileName :
* localDatacenter:
Specifies the datacenter that is considered "local" by the load balancing policy.<br><br>
This is a programmatic alternative to the configuration option basic.load-balancing-policy.local-datacenter.
If this method is used, it takes precedence and overrides the configuration.<br><br>
Note that this setting may or may not be relevant depending on the load balancing policy
implementation in use. The driver's built-in DefaultLoadBalancingPolicy relies on it;
if you use a third-party implementation, refer to their documentation.

* applicationName :
The name of the application using the created session. <p>It will be sent in the STARTUP protocol message,
under the key APPLICATION_NAME, for each new connection established by the driver. Currently,
this information is used by Insights monitoring (if the target cluster does not support Insights,
 the entry will be ignored by the server).</p> <p>This can also be defined in the driver configuration with the option  basic.application.name;
if you specify both, this method takes precedence and the  configuration option will be ignored.
If neither is specified, the entry is not included in the message.</p>

* applicationVersion :
The version of the application using the created session.<p>It will be sent in the STARTUP protocol message,
under the key APPLICATION_VERSION, for each new connection established by the driver.
Currently, this information is used by Insights monitoring (if the target cluster does not support Insights,
the entry will be ignored by the server).<p>This can also be defined in the driver configuration with the
option basic.application.version; if you specify both, this method takes precedence and the configuration
option will be ignored. If neither is specified, the entry is not included in the message.

* contactPoints :
contact points to use for the initial connection to the cluster.<p>These are addresses of Cassandra nodes that the driver uses to discover the cluster
 topology. Only one contact point is required (the driver will retrieve the address of the other
  nodes automatically), but it is usually a good idea to provide more than one contact point,
  because if that single contact point is unavailable, the driver cannot initialize itself
  correctly.
  <p>Contact points can also be provided statically in the configuration. If both are specified,
  they will be merged. If both are absent, the driver will default to 127.0.0.1:9042.
  <p>Contrary to the configuration, DNS names with multiple A-records will not be handled here.
  If you need that, extract them manually with  java.net.InetAddress#getAllByName(String)
  before calling this method. Similarly, if you need connect addresses to stay unresolved, make
  sure you pass unresolved instances here (see advanced.resolve-contact-points in the
  configuration for more explanations).
 
* typeCodecs:
  Registers additional codecs for custom type mappings.
  typeCodecs neither the individual codecs, nor the vararg array itself, can be null.
  
* nodeStateListener:
  Registers a node state listener to use with the session.<p>If the listener is specified programmatically
  with this method, it overrides the  configuration (that is, the metadata.node-state-listener.class option will be ignored).

* schemaChangeListener:
Registers a schema change listener to use with the session.<p>If the listener is specified programmatically with this method, it overrides the configuration 
(that is, the metadata.schema-change-listener.class option will be ignored).

* requestTracker:
Registers a request tracker to use with the session.<p>If the tracker is specified programmatically with 
this method, it overrides the  configuration (that is, the code request.tracker.class option will be ignored).

* authProvider:
Registers an authentication provider to use with the session. <p>If the provider is specified programmatically with this method, it overrides the
configuration (that is, the advanced.auth-provider.class option will be ignored).

* username:
* password:
Configures the session to use plaintext authentication with the given username and password. <p>This
methods calls withAuthProvider(AuthProvider) to register a special provider implementation.
Therefore calling it overrides the configuration (that is, the  advanced.auth-provider.class
option will be ignored). <p>Note that this approach holds the credentials in clear text in memory,
which makes them  vulnerable to an attacker who is able to perform memory dumps.
If this is not acceptable for  you, consider writing your own AuthProvider implementation
( PlainTextAuthProviderBase is a good starting point), and providing it either with withAuthProvider(AuthProvider)
 or via the configuration ( advanced.auth-provider.class).

* authorizationId:
Configures the session to use DSE plaintext authentication with the given username and
 password, and perform proxy authentication with the given authorization id. <p>This feature is only
 available in Datastax Enterprise. If connecting to Apache Cassandra,  the authorization id will be ignored;
 it is recommended to use withAuthCredentials(String, String) instead.(not set authorizationId) <p>This
 methods calls withAuthProvider(AuthProvider) to register a special provider
 implementation. Therefore calling it overrides the configuration
 (that is, the  advanced.auth-provider.class option will be ignored). <p>Note that this approach holds the credentials
 in clear text in memory, which makes them vulnerable to an attacker who is able to perform memory dumps.
 If this is not acceptable for you, consider writing your own AuthProvider implementation (the internal
  class  PlainTextAuthProviderBase is a good starting point), and providing it either with 
  withAuthProvider(AuthProvider) or via the configuration ( advanced.auth-provider.class).
  
* sslEngineFactory:
Registers an SSL engine factory for the session. <p>If the factory is provided programmatically with this
method, it overrides the configuration  (that is, the advanced.ssl-engine-factory option will be ignored).
see ProgrammaticSslEngineFactory
   
* sslContext:
Configures the session to use SSL with the given context. <p>This is a convenience method for clients that
 already have an SSLContext instance. It wraps its argument into a ProgrammaticSslEngineFactory, and passes
 it to withSslEngineFactory(SslEngineFactory). <p>If you use this method, there is no way to customize
 cipher suites, or turn on host name validation. If you need finer control,
 use withSslEngineFactory(SslEngineFactory)
 directly and pass either your own implementation of SslEngineFactory, or a ProgrammaticSslEngineFactory
 created with custom cipher suites and/or host name validation. <p>Also, note that SSL engines will be
 created with advisory peer information ( SSLContext#createSSLEngine(String, int)) whenever possible.
 
 * keyspaceName:
 Sets the keyspace to connect the session to. <p>Note that this can also be provided by the configuration;
 if both are defined, this method takes precedence.