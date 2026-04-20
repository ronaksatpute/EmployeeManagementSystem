import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service';

@Component({
  selector: 'app-create-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-employee.html',
  styleUrl: './create-employee.css',
})
export class CreateEmployeeComponent {
  employee: Employee = new Employee();

  constructor(
    private readonly employeeService: EmployeeService,
    private readonly router: Router
  ) {}

  saveEmployee(): void {
    this.employeeService.createEmployee(this.employee).subscribe({
      next: () => {
        this.goToEmployeeList();
      },
      error: (error) => {
        console.error('Error creating employee:', error);
      }
    });
  }

  private goToEmployeeList(): void {
    this.router.navigate(['/employees']);
  }

  onSubmit(): void {
    console.log(this.employee);
    this.saveEmployee();
  }
}