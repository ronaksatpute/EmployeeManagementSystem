import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
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

  constructor(private readonly employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.getEmployees();
  }

  private getEmployees(): void {
    this.employeeService.getEmployeeList().subscribe({
      next: (data) => {
        console.log('Employees loaded:', data);
        this.employees = data;
      },
      error: (error) => {
        console.error('Error fetching employees:', error);
      }
    });
  }
}