package org.springframework.data.gigaspaces.querydsl.jpa;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.gigaspaces.integration.BaseRepositoryTest;
import org.springframework.data.gigaspaces.model.QPerson;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Your IDE will for sure recompile classes when trying to run this test.
 * Run mvn openjpa:test-enhance to make it work.
 * Person.class in /target must have weird pc... fields
 *
 * @author Leonid_Poliakov
 */
@ExtendWith(SpringExtension.class)
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
                new JPAQueryFactory(entityManager)
                        .from(QPerson.person)
                        .where(chrisIdPredicate)
                        .fetchOne());
    }

    @Test
    public void testFindTwoWithPredicate() {
        assertEquals(
                newHashSet(chris, chris2, chris3, paul, paul2),
                newHashSet(new JPAQueryFactory(entityManager)
                        .from(QPerson.person)
                        .where(chrisOrPaulPredicate)
                        .fetch())
        );
    }
}