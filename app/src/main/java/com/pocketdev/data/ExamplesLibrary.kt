package com.pocketdev.data

import com.pocketdev.model.ProgrammingLanguage

data class CodeExample(
    val id: String,
    val title: String,
    val description: String,
    val language: ProgrammingLanguage,
    val code: String,
    val category: ExampleCategory
)

enum class ExampleCategory {
    BASICS,
    CONTROL_FLOW,
    FUNCTIONS,
    DATA_STRUCTURES,
    OBJECT_ORIENTED,
    DOM_MANIPULATION,
    ASYNC,
    STYLING,
    FORMS
}

object ExamplesLibrary {

    val allExamples = listOf(
        // Python Examples
        CodeExample(
            id = "py_hello",
            title = "Hello World & Variables",
            description = "Learn the basics of Python: printing output and working with variables",
            language = ProgrammingLanguage.PYTHON,
            category = ExampleCategory.BASICS,
            code = """# Hello World & Variables in Python

# Print a simple message
print("Hello, World!")

# Variables store data
name = "Alice"
age = 20
height = 5.6
is_student = True

# Print variables
print("Name:", name)
print("Age:", age)
print("Height:", height, "feet")
print("Is student?", is_student)

# Variable types
print("\nVariable Types:")
print(f"name is {type(name).__name__}")
print(f"age is {type(age).__name__}")
print(f"height is {type(height).__name__}")

# String formatting
print(f"\n{name} is {age} years old.")
"""
        ),
        
        CodeExample(
            id = "py_loops",
            title = "Loops & Conditionals",
            description = "Learn if statements, for loops, and while loops",
            language = ProgrammingLanguage.PYTHON,
            category = ExampleCategory.CONTROL_FLOW,
            code = """# Loops & Conditionals in Python

# If-elif-else statement
score = 85

if score >= 90:
    grade = "A"
elif score >= 80:
    grade = "B"
elif score >= 70:
    grade = "C"
elif score >= 60:
    grade = "D"
else:
    grade = "F"

print(f"Score: {score}, Grade: {grade}")

# For loop - iterate over a range
print("\nCounting from 1 to 5:")
for i in range(1, 6):
    print(f"  Count: {i}")

# For loop - iterate over a list
fruits = ["apple", "banana", "cherry"]
print("\nFruits:")
for fruit in fruits:
    print(f"  - {fruit}")

# While loop
print("\nCountdown:")
count = 5
while count > 0:
    print(f"  {count}...")
    count -= 1
print("  Blast off!")

# Loop with break and continue
print("\nEven numbers from 1 to 10:")
for num in range(1, 11):
    if num % 2 != 0:
        continue  # Skip odd numbers
    print(f"  {num}", end=" ")
"""
        ),
        
        CodeExample(
            id = "py_functions",
            title = "Functions & Parameters",
            description = "Learn how to define and use functions",
            language = ProgrammingLanguage.PYTHON,
            category = ExampleCategory.FUNCTIONS,
            code = """# Functions & Parameters in Python

# Simple function
def greet():
    print("Hello!")

greet()

# Function with parameters
def greet_person(name):
    print(f"Hello, {name}!")

greet_person("Alice")
greet_person("Bob")

# Function with multiple parameters
def add_numbers(a, b):
    return a + b

result = add_numbers(5, 3)
print(f"\n5 + 3 = {result}")

# Function with default parameters
def greet_with_title(name, title="Mr./Ms."):
    print(f"Hello, {title} {name}!")

greet_with_title("Smith")
greet_with_title("Johnson", "Dr.")

# Function with keyword arguments
def create_profile(name, age, city):
    print(f"\nProfile:")
    print(f"  Name: {name}")
    print(f"  Age: {age}")
    print(f"  City: {city}")

create_profile(age=25, city="New York", name="Alice")

# Lambda (anonymous) function
square = lambda x: x ** 2
print(f"\nSquare of 5: {square(5)}")

# Higher-order function
def apply_operation(numbers, operation):
    return [operation(n) for n in numbers]

numbers = [1, 2, 3, 4, 5]
doubled = apply_operation(numbers, lambda x: x * 2)
print(f"Doubled: {doubled}")
"""
        ),
        
        CodeExample(
            id = "py_lists",
            title = "Lists & Dictionaries",
            description = "Learn to work with lists and dictionaries",
            language = ProgrammingLanguage.PYTHON,
            category = ExampleCategory.DATA_STRUCTURES,
            code = """# Lists & Dictionaries in Python

# Lists - ordered, mutable collections
fruits = ["apple", "banana", "cherry"]
print("Fruits:", fruits)

# Accessing list items
print(f"First fruit: {fruits[0]}")
print(f"Last fruit: {fruits[-1]}")

# Modifying lists
fruits.append("orange")
print(f"After append: {fruits}")

fruits.insert(1, "mango")
print(f"After insert: {fruits}")

fruits.remove("banana")
print(f"After remove: {fruits}")

# List operations
numbers = [3, 1, 4, 1, 5, 9, 2, 6]
print(f"\nNumbers: {numbers}")
print(f"Length: {len(numbers)}")
print(f"Max: {max(numbers)}")
print(f"Min: {min(numbers)}")
print(f"Sum: {sum(numbers)}")
print(f"Sorted: {sorted(numbers)}")

# List slicing
print(f"First 3: {numbers[:3]}")
print(f"Last 3: {numbers[-3:]}")

# Dictionaries - key-value pairs
student = {
    "name": "Alice",
    "age": 20,
    "grade": "A",
    "courses": ["Math", "Science", "History"]
}

print(f"\nStudent: {student}")
print(f"Name: {student['name']}")
print(f"Age: {student.get('age')}")

# Modifying dictionaries
student["email"] = "alice@example.com"
student["age"] = 21
print(f"\nUpdated student: {student}")

# Dictionary methods
print(f"\nKeys: {list(student.keys())}")
print(f"Values: {list(student.values())}")
print(f"Items: {list(student.items())}")

# Iterating over dictionary
print("\nStudent Info:")
for key, value in student.items():
    print(f"  {key}: {value}")
"""
        ),
        
        CodeExample(
            id = "py_classes",
            title = "Classes & Objects",
            description = "Learn object-oriented programming in Python",
            language = ProgrammingLanguage.PYTHON,
            category = ExampleCategory.OBJECT_ORIENTED,
            code = """# Classes & Objects in Python

# Define a class
class Person:
    # Class attribute (shared by all instances)
    species = "Homo sapiens"
    
    # Constructor (initializer)
    def __init__(self, name, age):
        self.name = name  # Instance attribute
        self._age = age   # Convention: "private" attribute
    
    # Instance method
    def introduce(self):
        return f"Hi, I'm {self.name} and I'm {self._age} years old."
    
    # Property getter
    @property
    def age(self):
        return self._age
    
    # Property setter
    @age.setter
    def age(self, value):
        if value >= 0:
            self._age = value
        else:
            raise ValueError("Age cannot be negative")
    
    # String representation
    def __str__(self):
        return f"Person(name='{self.name}', age={self._age})"
    
    def __repr__(self):
        return self.__str__()

# Create objects
alice = Person("Alice", 25)
bob = Person("Bob", 30)

print(alice.introduce())
print(bob.introduce())

# Access attributes
print(f"\nAlice's name: {alice.name}")
print(f"Alice's age: {alice.age}")

# Modify attributes
alice.age = 26
print(f"Updated age: {alice.age}")

# Inheritance
class Student(Person):
    def __init__(self, name, age, student_id, major):
        super().__init__(name, age)
        self.student_id = student_id
        self.major = major
        self.grades = []
    
    def add_grade(self, grade):
        self.grades.append(grade)
    
    def get_average(self):
        if not self.grades:
            return 0
        return sum(self.grades) / len(self.grades)
    
    def introduce(self):  # Method overriding
        base = super().introduce()
        return f"{base} I'm a {self.major} major."

# Create student object
carol = Student("Carol", 20, "S12345", "Computer Science")
print(f"\n{carol.introduce()}")

carol.add_grade(85)
carol.add_grade(92)
carol.add_grade(78)
print(f"Average grade: {carol.get_average():.2f}")

# Check types
print(f"\nIs Carol a Person? {isinstance(carol, Person)}")
print(f"Is Carol a Student? {isinstance(carol, Student)}")
"""
        ),
        
        // JavaScript Examples
        CodeExample(
            id = "js_variables",
            title = "Variables & Data Types",
            description = "Learn JavaScript variables and basic data types",
            language = ProgrammingLanguage.JAVASCRIPT,
            category = ExampleCategory.BASICS,
            code = """// Variables & Data Types in JavaScript

// Variable declarations
let name = "Alice";           // String
const age = 25;               // Number (constant)
var isStudent = true;         // Boolean

// Data types
let greeting = "Hello";       // String
let count = 42;               // Number
let price = 19.99;            // Number (float)
let isActive = false;         // Boolean
let nothing = null;           // Null
let notDefined;               // Undefined
let person = {                // Object
    firstName: "John",
    lastName: "Doe",
    age: 30
};
let numbers = [1, 2, 3, 4, 5]; // Array

// Output values
console.log("=== Variables ===");
console.log("Name:", name);
console.log("Age:", age);
console.log("Is student:", isStudent);

// Check types
console.log("\n=== Types ===");
console.log("typeof greeting:", typeof greeting);
console.log("typeof count:", typeof count);
console.log("typeof isActive:", typeof isActive);
console.log("typeof person:", typeof person);
console.log("typeof numbers:", typeof numbers);
console.log("Array.isArray(numbers):", Array.isArray(numbers));

// String operations
console.log("\n=== String Operations ===");
let message = "Hello, World!";
console.log("Original:", message);
console.log("Length:", message.length);
console.log("Uppercase:", message.toUpperCase());
console.log("Lowercase:", message.toLowerCase());
console.log("Substring:", message.substring(0, 5));
console.log("Replace:", message.replace("World", "JavaScript"));

// Template literals
console.log("\n=== Template Literals ===");
let product = "laptop";
let cost = 999;
console.log(`The ${product} costs $${cost}.`);
console.log(`Next year, I'll be ${age + 1} years old.`);

// Number operations
console.log("\n=== Number Operations ===");
let a = 10;
let b = 3;
console.log(`${a} + ${b} =`, a + b);
console.log(`${a} - ${b} =`, a - b);
console.log(`${a} * ${b} =`, a * b);
console.log(`${a} / ${b} =`, a / b);
console.log(`${a} % ${b} =`, a % b);
console.log(`${a} ** ${b} =`, a ** b);

// Math object
console.log("\n=== Math Operations ===");
console.log("Math.PI:", Math.PI);
console.log("Math.round(4.7):", Math.round(4.7));
console.log("Math.floor(4.7):", Math.floor(4.7));
console.log("Math.ceil(4.2):", Math.ceil(4.2));
console.log("Math.random():", Math.random().toFixed(4));
console.log("Math.max(1, 5, 3):", Math.max(1, 5, 3));
console.log("Math.min(1, 5, 3):", Math.min(1, 5, 3));
"""
        ),
        
        CodeExample(
            id = "js_functions",
            title = "Functions & Arrow Functions",
            description = "Learn different ways to define functions in JavaScript",
            language = ProgrammingLanguage.JAVASCRIPT,
            category = ExampleCategory.FUNCTIONS,
            code = """// Functions & Arrow Functions in JavaScript

// Function declaration
function greet() {
    console.log("Hello!");
}
greet();

// Function with parameters
function greetPerson(name) {
    console.log(`Hello, ${name}!`);
}
greetPerson("Alice");
greetPerson("Bob");

// Function with return value
function add(a, b) {
    return a + b;
}
console.log("\n5 + 3 =", add(5, 3));

// Function expression
const multiply = function(a, b) {
    return a * b;
};
console.log("4 * 7 =", multiply(4, 7));

// Arrow function (ES6+)
const subtract = (a, b) => a - b;
console.log("10 - 4 =", subtract(10, 4));

// Arrow function with block
const divide = (a, b) => {
    if (b === 0) {
        return "Cannot divide by zero";
    }
    return a / b;
};
console.log("20 / 4 =", divide(20, 4));
console.log("20 / 0 =", divide(20, 0));

// Default parameters
function greetWithTitle(name, title = "Mr./Ms.") {
    console.log(`Hello, ${title} ${name}!`);
}
console.log("");
greetWithTitle("Smith");
greetWithTitle("Johnson", "Dr.");

// Rest parameters
function sum(...numbers) {
    return numbers.reduce((total, num) => total + num, 0);
}
console.log("\nSum of 1, 2, 3:", sum(1, 2, 3));
console.log("Sum of 1, 2, 3, 4, 5:", sum(1, 2, 3, 4, 5));

// Higher-order function
function createMultiplier(factor) {
    return function(number) {
        return number * factor;
    };
}
const double = createMultiplier(2);
const triple = createMultiplier(3);
console.log("\nDouble 5:", double(5));
console.log("Triple 5:", triple(5));

// Callback function
function processData(data, callback) {
    const result = data.map(x => x * 2);
    callback(result);
}
processData([1, 2, 3, 4], result => {
    console.log("\nProcessed data:", result);
});

// Immediately Invoked Function Expression (IIFE)
(function() {
    console.log("\nIIFE executed immediately!");
})();

// Recursive function
function factorial(n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
console.log("\n5! =", factorial(5));
"""
        ),
        
        CodeExample(
            id = "js_dom",
            title = "DOM Manipulation",
            description = "Learn to interact with HTML elements using JavaScript",
            language = ProgrammingLanguage.JAVASCRIPT,
            category = ExampleCategory.DOM_MANIPULATION,
            code = """// DOM Manipulation in JavaScript
// Note: This example shows DOM code that would work in a browser

console.log("=== DOM Manipulation Examples ===");
console.log("These examples show how to manipulate HTML elements.");
console.log("Run this code in an HTML file with corresponding elements.");
console.log("");

// Selecting elements
console.log("// Selecting elements:");
console.log(`
// By ID
const header = document.getElementById('header');

// By class name
const items = document.getElementsByClassName('item');

// By tag name
const paragraphs = document.getElementsByTagName('p');

// Query selector (CSS selector)
const firstItem = document.querySelector('.item');
const allItems = document.querySelectorAll('.item');
`);

// Modifying content
console.log("// Modifying content:");
console.log(`
// Change text content
header.textContent = 'New Title';

// Change HTML content
element.innerHTML = '<strong>Bold text</strong>';

// Change input value
input.value = 'New value';
`);

// Modifying styles
console.log("// Modifying styles:");
console.log(`
// Direct style manipulation
element.style.color = 'red';
element.style.backgroundColor = 'yellow';
element.style.fontSize = '20px';
element.style.padding = '10px';

// Add/remove CSS classes
element.classList.add('highlight');
element.classList.remove('hidden');
element.classList.toggle('active');
element.classList.contains('active'); // true/false
`);

// Creating and removing elements
console.log("// Creating and removing elements:");
console.log(`
// Create new element
const newDiv = document.createElement('div');
newDiv.textContent = 'I am new!';
newDiv.className = 'new-element';

// Add to page
document.body.appendChild(newDiv);

// Insert at specific position
parentElement.insertBefore(newElement, referenceElement);

// Remove element
element.remove();
// or
parentElement.removeChild(childElement);
`);

// Event handling
console.log("// Event handling:");
console.log(`
// Add event listener
button.addEventListener('click', function(event) {
    console.log('Button clicked!');
    console.log('Event:', event);
});

// Common events:
// - click, dblclick
// - mouseover, mouseout
// - keydown, keyup, keypress
// - submit, change, input
// - load, resize, scroll

// Remove event listener
button.removeEventListener('click', handler);
`);

// Traversing the DOM
console.log("// Traversing the DOM:");
console.log(`
// Parent and children
const parent = element.parentNode;
const children = element.children;
const firstChild = element.firstElementChild;
const lastChild = element.lastElementChild;

// Siblings
const next = element.nextElementSibling;
const previous = element.previousElementSibling;
`);

// Example: Creating a simple interactive element
console.log("// Example: Interactive counter");
console.log(`
// HTML: <div id="counter">0</div>
//       <button id="increment">+</button>
//       <button id="decrement">-</button>

let count = 0;
const counterDisplay = document.getElementById('counter');

document.getElementById('increment').addEventListener('click', () => {
    count++;
    counterDisplay.textContent = count;
});

document.getElementById('decrement').addEventListener('click', () => {
    count--;
    counterDisplay.textContent = count;
});
`);
"""
        ),
        
        CodeExample(
            id = "js_async",
            title = "Promises & Async/Await",
            description = "Learn asynchronous programming in JavaScript",
            language = ProgrammingLanguage.JAVASCRIPT,
            category = ExampleCategory.ASYNC,
            code = """// Promises & Async/Await in JavaScript

console.log("=== Promises & Async/Await ===\n");

// Creating a Promise
function fetchData(delay, shouldFail = false) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (shouldFail) {
                reject(new Error("Failed to fetch data"));
            } else {
                resolve({ id: 1, name: "Alice", data: "Some data" });
            }
        }, delay);
    });
}

// Using Promises with .then() and .catch()
console.log("1. Using Promises with .then() and .catch():");
fetchData(100)
    .then(data => {
        console.log("  Success:", data);
        return data.name;
    })
    .then(name => {
        console.log("  Name:", name);
    })
    .catch(error => {
        console.error("  Error:", error.message);
    })
    .finally(() => {
        console.log("  Promise completed\n");
    });

// Promise.all - wait for multiple promises
console.log("2. Promise.all - multiple promises:");
const promises = [
    fetchData(100),
    fetchData(150),
    fetchData(200)
];

Promise.all(promises)
    .then(results => {
        console.log("  All results:", results);
    })
    .catch(error => {
        console.error("  One failed:", error.message);
    });

// Async/Await - cleaner syntax
console.log("\n3. Using async/await:");

async function getUserData() {
    try {
        console.log("  Fetching user...");
        const user = await fetchData(100);
        console.log("  User:", user);
        return user;
    } catch (error) {
        console.error("  Error:", error.message);
        throw error;
    }
}

// Call async function
getUserData().then(() => {
    console.log("  Done\n");
});

// Sequential async operations
console.log("4. Sequential async operations:");

async function sequentialFetch() {
    console.log("  Starting sequential fetch...");
    
    const start = Date.now();
    
    const result1 = await fetchData(50);
    console.log("  First:", result1.name);
    
    const result2 = await fetchData(50);
    console.log("  Second:", result2.name);
    
    const result3 = await fetchData(50);
    console.log("  Third:", result3.name);
    
    console.log(`  Total time: ${Date.now() - start}ms\n`);
}

sequentialFetch();

// Parallel async operations
console.log("5. Parallel async operations:");

async function parallelFetch() {
    console.log("  Starting parallel fetch...");
    
    const start = Date.now();
    
    const [result1, result2, result3] = await Promise.all([
        fetchData(50),
        fetchData(50),
        fetchData(50)
    ]);
    
    console.log("  Results:", result1.name, result2.name, result3.name);
    console.log(`  Total time: ${Date.now() - start}ms\n`);
}

parallelFetch();

// Error handling
console.log("6. Error handling:");

async function handleErrors() {
    try {
        console.log("  Trying to fetch with error...");
        await fetchData(50, true); // Will fail
    } catch (error) {
        console.log("  Caught error:", error.message);
    }
    
    try {
        console.log("  Trying to fetch successfully...");
        const data = await fetchData(50);
        console.log("  Success:", data.name);
    } catch (error) {
        console.log("  This won't execute");
    }
}

handleErrors();

// Simulating fetch API
console.log("\n7. Simulating fetch API:");

function mockFetch(url) {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve({
                json: () => Promise.resolve({ 
                    url, 
                    data: "Mock response",
                    timestamp: new Date().toISOString()
                })
            });
        }, 100);
    });
}

async function fetchExample() {
    try {
        const response = await mockFetch("https://api.example.com/data");
        const data = await response.json();
        console.log("  Fetched:", data);
    } catch (error) {
        console.error("  Fetch error:", error.message);
    }
}

fetchExample();
"""
        ),
        
        CodeExample(
            id = "js_fetch",
            title = "Fetch API & AJAX",
            description = "Learn to make HTTP requests with the Fetch API",
            language = ProgrammingLanguage.JAVASCRIPT,
            category = ExampleCategory.ASYNC,
            code = """// Fetch API & AJAX in JavaScript
// Note: These examples use mock functions since we can't make real HTTP requests here

console.log("=== Fetch API & AJAX ===\n");

// Mock fetch for demonstration
function mockFetch(url, options = {}) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            if (url.includes('error')) {
                reject(new Error('Network error'));
                return;
            }
            
            const mockResponse = {
                ok: !url.includes('404'),
                status: url.includes('404') ? 404 : 200,
                statusText: url.includes('404') ? 'Not Found' : 'OK',
                url: url,
                json: () => Promise.resolve({
                    id: 1,
                    title: 'Sample Post',
                    body: 'This is a mock response',
                    userId: 1
                }),
                text: () => Promise.resolve('Plain text response'),
                headers: new Map([
                    ['content-type', 'application/json']
                ])
            };
            
            resolve(mockResponse);
        }, 100);
    });
}

// Basic GET request
console.log("1. Basic GET request:");

async function getData() {
    try {
        const response = await mockFetch('https://api.example.com/posts/1');
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        console.log("  Response:", data);
    } catch (error) {
        console.error("  Error:", error.message);
    }
}

getData();

// POST request with JSON body
console.log("\n2. POST request:");

async function postData() {
    try {
        const postData = {
            title: 'New Post',
            body: 'This is the content',
            userId: 1
        };
        
        const response = await mockFetch('https://api.example.com/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(postData)
        });
        
        const data = await response.json();
        console.log("  Created:", data);
    } catch (error) {
        console.error("  Error:", error.message);
    }
}

postData();

// PUT request (update)
console.log("\n3. PUT request (update):");

async function updateData() {
    try {
        const updateData = {
            id: 1,
            title: 'Updated Post',
            body: 'Updated content'
        };
        
        const response = await mockFetch('https://api.example.com/posts/1', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updateData)
        });
        
        const data = await response.json();
        console.log("  Updated:", data);
    } catch (error) {
        console.error("  Error:", error.message);
    }
}

updateData();

// DELETE request
console.log("\n4. DELETE request:");

async function deleteData() {
    try {
        const response = await mockFetch('https://api.example.com/posts/1', {
            method: 'DELETE'
        });
        
        console.log("  Deleted successfully");
        console.log("  Status:", response.status);
    } catch (error) {
        console.error("  Error:", error.message);
    }
}

deleteData();

// Handling different response types
console.log("\n5. Handling response types:");

async function handleResponses() {
    try {
        const response = await mockFetch('https://api.example.com/data');
        
        // Check content type
        const contentType = response.headers.get('content-type');
        console.log("  Content-Type:", contentType);
        
        // Parse based on content type
        if (contentType && contentType.includes('application/json')) {
            const jsonData = await response.json();
            console.log("  JSON:", jsonData);
        } else {
            const textData = await response.text();
            console.log("  Text:", textData);
        }
    } catch (error) {
        console.error("  Error:", error.message);
    }
}

handleResponses();

// Error handling
console.log("\n6. Error handling:");

async function handleErrors() {
    try {
        const response = await mockFetch('https://api.example.com/404');
        
        if (!response.ok) {
            if (response.status === 404) {
                console.log("  Resource not found");
            } else if (response.status === 500) {
                console.log("  Server error");
            } else {
                console.log(`  HTTP Error: ${response.status}`);
            }
            return;
        }
        
        const data = await response.json();
        console.log("  Data:", data);
    } catch (error) {
        if (error.message === 'Network error') {
            console.log("  Network error - check your connection");
        } else {
            console.error("  Error:", error.message);
        }
    }
}

handleErrors();

// Using with Promise.all for multiple requests
console.log("\n7. Multiple requests with Promise.all:");

async function fetchMultiple() {
    try {
        const urls = [
            'https://api.example.com/posts/1',
            'https://api.example.com/posts/2',
            'https://api.example.com/posts/3'
        ];
        
        const responses = await Promise.all(
            urls.map(url => mockFetch(url))
        );
        
        const data = await Promise.all(
            responses.map(response => response.json())
        );
        
        console.log("  All posts:", data);
    } catch (error) {
        console.error("  Error:", error.message);
    }
}

fetchMultiple();

// AbortController for cancelling requests
console.log("\n8. AbortController (for cancelling requests):");
console.log(`
// Example usage:
const controller = new AbortController();
const signal = controller.signal;

fetch('https://api.example.com/data', { signal })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => {
        if (error.name === 'AbortError') {
            console.log('Request was cancelled');
        }
    });

// Cancel after 5 seconds
setTimeout(() => controller.abort(), 5000);
`);
"""
        ),
        
        // HTML Examples
        CodeExample(
            id = "html_basic",
            title = "Basic HTML Page Structure",
            description = "Learn the fundamental structure of an HTML document",
            language = ProgrammingLanguage.HTML,
            category = ExampleCategory.BASICS,
            code = """<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Character encoding -->
    <meta charset="UTF-8">
    
    <!-- Viewport for responsive design -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Page title (shown in browser tab) -->
    <title>My First Web Page</title>
    
    <!-- Description for search engines -->
    <meta name="description" content="A simple HTML page">
    
    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="/favicon.ico">
    
    <!-- Internal CSS styles -->
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        
        h1 {
            color: #333;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 10px;
        }
        
        p {
            line-height: 1.6;
            color: #666;
        }
        
        .highlight {
            background-color: #fff3cd;
            padding: 10px;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <!-- Main content goes here -->
    <header>
        <h1>Welcome to My Website</h1>
        <nav>
            <a href="#home">Home</a> |
            <a href="#about">About</a> |
            <a href="#contact">Contact</a>
        </nav>
    </header>
    
    <main>
        <section id="home">
            <h2>Home</h2>
            <p>This is the main content area of the page.</p>
            <p class="highlight">
                This paragraph has a special highlight style.
            </p>
        </section>
        
        <section id="about">
            <h2>About</h2>
            <p>Learn more about what we do.</p>
        </section>
        
        <article>
            <h2>Latest News</h2>
            <p>This is an article element.</p>
        </article>
    </main>
    
    <aside>
        <h3>Sidebar</h3>
        <p>Related information goes here.</p>
    </aside>
    
    <footer>
        <p>&copy; 2024 My Website. All rights reserved.</p>
    </footer>
    
    <!-- JavaScript at the end for better performance -->
    <script>
        console.log('Page loaded!');
        
        // Add interactivity
        document.querySelector('h1').addEventListener('click', function() {
            alert('You clicked the heading!');
        });
    </script>
</body>
</html>"""
        ),
        
        CodeExample(
            id = "html_forms",
            title = "Forms & Inputs",
            description = "Learn to create interactive forms with various input types",
            language = ProgrammingLanguage.HTML,
            category = ExampleCategory.FORMS,
            code = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HTML Forms Example</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        
        input, select, textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        
        input[type="radio"],
        input[type="checkbox"] {
            width: auto;
            margin-right: 5px;
        }
        
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        
        button:hover {
            background-color: #45a049;
        }
        
        .inline-label {
            display: inline;
            font-weight: normal;
        }
    </style>
</head>
<body>
    <h1>Registration Form</h1>
    
    <form action="/submit" method="POST">
        <!-- Text input -->
        <div class="form-group">
            <label for="fullname">Full Name:</label>
            <input type="text" id="fullname" name="fullname" 
                   placeholder="Enter your full name" required>
        </div>
        
        <!-- Email input -->
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" 
                   placeholder="your@email.com" required>
        </div>
        
        <!-- Password input -->
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" 
                   minlength="8" required>
        </div>
        
        <!-- Number input -->
        <div class="form-group">
            <label for="age">Age:</label>
            <input type="number" id="age" name="age" 
                   min="13" max="120">
        </div>
        
        <!-- Date input -->
        <div class="form-group">
            <label for="birthdate">Birth Date:</label>
            <input type="date" id="birthdate" name="birthdate">
        </div>
        
        <!-- Select dropdown -->
        <div class="form-group">
            <label for="country">Country:</label>
            <select id="country" name="country">
                <option value="">Select a country</option>
                <option value="us">United States</option>
                <option value="uk">United Kingdom</option>
                <option value="ca">Canada</option>
                <option value="au">Australia</option>
                <option value="other">Other</option>
            </select>
        </div>
        
        <!-- Radio buttons -->
        <div class="form-group">
            <label>Gender:</label>
            <input type="radio" id="male" name="gender" value="male">
            <label for="male" class="inline-label">Male</label><br>
            <input type="radio" id="female" name="gender" value="female">
            <label for="female" class="inline-label">Female</label><br>
            <input type="radio" id="other" name="gender" value="other">
            <label for="other" class="inline-label">Other</label>
        </div>
        
        <!-- Checkboxes -->
        <div class="form-group">
            <label>Interests:</label>
            <input type="checkbox" id="sports" name="interests" value="sports">
            <label for="sports" class="inline-label">Sports</label><br>
            <input type="checkbox" id="music" name="interests" value="music">
            <label for="music" class="inline-label">Music</label><br>
            <input type="checkbox" id="reading" name="interests" value="reading">
            <label for="reading" class="inline-label">Reading</label>
        </div>
        
        <!-- Textarea -->
        <div class="form-group">
            <label for="bio">Bio:</label>
            <textarea id="bio" name="bio" rows="4" 
                      placeholder="Tell us about yourself..."></textarea>
        </div>
        
        <!-- File input -->
        <div class="form-group">
            <label for="avatar">Profile Picture:</label>
            <input type="file" id="avatar" name="avatar" 
                   accept="image/*">
        </div>
        
        <!-- Submit and reset buttons -->
        <div class="form-group">
            <button type="submit">Register</button>
            <button type="reset">Clear</button>
        </div>
    </form>
</body>
</html>"""
        ),
        
        CodeExample(
            id = "html_tables",
            title = "Tables & Lists",
            description = "Learn to create tables and different types of lists",
            language = ProgrammingLanguage.HTML,
            category = ExampleCategory.BASICS,
            code = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tables & Lists</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        
        /* Table styles */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        
        th {
            background-color: #4CAF50;
            color: white;
        }
        
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        
        tr:hover {
            background-color: #ddd;
        }
        
        /* List styles */
        ul, ol {
            margin: 10px 0;
            padding-left: 30px;
        }
        
        li {
            margin: 5px 0;
        }
        
        .styled-list {
            list-style-type: square;
            color: #4CAF50;
        }
        
        .inline-list li {
            display: inline;
            margin-right: 15px;
        }
        
        .custom-bullet li::marker {
            color: #FF5722;
            font-size: 1.2em;
        }
    </style>
</head>
<body>
    <h1>Tables & Lists</h1>
    
    <h2>Data Table</h2>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Age</th>
                <th>City</th>
                <th>Country</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Alice Johnson</td>
                <td>28</td>
                <td>New York</td>
                <td>USA</td>
            </tr>
            <tr>
                <td>Bob Smith</td>
                <td>35</td>
                <td>London</td>
                <td>UK</td>
            </tr>
            <tr>
                <td>Carol White</td>
                <td>22</td>
                <td>Sydney</td>
                <td>Australia</td>
            </tr>
            <tr>
                <td>David Brown</td>
                <td>31</td>
                <td>Toronto</td>
                <td>Canada</td>
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="4">Total employees: 4</td>
            </tr>
        </tfoot>
    </table>
    
    <h2>Product Table with Colspan</h2>
    <table>
        <thead>
            <tr>
                <th>Product</th>
                <th>Category</th>
                <th>Price</th>
                <th>Stock</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Laptop</td>
                <td>Electronics</td>
                <td>$999</td>
                <td>15</td>
            </tr>
            <tr>
                <td>Mouse</td>
                <td>Accessories</td>
                <td>$25</td>
                <td>50</td>
            </tr>
            <tr>
                <td colspan="2"><strong>Total Value</strong></td>
                <td colspan="2"><strong>$16,235</strong></td>
            </tr>
        </tbody>
    </table>
    
    <h2>Unordered List</h2>
    <ul>
        <li>Apples</li>
        <li>Bananas</li>
        <li>Oranges</li>
        <li>Grapes</li>
    </ul>
    
    <h2>Ordered List</h2>
    <ol>
        <li>First step</li>
        <li>Second step</li>
        <li>Third step</li>
        <li>Fourth step</li>
    </ol>
    
    <h2>Nested List</h2>
    <ul>
        <li>Fruits
            <ul>
                <li>Citrus
                    <ul>
                        <li>Orange</li>
                        <li>Lemon</li>
                        <li>Lime</li>
                    </ul>
                </li>
                <li>Berries
                    <ul>
                        <li>Strawberry</li>
                        <li>Blueberry</li>
                        <li>Raspberry</li>
                    </ul>
                </li>
            </ul>
        </li>
        <li>Vegetables
            <ul>
                <li>Leafy greens</li>
                <li>Root vegetables</li>
            </ul>
        </li>
    </ul>
    
    <h2>Styled List</h2>
    <ul class="styled-list">
        <li>Item one</li>
        <li>Item two</li>
        <li>Item three</li>
    </ul>
    
    <h2>Description List</h2>
    <dl>
        <dt>HTML</dt>
        <dd>HyperText Markup Language - the standard markup language for web pages</dd>
        
        <dt>CSS</dt>
        <dd>Cascading Style Sheets - used for styling HTML elements</dd>
        
        <dt>JavaScript</dt>
        <dd>A programming language that enables interactive web pages</dd>
    </dl>
</body>
</html>"""
        ),
        
        CodeExample(
            id = "html_css",
            title = "CSS Styling (Embedded)",
            description = "Learn different ways to apply CSS styles to HTML",
            language = ProgrammingLanguage.HTML,
            category = ExampleCategory.STYLING,
            code = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CSS Styling Example</title>
    <style>
        /* CSS Variables (Custom Properties) */
        :root {
            --primary-color: #4CAF50;
            --secondary-color: #2196F3;
            --accent-color: #FF9800;
            --text-color: #333;
            --bg-color: #f5f5f5;
            --card-bg: white;
            --border-radius: 8px;
            --shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        
        /* Reset and base styles */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--bg-color);
            color: var(--text-color);
            line-height: 1.6;
            padding: 20px;
        }
        
        /* Typography */
        h1 {
            color: var(--primary-color);
            text-align: center;
            margin-bottom: 10px;
        }
        
        h2 {
            color: var(--secondary-color);
            border-bottom: 2px solid var(--secondary-color);
            padding-bottom: 5px;
            margin: 30px 0 15px;
        }
        
        /* Card component */
        .card {
            background: var(--card-bg);
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            padding: 20px;
            margin: 20px 0;
        }
        
        .card-title {
            font-size: 1.25rem;
            color: var(--primary-color);
            margin-bottom: 10px;
        }
        
        .card-content {
            color: #666;
        }
        
        /* Button styles */
        .btn {
            display: inline-block;
            padding: 10px 20px;
            border: none;
            border-radius: var(--border-radius);
            cursor: pointer;
            font-size: 1rem;
            text-decoration: none;
            transition: all 0.3s ease;
            margin: 5px;
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
        }
        
        .btn-secondary {
            background-color: var(--secondary-color);
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #1976D2;
        }
        
        .btn-outline {
            background-color: transparent;
            border: 2px solid var(--primary-color);
            color: var(--primary-color);
        }
        
        .btn-outline:hover {
            background-color: var(--primary-color);
            color: white;
        }
        
        /* Grid layout */
        .grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin: 20px 0;
        }
        
