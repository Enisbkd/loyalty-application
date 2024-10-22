import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ILoyaltyPoints } from '../loyalty-points.model';
import { LoyaltyPointsService } from '../service/loyalty-points.service';

@Component({
  standalone: true,
  templateUrl: './loyalty-points-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class LoyaltyPointsDeleteDialogComponent {
  loyaltyPoints?: ILoyaltyPoints;

  protected loyaltyPointsService = inject(LoyaltyPointsService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loyaltyPointsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
