import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Employee } from '../employee';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.css',
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];

  constructor() {}

  ngOnInit(): void {
    this.employees = [
      {
        id: 1,
        firstName: 'John',
        lastName: 'Doe',
        emailId: 'john.doe@example.com'
      },
      {
        id: 2,
        firstName: 'Jane',
        lastName: 'Smith',
        emailId: 'jane.smith@example.com'
      }
    ];
  }

}