        .grid-item {
            background: var(--card-bg);
            padding: 20px;
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            text-align: center;
        }
        
        .grid-item h3 {
            color: var(--accent-color);
            margin-bottom: 10px;
        }
        
        /* Flexbox navigation */
        nav {
            background: var(--card-bg);
            padding: 15px;
            border-radius: var(--border-radius);
            box-shadow: var(--shadow);
            margin-bottom: 20px;
        }
        
        nav ul {
            list-style: none;
            display: flex;
            justify-content: center;
            gap: 30px;
        }
        
        nav a {
            text-decoration: none;
            color: var(--text-color);
            font-weight: 500;
            padding: 5px 10px;
            border-radius: 4px;
            transition: all 0.3s;
        }
        
        nav a:hover {
            background-color: var(--primary-color);
            color: white;
        }
        
        /* Responsive design */
        @media (max-width: 600px) {
            nav ul {
                flex-direction: column;
                gap: 10px;
            }
            
            .grid {
                grid-template-columns: 1fr;
            }
        }
        
        /* Animation */
        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        .animate-fade-in {
            animation: fadeIn 0.5s ease-out;
        }
        
        /* Utility classes */
        .text-center { text-align: center; }
        .mt-20 { margin-top: 20px; }
        .mb-20 { margin-bottom: 20px; }
        .p-20 { padding: 20px; }
    </style>
</head>
<body>
    <h1>CSS Styling Demo</h1>
    
