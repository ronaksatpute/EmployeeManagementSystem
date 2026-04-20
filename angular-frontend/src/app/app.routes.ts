import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'employees', pathMatch: 'full' },
  {
    path: 'employees',
    loadComponent: () =>
      import('./employee-list/employee-list').then(m => m.EmployeeListComponent)
  },
  {
    path: 'create-employee',
    loadComponent: () =>
      import('./create-employee/create-employee').then(m => m.CreateEmployeeComponent)
  }
];