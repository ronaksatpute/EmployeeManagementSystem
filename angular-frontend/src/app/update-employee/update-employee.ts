import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Employee } from '../employee';
import { EmployeeService } from '../employee-service';

@Component({
  selector: 'app-update-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './update-employee.html',
  styleUrl: './update-employee.css',
})
export class UpdateEmployeeComponent implements OnInit {
  id!: number;
  employee: Employee = new Employee();

  constructor(
    private readonly employeeService: EmployeeService,
    private readonly route: ActivatedRoute,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.params['id']);

    this.employeeService.getEmployeeById(this.id).subscribe({
      next: (data) => {
        this.employee = data;
      },
      error: (error) => {
        console.error('Error loading employee:', error);
      }
    });
  }

  onSubmit(): void {
    this.employeeService.updateEmployee(this.id, this.employee).subscribe({
      next: () => {
        this.router.navigate(['/employees']);
      },
      error: (error) => {
        console.error('Error updating employee:', error);
      }
    });
  }
}