package org.springframework.data.xap.integration.manual;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.xap.integration.BaseRepositoryTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Test for manual wiring of custom repository implementations (implementing custom methods and using repository interface at the same time).
 *
 * @author Leonid_Poliakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RepositoryManualWiringTest extends BaseRepositoryTest {
    @Autowired
    private PersonRepositoryExtended repositoryExtended;

    @Test
    public void testCustomMethod() {
        assertEquals("Hello, world!", repositoryExtended.customMethod());
    }
}