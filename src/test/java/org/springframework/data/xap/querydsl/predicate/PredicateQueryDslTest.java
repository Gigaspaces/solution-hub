package org.springframework.data.xap.querydsl.predicate;

import com.mysema.query.types.Ops;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.ComparableExpression;
import com.mysema.query.types.expr.ComparableOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.xap.model.Person;
import org.springframework.data.xap.model.Team;
import org.springframework.data.xap.model.TeamStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.springframework.data.xap.model.QTeam.team;
import static org.springframework.data.xap.model.TeamStatus.INACTIVE;
import static org.springframework.data.xap.model.TeamStatus.UNKNOWN;

/**
 * @author Leonid_Poliakov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PredicateQueryDslTest {
    private static final Person nick = new Person("1", "Nick", 25);
    private static final Person cris = new Person("2", "Cris", 40);
    private static final Person paul = new Person("3", "Paul", 33);
    private static final Team itspecial = new Team("1", "itspecial", cris, 10, paul, TeamStatus.ACTIVE, currentDay(+1));
    private static final Team avolition = new Team("2", "avolition", nick, 50, null, INACTIVE, currentDay(-1));
    private static final Set<Team> allTeams = newHashSet(itspecial, avolition);

    @Autowired
    private PredicateTeamRepository repository;

    private static Date currentDay(int daysOffset) {
        return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(daysOffset));
    }

    @Before
    public void setUp() {
        repository.save(asList(itspecial, avolition));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testFindAll() {
        assertEquals(
                allTeams,
                unsorted(null)
        );
    }

    @Test
    public void testFindByFields() {
        // single field
        assertEquals(
                avolition,
                one(team.name.eq(avolition.getName()))
        );
        // two fields of one team
        assertEquals(
                avolition,
                one(team.id.eq(avolition.getId()).and(team.name.eq(avolition.getName())))
        );
        // two fields of different teams, and operator
        assertNull(
                one(team.id.eq(itspecial.getId()).and(team.name.eq(avolition.getName())))
        );
        // two fields of different teams, or operator
        assertEquals(
                allTeams,
                unsorted(team.name.eq(itspecial.getName()).or(team.name.eq(avolition.getName())))
        );
    }

    @Test
    public void testFindByBooleanOperations() {
        // not equals
        assertEquals(
                itspecial,
                one(team.id.ne(avolition.getId()))
        );
        // less than
        assertEquals(
                allTeams,
                unsorted(team.membersCount.lt(60))
        );
        // greater than
        assertEquals(
                avolition,
                one(team.membersCount.gt(40))
        );
        // less than or equals
        assertEquals(
                itspecial,
                one(team.membersCount.loe(itspecial.getMembersCount()))
        );
        // greater than or equals
        assertEquals(
                avolition,
                one(team.membersCount.goe(avolition.getMembersCount()))
        );
        // like
        assertEquals(
                avolition,
                one(team.name.like("%tion%"))
        );
        // is null
        assertEquals(
                avolition,
                one(team.sponsor.isNull())
        );
        // is not null
        assertEquals(
                itspecial,
                one(team.sponsor.isNotNull())
        );
        // in
        assertEquals(
                itspecial,
                one(team.membersCount.in(5, 10, 15))
        );
        // not in
        assertEquals(
                avolition,
                one(team.membersCount.notIn(5, 10, 15))
        );
        // between
        assertEquals(
                allTeams,
                unsorted(team.membersCount.between(5, 55))
        );
    }

    @Test
    public void testFindByEmbeddedFields() {
        // leader name equals
        assertEquals(
                avolition,
                one(team.leader.name.eq(avolition.getLeader().getName()))
        );
        // sponsor name not equals (test if team with sponsor will be found)
        assertEquals(
                itspecial,
                one(team.sponsor.name.ne("whoami"))
        );
        // sponsor name not equals (test if team with null sponsor is ignored)
        assertNull(
                one(team.sponsor.name.ne(itspecial.getSponsor().getName()))
        );
    }

    @Test
    public void testFindByEnum() {
        // enum equals
        assertEquals(
                avolition,
                one(team.status.eq(INACTIVE))
        );
        // enum in
        assertEquals(
                avolition,
                one(team.status.in(INACTIVE, UNKNOWN))
        );
        // enum not equals
        assertEquals(
                allTeams,
                unsorted(team.status.ne(UNKNOWN))
        );
        // enum not null
        assertEquals(
                allTeams,
                unsorted(team.status.isNotNull())
        );
    }

    @Test
    public void testCount() {
        // count all
        assertEquals(
                allTeams.size(),
                repository.count(team.status.isNotNull())
        );
        // count by name
        assertEquals(
                1,
                repository.count(team.name.eq(avolition.getName()))
        );
    }

    @Test
    public void testFindAndSort() {
        // sort by name
        assertEquals(
                asList(avolition, itspecial),
                sorted(null, team.name.asc())
        );
        // sort by id desc
        assertEquals(
                asList(avolition, itspecial),
                sorted(null, team.id.desc())
        );
        // sort by embedded field (nulls first)
        assertEquals(
                asList(avolition, itspecial),
                sorted(null, team.sponsor.name.desc())
        );
    }

    @Test
    public void testPagination() {
        // sort by name
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        // select pages of size 1 with sort
        Page<Team> firstPage = repository.findAll(null, new PageRequest(0, 1, sort));
        Page<Team> secondPage = repository.findAll(null, new PageRequest(1, 1, sort));
        Page<Team> thirdPage = repository.findAll(null, new PageRequest(2, 1, sort));
        // check pages
        assertEquals(asList(avolition), newArrayList(firstPage));
        assertEquals(asList(itspecial), newArrayList(secondPage));
        assertEquals(0, thirdPage.getSize());

        // paging without sort
        assertEquals(allTeams, newHashSet(repository.findAll(null, new PageRequest(0, 2))));
    }

    @Test
    public void testOperationPrecedence() {
        // expected: select A1 and A2 or B1 and B2 = {A,B}
        // query must result in "name = itspecial and id = 1 or name = avolition and id = 2"
        BooleanExpression itspecialClauses = team.name.eq(itspecial.getName()).and(team.id.eq(itspecial.getId()));
        BooleanExpression avolitionClauses = team.name.eq(avolition.getName()).and(team.id.eq(avolition.getId()));
        assertEquals(
                allTeams,
                unsorted(itspecialClauses.or(avolitionClauses))
        );

        // expected: select (A1 or B1) and (A2 or B2) = {A,B}
        // query must result in "(name = itspecial or name = avolition) and (id = 1 or id = 2)"
        BooleanExpression nameClauses = team.name.eq(itspecial.getName()).or(team.name.eq(avolition.getName()));
        BooleanExpression idClauses = team.id.eq(itspecial.getId()).or(team.id.eq(avolition.getId()));
        assertEquals(
                allTeams,
                unsorted(nameClauses.and(idClauses))
        );
    }


    // Negative tests for unsupported Query DSL operators

    @Test
    public void testFindWithSysdate() {
        // before sysdate
        assertEquals(
                avolition,
                one(team.creationDate.lt(sysdate()))
        );

        // after sysdate
        assertEquals(
                itspecial,
                one(team.creationDate.gt(sysdate()))
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContains() {
        one(team.name.contains("avoli"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testContainsIgnoreCase() {
        one(team.name.containsIgnoreCase("avoli"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEndsWith() {
        one(team.name.endsWith("tion"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEndsWithIgnoreCase() {
        one(team.name.endsWithIgnoreCase("tion"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testStartsWith() {
        one(team.name.startsWith("avoli"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testStartsWithIgnoreCase() {
        one(team.name.startsWithIgnoreCase("avoli"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsEmpty() {
        one(team.name.isEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testIsNotEmpty() {
        one(team.name.isNotEmpty());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMatches() {
        one(team.name.matches(".*tion"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNotBetween() {
        one(team.membersCount.notBetween(5, 55));
    }

    private Team one(Predicate predicate) {
        return repository.findOne(predicate);
    }

    private List<Team> sorted(Predicate predicate, OrderSpecifier<?>... orders) {
        return newArrayList(repository.findAll(predicate, orders));
    }

    private Set<Team> unsorted(Predicate predicate) {
        return newHashSet(repository.findAll(predicate));
    }

    private ComparableExpression<Date> sysdate() {
        return ComparableOperation.create(Date.class, Ops.DateTimeOps.SYSDATE);
    }

}