package org.springframework.data.xap.querydsl.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.xap.integration.BaseRepositoryTest;
import org.springframework.data.xap.model.QPerson;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.*;

/**
 * @author Leonid_Poliakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JpaQueryDslTest extends BaseRepositoryTest {
    private static final BooleanExpression chrisIdPredicate = QPerson.person.id.eq(chris.getId());
    private static final BooleanExpression chrisPredicate = QPerson.person.name.eq(chris.getName());
    private static final BooleanExpression paulPredicate = QPerson.person.name.eq(paul.getName());
    private static final BooleanExpression chrisOrPaulPredicate = chrisPredicate.or(paulPredicate);

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFindOneWithPredicate() {
        assertEquals(
                chris,
                new JPAQuery(entityManager)
                        .from(QPerson.person)
                        .where(chrisIdPredicate)
                        .uniqueResult(QPerson.person)
        );
    }

    @Test
    public void testFindTwoWithPredicate() {
        assertEquals(
                newHashSet(chris, chris2, chris3, paul, paul2),
                newHashSet(new JPAQuery(entityManager)
                        .from(QPerson.person)
                        .where(chrisOrPaulPredicate)
                        .list(QPerson.person))
        );
    }
}