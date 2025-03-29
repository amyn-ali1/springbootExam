import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  credentials = {
    username: '',
    password: ''
  };
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    console.log(this.credentials)
    this.authService.login(this.credentials).subscribe(
      () => {
        this.router.navigate(['/employees']);
      },
      (err) => {
        this.error = 'Invalid username or password';
        console.error(err);
      }
    );
  }
}
