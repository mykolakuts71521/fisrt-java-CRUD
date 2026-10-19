package com.example.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
@RequestMapping("/api/tasks")
public class CrudApplication {
    private final ConcurrentHashMap<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

    public record Task(long id, String title, boolean done) {}
    public record TaskRequest(String title, boolean done) {}

    @GetMapping
    public List<Task> list() {
        return tasks.values().stream()
                .sorted(Comparator.comparingLong(Task::id))
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@RequestBody TaskRequest request) {
        validate(request);
        long id = nextId.getAndIncrement();
        Task task = new Task(id, request.title().trim(), request.done());
        tasks.put(id, task);
        return task;
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable long id, @RequestBody TaskRequest request) {
        validate(request);
        if (!tasks.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
        Task updated = new Task(id, request.title().trim(), request.done());
        tasks.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        if (tasks.remove(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
    }

    private void validate(TaskRequest request) {
        if (request == null || request.title() == null || request.title().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }
    }
}
