package de.olexiy.webfluxdemo.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    Person olexiy = new Person(1, "Olexiy", "Sakura");
    Person fiona = new Person(2, "Fiona", "Glenanne");
    Person sam = new Person(3, "Sam", "Axe");
    Person jesse = new Person(3, "Jesse", "Porter");

    @Override
    public Mono<Person> getById(Integer id) {
        return Flux.just(olexiy, fiona, sam, jesse).filter(person -> person.getId() == id)
            .single()
            .onErrorReturn(Person.builder().build());
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(olexiy, fiona, sam, jesse);
    }
}
