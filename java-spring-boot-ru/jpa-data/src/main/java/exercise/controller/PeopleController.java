package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Person;

@RestController
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/people/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN

    @GetMapping("/people")
    public ResponseEntity<List<Person>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var people = personRepository.findAll();
        var result = people.stream().limit(limit).toList();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(people.size()))
                .body(result);
    }

    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        personRepository.save(person);
        return person;
    }

    @DeleteMapping("/people/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        personRepository.deleteById(id);
    }
    // END
}
