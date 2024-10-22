import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { RiskEpurationPointsService } from '../service/risk-epuration-points.service';
import { IRiskEpurationPoints } from '../risk-epuration-points.model';
import { RiskEpurationPointsFormService } from './risk-epuration-points-form.service';

import { RiskEpurationPointsUpdateComponent } from './risk-epuration-points-update.component';

describe('RiskEpurationPoints Management Update Component', () => {
  let comp: RiskEpurationPointsUpdateComponent;
  let fixture: ComponentFixture<RiskEpurationPointsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let riskEpurationPointsFormService: RiskEpurationPointsFormService;
  let riskEpurationPointsService: RiskEpurationPointsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RiskEpurationPointsUpdateComponent],
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
      .overrideTemplate(RiskEpurationPointsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RiskEpurationPointsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    riskEpurationPointsFormService = TestBed.inject(RiskEpurationPointsFormService);
    riskEpurationPointsService = TestBed.inject(RiskEpurationPointsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const riskEpurationPoints: IRiskEpurationPoints = { id: 456 };

      activatedRoute.data = of({ riskEpurationPoints });
      comp.ngOnInit();

      expect(comp.riskEpurationPoints).toEqual(riskEpurationPoints);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRiskEpurationPoints>>();
      const riskEpurationPoints = { id: 123 };
      jest.spyOn(riskEpurationPointsFormService, 'getRiskEpurationPoints').mockReturnValue(riskEpurationPoints);
      jest.spyOn(riskEpurationPointsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ riskEpurationPoints });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: riskEpurationPoints }));
      saveSubject.complete();

      // THEN
      expect(riskEpurationPointsFormService.getRiskEpurationPoints).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(riskEpurationPointsService.update).toHaveBeenCalledWith(expect.objectContaining(riskEpurationPoints));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRiskEpurationPoints>>();
      const riskEpurationPoints = { id: 123 };
      jest.spyOn(riskEpurationPointsFormService, 'getRiskEpurationPoints').mockReturnValue({ id: null });
      jest.spyOn(riskEpurationPointsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ riskEpurationPoints: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: riskEpurationPoints }));
      saveSubject.complete();

      // THEN
      expect(riskEpurationPointsFormService.getRiskEpurationPoints).toHaveBeenCalled();
      expect(riskEpurationPointsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRiskEpurationPoints>>();
      const riskEpurationPoints = { id: 123 };
      jest.spyOn(riskEpurationPointsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ riskEpurationPoints });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(riskEpurationPointsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
