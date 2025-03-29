import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Department } from '../models/department.model';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  apiUrl ="http://localhost:8080/api/getAllDepatment"
  constructor(private http: HttpClient) { }

  getAllDepartments(): Observable<Department[]> {
    const token = localStorage.getItem('auth_token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Department[]>(this.apiUrl, {headers: headers});
  }
}
