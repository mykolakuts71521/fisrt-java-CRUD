# Java CRUD - Mykola (71521)

Simple Spring Boot Java CRUD web application. Tasks are kept in memory (restarting resets the list).

## Run

1. Open project folder in IntelliJ IDEA.
2. Wait for Maven dependency import to finish (use JDK 17 or newer).
3. Open `src/main/java/com/example/crud/CrudApplication.java`.
4. Click green Run triangle next to `main`.
5. Open `http://localhost:8080/`.
6. Test create task, edit task, Done/Undo, delete task.

If both projects must run **simultaneously on the same computer**, run one with program argument `--server.port=8081`.

## CRUD endpoints

- GET `/api/tasks` - read all tasks
- POST `/api/tasks` with JSON `{"title":"Example","done":false}` - create
- PUT `/api/tasks/1` with same JSON - update
- DELETE `/api/tasks/1` - delete

## Evidence

Save actual screenshots (not mockups) to `doc/`:
- `running-app.png` - web app open locally
- `crud.png` - tasks created / edited
- `startup-console.png` - Spring Boot successful startup logs

Each student owns their own public repository and accepts the other student's PR. Ensure both students' commits are visible in the commit history.
