import { Component, signal } from '@angular/core';
import { RouterOutlet, ɵEmptyOutletComponent } from '@angular/router';
import { Login } from "./components/auth/login/login";
import { HttpClient } from '@angular/common/http';
import { MatToolbar, MatToolbarRow } from "@angular/material/toolbar";
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButton, MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
  imports: [RouterOutlet, MatIconModule, MatSidenavModule, MatButtonModule]
})
export class App {
showFiller: any;
drawer: any;

  
}
