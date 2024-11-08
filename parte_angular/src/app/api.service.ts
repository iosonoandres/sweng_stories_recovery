import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';  // Cambia con il tuo endpoint

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  // Metodi per interagire con le storie
  getAllStorie(): Observable<any> {
    return this.http.get(`${this.baseUrl}/storie`, this.httpOptions).pipe(
      catchError(this.handleError('getAllStorie'))
    );
  }

  getStoriaById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/storie/${id}`, this.httpOptions).pipe(
      catchError(this.handleError('getStoriaById'))
    );
  }

  getStoriaConUsername(username: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/storie/utente/${username}`, this.httpOptions).pipe(
      catchError(this.handleError('getStoriaConUsername'))
    );
  }

  inserisciStoria(storia: any): Observable<any> {
    console.log("DETTAGLI INVIO POWER");
    console.log(storia); // Rimuovi JSON.stringify qui
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${this.baseUrl}/storie`, storia, { headers }).pipe(
      catchError(this.handleError('inserisciStoria'))
    );
  }



  updateScenario(idStoria: number, idScenario: number, nuovoTesto: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/storie/${idStoria}/scenari/${idScenario}`, {}, {
      params: { nuovoTesto },
      ...this.httpOptions
    }).pipe(
      catchError(this.handleError('updateScenario'))
    );
  }


  getScenario(idStoria: number, idScenario: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/storie/${idStoria}/scenari/${idScenario}`, this.httpOptions).pipe(
      catchError(this.handleError('getScenario'))
    );
  }

  inserisciScenario(idStoria: number, scenario: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/storie/scenari`, scenario, this.httpOptions).pipe(
      catchError(this.handleError('inserisciScenario'))
    );
  }

  getScenariStoria(idStoria: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/storie/scenaristoria/${idStoria}`, this.httpOptions).pipe(
      catchError(this.handleError('getScenariStoria'))
    );
  }

  // Metodi per interagire con le sessioni di gioco
  elaboraIndovinello(idSessione: String, idScenario: number, risposta: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/sessioni/SessioneGioco/${idSessione}/scenari/${idScenario}/indovinello`, {}, {
      params: { risposta },
      ...this.httpOptions
    }).pipe(
      catchError(this.handleError('elaboraIndovinello'))
    );
  }

  elaboraAlternativa(idSessione: string, idScenarioDiPartenza: number, idScenarioSuccessivo: number, testoAlternativa: string): Observable<any> {
    return this.http.put(
      `${this.baseUrl}/sessioni/SessioneGioco/${idSessione}/scenari/${idScenarioSuccessivo}/alternativa/${idScenarioDiPartenza}`,
      {},
      {
        params: {
          testoAlternativa: testoAlternativa // Passa `testoAlternativa` come parametro della query
        },
        ...this.httpOptions
      }
    ).pipe(
      catchError(this.handleError('elaboraAlternativa'))
    );
  }




  raccogliOggetto(idSessione: String, oggetto: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/sessioni/SessioneGioco/${idSessione}/inventario`, {}, {
      params: { oggetto },
      ...this.httpOptions
    }).pipe(
      catchError(this.handleError('raccogliOggetto'))
    );
  }

  creaSessione(partita: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/sessioni/SessioneGioco/`, partita, this.httpOptions).pipe(
      catchError(this.handleError('creaSessione'))
    );
  }

  eliminaSessione(idSessione: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/sessioni/SessioneGioco/${idSessione}`, this.httpOptions).pipe(
      catchError(this.handleError('eliminaSessione'))
    );
  }

  getSessioniUtente(username: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/sessioni/SessioneGioco/utente/${username}`, this.httpOptions).pipe(
      catchError(this.handleError('getSessioniUtente'))
    );
  }

  getSessioneConID(idSessione: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/sessioni/SessioneGioco/${idSessione}`, this.httpOptions).pipe(
      catchError(this.handleError('getSessioneConID'))
    );
  }

  // Metodi per interagire con l'autenticazione
  registraUtente(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/AuthController/register`, {}, {
      params: { username, password },
      ...this.httpOptions
    }).pipe(
      catchError(this.handleError('registraUtente'))
    );
  }

  loginUtente(formData: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/AuthController/login`, {}, {
      params: { username: formData.username, password: formData.password },
      ...this.httpOptions
    }).pipe(
      catchError(this.handleError('loginUtente'))
    );
  }


  getUtente(username: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/auth/AuthController/user/${username}`, this.httpOptions).pipe(
      catchError(this.handleError('getUtente'))
    );
  }

  // Gestione degli errori
  private handleError(operation = 'operation') {
    return (error: any): Observable<never> => {
      console.error(`${operation} failed: ${error.message}`);
      return throwError(() => new Error(`Operazione ${operation} fallita: ${error.message}`));
    };
  }
}
