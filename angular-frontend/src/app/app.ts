import { Component, signal } from '@angular/core';
import { EmployeeListComponent } from './employee-list/employee-list';

@Component({
  selector: 'app-root',
  imports: [EmployeeListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('angular-frontend');
}