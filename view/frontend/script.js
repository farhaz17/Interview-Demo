const departmentTableBody = document.getElementById('departmentTable').querySelector('tbody');
const employeeTableBody = document.getElementById('employeeTable').querySelector('tbody');


// API Base URL
const API_BASE_URL = 'http://localhost:8080/api';

// Load Department Details
function loadDepartments() {
    departmentTableBody.innerHTML = ''; // Clear existing rows
  
    fetch(`${API_BASE_URL}/departments`)
      .then(response => response.json())
      .then(departments => {
        departments.forEach(department => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${department.id}</td>
            <td>${department.name}</td>
            <td>${department.location}</td>
            <td>${Array.isArray(department?.employees) ? department.employees.length : 'null'}</td>
            <td>
              <button  class="btn btn-info btn-sm" onclick="addEmployees(${department.id})">Add Employees</button>
            </td>
          `;
          departmentTableBody.appendChild(row);
        });
      })
      .catch(error => console.error('Error loading departments:', error));
  }

  function loadEmployees() {
    employeeTableBody.innerHTML = ''; // Clear existing rows
  
    fetch(`${API_BASE_URL}/employees`) // Replace with your API endpoint for fetching employees
      .then(response => response.json())
      .then(employees => {
        employees.forEach(employee => {
          const row = document.createElement('tr');
          row.innerHTML = `
            <td>${employee.id}</td>
            <td>${employee.name}</td>
            <td>${employee.email}</td>
            <td>${employee.position}</td>
            <td>${employee.salary}</td>
            <td>${employee.department.name}</td>
            <td>
                <button class="btn btn-info btn-sm" onclick="viewEmployee(${employee.id})">View Details</button>
                <button class="btn btn-danger btn-sm" onclick="deleteEmployee(${employee.id})">Delete</button>
            </td>
          `;
          employeeTableBody.appendChild(row);
        });
      })
      .catch(error => console.error('Error loading employees:', error));
  }

  function viewEmployee(employeeId) {
    localStorage.setItem('currentEmployeeId', employeeId);
  
    window.location.href = 'employee.html';
  }

  function deleteEmployee(employeeId) {
    if (confirm('Are you sure you want to delete this employee?')) {
      fetch(`${API_BASE_URL}/employees/${employeeId}`, {
        method: 'DELETE',
      })
        .then(response => {
          if (!response.ok) {
            throw new Error(`Failed to delete employee. Status: ${response.status}`);
          }
          alert('Employee deleted successfully!');
          loadEmployees(); // Reload employees after deletion
        })
        .catch(error => {
          console.error('Error deleting employee:', error);
          alert('Failed to delete employee. Please try again.');
        });
    }
  }
  

// Load departments on page load
document.addEventListener('DOMContentLoaded', () => {
    loadDepartments(); // Load departments
    loadEmployees();   // Load employees
  });

document.getElementById('addDepartmentForm').addEventListener('submit', function (event) {
  event.preventDefault();

  const departmentName = document.getElementById('departmentName').value.trim();
  const departmentLocation = document.getElementById('departmentLocation').value.trim();

  // Create Department Object
  const newDepartment = {
    name: departmentName,
    location: departmentLocation,
  };

  // Send POST Request to Backend
  fetch(`${API_BASE_URL}/departments`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newDepartment),
  })
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .then(data => {
      console.log('Department added:', data);
      document.getElementById('addDepartmentForm').reset(); // Reset form
      const modalElement = document.getElementById('addDepartmentModal');
      const modalInstance = bootstrap.Modal.getInstance(modalElement); // Bootstrap 5 API
      modalInstance.hide(); // Hide modal
      loadDepartments(); // Reload departments
    })
    .catch(error => {
      console.error('Error adding department:', error);
      alert('Failed to add department. Please try again.');
    });
});

function addEmployees(departmentId) {
    // Save department ID for later use when submitting the form
    localStorage.setItem('currentDepartmentId', departmentId);
  
    // Programmatically show the modal using Bootstrap's JavaScript API
    const modalElement = document.getElementById('addEmployeeModal');
    const modalInstance = new bootstrap.Modal(modalElement);
    modalInstance.show();
  }
  
  // Add Employee Form Submission
  document.getElementById('addEmployeeForm').addEventListener('submit', function (event) {
    event.preventDefault();
  
    const employeeName = document.getElementById('employeeName').value.trim();
    const employeeEmail = document.getElementById('employeeEmail').value.trim();
    const employeePosition = document.getElementById('employeePosition').value.trim();
    const employeeSalary = document.getElementById('employeeSalary').value.trim();
  
    const departmentId = localStorage.getItem('currentDepartmentId'); // Retrieve department ID
  
    const newEmployee = {
      name: employeeName,
      email: employeeEmail,
      position: employeePosition,
      salary: employeeSalary,
    };
  
    fetch(`http://localhost:8080/api/employees/department/${departmentId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newEmployee),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {
        console.log('Employee added:', data);
        alert('Employee added successfully!');
        document.getElementById('addEmployeeForm').reset(); // Reset form
  
        const modalElement = document.getElementById('addEmployeeModal');
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide(); // Hide modal
        loadEmployees();
      })
      .catch(error => {
        console.error('Error adding employee:', error);
        alert('Failed to add employee. Please try again.');
      });
  });

  function downloadReport() {
    fetch('http://localhost:8080/api/employees/report', {
        method: 'GET',
        headers: {
            'Accept': 'application/pdf',
        },
    })
        .then(response => {
            if (response.ok) {
                return response.blob(); // Convert response to a blob
            }
            throw new Error('Failed to download report');
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'employee_report.pdf'; // Set the filename
            document.body.appendChild(a);
            a.click();
            a.remove(); // Remove the anchor element after clicking
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

  
