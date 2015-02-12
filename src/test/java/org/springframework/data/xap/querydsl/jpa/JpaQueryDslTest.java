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
    private static final BooleanExpression crisPredicate = QPerson.person.name.eq(chris.getName());
    private static final BooleanExpression paulPredicate = QPerson.person.name.eq(paul.getName());
    private static final BooleanExpression crisOrPaulPredicate = crisPredicate.or(paulPredicate);

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFindOneWithPredicate() {
        //TODO clean test data up
        personService.delete("4");
        personService.delete("5");

        assertEquals(
                chris,
                new JPAQuery(entityManager)
                        .from(QPerson.person)
                        .where(crisPredicate)
                        .uniqueResult(QPerson.person)
        );
    }

    @Test
    public void testFindTwoWithPredicate() {
        //TODO clean test data up
        personService.delete("4");
        personService.delete("5");
        personService.delete("6");
        assertEquals(
                newHashSet(chris, paul),
                newHashSet(new JPAQuery(entityManager)
                        .from(QPerson.person)
                        .where(crisOrPaulPredicate)
                        .list(QPerson.person))
        );
    }

}