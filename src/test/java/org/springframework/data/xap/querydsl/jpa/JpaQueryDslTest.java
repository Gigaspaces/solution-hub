package org.springframework.data.xap.querydsl.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.xap.integration.AbstractRepositoryTest;
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
public class JpaQueryDslTest extends AbstractRepositoryTest {
    private static final BooleanExpression crisPredicate = QPerson.person.name.eq(cris.getName());
    private static final BooleanExpression paulPredicate = QPerson.person.name.eq(paul.getName());
    private static final BooleanExpression crisOrPaulPredicate = crisPredicate.or(paulPredicate);

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFindOneWithPredicate() {
        assertEquals(
                cris,
                new JPAQuery(entityManager)
                        .from(QPerson.person)
                        .where(crisPredicate)
                        .uniqueResult(QPerson.person)
        );
    }

    @Test
    public void testFindTwoWithPredicate() {
        assertEquals(
                newHashSet(cris, paul),
                newHashSet(new JPAQuery(entityManager)
                        .from(QPerson.person)
                        .where(crisOrPaulPredicate)
                        .list(QPerson.person))
        );
    }

}