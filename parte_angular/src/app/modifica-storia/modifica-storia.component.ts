import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { Router, ActivatedRoute } from '@angular/router';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-modifica-storia',
  templateUrl: './modifica-storia.component.html',
  styleUrls: ['./modifica-storia.component.scss']
})
export class ModificaStoriaComponent implements OnInit {
  selectedStoria: any = null;
  scenarioDescriptions: string[] = []; // Array per memorizzare le descrizioni degli scenari
  idStoria: number = 0; // Memorizza l'ID della storia

  constructor(
    private apiService: ApiService, 
    private router: Router, 
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Ottieni l'ID della storia dalla rotta
    this.idStoria = Number(this.route.snapshot.paramMap.get('id'));
    this.loadStoria(this.idStoria); // Carica la storia selezionata
  }

  loadStoria(id: number): void {
    // Ottieni i dettagli della storia
    this.apiService.getStoriaById(id).subscribe(
      storia => {
        this.selectedStoria = storia;
        this.loadScenariStoria(id); // Carica gli scenari per la storia
      },
      error => {
        console.error('Errore nel caricamento della storia', error);
        this.router.navigate(['/selezione-storia']); // Reindirizza in caso di errore
      }
    );
  }

  loadScenariStoria(idStoria: number): void {
    // Usa getScenariStoria per ottenere gli scenari collegati a questa storia
    this.apiService.getScenariStoria(idStoria).subscribe(
      scenari => {
        this.selectedStoria.scenari = scenari;
        // Inizializza le descrizioni degli scenari nel form
        this.scenarioDescriptions = scenari.map((scenario: any) => scenario.testoScenario || '');
      },
      error => {
        console.error('Errore nel caricamento degli scenari della storia', error);
      }
    );
  }

  updateStoria(): void {
    if (this.selectedStoria && this.selectedStoria.scenari) {
      // Crea un array di osservabili per aggiornare ciascuno scenario
      const updateRequests = this.selectedStoria.scenari.map((scenario: any, index: number) => {
        const nuovoTesto = this.scenarioDescriptions[index];
        const idScenario = scenario.idScenario;
        const idStoria = this.idStoria;
        console.log("NUOVOTESTO: ", nuovoTesto, " CON ID SCENARIO:  ", idScenario, " E CON ID STORIA: ", idStoria );
        return this.apiService.updateScenario(idStoria, idScenario, nuovoTesto);
      });

      // Usa forkJoin per eseguire tutte le richieste contemporaneamente
      forkJoin(updateRequests).subscribe(
        () => {
          console.log('Tutti gli scenari sono stati aggiornati con successo!');
          this.router.navigate(['/selezione-storia']); // Reindirizza dopo l'aggiornamento
        },
        error => {
          console.error('Errore nell\'aggiornamento di uno o più scenari', error);
        }
      );
    }
  }

  annullaModifiche(): void {
    this.router.navigate(['/selezione-storia']);
  }
}
