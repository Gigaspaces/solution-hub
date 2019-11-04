package org.springframework.data.gigaspaces.querydsl.predicate;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.gigaspaces.model.Person;
import org.springframework.data.gigaspaces.model.Team;
import org.springframework.data.gigaspaces.model.TeamStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.data.gigaspaces.model.QTeam.team;
import static org.springframework.data.gigaspaces.model.TeamStatus.INACTIVE;
import static org.springframework.data.gigaspaces.model.TeamStatus.UNKNOWN;
import static org.springframework.data.gigaspaces.querydsl.QChangeSet.changeSet;
import static org.springframework.data.gigaspaces.querydsl.GigaspacesDslProjection.projection;

/**
 * @author Leonid_Poliakov
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class PredicateQueryDslTest {
    private static final Person nick = new Person("1", "Nick", 25);
    private static final Person chris = new Person("2", "", 40);
    private static final Person paul = new Person("3", "Paul", 33);
    private static final Team itspecial = new Team("1", "itspecial", chris, 10, paul, TeamStatus.ACTIVE, currentDay(+1));
    private static final Team avolition = new Team("2", "avolition", nick, 50, null, INACTIVE, currentDay(-1));
    private static final Set<Team> allTeams = newHashSet(itspecial, avolition);

    @Autowired
    private PredicateTeamRepository repository;

    private static Date currentDay(int daysOffset) {
        return new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(daysOffset));
    }

    @BeforeEach
    public void setUp() {
        repository.saveAll(asList(itspecial, avolition));
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("Test for fin all unsorted")
    @Test
    public void testFindAll() {
        assertEquals(
                allTeams,
                unsorted(null)
        );
    }

    @Test
    public void testFindBySingleField() {
        // single field
        assertEquals(
                avolition,
                one(team.name.eq(avolition.getName())).orElse(null)
        );
    }
    @Test
    public void testFindByTwoFieldofOneTeam() {
        // two fields of one team
        assertEquals(
                avolition,
                one(team.id.eq(avolition.getId()).and(team.name.eq(avolition.getName()))).orElse(null)
        );
    }

    @Test
    public void testFindByTwoFieldOfDifferentTeamsAndOperator() {
        // two fields of different teams, and operator
        assertNull(
                one(team.id.eq(itspecial.getId()).and(team.name.eq(avolition.getName()))).orElse(null)
        );
    }
    @Test
    public void testFindByTwoFieldOfDifferentTeamsOrOperator() {
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
                one(team.id.ne(avolition.getId())).orElse(null)
        );
        // less than
        assertEquals(
                allTeams,
                unsorted(team.membersCount.lt(60))
        );
        // greater than
        assertEquals(
                avolition,
                one(team.membersCount.gt(40)).orElse(null)
        );
        // less than or equals
        assertEquals(
                itspecial,
                one(team.membersCount.loe(itspecial.getMembersCount())).orElse(null)
        );
        // greater than or equals
        assertEquals(
                avolition,
                one(team.membersCount.goe(avolition.getMembersCount())).orElse(null)
        );
        // like
        assertEquals(
                avolition,
                one(team.name.like("%tion%")).orElse(null)
        );
        // is null
        assertEquals(
                avolition,
                one(team.sponsor.isNull()).orElse(null)
        );
        // is not null
        assertEquals(
                itspecial,
                one(team.sponsor.isNotNull()).orElse(null)
        );
        // in
        assertEquals(
                itspecial,
                one(team.membersCount.in(5, 10, 15)).orElse(null)
        );
        // not in
        assertEquals(
                avolition,
                one(team.membersCount.notIn(5, 10, 15)).orElse(null)
        );
        // between
        assertEquals(
                allTeams,
                unsorted(team.membersCount.between(5, 55))
        );
        // not between
        assertEquals(
                itspecial,
                one(team.membersCount.notBetween(40, 60)).orElse(null)
        );
        // matches regex
        assertEquals(
                avolition,
                one(team.name.matches(".*tion")).orElse(null)
        );
        // is empty
        assertEquals(
                itspecial,
                one(team.leader.name.isEmpty()).orElse(null)
        );
        // is not empty
        assertEquals(
                avolition,
                one(team.leader.name.isNotEmpty()).orElse(null)
        );
    }

    @Test
    public void testFindByEmbeddedFields() {
        // leader name equals
        assertEquals(
                avolition,
                one(team.leader.name.eq(avolition.getLeader().getName())).orElse(null)
        );
        // sponsor name not equals (test if team with sponsor will be found)
        assertEquals(
                itspecial,
                one(team.sponsor.name.ne("whoami")).orElse(null)
        );
        // sponsor name not equals (test if team with null sponsor is ignored)
        assertNull(
                one(team.sponsor.name.ne(itspecial.getSponsor().getName())).orElse(null)
        );
    }

    @Test
    public void testFindByEnum() {
        // enum equals
        assertEquals(
                avolition,
                one(team.status.eq(INACTIVE)).orElse(null)
        );
        // enum in
        assertEquals(
                avolition,
                one(team.status.in(INACTIVE, UNKNOWN)).orElse(null)
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
        Sort sort =  Sort.by("name").ascending();
        // select pages of size 1 with sort
        Page<Team> firstPage = repository.findAll(null, PageRequest.of(0, 1, sort));
        Page<Team> secondPage = repository.findAll(null, PageRequest.of(1, 1, sort));
        Page<Team> thirdPage = repository.findAll(null, PageRequest.of(2, 1, sort));
        // check pages
        assertEquals(asList(avolition), newArrayList(firstPage));
        assertEquals(asList(itspecial), newArrayList(secondPage));
        assertEquals(0, thirdPage.getSize());

        // paging without sort
        assertEquals(allTeams, newHashSet(repository.findAll(null, PageRequest.of(0, 2))));
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

    @Test
    public void testFindOneWithProjection() {
        Team foundTeam = repository.findOne(team.name.eq(avolition.getName()), projection(team.name, team.leader.age)).orElse(null);
        assertEquals(avolition.getName(), foundTeam.getName());
        assertNull(foundTeam.getId());
        assertNotNull(foundTeam.getLeader());
        assertNull(foundTeam.getLeader().getId());
        assertNotNull(foundTeam.getLeader().getAge());
    }

    @Test
    public void testFindAllWithProjection() {
        Predicate allPredicate = null;
        Set<Team> foundTeams = newHashSet(repository.findAll(allPredicate, projection(team.name)));
        assertEquals(2, foundTeams.size());
        for (Team team : foundTeams) {
            assertNotNull(team.getName());
            assertNull(team.getId());
        }
    }

    @Test
    public void testFindAllWithOrderAndProjection() {
        Predicate allPredicate = null;
        List<Team> foundTeams = newArrayList(repository.findAll(allPredicate, projection(team.name), team.name.asc()));
        assertEquals(2, foundTeams.size());
        for (Team team : foundTeams) {
            assertNotNull(team.getName());
            assertNull(team.getId());
        }
        assertEquals(avolition.getName(), foundTeams.get(0).getName());
        assertEquals(itspecial.getName(), foundTeams.get(1).getName());
    }

    @Test
    public void testFindAllWithPagingAndProjection() {
        PageRequest page =  PageRequest.of(0, 1, Sort.by( "name").ascending());
        List<Team> foundTeams = newArrayList(repository.findAll(team.name.isNotNull(), page, projection(team.name)));
        assertEquals(1, foundTeams.size());
        assertNotNull(foundTeams.get(0).getName());
        assertNull(foundTeams.get(0).getId());
        assertEquals(avolition.getName(), foundTeams.get(0).getName());
    }

    @Test
    public void testFindWithSysdate() {
        // before sysdate
        assertEquals(
                avolition,
                one(team.creationDate.lt(sysdate())).orElse(null)
        );

        // after sysdate
        assertEquals(
                itspecial,
                one(team.creationDate.gt(sysdate())).orElse(null)
        );
    }

    @Test
    public void testChange() {
        repository.change(
                team.name.eq(avolition.getName()),
                changeSet().increment(team.leader.age, 5).unset(team.status)
        );

        Team updated = repository.findOne(team.name.eq(avolition.getName())).orElse(null);
        assertEquals(avolition.getLeader().getAge() + 5, updated.getLeader().getAge().intValue());
        assertNull(updated.getStatus());
    }

    @Test
    public void testStringComparison() {
        Team weirdo = new Team("3", "weir}DO", null, null, null, TeamStatus.UNKNOWN, null);
        repository.save(weirdo);

        // contains
        assertEquals(
                weirdo,
                one(team.name.contains("r}D")).orElse(null)
        );
        assertEquals(
                weirdo,
                one(team.name.containsIgnoreCase("IR}do")).orElse(null)
        );

        // starts with
        assertEquals(
                avolition,
                one(team.name.startsWith("avo")).orElse(null)
        );
        assertEquals(
                weirdo,
                one(team.name.startsWithIgnoreCase("WeiR}d")).orElse(null)
        );

        // ends with
        assertEquals(
                avolition,
                one(team.name.endsWith("tion")).orElse(null)
        );
        assertEquals(
                weirdo,
                one(team.name.endsWithIgnoreCase("R}do")).orElse(null)
        );
    }

    @Test
    public void testTakeOne() {
        Predicate predicate = team.name.eq(avolition.getName());
        assertEquals(
                avolition,
                repository.takeOne(predicate)
        );
        assertEquals(0, repository.count(predicate));
        assertEquals(1, repository.count());
    }

    @Test
    public void testTakeAll() {
        Predicate predicate = team.name.isNotEmpty();
        assertEquals(
                allTeams,
                newHashSet(repository.takeAll(predicate))
        );
        assertEquals(0, repository.count(predicate));
        assertEquals(0, repository.count());
    }

    @Test
    public void testTakeOneProjection() {
        Predicate predicate = team.name.eq(avolition.getName());
        Team result = repository.takeOne(predicate, projection(team.name));
        assertEquals(avolition.getName(), result.getName());
        assertNull(result.getId());
        assertNull(result.getStatus());

        assertEquals(0, repository.count(predicate));
        assertEquals(1, repository.count());
    }

    @Test
    public void testTakeAllProjection() {
        Predicate predicate = team.name.isNotNull();
        for (Team result : repository.takeAll(predicate, projection(team.name))) {
            assertNotNull(result.getName());
            assertNull(result.getId());
            assertNull(result.getStatus());
        }

        assertEquals(0, repository.count(predicate));
        assertEquals(0, repository.count());
    }

    private Optional<Team> one(Predicate predicate) {
        return repository.findOne(predicate);
    }

    private List<Team> sorted(Predicate predicate, OrderSpecifier<?>... orders) {
        return newArrayList(repository.findAll(predicate, orders));
    }

    private Set<Team> unsorted(Predicate predicate) {
        return newHashSet(repository.findAll(predicate));
    }

    private ComparableExpression<Date> sysdate() {
        return Expressions.dateTimeOperation(Date.class,Ops.DateTimeOps.SYSDATE);
    }

}