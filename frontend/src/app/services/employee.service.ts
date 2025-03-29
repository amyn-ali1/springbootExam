import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../models/employee.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiUrl = 'http://localhost:8080/api';
  private  token = localStorage.getItem('auth_token');
      

  constructor(private http: HttpClient) { }

  getEmployeesByDepartment(departmentId: number): Observable<Employee[]> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.get<Employee[]>(`${this.apiUrl}/departement/${departmentId}/employes`, {headers: headers});
  }

  addEmployee(data:any): Observable<Employee> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    console.log("test")
    return this.http.post<Employee>(`${this.apiUrl}/employes`, data, {headers: headers});
  }

  updateEmployee(id: number, employee: any): Observable<Employee> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    return this.http.put<Employee>(`${this.apiUrl}/employes/${id}`, employee, {headers: headers});
  }

  deleteEmployee(id: number): Observable<void> {
    const token = localStorage.getItem('auth_token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<void>(`${this.apiUrl}/employes/${id}`, { headers });
  }

  deleteEmployeesOverAge(age: number): Observable<void> {
    const token = localStorage.getItem('auth_token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<void>(`${this.apiUrl}/employes/age/${age}`, { headers });
  }


  // Add this new method
  deleteEmployeesOver60(): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/age/60`);
  }
}
