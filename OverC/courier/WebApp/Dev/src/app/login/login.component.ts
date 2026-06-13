import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from '../core/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  hide = true;
  lgForm = this.fb.group({
    userName: ["", Validators.required],
    password: ["", Validators.required]
  });

constructor(private router : Router,private fb: FormBuilder, private messageService: MessageService, private auth: AuthService ){

}

login(){
  if (this.lgForm.invalid) {
    this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Please fill required fields to continue' });
    return;
  }
  this.auth.login(this.lgForm.value)
}
}
