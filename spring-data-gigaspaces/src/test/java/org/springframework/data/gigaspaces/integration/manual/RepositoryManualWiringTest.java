package org.springframework.data.gigaspaces.integration.manual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gigaspaces.integration.BaseRepositoryTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.Alphanumeric;

/**
 * Test for manual wiring of custom repository implementations (implementing custom methods and using repository interface at the same time).
 *
 * @author Leonid_Poliakov
 */

@TestMethodOrder(Alphanumeric.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class RepositoryManualWiringTest extends BaseRepositoryTest {
    @Autowired
    private PersonRepositoryExtended repositoryExtended;

    @Test
    public void testCustomMethod() {
        assertEquals("Hello, world!", repositoryExtended.customMethod());
    }
}