import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../api.service';
import { Scenario, Oggetto, Storia, Alternativa } from './scenario.model';
import { response } from 'express';

@Component({
  selector: 'app-gioca-storia',
  templateUrl: './gioca-storia.component.html',
  styleUrls: ['./gioca-storia.component.scss']
})
export class GiocaStoriaComponent implements OnInit {
  currentScenario: Scenario | null = null;
  inventory: string[]=[]; 
  userRiddleAnswer: string = '';
  storiaId: number = 0;
  storia: Storia | null = null;
  idSessione: string | null = null;

  // Lista temporanea degli scenari
  scenari: Scenario[] = [];


  constructor(private route: ActivatedRoute, private apiService: ApiService, private router: Router) {}

  ngOnInit(): void {
    const state = history.state;
    if (state.selectedStoria && state.idSessione) { 
      this.storia = state.selectedStoria;
      this.storiaId = state.selectedStoria.id;
      this.idSessione = state.idSessione;
      console.log(this.idSessione);
      this.inventory = state.inventory || [];
      this.currentScenario = this.storia?.inizio || null;
  
      // Controlla se currentScenario è impostato
      if (!this.currentScenario) {
        console.error('Errore: currentScenario è null');
        this.router.navigate(['/seleziona-storia']);
      }
  
      console.log('Alternative disponibili:', this.currentScenario?.alternative);
      console.log('Storia e sessione ricevute:', this.storia, this.idSessione, this.currentScenario);
    } else {
      console.error('Errore: nessuna storia o sessione passata allo stato.');
      this.router.navigate(['/seleziona-storia']);
    }
  }


  submitRiddle(): void {
    if (this.currentScenario && this.currentScenario.indovinello && this.idSessione) {
      const indovinello = this.currentScenario.indovinello;
      this.apiService.elaboraIndovinello(this.idSessione, this.currentScenario.idScenario, this.userRiddleAnswer)
        .subscribe(response => {

          console.log("Risposta api Indovinello: ", response);
          // Aggiorna `currentScenario` con il nuovo scenario
          this.currentScenario = response;
          console.log(this.currentScenario);
        });
    }
  }
  
  

  makeChoice(alternative: Alternativa): void {
    if (this.idSessione && this.currentScenario) {
      const idScenarioDiPartenza = this.currentScenario.idScenario;
      console.log("ID SESSIONE:", this.idSessione);
      console.log("ID SCENARIO DI PARTENZA:", idScenarioDiPartenza);
      console.log("ID SCENARIO SUCCESSIVO:", alternative.idScenarioSuccessivo);
      console.log("TESTO ALTERNATIVA:", alternative.testoAlternativa);
  
      this.apiService.elaboraAlternativa(this.idSessione, idScenarioDiPartenza, alternative.idScenarioSuccessivo, alternative.testoAlternativa)
        .subscribe(response => {
          console.log("Risposta dalla chiamata API:", response);
  
          // Aggiorna `currentScenario` con il nuovo scenario
          this.currentScenario = response;
  
          // Usa il punto esclamativo per indicare che currentScenario non è null
          if (this.currentScenario!.indovinello) {
            console.log("Nuovo indovinello trovato:", this.currentScenario!.indovinello);
          }
        });
    }
  }

  isAlternativeUnlocked(alternative: Alternativa): boolean {
    // Verifica se l'alternativa richiede un oggetto specifico
    if (alternative.oggettoRichiesto) {
      return this.inventory.some(item => item === alternative.oggettoRichiesto);
    }
    // Se non è richiesto alcun oggetto, l'alternativa è sbloccata di default
    return true;
  }
 


  collectItem(item: string): void {
    if (this.idSessione) {
      this.apiService.raccogliOggetto(this.idSessione, item).subscribe(() => {
        this.inventory.push(item);
        if (this.currentScenario && this.currentScenario.oggetto) {
          this.currentScenario.oggetto = this.currentScenario.oggetto;
        }
      });
    }
  }

  updateAvailableItems(): void {
    if (this.storia) {
      this.apiService.getScenariStoria(this.storia.id).subscribe(
        (scenari) => {
          this.scenari = scenari;

          this.scenari.forEach((scenario: Scenario) => {
            scenario.alternative.forEach((alternative: Alternativa) => {
              if (alternative.oggettoRichiesto && this.inventory.includes(alternative.oggettoRichiesto)) {
                alternative.oggettoRichiesto = ''; // Imposta su stringa vuota se già raccolto
              }
            });
          });
        },
        (error) => {
          console.error('Errore nel caricamento degli scenari della storia', error);
        }
      );
    }
  }


  terminaSessione(): void {
    console.log('Sessione terminata');
    this.router.navigate(['/dashboard']);
  }
}
