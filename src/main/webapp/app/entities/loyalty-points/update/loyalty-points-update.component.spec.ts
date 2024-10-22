import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IRiskEpurationPoints } from 'app/entities/risk-epuration-points/risk-epuration-points.model';
import { RiskEpurationPointsService } from 'app/entities/risk-epuration-points/service/risk-epuration-points.service';
import { LoyaltyPointsService } from '../service/loyalty-points.service';
import { ILoyaltyPoints } from '../loyalty-points.model';
import { LoyaltyPointsFormService } from './loyalty-points-form.service';

import { LoyaltyPointsUpdateComponent } from './loyalty-points-update.component';

describe('LoyaltyPoints Management Update Component', () => {
  let comp: LoyaltyPointsUpdateComponent;
  let fixture: ComponentFixture<LoyaltyPointsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loyaltyPointsFormService: LoyaltyPointsFormService;
  let loyaltyPointsService: LoyaltyPointsService;
  let riskEpurationPointsService: RiskEpurationPointsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LoyaltyPointsUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(LoyaltyPointsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoyaltyPointsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loyaltyPointsFormService = TestBed.inject(LoyaltyPointsFormService);
    loyaltyPointsService = TestBed.inject(LoyaltyPointsService);
    riskEpurationPointsService = TestBed.inject(RiskEpurationPointsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call riskEpurationPoints query and add missing value', () => {
      const loyaltyPoints: ILoyaltyPoints = { id: 456 };
      const riskEpurationPoints: IRiskEpurationPoints = { id: 27891 };
      loyaltyPoints.riskEpurationPoints = riskEpurationPoints;

      const riskEpurationPointsCollection: IRiskEpurationPoints[] = [{ id: 18748 }];
      jest.spyOn(riskEpurationPointsService, 'query').mockReturnValue(of(new HttpResponse({ body: riskEpurationPointsCollection })));
      const expectedCollection: IRiskEpurationPoints[] = [riskEpurationPoints, ...riskEpurationPointsCollection];
      jest.spyOn(riskEpurationPointsService, 'addRiskEpurationPointsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loyaltyPoints });
      comp.ngOnInit();

      expect(riskEpurationPointsService.query).toHaveBeenCalled();
      expect(riskEpurationPointsService.addRiskEpurationPointsToCollectionIfMissing).toHaveBeenCalledWith(
        riskEpurationPointsCollection,
        riskEpurationPoints,
      );
      expect(comp.riskEpurationPointsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loyaltyPoints: ILoyaltyPoints = { id: 456 };
      const riskEpurationPoints: IRiskEpurationPoints = { id: 10988 };
      loyaltyPoints.riskEpurationPoints = riskEpurationPoints;

      activatedRoute.data = of({ loyaltyPoints });
      comp.ngOnInit();

      expect(comp.riskEpurationPointsCollection).toContain(riskEpurationPoints);
      expect(comp.loyaltyPoints).toEqual(loyaltyPoints);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoyaltyPoints>>();
      const loyaltyPoints = { id: 123 };
      jest.spyOn(loyaltyPointsFormService, 'getLoyaltyPoints').mockReturnValue(loyaltyPoints);
      jest.spyOn(loyaltyPointsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loyaltyPoints });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loyaltyPoints }));
      saveSubject.complete();

      // THEN
      expect(loyaltyPointsFormService.getLoyaltyPoints).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loyaltyPointsService.update).toHaveBeenCalledWith(expect.objectContaining(loyaltyPoints));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoyaltyPoints>>();
      const loyaltyPoints = { id: 123 };
      jest.spyOn(loyaltyPointsFormService, 'getLoyaltyPoints').mockReturnValue({ id: null });
      jest.spyOn(loyaltyPointsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loyaltyPoints: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loyaltyPoints }));
      saveSubject.complete();

      // THEN
      expect(loyaltyPointsFormService.getLoyaltyPoints).toHaveBeenCalled();
      expect(loyaltyPointsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoyaltyPoints>>();
      const loyaltyPoints = { id: 123 };
      jest.spyOn(loyaltyPointsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loyaltyPoints });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loyaltyPointsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRiskEpurationPoints', () => {
      it('Should forward to riskEpurationPointsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(riskEpurationPointsService, 'compareRiskEpurationPoints');
        comp.compareRiskEpurationPoints(entity, entity2);
        expect(riskEpurationPointsService.compareRiskEpurationPoints).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
