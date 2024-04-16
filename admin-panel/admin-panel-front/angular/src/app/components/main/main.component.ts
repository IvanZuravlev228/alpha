import {Component, OnInit} from '@angular/core';
import {SessionService} from "../../services/session.service";
import {Session} from "../../model/Session";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{
  sessions: Session[] = [];
  sessionsForShow: Session[] = [];
  filterFileName = '';
  paginationPage: number = 0;

  constructor(private sessionService: SessionService,
              private cookie: CookieService,
              private http: HttpClient) {
  }

  ngOnInit() {
    this.getSessionByOwner()
  }

  public getSessionByOwner() {
    this.sessionService.getSessionByOwner(this.paginationPage).subscribe({
      next: (sessions) => {
        this.sessions = sessions;
        this.sessionsForShow = sessions;
        // this.sortSessionsByUploadTime();
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  public downloadSession(id: string, fileName: string) {
    // Создайте объект HttpHeaders с заголовком Authorization
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + this.cookie.get('jwt-token')
    });

    // Добавьте заголовки к запросу
    this.http.get("http://37.221.125.51:8090/files/session/" + id, { responseType: 'blob', headers: headers, withCredentials: true })
      .subscribe((data: Blob) => {
        const downloadLink = document.createElement('a');
        const url = window.URL.createObjectURL(data);
        downloadLink.href = url;
        downloadLink.download = fileName + ".session";
        document.body.appendChild(downloadLink);

        downloadLink.setAttribute('style', 'display:none');
        document.body.appendChild(downloadLink);
        downloadLink.click();

        document.body.removeChild(downloadLink);
        window.URL.revokeObjectURL(url);
      });
  }

  filteredSessions() {
    this.sessionsForShow = this.sessions.filter(session =>
      session.fileName.toLowerCase().includes(this.filterFileName.toLowerCase()));
    // this.sortSessionsByUploadTime();
  }

  resetFilter() {
    this.sessionsForShow = this.sessions;
  }

  downloadAuth(id: string, fileName: string) {
// Создайте объект HttpHeaders с заголовком Authorization
    const headers = new HttpHeaders({
      'Authorization': 'Bearer ' + this.cookie.get('jwt-token')
    });

    // Добавьте заголовки к запросу
    this.http.get("http://37.221.125.51:8090/files/auth/" + id, { responseType: 'blob', headers: headers, withCredentials: true })
      .subscribe((data: Blob) => {
        const downloadLink = document.createElement('a');
        const url = window.URL.createObjectURL(data);
        downloadLink.href = url;
        downloadLink.download = fileName + ".txt";
        document.body.appendChild(downloadLink);

        downloadLink.setAttribute('style', 'display:none');
        document.body.appendChild(downloadLink);
        downloadLink.click();

        document.body.removeChild(downloadLink);
        window.URL.revokeObjectURL(url);
      });
  }

  sortSessionsByUploadTime() {
    this.sessionsForShow.sort((a, b) => {
      const dateA = new Date(a.uploadTime).getTime();
      const dateB = new Date(b.uploadTime).getTime();

      // return dateA - dateB;
      return dateB - dateA;
    });
  }

  prevPage() {
    if (this.paginationPage == 0) {
      return;
    }
    this.paginationPage--;
    this.getSessionByOwner();
  }

  nextPage() {
    if (this.sessions.length < 25) {
      return;
    }
    this.paginationPage++;
    this.getSessionByOwner();
  }

  refresh() {
    this.getSessionByOwner()
  }

  deleteSessionById(id: string) {
    this.sessionService.deleteById(id).subscribe({
      next: () => {
        this.getSessionByOwner();
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
