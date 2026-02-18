// Shared configuration
const SERVER_URL = "http://localhost:8080";
const token = localStorage.getItem("token");

// --- 1. LOGIN LOGIC ---
function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!email || !password) {
        alert("Please enter both email and password");
        return;
    }

    fetch(`${SERVER_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(response => {
        if (!response.ok) {
   
            return response.json().then(err => { throw new Error(err.message || "Login Failed!") });
        }
        return response.json();
    })
    .then(data => {
        
        localStorage.setItem("token", data.token);
        window.location.href = "todos.html";
    })
    .catch(error => alert(error.message));
}

// --- 2. REGISTER LOGIC ---
function register() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(response => {
        if (response.ok) {
            alert("Registration Successfully, Please Login!");
            window.location.href = "login.html";
        } else {
            return response.json().then(data => {
                throw new Error(data.message || "Registration Failed!");
            });
        }
    })
    .catch(error => alert(error.message));
}

// --- 3. LOAD TODOS (GET) ---
function loadTodos() {
    if (!token) {
        alert("Please Login First!");
        window.location.href = "login.html";
        return;
    }

    fetch(`${SERVER_URL}/todo`, {
        method: "GET",
        headers: { "Authorization": `Bearer ${token}` }
       
    })
    .then(response => {
        if (!response.ok) throw new Error("Failed to load todos");
        return response.json();
    })
    .then((todos) => {
        const todoList = document.getElementById("todo-list");
        todoList.innerHTML = "";

        if (!todos || todos.length === 0) {
            todoList.innerHTML = `<p id="empty-message">No Todos yet. Add one below</p>`;
        } else {
            todos.forEach(todo => {
                todoList.appendChild(createTodoCard(todo));
            });
        }
    })
    .catch(error => {
        console.error(error);
        document.getElementById("todo-list").innerHTML = `<p style="color:red">Failed to load Todos</p>`;
    });
}

// --- 4. ADD TODO (POST) ---
function addTodo() {
    const input = document.getElementById("new-todo");
    const todoText = input.value.trim();

    if (!todoText) return;

    fetch(`${SERVER_URL}/todo/create`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({ title: todoText, isCompleted: false })
    })
    .then(async response => {
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Failed to add todo!");
        }
        
        
        const text = await response.text();
        return text ? JSON.parse(text) : {}; 
    })
    .then((data) => {
        console.log("Added successfully:", data);
        input.value = ""; 
        loadTodos();
    })
    .catch(error => {
        console.error("Add Error:", error);
        alert("Failed to add todo!");
    });
}

// --- 5. UPDATE STATUS (PUT) ---
function updateTodoStatus(todo) {
    fetch(`${SERVER_URL}/todo/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(todo)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text || "Update failed") });
        }
        
        return response.text(); 
    })
    .then(() => {
        console.log("Update Success");
        loadTodos(); 
    })
    .catch(error => alert(error.message));
}

// --- 6. DELETE TODO (DELETE) ---
function deleteTodo(id) {
    fetch(`${SERVER_URL}/todo/${id}`, {
        method: "DELETE",
        headers: { "Authorization": `Bearer ${token}` }
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text || "Delete failed") });
        }
        return response.text();
    })
    .then(() => loadTodos())
    .catch(error => alert(error.message));
}

// --- UI HELPERS ---
function createTodoCard(todo) {
    const card = document.createElement("div");
    card.className = "todo-card";

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.checked = todo.isCompleted;
    checkbox.addEventListener("change", () => {
    
        updateTodoStatus({ ...todo, isCompleted: checkbox.checked });
    });

    const span = document.createElement("span");
    span.textContent = todo.title;
    if (todo.isCompleted) {
        span.style.textDecoration = "line-through";
        span.style.color = "#aaa";
    }

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "X";
    deleteBtn.onclick = () => deleteTodo(todo.id);

    card.appendChild(checkbox);
    card.appendChild(span);
    card.appendChild(deleteBtn);
    return card;
}

// Initialization
document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById("todo-list")) loadTodos();

});
