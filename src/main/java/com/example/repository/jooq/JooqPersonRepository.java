package com.example.repository.jooq;

import com.example.model.Person;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.*;

@Repository
@ConditionalOnProperty(name = "data-access.jooq.enabled", havingValue = "true")
public class JooqPersonRepository {
    private final DSLContext dsl;

    public JooqPersonRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Person> findAll() {
        return dsl.selectFrom(table("person"))
                .fetch()
                .map(record -> {
                    Person person = new Person();
                    person.setId(record.get("id", Long.class));
                    person.setFirstName(record.get("first_name", String.class));
                    person.setLastName(record.get("last_name", String.class));
                    person.setAge(record.get("age", Integer.class));
                    person.setEmail(record.get("email", String.class));
                    return person;
                });
    }

    public Optional<Person> findById(Long id) {
        return dsl.selectFrom(table("person"))
                .where(field("id").eq(id))
                .fetchOptional()
                .map(record -> {
                    Person person = new Person();
                    person.setId(record.get("id", Long.class));
                    person.setFirstName(record.get("first_name", String.class));
                    person.setLastName(record.get("last_name", String.class));
                    person.setAge(record.get("age", Integer.class));
                    person.setEmail(record.get("email", String.class));
                    return person;
                });
    }

    public Person save(Person person) {
        if (person.getId() == null) {
            dsl.insertInto(table("person"))
                    .columns(
                            field("first_name"),
                            field("last_name"),
                            field("age"),
                            field("email")
                    )
                    .values(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getAge(),
                            person.getEmail()
                    )
                    .execute();
        } else {
            dsl.update(table("person"))
                    .set(field("first_name"), person.getFirstName())
                    .set(field("last_name"), person.getLastName())
                    .set(field("age"), person.getAge())
                    .set(field("email"), person.getEmail())
                    .where(field("id").eq(person.getId()))
                    .execute();
        }
        return person;
    }

    public void deleteById(Long id) {
        dsl.deleteFrom(table("person"))
                .where(field("id").eq(id))
                .execute();
    }
} 