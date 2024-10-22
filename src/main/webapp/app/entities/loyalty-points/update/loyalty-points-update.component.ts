import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRiskEpurationPoints } from 'app/entities/risk-epuration-points/risk-epuration-points.model';
import { RiskEpurationPointsService } from 'app/entities/risk-epuration-points/service/risk-epuration-points.service';
import { ILoyaltyPoints } from '../loyalty-points.model';
import { LoyaltyPointsService } from '../service/loyalty-points.service';
import { LoyaltyPointsFormGroup, LoyaltyPointsFormService } from './loyalty-points-form.service';

@Component({
  standalone: true,
  selector: 'jhi-loyalty-points-update',
  templateUrl: './loyalty-points-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class LoyaltyPointsUpdateComponent implements OnInit {
  isSaving = false;
  loyaltyPoints: ILoyaltyPoints | null = null;

  riskEpurationPointsCollection: IRiskEpurationPoints[] = [];

  protected loyaltyPointsService = inject(LoyaltyPointsService);
  protected loyaltyPointsFormService = inject(LoyaltyPointsFormService);
  protected riskEpurationPointsService = inject(RiskEpurationPointsService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: LoyaltyPointsFormGroup = this.loyaltyPointsFormService.createLoyaltyPointsFormGroup();

  compareRiskEpurationPoints = (o1: IRiskEpurationPoints | null, o2: IRiskEpurationPoints | null): boolean =>
    this.riskEpurationPointsService.compareRiskEpurationPoints(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loyaltyPoints }) => {
      this.loyaltyPoints = loyaltyPoints;
      if (loyaltyPoints) {
        this.updateForm(loyaltyPoints);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loyaltyPoints = this.loyaltyPointsFormService.getLoyaltyPoints(this.editForm);
    if (loyaltyPoints.id !== null) {
      this.subscribeToSaveResponse(this.loyaltyPointsService.update(loyaltyPoints));
    } else {
      this.subscribeToSaveResponse(this.loyaltyPointsService.create(loyaltyPoints));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoyaltyPoints>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(loyaltyPoints: ILoyaltyPoints): void {
    this.loyaltyPoints = loyaltyPoints;
    this.loyaltyPointsFormService.resetForm(this.editForm, loyaltyPoints);

    this.riskEpurationPointsCollection = this.riskEpurationPointsService.addRiskEpurationPointsToCollectionIfMissing<IRiskEpurationPoints>(
      this.riskEpurationPointsCollection,
      loyaltyPoints.riskEpurationPoints,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.riskEpurationPointsService
      .query({ filter: 'loyaltypoints-is-null' })
      .pipe(map((res: HttpResponse<IRiskEpurationPoints[]>) => res.body ?? []))
      .pipe(
        map((riskEpurationPoints: IRiskEpurationPoints[]) =>
          this.riskEpurationPointsService.addRiskEpurationPointsToCollectionIfMissing<IRiskEpurationPoints>(
            riskEpurationPoints,
            this.loyaltyPoints?.riskEpurationPoints,
          ),
        ),
      )
      .subscribe((riskEpurationPoints: IRiskEpurationPoints[]) => (this.riskEpurationPointsCollection = riskEpurationPoints));
  }
}
