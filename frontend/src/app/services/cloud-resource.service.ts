import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CloudResource } from '../models/cloud-resource.model';

@Injectable({
  providedIn: 'root'
})
export class CloudResourceService {
  private apiUrl = 'http://localhost:8080/api/resources';

  constructor(private http: HttpClient) {}

  getAllResources(): Observable<CloudResource[]> {
    return this.http.get<CloudResource[]>(this.apiUrl, {
      withCredentials: true
    });
  }

  getResourceById(id: number): Observable<CloudResource> {
    return this.http.get<CloudResource>(`${this.apiUrl}/${id}`, {
      withCredentials: true
    });
  }

  createResource(resource: CloudResource): Observable<CloudResource> {
    return this.http.post<CloudResource>(this.apiUrl, resource, {
      withCredentials: true
    });
  }

  updateResource(id: number, resource: CloudResource): Observable<CloudResource> {
    return this.http.put<CloudResource>(`${this.apiUrl}/${id}`, resource, {
      withCredentials: true
    });
  }

  deleteResource(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      withCredentials: true
    });
  }
}
