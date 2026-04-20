import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private baseUrl = 'http://localhost:8080/api/v1/employees';

  constructor(private readonly httpClient: HttpClient) {}

  getEmployeeList(): Observable<Employee[]> {
    return this.httpClient.get<Employee[]>(this.baseUrl);
  }

  createEmployee(employee: Employee): Observable<Employee> {
    return this.httpClient.post<Employee>(this.baseUrl, employee);
  }

  getEmployeeById(id: number): Observable<Employee> {
    return this.httpClient.get<Employee>(`${this.baseUrl}/${id}`);
  }

  updateEmployee(id: number, employee: Employee): Observable<Employee> {
    return this.httpClient.put<Employee>(`${this.baseUrl}/${id}`, employee);
  }

  deleteEmployee(id: number): Observable<object> {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}