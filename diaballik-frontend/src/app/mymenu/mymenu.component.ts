import { Component, OnInit, ViewChild, AfterViewInit, ElementRef, ViewChildren, QueryList} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Mydata } from '../mydata';
import { BootstrapOptions } from '@angular/core/src/application_ref';

@Component({
  selector: 'app-mymenu',
  templateUrl: './mymenu.component.html',
  styleUrls: ['./mymenu.component.css']
})
export class MymenuComponent implements OnInit, AfterViewInit {
  
  /* -------------- Attributs -------------- */

  public home: boolean;
  public newGame: boolean;
  public gamepvp: boolean;
  public gamepvia: boolean;
  public next: boolean;
  public load: boolean;
  public replay: boolean;
  public gameReplay: boolean;

  /* -------------- Constructeur -------------- */

  public constructor(private data: Mydata, private router: Router, private http: HttpClient){
  }

  /* -------------- OnInit -------------- */

  ngOnInit() {
    this.home=true;
  }

  /* -------------- Getters -------------- */

  getData() {
    return this.data;
  }

  getSavedGames() {
    return this.data.savedGames;
  }

  isHome(){
    return this.home;
  }

  isNewGame(){
    return this.newGame;
  }

  isLoad() {
    return this.load;
  }

  isReplay() {
    return this.replay;
  }

  isNext() {
    return this.next;
  }

  isGamePvP() {
    if((document.getElementById('nbPlayers2') as HTMLInputElement).checked){
      this.gamepvp = true;
    } else {
      this.gamepvp = false;
    }
    return this.gamepvp;
  }

  isGamePvIA() {
    if((document.getElementById('nbPlayers1') as HTMLInputElement).checked){
      this.gamepvia = true;
    } else {
      this.gamepvia = false;
    }
    return this.gamepvia;
  }
 
  /* -------------- onClicked -------------- */

  myNewGameClicked(event:MouseEvent){
    this.home=false;
    this.newGame = true;
  }

  myLoadClicked(event: MouseEvent) {
    this.data.getSavedGames();
    this.home = false;
    this.load = true;
  }

  myReplayClicked(event: MouseEvent) {
    this.data.getSavedGames();
    this.home = false;
    this.replay = true;
  }

  myNextHomeClicked(event: MouseEvent) {
    this.home = false;
    this.next = true;
  }

  /* -------------- Actions -------------- */

  myNewGame(event:MouseEvent){
    if(this.gamepvia){
      this.myNewGamePvIA();
    } else {
      this.myNewGamePvP();
    }
  }

  myNewGamePvP(){
    var scenario = 0;

    if((document.getElementById('scenario1') as HTMLInputElement).checked){
      scenario = 1;
    }
    if((document.getElementById('scenario2') as HTMLInputElement).checked) {
      scenario = 2;
    }
    const nameP1 = document.getElementById("nameP1") as HTMLInputElement;
    const nameP2 = document.getElementById("nameP2") as HTMLInputElement;
    this.data.newgamepvp(scenario,nameP1.value,nameP2.value);
  }

  myNewGamePvIA(){
    var scenario = 0;

    if((document.getElementById('scenario1') as HTMLInputElement).checked){
      scenario = 1;
    }
    if((document.getElementById('scenario2') as HTMLInputElement).checked) {
      scenario = 2;
    }
    var level = 0;
    if((document.getElementById('level1') as HTMLInputElement).checked) {
      level = 1;
    }
    if((document.getElementById('level2') as HTMLInputElement).checked) {
      level = 2;
    }
    const nameP1 = document.getElementById("nameP1") as HTMLInputElement;
    this.data.newgamepvia(scenario,nameP1.value,level);
  }

  myValidLoad(event: MouseEvent) {
    const filename = document.getElementById("game") as HTMLInputElement;
    this.data.load(filename.value);  
  }

  myValidReplay(event: MouseEvent) {
    const filename = document.getElementById("game") as HTMLInputElement;
    this.data.replay(filename.value); 
  }

  /* -------------- AfterViewInit -------------- */

  ngAfterViewInit(): void {
  }

}
