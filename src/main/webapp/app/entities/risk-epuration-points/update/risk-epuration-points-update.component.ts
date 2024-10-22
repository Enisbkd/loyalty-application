import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRiskEpurationPoints } from '../risk-epuration-points.model';
import { RiskEpurationPointsService } from '../service/risk-epuration-points.service';
import { RiskEpurationPointsFormGroup, RiskEpurationPointsFormService } from './risk-epuration-points-form.service';

@Component({
  standalone: true,
  selector: 'jhi-risk-epuration-points-update',
  templateUrl: './risk-epuration-points-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RiskEpurationPointsUpdateComponent implements OnInit {
  isSaving = false;
  riskEpurationPoints: IRiskEpurationPoints | null = null;

  protected riskEpurationPointsService = inject(RiskEpurationPointsService);
  protected riskEpurationPointsFormService = inject(RiskEpurationPointsFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RiskEpurationPointsFormGroup = this.riskEpurationPointsFormService.createRiskEpurationPointsFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ riskEpurationPoints }) => {
      this.riskEpurationPoints = riskEpurationPoints;
      if (riskEpurationPoints) {
        this.updateForm(riskEpurationPoints);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const riskEpurationPoints = this.riskEpurationPointsFormService.getRiskEpurationPoints(this.editForm);
    if (riskEpurationPoints.id !== null) {
      this.subscribeToSaveResponse(this.riskEpurationPointsService.update(riskEpurationPoints));
    } else {
      this.subscribeToSaveResponse(this.riskEpurationPointsService.create(riskEpurationPoints));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRiskEpurationPoints>>): void {
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

  protected updateForm(riskEpurationPoints: IRiskEpurationPoints): void {
    this.riskEpurationPoints = riskEpurationPoints;
    this.riskEpurationPointsFormService.resetForm(this.editForm, riskEpurationPoints);
  }
}
