import { Component, OnInit } from '@angular/core';
import { Employee } from '../../models/employee.model';
import { EmployeeService } from '../../services/employee.service';
import { Department } from '../../models/department.model';
import { DepartmentService } from '../../services/department.service';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

declare var $: any;

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, HttpClientModule, CommonModule],
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css'
})
export class EmployeeListComponent implements OnInit {

  departments: Department[] = [];
  selectedDepartmentId: number | null = null;
  employees: Employee[] = [];
  currentEmployee: Employee = new Employee();
  isEditMode = false;
  selectedFile: File | null = null;

  ngOnInit(): void {
    this.loadDepartments();
  }

  constructor(
    private employeeService: EmployeeService,
    private departmentService: DepartmentService,
    private authService: AuthService,
    private router: Router
  ) {}

  loadDepartments(): void {
    this.departmentService.getAllDepartments().subscribe(
      (data:any) => {
        console.log(data)
        this.departments = data;
      },
      (error:any) => {
        console.error('Error loading departments', error);
      }
    );
  }

  selectDepartment(departmentId: number): void {
    this.selectedDepartmentId = departmentId;
    console.log(departmentId)
    this.loadEmployees(departmentId);
  }

  loadEmployees(departmentId: number): void {
    this.employeeService.getEmployeesByDepartment(departmentId).subscribe(
      (data) => {
        this.employees = data;
      },
      (error) => {
        console.error('Error loading employees', error);
      }
    );
  }

  openAddEmployeeModal(): void {
    this.isEditMode = false;
    this.currentEmployee = new Employee();
    if (this.selectedDepartmentId) {
      this.currentEmployee.department = { id: this.selectedDepartmentId } as Department;
    }
    $('#employeeModal').modal('show');
  }

  openEditEmployeeModal(employee: Employee): void {
    this.isEditMode = true;
    this.currentEmployee = { ...employee };
    $('#employeeModal').modal('show');
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  addEmployee(): void {
    this.currentEmployee.departmentId = this.currentEmployee.department.id
    console.log(this.currentEmployee.department.id)
    console.log(!this.selectedDepartmentId)
    // if (!this.selectedDepartmentId) return;

   
    console.log(this.currentEmployee)
    this.currentEmployee
    this.employeeService.addEmployee(this.currentEmployee).subscribe(
      () => {
        this.loadEmployees(this.selectedDepartmentId!);
        $('#employeeModal').modal('hide');
      },
      (error) => {
        console.error('Error adding employee', error);
      }
    );
  }

  updateEmployee(): void {
    console.log(this.currentEmployee)
    this.employeeService.updateEmployee(this.currentEmployee.id, this.currentEmployee).subscribe(
      () => {
        this.loadEmployees(this.selectedDepartmentId!);
        $('#employeeModal').modal('hide');
      },
      (error) => {
        console.error('Error updating employee', error);
      }
    );
  }

  deleteEmployee(id: number): void {
    if (confirm('Are you sure you want to delete this employee?')) {
      this.employeeService.deleteEmployee(id).subscribe(
        () => {
          this.loadEmployees(this.selectedDepartmentId!);
        },
        (error) => {
          console.error('Error deleting employee', error);
        }
      );
    }
  }

  deleteEmployeesOver60(): void {
    if (confirm('Are you sure you want to delete all employees over 60?')) {
      this.employeeService.deleteEmployeesOver60().subscribe(
        () => {
          if (this.selectedDepartmentId) {
            this.loadEmployees(this.selectedDepartmentId);
          }
        },
        (error) => {
          console.error('Error deleting employees over 60', error);
        }
      );
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
 
}
