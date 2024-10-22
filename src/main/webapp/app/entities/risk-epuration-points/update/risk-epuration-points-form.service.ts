import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRiskEpurationPoints, NewRiskEpurationPoints } from '../risk-epuration-points.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRiskEpurationPoints for edit and NewRiskEpurationPointsFormGroupInput for create.
 */
type RiskEpurationPointsFormGroupInput = IRiskEpurationPoints | PartialWithRequiredKeyOf<NewRiskEpurationPoints>;

type RiskEpurationPointsFormDefaults = Pick<NewRiskEpurationPoints, 'id'>;

type RiskEpurationPointsFormGroupContent = {
  id: FormControl<IRiskEpurationPoints['id'] | NewRiskEpurationPoints['id']>;
  points: FormControl<IRiskEpurationPoints['points']>;
  validUntil: FormControl<IRiskEpurationPoints['validUntil']>;
};

export type RiskEpurationPointsFormGroup = FormGroup<RiskEpurationPointsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RiskEpurationPointsFormService {
  createRiskEpurationPointsFormGroup(riskEpurationPoints: RiskEpurationPointsFormGroupInput = { id: null }): RiskEpurationPointsFormGroup {
    const riskEpurationPointsRawValue = {
      ...this.getFormDefaults(),
      ...riskEpurationPoints,
    };
    return new FormGroup<RiskEpurationPointsFormGroupContent>({
      id: new FormControl(
        { value: riskEpurationPointsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      points: new FormControl(riskEpurationPointsRawValue.points),
      validUntil: new FormControl(riskEpurationPointsRawValue.validUntil),
    });
  }

  getRiskEpurationPoints(form: RiskEpurationPointsFormGroup): IRiskEpurationPoints | NewRiskEpurationPoints {
    return form.getRawValue() as IRiskEpurationPoints | NewRiskEpurationPoints;
  }

  resetForm(form: RiskEpurationPointsFormGroup, riskEpurationPoints: RiskEpurationPointsFormGroupInput): void {
    const riskEpurationPointsRawValue = { ...this.getFormDefaults(), ...riskEpurationPoints };
    form.reset(
      {
        ...riskEpurationPointsRawValue,
        id: { value: riskEpurationPointsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RiskEpurationPointsFormDefaults {
    return {
      id: null,
    };
  }
}
