import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { CardDisplayComponent } from './card-display/card-display.component';
import { FontService } from './services/font.service';
import { SidebarComponent } from './sidebar/sidebar.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CardDisplayComponent,
    SidebarComponent
  ],
  imports: [
    BrowserModule,
    HttpModule
  ],
  providers: [
    FontService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
