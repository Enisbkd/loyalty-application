import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { LoyaltyPointsDetailComponent } from './loyalty-points-detail.component';

describe('LoyaltyPoints Management Detail Component', () => {
  let comp: LoyaltyPointsDetailComponent;
  let fixture: ComponentFixture<LoyaltyPointsDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoyaltyPointsDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./loyalty-points-detail.component').then(m => m.LoyaltyPointsDetailComponent),
              resolve: { loyaltyPoints: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(LoyaltyPointsDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoyaltyPointsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loyaltyPoints on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', LoyaltyPointsDetailComponent);

      // THEN
      expect(instance.loyaltyPoints()).toEqual(expect.objectContaining({ id: 123 }));
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
