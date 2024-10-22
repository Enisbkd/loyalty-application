import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ILoyaltyPoints, NewLoyaltyPoints } from '../loyalty-points.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoyaltyPoints for edit and NewLoyaltyPointsFormGroupInput for create.
 */
type LoyaltyPointsFormGroupInput = ILoyaltyPoints | PartialWithRequiredKeyOf<NewLoyaltyPoints>;

type LoyaltyPointsFormDefaults = Pick<NewLoyaltyPoints, 'id'>;

type LoyaltyPointsFormGroupContent = {
  id: FormControl<ILoyaltyPoints['id'] | NewLoyaltyPoints['id']>;
  statusPoints: FormControl<ILoyaltyPoints['statusPoints']>;
  myPoints: FormControl<ILoyaltyPoints['myPoints']>;
  riskEpurationPoints: FormControl<ILoyaltyPoints['riskEpurationPoints']>;
};

export type LoyaltyPointsFormGroup = FormGroup<LoyaltyPointsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoyaltyPointsFormService {
  createLoyaltyPointsFormGroup(loyaltyPoints: LoyaltyPointsFormGroupInput = { id: null }): LoyaltyPointsFormGroup {
    const loyaltyPointsRawValue = {
      ...this.getFormDefaults(),
      ...loyaltyPoints,
    };
    return new FormGroup<LoyaltyPointsFormGroupContent>({
      id: new FormControl(
        { value: loyaltyPointsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      statusPoints: new FormControl(loyaltyPointsRawValue.statusPoints),
      myPoints: new FormControl(loyaltyPointsRawValue.myPoints),
      riskEpurationPoints: new FormControl(loyaltyPointsRawValue.riskEpurationPoints),
    });
  }

  getLoyaltyPoints(form: LoyaltyPointsFormGroup): ILoyaltyPoints | NewLoyaltyPoints {
    return form.getRawValue() as ILoyaltyPoints | NewLoyaltyPoints;
  }

  resetForm(form: LoyaltyPointsFormGroup, loyaltyPoints: LoyaltyPointsFormGroupInput): void {
    const loyaltyPointsRawValue = { ...this.getFormDefaults(), ...loyaltyPoints };
    form.reset(
      {
        ...loyaltyPointsRawValue,
        id: { value: loyaltyPointsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): LoyaltyPointsFormDefaults {
    return {
      id: null,
    };
  }
}
