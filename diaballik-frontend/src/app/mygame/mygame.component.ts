import { Component, OnInit, AfterViewInit, ViewChildren, QueryList, ElementRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import {Mydata} from '../mydata';

@Component({
  selector: 'app-mygame',
  templateUrl: './mygame.component.html',
  styleUrls: ['./mygame.component.css']
})

export class MygameComponent implements OnInit,AfterViewInit {
  
  /* -------------- Attributs -------------- */

  @ViewChildren('pieces')
  private pieces: QueryList<ElementRef>;
  private selectedPiece: Element;
  private save: boolean;
  
  /* -------------- Constructeur -------------- */

  public constructor(private data: Mydata, private router: Router, private http: HttpClient){
  }

  /* -------------- OnInit -------------- */

  ngOnInit() {
  }

  /* -------------- Getters -------------- */
  getData() {
    return this.data;
  }
  
  getPieces() {
    return this.data.storage.board.list;
  }

  getCurrentPlayer() {
    if (this.data.storage.j1.nbAction === 0 ){
      return this.data.storage.j2['@id'];
    }
    return this.data.storage.j1['@id'];
  }

  getWin(){
    return this.data.storage.win;
  }

  isSave(){
    return this.save;
  }

  isReplay(){
    return this.data.replayGame;
  }

  /* -------------- onClicked et actions -------------- */

  onPieceClicked(event: MouseEvent) {
    if (this.selectedPiece) {
      const elem = event.currentTarget as Element;
      const oldX = parseInt(this.selectedPiece.getAttribute('data-x'));
      const oldY = parseInt(this.selectedPiece.getAttribute('data-y'));
      const newX = parseInt(elem.getAttribute('data-x'));
      const newY = parseInt(elem.getAttribute('data-y'));
      this.data.playerAction(true, oldX, oldY, newX, newY);
      this.selectedPiece = undefined;
    } else {
      const elem = event.currentTarget as Element;
      const oldX = parseInt(elem.getAttribute('data-x'));
      const oldY = parseInt(elem.getAttribute('data-y'));
      console.log(this.getCurrentPlayer());
      console.log(this.data.getPlayerForElem(oldX, oldY));
      if (this.getCurrentPlayer() === this.data.getPlayerForElem(oldX, oldY)) {
        this.selectedPiece = elem;
      }
    }
  }

  onCaseClicked(event: MouseEvent) {
    if (this.selectedPiece) {
      const elem = event.currentTarget as Element;
      const oldX = parseInt(this.selectedPiece.getAttribute('data-x'));
      const oldY = parseInt(this.selectedPiece.getAttribute('data-y'));
      const newX = parseInt(elem.getAttribute('data-x'));
      const newY = parseInt(elem.getAttribute('data-y'));
      this.data.playerAction(false, oldX, oldY, newX, newY);
      this.selectedPiece = undefined;
      }
  }

  saveClicked(event: MouseEvent){
    this.save=true;
  }

  saveMyGame(event: MouseEvent){
    const filename = document.getElementById("filename") as HTMLInputElement ;
    this.data.save(filename.value);
  }

  undoMyAction(event: MouseEvent){
    this.data.undo();
  }

  redoMyAction(event: MouseEvent){
    this.data.redo();
  }

  myPN() {
    var element = document.getElementById("myPN");
    element.classList.remove("pn");
    }

  /* -------------- AfterViewInit -------------- */

  ngAfterViewInit(): void {
    // 'myobjects' is not an array: it is a query that queries the html for the elements
    // The content of the query is not the objects but a set of references to the objects (have to use 'nativeElement')
    // So here: each 'nativeElement' is the div tag
    // Do that only after that the view has been initialised (in or after ngAfterViewInit)!
    this.pieces.forEach(p => console.log(p.nativeElement));
  }

}

