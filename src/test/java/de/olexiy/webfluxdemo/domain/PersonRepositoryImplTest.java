package de.olexiy.webfluxdemo.domain;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {
    PersonRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PersonRepositoryImpl();
    }

    @Test
    void getByIdBlock() {
        Mono<Person> personMono = repository.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());
    }

    @Test
    void getByIdSubscribe() {
        Mono<Person> personMono = repository.getById(1);

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void getByIdMapFunction() {
        Mono<Person> personMono = repository.getById(1);

        personMono
            .map(Person::getFirstName).subscribe(firstName -> System.out.println(firstName));
    }

    @Test
    void fluxTestBlockFirst() {
        Flux<Person> personFlux = repository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());
    }

    @Test
    void fluxTestSubscribe() {
        Flux<Person> personFlux = repository.findAll();

        personFlux.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void fluxTestToListMono() {
        Flux<Person> personFlux = repository.findAll();
        Mono<List<Person>> personListMono = personFlux.collectList();
        personListMono.subscribe(list -> {
            list.forEach(person -> System.out.println(person));
        });
    }

    @Test
    void fluxTestFindPersonById() {
        Flux<Person> personFlux = repository.findAll();
        final Integer id = 3;
        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).next();
        personMono.subscribe(person -> System.out.println(person.toString()));
    }

    @Test
    void fluxTestFindPersonByIdNotFound() {
        Flux<Person> personFlux = repository.findAll();
        final Integer id = 8;
        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single();
        personMono.doOnError(throwable -> System.out.println("I went boom!!!!"))
            .onErrorReturn(Person.builder().build())
            .subscribe(person -> System.out.println(person.toString()));
    }
}


















