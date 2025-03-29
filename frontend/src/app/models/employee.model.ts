import { Department } from "./department.model";

export class Employee {
  id!: number ;
  firstName: string = '';
  lastName: string = '';
  email: string = '';
  birthDate: Date = new Date(); // Ensure this is a Date object
  photoUrl: string = '';
  department: Department = new Department();
  departmentId:number = 1
}