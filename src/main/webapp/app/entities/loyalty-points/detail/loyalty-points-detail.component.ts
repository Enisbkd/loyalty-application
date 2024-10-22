import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { ILoyaltyPoints } from '../loyalty-points.model';

@Component({
  standalone: true,
  selector: 'jhi-loyalty-points-detail',
  templateUrl: './loyalty-points-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class LoyaltyPointsDetailComponent {
  loyaltyPoints = input<ILoyaltyPoints | null>(null);

  previousState(): void {
    window.history.back();
  }
}
