package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO createContact(@RequestBody ContactCreateDTO createDTO) {
        // Преобразуем DTO в сущность
        Contact contact = new Contact();
        contact.setFirstName(createDTO.getFirstName());
        contact.setLastName(createDTO.getLastName());
        contact.setPhone(createDTO.getPhone());

        // Сохраняем в базе
        Contact savedContact = contactRepository.save(contact);

        // Преобразуем сущность в DTO
        ContactDTO dto = new ContactDTO();
        dto.setId(savedContact.getId());
        dto.setFirstName(savedContact.getFirstName());
        dto.setLastName(savedContact.getLastName());
        dto.setPhone(savedContact.getPhone());
        dto.setCreatedAt(savedContact.getCreatedAt());
        dto.setUpdatedAt(savedContact.getUpdatedAt());

        return dto;
    }
    // END
}
