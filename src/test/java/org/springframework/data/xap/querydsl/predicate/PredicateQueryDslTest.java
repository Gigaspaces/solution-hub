package org.springframework.data.xap.querydsl.predicate;

import com.mysema.query.types.expr.BooleanExpression;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.integration.AbstractRepositoryTest;
import org.springframework.data.xap.model.QPerson;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.*;

/**
 * @author Leonid_Poliakov
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PredicateQueryDslTest extends AbstractRepositoryTest {
    private static final BooleanExpression chrisPredicate = QPerson.person.name.eq(chris.getName());
    private static final BooleanExpression paulPredicate = QPerson.person.name.eq(paul.getName());
    private static final BooleanExpression chrisOrPaulPredicate = chrisPredicate.or(paulPredicate);

    @Autowired
    private PredicatePersonRepository predicateRepository;

    @Test
    public void testFindOneWithPredicate() {
        assertEquals(chris, predicateRepository.findOne(chrisPredicate));
    }

    @Test
    public void testFindAllWithPredicate() {
        assertEquals(
                newHashSet(chris, paul),
                newHashSet(
                        predicateRepository.findAll(chrisOrPaulPredicate)
                )
        );
    }

    @Test
    public void testFindAllSortedWithPredicate() {
        assertEquals(
                newArrayList(paul, chris),
                newArrayList(
                        predicateRepository.findAll(chrisOrPaulPredicate, QPerson.person.id.desc())
                )
        );
    }

    @Test
    public void testFindAllWithPagePredicate() {
        Sort sort = new Sort(Sort.Direction.ASC, QPerson.person.id.toString());
        Pageable paging = new PageRequest(2, 1, sort);
        assertEquals(
                newArrayList(paul),
                newArrayList(
                        predicateRepository.findAll(chrisOrPaulPredicate, paging)
                )
        );
    }

    @Test
    public void testCountPredicate() {
        assertEquals(
                2,
                predicateRepository.count(chrisOrPaulPredicate)
        );
    }

}