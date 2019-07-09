import { BrowserModule } from '@angular/platform-browser';
import {CommonModule} from '@angular/common';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {Mydata} from './mydata';


import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { MygameComponent } from './mygame/mygame.component';
import { MymenuComponent } from './mymenu/mymenu.component';


const appRoutes: Routes = [
  { path: '',
    redirectTo: '/mymenu',
    pathMatch: 'full'
  } ,
  { 
    path: 'mymenu',
    component: MymenuComponent
  } ,
  { 
    path: 'mygame',
    component: MygameComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    MygameComponent,
    MymenuComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false }
    ),
    BrowserModule,
    HttpClientModule, 
  ],
  providers: [Mydata, 
  CommonModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
