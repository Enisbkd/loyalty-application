import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RiskEpurationPointsDetailComponent } from './risk-epuration-points-detail.component';

describe('RiskEpurationPoints Management Detail Component', () => {
  let comp: RiskEpurationPointsDetailComponent;
  let fixture: ComponentFixture<RiskEpurationPointsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiskEpurationPointsDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./risk-epuration-points-detail.component').then(m => m.RiskEpurationPointsDetailComponent),
              resolve: { riskEpurationPoints: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RiskEpurationPointsDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RiskEpurationPointsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load riskEpurationPoints on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RiskEpurationPointsDetailComponent);

      // THEN
      expect(instance.riskEpurationPoints()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
