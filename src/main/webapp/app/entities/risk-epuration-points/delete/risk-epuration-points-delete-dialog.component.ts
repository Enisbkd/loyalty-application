import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRiskEpurationPoints } from '../risk-epuration-points.model';
import { RiskEpurationPointsService } from '../service/risk-epuration-points.service';

@Component({
  standalone: true,
  templateUrl: './risk-epuration-points-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RiskEpurationPointsDeleteDialogComponent {
  riskEpurationPoints?: IRiskEpurationPoints;

  protected riskEpurationPointsService = inject(RiskEpurationPointsService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.riskEpurationPointsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