    <nav>
        <ul>
            <li><a href="#home">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#services">Services</a></li>
            <li><a href="#portfolio">Portfolio</a></li>
            <li><a href="#contact">Contact</a></li>
        </ul>
    </nav>
    
    <div class="card animate-fade-in">
        <h2 class="card-title">Welcome to CSS Styling</h2>
        <p class="card-content">
            This page demonstrates various CSS techniques including:
            CSS variables, flexbox, grid, animations, and responsive design.
        </p>
        <div class="mt-20">
            <button class="btn btn-primary">Primary Button</button>
            <button class="btn btn-secondary">Secondary Button</button>
            <button class="btn btn-outline">Outline Button</button>
        </div>
    </div>
    
    <h2>CSS Grid Layout</h2>
    <div class="grid">
        <div class="grid-item">
            <h3>Feature 1</h3>
            <p>Responsive grid layout that adapts to screen size.</p>
        </div>
        <div class="grid-item">
            <h3>Feature 2</h3>
            <p>CSS variables for easy theming and maintenance.</p>
        </div>
        <div class="grid-item">
            <h3>Feature 3</h3>
            <p>Smooth transitions and hover effects.</p>
        </div>
        <div class="grid-item">
            <h3>Feature 4</h3>
            <p>Mobile-first responsive design approach.</p>
        </div>
    </div>
    
