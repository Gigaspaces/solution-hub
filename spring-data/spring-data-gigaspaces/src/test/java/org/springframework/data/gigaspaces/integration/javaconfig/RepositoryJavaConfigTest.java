package org.springframework.data.gigaspaces.integration.javaconfig;

/**
 * @author Anna_Babich
 */

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.gigaspaces.integration.BaseRepositoryTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.MethodOrderer.Alphanumeric;
/**
 * Tests for creating Repository using JavaConfig.
 */

@TestMethodOrder(Alphanumeric.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,classes={RepositoryContextConfiguration.class})
public class RepositoryJavaConfigTest extends BaseRepositoryTest {
}