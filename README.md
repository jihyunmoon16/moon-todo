# 🧭 Eisenhower Todo App

**A productivity-focused todo application for working students balancing work and study, based on the Eisenhower Matrix.**

---

## 🚀 Features

* Create, update, delete todos
* Mark todos as complete
* Daily/weekly view of tasks
* Pomodoro timer integration to track focus time per task
* Eisenhower Matrix priority classification
* Tag and category system
* Basic statistics and achievement reports
* Email-based login/signup with JWT authentication

---

## 📌 Eisenhower Matrix Priorities

| Quadrant | Description                | Enum | Priority Order |
| -------- | -------------------------- | ---- | -------------- |
| Q2       | Important & Not Urgent     | `Q2` | 1              |
| Q1       | Important & Urgent         | `Q1` | 2              |
| Q3       | Not Important & Urgent     | `Q3` | 3              |
| Q4       | Not Important & Not Urgent | `Q4` | 4              |

Tasks are sorted in the following order: **Q2 → Q1 → Q3 → Q4**

---

## 🛠 Tech Stack

* **Backend**: Java 17, Spring Boot 3, JPA, Spring Security
* **Authentication**: JWT (email login)
* **Database**: MySQL
* **Frontend**: React (planned)
* **Tools**: Lombok, Validation, Postman

---

## 📡 API Overview

### 🔐 Auth

**POST /signup**

```json
{
  "email": "test@example.com",
  "password": "1234",
  "name": "John Doe"
}
```

**POST /login**

```json
{
  "email": "test@example.com",
  "password": "1234"
}
```

Returns JWT token.

---

### ✅ Todos

**GET /todos**

* Optional query param: `date=yyyy-mm-dd`
* Returns sorted list of todos for the user

**POST /todos**

```json
{
  "title": "Submit assignment",
  "description": "Due tonight",
  "dueDate": "2025-07-14",
  "tag": "Study",
  "priority": "Q1"
}
```

**PATCH /todos/{id}/complete** → Mark as done
**PATCH /todos/{id}/pomodoro?minutes=25** → Add pomodoro time

---

## 📊 Statistics (Planned)

* Completion rate by quadrant
* Time spent per tag
* Daily/weekly focus time graphs

---

## 🧪 How to Test

Use Postman or curl with the JWT token set in the `Authorization` header:

```
Authorization: Bearer {token}
```
