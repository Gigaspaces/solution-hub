package org.springframework.data.gigaspaces.integration.xml;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.gigaspaces.integration.BaseRepositoryTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.MethodOrderer.Alphanumeric;

/**
 * Test for repository XML configuration.
 *
 * Repository is declared in RepositoryXmlTest-context.xml
 *
 * <gigaspaces-data:repositories /> element allows you to simply define a base package that Spring scans for you.
 *
 * @author Anna_Babich
 */
@ExtendWith(SpringExtension.class)
@TestMethodOrder(Alphanumeric.class)
@ContextConfiguration
public class RepositoryXmlTest extends BaseRepositoryTest {

}
