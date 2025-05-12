package exercise.controller;

import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatIndexOutOfBoundsException;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    void testShow() throws Exception {
        Task task = new Task(10L, faker.lorem().word(), faker.lorem().sentence());
        taskRepository.save(task);

        var result = mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();

        assertThatJson(body).and(
                json -> json.node("id").isEqualTo(task.getId()),
                json -> json.node("title").isEqualTo(task.getTitle())
        );
    }

    @Test
    void testCreate() throws Exception {
        String title = faker.lorem().word();
        String description = faker.lorem().sentence();

        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);

        System.out.println(data);

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        var response = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();

        Task saved = taskRepository.findAll()
                .stream()
                .filter(t -> t.getTitle().equals(title) && t.getDescription().equals(description))
                .findFirst()
                .orElseThrow();

        assertThat(saved.getTitle()).isEqualTo(title);
        assertThat(saved.getDescription()).isEqualTo(description);
    }

    @Test
    void testUpdate() throws Exception {
        // Генерация и сохранение задачи с Instancio и Faker
        Task task = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().sentence())
                .create();

        taskRepository.save(task);

        Map<String, Object> updated = new HashMap<>();
        updated.put("title", "Updated title");
        updated.put("description", "Updated desc");

        var request = put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(updated));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        Task actual = taskRepository.findById(task.getId()).get();
        assertThat(actual.getTitle()).isEqualTo("Updated title");
        assertThat(actual.getDescription()).isEqualTo("Updated desc");
    }

    @Test
    void testDelete() throws Exception {
        Task task = new Task(faker.lorem().word(), faker.lorem().sentence());
        taskRepository.save(task);

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isOk());

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }
    // END
}
