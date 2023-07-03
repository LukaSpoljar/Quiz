import { TestBed } from '@angular/core/testing';

import { ClientRoutesService } from './client-routes.service';

describe('ClientRoutesService', () => {
  let service: ClientRoutesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClientRoutesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
