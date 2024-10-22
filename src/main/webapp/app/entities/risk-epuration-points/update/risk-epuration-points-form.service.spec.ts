import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../risk-epuration-points.test-samples';

import { RiskEpurationPointsFormService } from './risk-epuration-points-form.service';

describe('RiskEpurationPoints Form Service', () => {
  let service: RiskEpurationPointsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RiskEpurationPointsFormService);
  });

  describe('Service methods', () => {
    describe('createRiskEpurationPointsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRiskEpurationPointsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            points: expect.any(Object),
            validUntil: expect.any(Object),
          }),
        );
      });

      it('passing IRiskEpurationPoints should create a new form with FormGroup', () => {
        const formGroup = service.createRiskEpurationPointsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            points: expect.any(Object),
            validUntil: expect.any(Object),
          }),
        );
      });
    });

    describe('getRiskEpurationPoints', () => {
      it('should return NewRiskEpurationPoints for default RiskEpurationPoints initial value', () => {
        const formGroup = service.createRiskEpurationPointsFormGroup(sampleWithNewData);

        const riskEpurationPoints = service.getRiskEpurationPoints(formGroup) as any;

        expect(riskEpurationPoints).toMatchObject(sampleWithNewData);
      });

      it('should return NewRiskEpurationPoints for empty RiskEpurationPoints initial value', () => {
        const formGroup = service.createRiskEpurationPointsFormGroup();

        const riskEpurationPoints = service.getRiskEpurationPoints(formGroup) as any;

        expect(riskEpurationPoints).toMatchObject({});
      });

      it('should return IRiskEpurationPoints', () => {
        const formGroup = service.createRiskEpurationPointsFormGroup(sampleWithRequiredData);

        const riskEpurationPoints = service.getRiskEpurationPoints(formGroup) as any;

        expect(riskEpurationPoints).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRiskEpurationPoints should not enable id FormControl', () => {
        const formGroup = service.createRiskEpurationPointsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRiskEpurationPoints should disable id FormControl', () => {
        const formGroup = service.createRiskEpurationPointsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
