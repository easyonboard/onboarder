import { TestBed, inject } from '@angular/core/testing';

import { CommonComponentsService } from './common-components.service';

describe('CommonComponentsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommonComponentsService]
    });
  });

  it('should be created', inject([CommonComponentsService], (service: CommonComponentsService) => {
    expect(service).toBeTruthy();
  }));
});
