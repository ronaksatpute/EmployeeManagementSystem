import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.css',
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];

  constructor(
    private readonly employeeService: EmployeeService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.getEmployees();
  }

  private getEmployees(): void {
    this.employeeService.getEmployeeList().subscribe({
      next: (data) => {
        this.employees = data;
      },
      error: (error) => {
        console.error('Error fetching employees:', error);
      }
    });
  }

  deleteEmployee(id: number): void {
    this.employeeService.deleteEmployee(id).subscribe({
      next: () => {
        this.getEmployees();
      },
      error: (error) => {
        console.error('Error deleting employee:', error);
      }
    });
  }

  updateEmployee(id: number): void {
    this.router.navigate(['update-employee', id]);
  }
}