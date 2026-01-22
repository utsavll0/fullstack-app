export interface CloudResource {
  id?: number;
  resourceName: string;
  resourceType: string;
  status: string;
  region: string;
  cpuUsage: number;
  memoryUsage: number;
  diskUsage: number;
  lastUpdated?: string;
  createdAt?: string;
}
