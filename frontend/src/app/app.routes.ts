import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { EmployeeListComponent } from './components/employee-list/employee-list.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'employees', component: EmployeeListComponent , canActivate:[AuthGuard] },
    { path: '', redirectTo: '/employees', pathMatch: 'full' },
    { path: '**', redirectTo: '/employees' }
];
