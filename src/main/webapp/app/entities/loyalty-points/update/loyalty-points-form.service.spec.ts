import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../loyalty-points.test-samples';

import { LoyaltyPointsFormService } from './loyalty-points-form.service';

describe('LoyaltyPoints Form Service', () => {
  let service: LoyaltyPointsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoyaltyPointsFormService);
  });

  describe('Service methods', () => {
    describe('createLoyaltyPointsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoyaltyPointsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            statusPoints: expect.any(Object),
            myPoints: expect.any(Object),
            riskEpurationPoints: expect.any(Object),
          }),
        );
      });

      it('passing ILoyaltyPoints should create a new form with FormGroup', () => {
        const formGroup = service.createLoyaltyPointsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            statusPoints: expect.any(Object),
            myPoints: expect.any(Object),
            riskEpurationPoints: expect.any(Object),
          }),
        );
      });
    });

    describe('getLoyaltyPoints', () => {
      it('should return NewLoyaltyPoints for default LoyaltyPoints initial value', () => {
        const formGroup = service.createLoyaltyPointsFormGroup(sampleWithNewData);

        const loyaltyPoints = service.getLoyaltyPoints(formGroup) as any;

        expect(loyaltyPoints).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoyaltyPoints for empty LoyaltyPoints initial value', () => {
        const formGroup = service.createLoyaltyPointsFormGroup();

        const loyaltyPoints = service.getLoyaltyPoints(formGroup) as any;

        expect(loyaltyPoints).toMatchObject({});
      });

      it('should return ILoyaltyPoints', () => {
        const formGroup = service.createLoyaltyPointsFormGroup(sampleWithRequiredData);

        const loyaltyPoints = service.getLoyaltyPoints(formGroup) as any;

        expect(loyaltyPoints).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoyaltyPoints should not enable id FormControl', () => {
        const formGroup = service.createLoyaltyPointsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoyaltyPoints should disable id FormControl', () => {
        const formGroup = service.createLoyaltyPointsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
