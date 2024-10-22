import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IRiskEpurationPoints } from '../risk-epuration-points.model';

@Component({
  standalone: true,
  selector: 'jhi-risk-epuration-points-detail',
  templateUrl: './risk-epuration-points-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class RiskEpurationPointsDetailComponent {
  riskEpurationPoints = input<IRiskEpurationPoints | null>(null);

  previousState(): void {
    window.history.back();
  }
}
