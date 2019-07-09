import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable()
export class Mydata {
    public storage: any;
    public savedGames: any;
    public replayGame: boolean;
    
    constructor(private http: HttpClient, private router: Router){
        
    }

    newgamepvp(scenario:number,nameP1:string,nameP2:string){
        this.http.post(`game/newGamePvP/${scenario}/${nameP1}/${nameP2}`, {}, {}).subscribe(
            returnedData => {
                // la requete s'est bien passée
                this.storage = returnedData;
                console.log(this.storage);
                this.router.navigate(['/mygame']);
            },
            error => {
                // si erreur de la requête
                console.log(error);
            }
        );
    }

    newgamepvia(scenario:number,nameP1:string,level:number){
        this.http.post(`game/newGamePvIA/${scenario}/${nameP1}/${level}`, {}, {}).subscribe(
            returnedData => {
                // la requete s'est bien passée
                this.storage = returnedData;
                console.log(this.storage);
                this.router.navigate(['/mygame']);
            },
            error => {
                // si erreur de la requête
                console.log(error);
            }
        );
    }

    playerAction(hasBall, tx, ty, tx2, ty2) {
        if(this.storage.win==false){
            console.log(hasBall,tx,ty,tx2,ty2);
            this.http.put(`game/player/move/${hasBall}/${tx}/${ty}/${tx2}/${ty2}`, {}, {}).subscribe(
                returnedData => {
                    // la requete s'est bien passée
                    this.storage = returnedData;
                    console.log(returnedData);
                    this.win();
                },        
                error => {
                    // si erreur de la requête
                    console.log(error);
                }
            );
        }
    }

    getPlayerForElem(x, y) {
        let player;
        this.storage.board.list.forEach(p => {
            if (p.tx === x && p.ty === y) {
                player = p.player;
            }
        });
        return player;
    }

    win(){
        if(this.storage.win==true){
            console.log("Gagné !!!");
        }
    }

    save(filename: string) {
        this.http.post(`game/save/${filename}`, {}, {}).subscribe(
            returnedData => {
                // la requete s'est bien passée
                this.storage = returnedData;
                console.log("Le fichier \"" + filename + "\" a bien ete sauvegarde.");
                this.router.navigate(['/mymenu']);
            },
            error => {
                // si erreur de la requête
                console.log(error);
            }
        );
    }

    getSavedGames() {
        this.http.get(`game/listGameSaved`, {}).subscribe(
            returnedData => {
                console.log(returnedData);
                this.savedGames = returnedData;
            },
            error => {
                console.log(error);
            }
        );
    }

    load(filename: string) {
        this.http.get(`game/load/${filename}`, {}).subscribe(
            returnedData => {
                console.log(returnedData);
                this.storage = returnedData;
                this.router.navigate(['/mygame']);
            },
            error => {
                console.log(error);
            }
        );
    }

    replay(filename: string) {
        this.replayGame = true;
        this.http.get(`game/replay/${filename}`, {}).subscribe(
            returnedData => {
                console.log(returnedData);
                this.storage = returnedData;
                this.router.navigate(['/mygame']);
            },
            error => {
                console.log(error);
            }
        );
    }

    undo() {
        this.http.put(`game/replay/undo`, {}, {}).subscribe(
            returnedData => {
                // la requete s'est bien passée
                console.log(this.storage);
                this.storage = returnedData;
                this.router.navigate(['/mygame']);
            },
            error => {
                // si erreur de la requête
                console.log(error);
            }
        );
    }
    
    redo() {
        this.http.put(`game/replay/redo`, {}, {}).subscribe(
            returnedData => {
                // la requete s'est bien passée
                console.log(this.storage);
                this.storage = returnedData;
                this.router.navigate(['/mygame']);
            },
            error => {
                // si erreur de la requête
                console.log(error);
            }
        );
    }


}