import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoyaltyPoints } from '../loyalty-points.model';
import { LoyaltyPointsService } from '../service/loyalty-points.service';

const loyaltyPointsResolve = (route: ActivatedRouteSnapshot): Observable<null | ILoyaltyPoints> => {
  const id = route.params.id;
  if (id) {
    return inject(LoyaltyPointsService)
      .find(id)
      .pipe(
        mergeMap((loyaltyPoints: HttpResponse<ILoyaltyPoints>) => {
          if (loyaltyPoints.body) {
            return of(loyaltyPoints.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default loyaltyPointsResolve;
