const API_BASE_URL = 'http://localhost:8080/api';
const employeeDetailsDiv = document.getElementById('employeeDetails');

// Load Employee Details
function loadEmployeeDetails() {
  const employeeId = localStorage.getItem('currentEmployeeId'); // Get the saved employee ID

  if (!employeeId) {
    alert('No employee selected!');
    window.location.href = 'index.html'; // Redirect back to employees page
    return;
  }

  fetch(`${API_BASE_URL}/employees/${employeeId}`)
    .then(response => response.json())
    .then(employee => {
      employeeDetailsDiv.innerHTML = `
        <p><strong>ID:</strong> ${employee.id}</p>
        <p><strong>Name:</strong> ${employee.name}</p>
        <p><strong>Email:</strong> ${employee.email}</p>
        <p><strong>Position:</strong> ${employee.position}</p>
        <p><strong>Salary:</strong> ${employee.salary}</p>
        <p><strong>Department:</strong> ${employee.department.name}</p>
      `;
    })
    .catch(error => {
      console.error('Error loading employee details:', error);
      alert('Failed to load employee details.');
    });
}

// Load employee details on page load
document.addEventListener('DOMContentLoaded', loadEmployeeDetails);