    <div class="card">
        <h2 class="card-title">CSS Best Practices</h2>
        <ul class="card-content">
            <li>Use CSS variables for consistent theming</li>
            <li>Implement mobile-first responsive design</li>
            <li>Use Flexbox and Grid for modern layouts</li>
            <li>Add smooth transitions for better UX</li>
            <li>Keep selectors specific but not overly complex</li>
        </ul>
    </div>
</body>
</html>"""
        ),
        
        CodeExample(
            id = "html_js",
            title = "JavaScript Integration",
            description = "Learn how to integrate JavaScript with HTML",
            language = ProgrammingLanguage.HTML,
            category = ExampleCategory.DOM_MANIPULATION,
            code = """<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JavaScript Integration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background: #f5f5f5;
        }
        
        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        
        h1 { color: #333; }
        h2 { color: #4CAF50; }
        
        .counter-display {
            font-size: 3rem;
            text-align: center;
            color: #4CAF50;
            margin: 20px 0;
        }
        
        .btn {
            padding: 10px 20px;
            margin: 5px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            transition: all 0.3s;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        
        .btn-primary { background: #4CAF50; color: white; }
        .btn-danger { background: #f44336; color: white; }
        .btn-secondary { background: #2196F3; color: white; }
        
        .todo-item {
            display: flex;
            align-items: center;
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
        
        .todo-item.completed span {
            text-decoration: line-through;
            color: #999;
        }
        
        .todo-item input[type="checkbox"] {
            margin-right: 10px;
        }
        
        .todo-item button {
            margin-left: auto;
            background: #f44336;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
        }
        
        #colorBox {
            width: 200px;
            height: 200px;
            background: #4CAF50;
            margin: 20px auto;
            border-radius: 8px;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 1.2rem;
        }
        
        .input-group {
            display: flex;
            gap: 10px;
            margin-bottom: 10px;
        }
        
        .input-group input {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        #message {
            padding: 10px;
            border-radius: 4px;
            margin-top: 10px;
            display: none;
        }
        
        #message.success {
            background: #d4edda;
            color: #155724;
            display: block;
        }
        
        #message.error {
            background: #f8d7da;
            color: #721c24;
            display: block;
        }
    </style>
</head>
<body>
    <h1>JavaScript Integration Demo</h1>
    
    <!-- Counter Section -->
    <div class="container">
        <h2>Interactive Counter</h2>
        <div class="counter-display" id="counter">0</div>
        <div style="text-align: center;">
            <button class="btn btn-primary" onclick="decrement()">-</button>
            <button class="btn btn-danger" onclick="reset()">Reset</button>
            <button class="btn btn-primary" onclick="increment()">+</button>
        </div>
    </div>
    
    <!-- Color Changer Section -->
    <div class="container">
        <h2>Color Changer</h2>
        <div id="colorBox">Click a button!</div>
        <div style="text-align: center;">
            <button class="btn btn-primary" onclick="changeColor('#4CAF50')">Green</button>
            <button class="btn btn-secondary" onclick="changeColor('#2196F3')">Blue</button>
            <button class="btn btn-danger" onclick="changeColor('#f44336')">Red</button>
            <button class="btn btn-secondary" onclick="randomColor()">Random</button>
        </div>
    </div>
    
    <!-- Todo List Section -->
    <div class="container">
        <h2>Todo List</h2>
        <div class="input-group">
            <input type="text" id="todoInput" placeholder="Add a new task...">
            <button class="btn btn-primary" onclick="addTodo()">Add</button>
        </div>
        <div id="todoList"></div>
    </div>
    
    <!-- Form Validation Section -->
    <div class="container">
        <h2>Form Validation</h2>
        <form id="myForm" onsubmit="return validateForm(event)">
            <div class="input-group">
                <input type="text" id="username" placeholder="Username (min 3 chars)">
            </div>
            <div class="input-group">
                <input type="email" id="email" placeholder="Email">
            </div>
            <div class="input-group">
                <input type="password" id="password" placeholder="Password (min 6 chars)">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <div id="message"></div>
    </div>
    
    <!-- Clock Section -->
    <div class="container">
        <h2>Live Clock</h2>
        <div class="counter-display" id="clock">00:00:00</div>
    </div>

    <!-- JavaScript -->
    <script>
        // ===== Counter functionality =====
        let count = 0;
        
        function updateCounter() {
            document.getElementById('counter').textContent = count;
        }
        
        function increment() {
            count++;
            updateCounter();
        }
        
        function decrement() {
            count--;
            updateCounter();
        }
        
        function reset() {
            count = 0;
            updateCounter();
        }
        
        // ===== Color changer functionality =====
        function changeColor(color) {
            const box = document.getElementById('colorBox');
            box.style.backgroundColor = color;
            box.textContent = color;
        }
        
        function randomColor() {
            const colors = ['#4CAF50', '#2196F3', '#f44336', '#FF9800', '#9C27B0', '#00BCD4'];
            const randomIndex = Math.floor(Math.random() * colors.length);
            changeColor(colors[randomIndex]);
        }
        
        // ===== Todo list functionality =====
        let todos = [];
        
        function renderTodos() {
            const list = document.getElementById('todoList');
            list.innerHTML = '';
            
            todos.forEach((todo, index) => {
                const item = document.createElement('div');
                item.className = 'todo-item' + (todo.completed ? ' completed' : '');
                item.innerHTML = `
                    <input type="checkbox" 
                           ${todo.completed ? 'checked' : ''} 
                           onchange="toggleTodo(${index})">
                    <span>${escapeHtml(todo.text)}</span>
                    <button onclick="deleteTodo(${index})">Delete</button>
                `;
                list.appendChild(item);
            });
        }
        
        function addTodo() {
            const input = document.getElementById('todoInput');
            const text = input.value.trim();
            
            if (text) {
                todos.push({ text, completed: false });
                input.value = '';
                renderTodos();
            }
        }
        
        function toggleTodo(index) {
            todos[index].completed = !todos[index].completed;
            renderTodos();
        }
        
        function deleteTodo(index) {
            todos.splice(index, 1);
            renderTodos();
        }
        
        function escapeHtml(text) {
            const div = document.createElement('div');
            div.textContent = text;
            return div.innerHTML;
        }
        
        // Allow Enter key to add todo
        document.getElementById('todoInput').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') addTodo();
        });
        
        // ===== Form validation =====
        function validateForm(event) {
            event.preventDefault();
            
            const username = document.getElementById('username').value.trim();
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;
            const message = document.getElementById('message');
            
            // Reset message
            message.className = '';
            message.style.display = 'none';
            
            // Validate
            if (username.length < 3) {
                showMessage('Username must be at least 3 characters', 'error');
                return false;
            }
            
            if (!email.includes('@')) {
                showMessage('Please enter a valid email', 'error');
                return false;
            }
            
            if (password.length < 6) {
                showMessage('Password must be at least 6 characters', 'error');
                return false;
            }
            
            showMessage('Form submitted successfully!', 'success');
            return false;
        }
        
        function showMessage(text, type) {
            const message = document.getElementById('message');
            message.textContent = text;
            message.className = type;
        }
        
        // ===== Live clock =====
        function updateClock() {
            const now = new Date();
            const timeString = now.toLocaleTimeString();
            document.getElementById('clock').textContent = timeString;
        }
        
        // Update clock every second
        setInterval(updateClock, 1000);
        updateClock(); // Initial call
        
        // ===== Console greeting =====
        console.log('Welcome to JavaScript Integration Demo!');
        console.log('Try interacting with the elements above.');
    </script>
</body>
</html>"""
        )
    )

    fun getExamplesByLanguage(language: ProgrammingLanguage): List<CodeExample> {
        return allExamples.filter { it.language == language }
    }

    fun getExampleById(id: String): CodeExample? {
        return allExamples.find { it.id == id }
    }

    fun getCategories(): List<ExampleCategory> {
        return ExampleCategory.values().toList()
    }
}
