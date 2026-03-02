import { GoogleSigninButtonModule, SocialAuthService, SocialLoginModule, SocialAuthServiceConfig } from '@abacritt/angularx-social-login';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,SocialLoginModule,GoogleSigninButtonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'google-login-app';

  user:any
  googleLoginUrl="http://localhost:9090/auth/login-with-google"


  constructor(private authService:SocialAuthService,private http:HttpClient) {
    this.authService.authState.subscribe((authData)=>{
      console.log("User logged in");      
      console.log(authData);

      //id token process hone ke lie backend :

      
      if(authData){


      //to send the request to backend for processing

      //HttpClient service

      this.http.post(this.googleLoginUrl,{idToken:authData.idToken}).subscribe((res)=>{
        console.log("response from backend");
        console.log(res);
        this.user=res
      }
    )


      }


    })
  }

}
