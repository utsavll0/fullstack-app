import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CloudResourceService } from '../../services/cloud-resource.service';
import { CloudResource } from '../../models/cloud-resource.model';
import { LoginResponse } from '../../models/auth.model';

@Component({
    selector: 'app-dashboard',
    imports: [CommonModule, FormsModule],
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  resources: CloudResource[] = [];
  filteredResources: CloudResource[] = [];
  currentUser: LoginResponse | null = null;
  isLoading: boolean = false;
  errorMessage: string = '';
  
  // Form for adding/editing resources
  showForm: boolean = false;
  isEditing: boolean = false;
  currentResource: CloudResource = this.getEmptyResource();
  
  // Filters
  filterStatus: string = 'all';
  filterType: string = 'all';
  searchTerm: string = '';

  constructor(
    private authService: AuthService,
    private cloudResourceService: CloudResourceService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    if (!this.currentUser?.success) {
      this.router.navigate(['/login']);
      return;
    }
    this.loadResources();
  }

  loadResources(): void {
    this.isLoading = true;
    this.cloudResourceService.getAllResources().subscribe({
      next: (data) => {
        this.resources = data;
        this.applyFilters();
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Failed to load resources';
        this.isLoading = false;
      }
    });
  }

  applyFilters(): void {
    this.filteredResources = this.resources.filter(resource => {
      const matchesStatus = this.filterStatus === 'all' || 
        resource.status.toLowerCase() === this.filterStatus.toLowerCase();
      const matchesType = this.filterType === 'all' || 
        resource.resourceType.toLowerCase() === this.filterType.toLowerCase();
      const matchesSearch = !this.searchTerm || 
        resource.resourceName.toLowerCase().includes(this.searchTerm.toLowerCase());
      
      return matchesStatus && matchesType && matchesSearch;
    });
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  getResourceTypes(): string[] {
    const types = new Set(this.resources.map(r => r.resourceType));
    return Array.from(types);
  }

  getResourceStatuses(): string[] {
    const statuses = new Set(this.resources.map(r => r.status));
    return Array.from(statuses);
  }

  showAddForm(): void {
    this.currentResource = this.getEmptyResource();
    this.isEditing = false;
    this.showForm = true;
  }

  showEditForm(resource: CloudResource): void {
    this.currentResource = { ...resource };
    this.isEditing = true;
    this.showForm = true;
  }

  cancelForm(): void {
    this.showForm = false;
    this.currentResource = this.getEmptyResource();
  }

  saveResource(): void {
    if (this.isEditing && this.currentResource.id) {
      this.cloudResourceService.updateResource(this.currentResource.id, this.currentResource)
        .subscribe({
          next: () => {
            this.loadResources();
            this.cancelForm();
          },
          error: (error) => {
            this.errorMessage = 'Failed to update resource';
          }
        });
    } else {
      this.cloudResourceService.createResource(this.currentResource)
        .subscribe({
          next: () => {
            this.loadResources();
            this.cancelForm();
          },
          error: (error) => {
            this.errorMessage = 'Failed to create resource';
          }
        });
    }
  }

  deleteResource(id: number | undefined): void {
    if (!id || !confirm('Are you sure you want to delete this resource?')) {
      return;
    }

    this.cloudResourceService.deleteResource(id).subscribe({
      next: () => {
        this.loadResources();
      },
      error: (error) => {
        this.errorMessage = 'Failed to delete resource';
      }
    });
  }

  getStatusClass(status: string): string {
    const statusLower = status.toLowerCase();
    if (statusLower === 'running' || statusLower === 'active') {
      return 'status-running';
    } else if (statusLower === 'stopped') {
      return 'status-stopped';
    }
    return 'status-unknown';
  }

  getUsageClass(usage: number): string {
    if (usage >= 80) return 'usage-high';
    if (usage >= 50) return 'usage-medium';
    return 'usage-low';
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => {
        this.router.navigate(['/login']);
      }
    });
  }

  private getEmptyResource(): CloudResource {
    return {
      resourceName: '',
      resourceType: 'EC2 Instance',
      status: 'Running',
      region: 'us-east-1',
      cpuUsage: 0,
      memoryUsage: 0,
      diskUsage: 0
    };
  }
}
